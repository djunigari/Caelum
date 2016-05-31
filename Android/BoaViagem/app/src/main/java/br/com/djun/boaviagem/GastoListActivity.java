package br.com.djun.boaviagem;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GastoListActivity extends ListActivity implements AdapterView.OnItemClickListener {
    private	List<Map<String,Object>> gastos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getListView().setOnItemClickListener(this);

        String[] de	= {"data","descricao","valor","categoria"};
        int[] para = {R.id.dataTextView,R.id.descricaoTextView,R.id.valorTextView,R.id.categoriaLinearLayout};

        SimpleAdapter adapter = new SimpleAdapter(this, listarGastos(), R.layout.lista_gasto, de, para);
        adapter.setViewBinder(new GastoViewBinder());
        setListAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Map<String, Object> map = gastos.get(position);
        String descricao = (String)map.get("descricao");
        Toast.makeText(this,"Gasto selecionado: "+descricao,Toast.LENGTH_SHORT).show();
    }

    private List<Map<String,Object>> listarGastos() {
        Map<String,	Object>	item = new HashMap<>();
        item.put("data","04/02/2012");
        item.put("descricao","Diária Hotel");
        item.put("valor","R$:260,00");
        item.put("categoria",R.color.categoria_hospedagem);
        gastos.add(item);
        item = new HashMap<>();
        item.put("data","04/02/2012");
        item.put("descricao","Almoço");
        item.put("valor","R$:30,00");
        item.put("categoria",R.color.categoria_alimentacao);
        gastos.add(item);
        item = new HashMap<>();
        item.put("data","05/02/2012");
        item.put("descricao","Diária Hotel");
        item.put("valor","R$:260,00");
        item.put("categoria",R.color.categoria_hospedagem);
        gastos.add(item);
        item = new HashMap<>();
        item.put("data","04/02/2012");
        item.put("descricao","Janta");
        item.put("valor","R$:30,00");
        item.put("categoria",R.color.categoria_alimentacao);
        gastos.add(item);
        return gastos;
    }

    private class GastoViewBinder implements SimpleAdapter.ViewBinder{
        private String dataAnterior = "";
        @Override
        public boolean setViewValue(View view, Object data, String textRepresentation) {
            if(view.getId() == R.id.dataTextView){
                if(!dataAnterior.equals(data)){
                    TextView textView = (TextView) view;
                    textView.setText(textRepresentation);
                    dataAnterior = textRepresentation;
                    view.setVisibility(View.VISIBLE);
                }else{
                    view.setVisibility(View.GONE);
                }
                return true;
            }
            if(view.getId() == R.id.categoriaLinearLayout){
                Integer id = (Integer) data;
                view.setBackgroundColor(getResources().getColor(id));
                return true;
            }
            return false;
        }
    }
}
