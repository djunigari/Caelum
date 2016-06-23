package br.com.djun.boaviagem.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import br.com.djun.boaviagem.R;
import br.com.djun.boaviagem.domain.Anotacao;
import br.com.djun.boaviagem.fragments.AnotacaoFragment;
import br.com.djun.boaviagem.fragments.AnotacaoListFragment;
import br.com.djun.boaviagem.fragments.ViagemListFragment;
import br.com.djun.boaviagem.listeners.AnotacaoListener;

/**
 * Created by djunigari on 19/06/16.
 */
public class AnotacaoActivity extends FragmentActivity implements AnotacaoListener{
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

    @Override
    public void viagemSelecionada(Bundle bundle) {
        FragmentManager manager = getSupportFragmentManager();
        AnotacaoListFragment fragment;

        if(tablet){
            fragment = (AnotacaoListFragment) manager.findFragmentById(R.id.fragment_anotacoes);
            fragment.listarAnotacoesPorViagem(bundle);
        }else{
            fragment = new AnotacaoListFragment();
            fragment.setArguments(bundle);

            manager.beginTransaction()
                    .replace(R.id.fragment_unico, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void anotacaoSelecionada(Anotacao anotacao) {
        FragmentManager manager = getSupportFragmentManager();
        AnotacaoFragment fragment;

        if(tablet){
            fragment = (AnotacaoFragment) manager.findFragmentById(R.id.fragment_anotacao);
            fragment.prepararEdicao(anotacao);
        }else {
            fragment = new AnotacaoFragment();
            fragment.setAnotacao(anotacao);

            manager.beginTransaction()
                    .replace(R.id.fragment_unico,fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void novaAnotacao() {
        FragmentManager manager = getSupportFragmentManager();
        AnotacaoFragment fragment;

        if(tablet){
            fragment = (AnotacaoFragment) manager.findFragmentById(R.id.fragment_anotacao);
            fragment.criarNovaAnotacao();
        }else {
            fragment = new AnotacaoFragment();
            manager.beginTransaction()
                    .replace(R.id.fragment_unico,fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
}
