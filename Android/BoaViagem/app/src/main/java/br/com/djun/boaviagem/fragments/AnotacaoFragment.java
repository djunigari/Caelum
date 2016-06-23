package br.com.djun.boaviagem.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import br.com.djun.boaviagem.R;
import br.com.djun.boaviagem.domain.Anotacao;

public class AnotacaoFragment extends Fragment implements View.OnClickListener {
    private EditText dia,titulo,descricao;
    private Button salvarButton;
    private Anotacao anotacao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.anotacao,container,false);
    }

    @Override
    public void onStart() {
        super.onStart();
        dia = (EditText) getActivity().findViewById(R.id.dia);
        titulo = (EditText) getActivity().findViewById(R.id.titulo);
        descricao = (EditText) getActivity().findViewById(R.id.descricao);
        salvarButton = (Button) getActivity().findViewById(R.id.salvar);
        salvarButton.setOnClickListener(this);

        if(anotacao != null){
            prepararEdicao(anotacao);
        }
    }

    public void prepararEdicao(Anotacao anotacao) {
        setAnotacao(anotacao);
        dia.setText(anotacao.getDia().toString());
        titulo.setText(anotacao.getTitulo());
        descricao.setText(anotacao.getDescricao());
    }

    public void setAnotacao(Anotacao anotacao) {
        this.anotacao = anotacao;
    }

    public void criarNovaAnotacao(){
        anotacao = new Anotacao();
        dia.setText("");
        titulo.setText("");
        descricao.setText("");
    }

    @Override
    public void onClick(View v) {
    }
}
