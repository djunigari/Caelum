package br.com.djun.boaviagem.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import br.com.djun.boaviagem.R;
import br.com.djun.boaviagem.domain.Anotacao;

/**
 * Created by djunigari on 20/06/16.
 */
public class AnotacaoListFragment extends ListFragment implements AdapterView.OnItemClickListener,View.OnClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lista_anotacoes,container,false);
    }

    @Override
    public void onStart() {
        super.onStart();
        List<Anotacao> anotacoes = listarAnotacoes();
        ArrayAdapter<Anotacao> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_expandable_list_item_1, anotacoes);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
        Button button = (Button) getActivity().findViewById(R.id.nova_anotacao);
        button.setOnClickListener(this);
    }

    private List<Anotacao> listarAnotacoes() {
        ArrayList<Anotacao> anotacoes = new ArrayList<>();
        for(int i=1;i<=20;i++){
            Anotacao anotacao = new Anotacao();
            anotacao.setDia(i);
            anotacao.setTitulo("Anotacao "+i);
            anotacao.setDescricao("Descrição "+i);
            anotacoes.add(anotacao);
        }
        return anotacoes;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onClick(View view) {

    }
}
