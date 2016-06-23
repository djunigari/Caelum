package br.com.djun.boaviagem.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

import java.util.Arrays;
import java.util.List;

import br.com.djun.boaviagem.Constantes;
import br.com.djun.boaviagem.listeners.AnotacaoListener;

public class ViagemListFragment extends ListFragment implements OnItemClickListener{
    private AnotacaoListener callback;

    @Override
    public void onStart() {
        super.onStart();
        List<String> viagens = Arrays.asList("Campo Grande", "Sao Paulo", "Miami");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, viagens);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String viagem  = (String) getListAdapter().getItem(i);
        Bundle bundle = new Bundle();
        bundle.putString(Constantes.VIAGEM_SELECIONADA, viagem);
        callback.viagemSelecionada(bundle);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callback = (AnotacaoListener) activity;
    }
}
