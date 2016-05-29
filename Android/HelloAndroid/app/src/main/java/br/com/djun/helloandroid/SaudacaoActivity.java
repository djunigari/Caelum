package br.com.djun.helloandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SaudacaoActivity extends Activity {
    public static final	String EXTRA_NOME_USUARIO = "helloandroid.EXTRA_NOME_USUARIO";
    public static final	String ACAO_EXIBIR_SAUDACAO = "helloandroid.ACAO_EXIBIR_SAUDACAO";
    public static final	String CATEGORIA_SAUDACAO = "helloandroid.CATEGORIA_SAUDACAO";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saudacao);

        TextView saudacaoTextView = (TextView) findViewById(R.id.saudacaoTextView);

        StringBuilder msg = null;
        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_NOME_USUARIO)){
            msg = new StringBuilder(getResources().getString(R.string.saudacao));
            msg.append(" ");
            msg.append(intent.getStringExtra(EXTRA_NOME_USUARIO));
        }else{
            msg = new StringBuilder("O nome do usuário não foi informado");
        }
        saudacaoTextView.setText(msg);
    }
}
