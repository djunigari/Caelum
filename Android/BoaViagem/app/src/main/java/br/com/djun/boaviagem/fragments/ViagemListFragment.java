package br.com.djun.boaviagem.fragments;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import br.com.djun.boaviagem.Constantes;
import br.com.djun.boaviagem.listeners.AnotacaoListener;
import br.com.djun.boaviagem.provider.BoaViagemContract;

public class ViagemListFragment extends ListFragment implements OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor>{
    private AnotacaoListener callback;
    private SimpleCursorAdapter adapter;

    @Override
    public void onStart() {
        super.onStart();
        adapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_expandable_list_item_1, null,
                new String[]{BoaViagemContract.Viagem.DESTINO},
                new int[]{android.R.id.text1}, 0);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        long viagem = getListAdapter().getItemId(i);
        Bundle bundle = new Bundle();
        bundle.putLong(Constantes.VIAGEM_SELECIONADA,viagem);
        callback.viagemSelecionada(bundle);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callback = (AnotacaoListener) activity;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0,null,this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = BoaViagemContract.Viagem.CONTENT_URI;
        String[] projection = {BoaViagemContract.Viagem._ID, BoaViagemContract.Viagem.DESTINO};

        return new CursorLoader(getActivity(),uri,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
