package br.com.djun.boaviagem.fragments;

import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by djunigari on 19/06/16.
 */
public class ViagemListFragment extends ListFragment implements OnItemClickListener{
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

    }
}
