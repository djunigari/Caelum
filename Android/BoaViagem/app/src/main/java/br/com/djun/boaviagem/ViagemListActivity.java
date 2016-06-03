package br.com.djun.boaviagem;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.djun.boaviagem.domain.Viagem;
import br.com.djun.boaviagem.repositories.ViagemRepository;

public class ViagemListActivity extends ListActivity implements AdapterView.OnItemClickListener,DialogInterface.OnClickListener,SimpleAdapter.ViewBinder {
    private	List<Map<String,Object>> viagens;
    private int posicaoViagem;
    private AlertDialog alertDialog;
    private AlertDialog dialogConfirmacao;
    private Double valorLimite;
    private ViagemRepository viagemRepository;
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viagemRepository = new ViagemRepository(this);
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        getListView().setOnItemClickListener(this);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        valorLimite = Double.valueOf(preferences.getString("valor_limite","-1"));

        String[] de = {"imagem","destino","data","total","barraProgresso"};
        int[] para = {R.id.tipoViagemImageView,
                R.id.destinoTextView,
                R.id.dataTextView,
                R.id.valorTextView,
                R.id.barraProgresso};
        SimpleAdapter adapter = new SimpleAdapter(this, listarViagens(), R.layout.lista_viagem, de, para);
        setListAdapter(adapter);
        adapter.setViewBinder(this);
        alertDialog = createAlertDialog();
        dialogConfirmacao = createAlertDialogConfirmacao();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        posicaoViagem = position;
        alertDialog.show();
    }

    private List<Map<String,Object>> listarViagens() {
        viagens = new ArrayList<>();
        List<Viagem> list = viagemRepository.listarViagens();
        Map<String, Object> item;
        for(Viagem v : list){
            String	periodo	= dateFormat.format(v.getDataChegada())	+ " a " + dateFormat.format(v.getDataSaida());
            item = new HashMap<String, Object>();
            item.put("id",v.getId());
            if(v.getTipoViagem() == Constantes.VIAGEM_LAZER){
                item.put("imagem", R.drawable.lazer);
            }else{
                item.put("imagem", R.drawable.negocios);
            }
            item.put("destino", v.getDestino());
            item.put("data", periodo);
            double	totalGasto	=	viagemRepository.getTotalGastoByViagemId(v.getId());
            double	alerta	=	v.getOrcamento()*valorLimite/100;
            item.put("total", "Gasto total R$:"+totalGasto);
            item.put("barraProgresso", new Double[]{v.getOrcamento(),alerta,totalGasto});
            viagens.add(item);
        }
        return viagens;
    }


    private AlertDialog createAlertDialog(){
        final CharSequence[] items= {
                getString(R.string.editar),
                getString(R.string.novo_gasto),
                getString(R.string.gastos_realizados),
                getString(R.string.remover)
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.opcoes);
        builder.setItems(items,this);
        return builder.create();
    }

    private AlertDialog createAlertDialogConfirmacao(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.confirmacao_exclusao_viagem);
        builder.setPositiveButton(getString(R.string.sim),this);
        builder.setNegativeButton(getString(R.string.nao),this);
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int item) {
        Long id = (Long) viagens.get(posicaoViagem).get("id");
        String destino = (String) viagens.get(posicaoViagem).get("destino");
        Intent intent;
        switch (item){
            case 0:
                intent = new Intent(this, ViagemActivity.class);
                intent.putExtra(Constantes.VIAGEM_ID,id);
                startActivity(intent);
                break;
            case 1:
                intent = new Intent(this,GastoActivity.class);
                intent.putExtra(Constantes.VIAGEM_ID,id);
                intent.putExtra(Constantes.VIAGEM_DESTINO,destino);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(this,GastoListActivity.class);
                intent.putExtra(Constantes.VIAGEM_ID,id);
                startActivity(intent);
                break;
            case 3:
                dialogConfirmacao.show();
                break;
            case AlertDialog.BUTTON_POSITIVE:
                viagens.remove(posicaoViagem);
                viagemRepository.removerViagem(id);
                getListView().invalidateViews();
                break;
            case AlertDialog.BUTTON_NEGATIVE:
                dialogConfirmacao.dismiss();
                break;

        }
    }

    @Override
    public boolean setViewValue(View view, Object data, String textRepresentation) {
        if(view.getId() == R.id.barraProgresso){
            ProgressBar progressBar = (ProgressBar) view;
            Double valores[]  = (Double[]) data;
            progressBar.setMax(valores[0].intValue());
            progressBar.setSecondaryProgress(valores[1].intValue());
            progressBar.setProgress(valores[2].intValue());
            return true;
        }
        return false;
    }
}
