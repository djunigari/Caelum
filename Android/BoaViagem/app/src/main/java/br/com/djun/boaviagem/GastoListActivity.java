package br.com.djun.boaviagem;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.djun.boaviagem.domain.Gasto;
import br.com.djun.boaviagem.repositories.GastoRepository;

public class GastoListActivity extends ListActivity implements AdapterView.OnItemClickListener {
    private	List<Map<String,Object>> gastos;
    private GastoRepository gastoRepository;
    private String dataAnterior = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getListView().setOnItemClickListener(this);
        gastoRepository = new GastoRepository(this);
        Long viagemId = getIntent().getLongExtra(Constantes.VIAGEM_ID,-1);

        String[] de	= {"data","descricao","valor","categoria"};
        int[] para = {R.id.dataTextView,R.id.descricaoTextView,R.id.valorTextView,R.id.categoriaLinearLayout};
        SimpleAdapter adapter = new SimpleAdapter(this, listarGastos(viagemId), R.layout.lista_gasto, de, para);
        adapter.setViewBinder(new GastoViewBinder());
        setListAdapter(adapter);
        registerForContextMenu(getListView());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        openContextMenu(view);//Sem esse metodo o default é onPress
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.gasto_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.remover){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            gastos.remove(info.position);
            getListView().invalidateViews();
            dataAnterior = "";
            return true;
        }
        return super.onContextItemSelected(item);
    }

    private List<Map<String,Object>> listarGastos(Long id) {
        gastos = new ArrayList<>();
        List<Gasto> list;
        if(id > -1){
            list = gastoRepository.getGastosByViagemId(id);
        }else{
            list = gastoRepository.getGastos();
        }

        Map<String,	Object>	item;
        for(Gasto g : list){
            item = new HashMap<>();
            item.put("data","04/02/2012");
            item.put("descricao","Diária Hotel");
            item.put("valor","R$:260,00");
            item.put("categoria",R.color.categoria_hospedagem);
            gastos.add(item);
        }
        return gastos;
    }

    private class GastoViewBinder implements SimpleAdapter.ViewBinder{
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
