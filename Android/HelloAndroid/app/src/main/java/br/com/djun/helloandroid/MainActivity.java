package br.com.djun.helloandroid;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity{

    private EditText nomeEditText;
    private TextView saudacaoTextView;
    private String saudacao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nomeEditText = (EditText) findViewById(R.id.nomeEditText);
        saudacaoTextView = (TextView) findViewById(R.id.saudacaoTextView);
        saudacao = getResources().getString(R.string.saudacao);
    }


    public void surpreenderUsuario(View view){
        saudacaoTextView.setText(saudacao.concat(" ").concat(nomeEditText.getText().toString()));
    }
}
