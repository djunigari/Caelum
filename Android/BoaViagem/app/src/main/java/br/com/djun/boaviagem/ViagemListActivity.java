package br.com.djun.boaviagem;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViagemListActivity extends ListActivity implements AdapterView.OnItemClickListener,DialogInterface.OnClickListener,SimpleAdapter.ViewBinder {
    private	List<Map<String,Object>> viagens;
    private int posicaoViagem;
    private AlertDialog alertDialog;
    private AlertDialog dialogConfirmacao;
    private DatabaseHelper helper;
    private SimpleDateFormat dateFormat;
    private Double valorLimite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getListView().setOnItemClickListener(this);

        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        helper = new DatabaseHelper(this);
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
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT _id, tipo_viagem, destino, data_chegada,data_saida,orcamento FROM viagem", null);
        cursor.moveToFirst();
        viagens = new ArrayList<>();
        for(int i = 0; i<cursor.getCount();i++) {
            String	id	=	cursor.getString(0);
            int	tipoViagem	=	cursor.getInt(1);
            String	destino	=	cursor.getString(2);
            long	dataChegada	=	cursor.getLong(3);
            long	dataSaida	=	cursor.getLong(4);
            double	orcamento	=	cursor.getDouble(5);
            String	periodo	=	dateFormat.format(new Date(dataChegada))	+
                    "	a	"	+	dateFormat.format(new	Date(dataSaida));
            double	totalGasto	=	calcularTotalGasto(db,	id);
            double	alerta	=	orcamento	*	valorLimite	/	100;

            Map<String, Object> item = new HashMap<String, Object>();
            item.put("id",id);
            if(tipoViagem == R.drawable.lazer){
                item.put("imagem", R.drawable.lazer);
            }else{
                item.put("imagem", R.drawable.negocios);
            }
            item.put("destino", destino);
            item.put("data", periodo);
            item.put("total", "Gasto total R$:"+totalGasto);
            item.put("barraProgresso", new Double[]{orcamento,alerta,totalGasto});
            viagens.add(item);

            cursor.moveToNext();
        }
        cursor.close();
        return viagens;
    }

    private double calcularTotalGasto(SQLiteDatabase db, String id) {
        Cursor cursor = db.rawQuery("SELECT SUM(valor) FROM gasto WHERE viagem_id = ?", new String[]{id});
        cursor.moveToFirst();
        double total = cursor.getDouble(0);
        cursor.close();
        return total;

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
        String id = (String) viagens.get(posicaoViagem).get("id");
        switch (item){
            case 0:
                Intent intent = new Intent(this, ViagemActivity.class);
                intent.putExtra(Constantes.VIAGEM_ID,id);
                startActivity(intent);
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
