package br.com.djun.boaviagem;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViagemListActivity extends ListActivity implements AdapterView.OnItemClickListener,DialogInterface.OnClickListener {
    private	List<Map<String,Object>> viagens = new ArrayList<>();
    private int posicaoViagem;
    private AlertDialog alertDialog;
    private AlertDialog dialogConfirmacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getListView().setOnItemClickListener(this);

        String[] de = {"imagem","destino","data","total"};
        int[] para = {R.id.tipoViagemImageView,
                R.id.destinoTextView,
                R.id.dataTextView,
                R.id.valorTextView};
        setListAdapter(new SimpleAdapter(this, listarViagens(),R.layout.lista_viagem, de, para));
        alertDialog = createAlertDialog();
        dialogConfirmacao = createAlertDialogConfirmacao();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        posicaoViagem = position;
        alertDialog.show();
    }

    private List<Map<String,Object>> listarViagens() {
        Map<String,Object> item = new HashMap<String, Object>();
        item.put("imagem", R.drawable.negocios);
        item.put("destino","São Paulo");
        item.put("data","02/02/2012	a 04/02/2012");
        item.put("total","Gasto total R$:314,98");
        viagens.add(item);
        item = new HashMap<String,	Object>();
        item.put("imagem", R.drawable.lazer);
        item.put("destino", "Maceió");
        item.put("data","14/05/2012	a 22/05/2012");
        item.put("total","Gasto	total R$:25834,67");
        viagens.add(item);
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
        switch (item){
            case 0:
                startActivity(new Intent(this,ViagemActivity.class));
                break;
            case 1:
                startActivity(new Intent(this,GastoActivity.class));
                break;
            case 2:
                startActivity(new Intent(this,GastoListActivity.class));
                break;
            case 3:
                dialogConfirmacao.show();
                break;
            case AlertDialog.BUTTON_POSITIVE:
                viagens.remove(posicaoViagem);
                getListView().invalidateViews();
                break;
            case AlertDialog.BUTTON_NEGATIVE:
                dialogConfirmacao.dismiss();
                break;

        }
    }
}
