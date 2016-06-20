package br.com.djun.boaviagem.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import br.com.djun.boaviagem.R;
import br.com.djun.boaviagem.fragments.ViagemListFragment;

/**
 * Created by djunigari on 19/06/16.
 */
public class AnotacaoActivity extends FragmentActivity {
    private boolean tablet = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anotacoes);
        View view = (View)findViewById(R.id.fragment_unico);

        if(view != null){
            tablet = false;
            ViagemListFragment fragment = new ViagemListFragment();
            fragment.setArguments(savedInstanceState);
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.fragment_unico,fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
