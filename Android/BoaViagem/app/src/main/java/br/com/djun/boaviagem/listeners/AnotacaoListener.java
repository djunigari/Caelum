package br.com.djun.boaviagem.listeners;

import android.os.Bundle;

import br.com.djun.boaviagem.domain.Anotacao;

public interface AnotacaoListener {
    void viagemSelecionada(Bundle bundle);
    void anotacaoSelecionada(Anotacao anotacao);
    void novaAnotacao();
}
