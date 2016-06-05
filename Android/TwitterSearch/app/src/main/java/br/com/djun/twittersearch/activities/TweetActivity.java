package br.com.djun.twittersearch.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import br.com.djun.twittersearch.R;

public class TweetActivity extends Activity{

    public static final String USUARIO = "user";
    public static final String TEXTO = "text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tweet);
        TextView usuario = (TextView)findViewById(R.id.usuario);
        TextView texto = (TextView)findViewById(R.id.texto);

        usuario.setText(getIntent().getStringExtra(USUARIO));
        texto.setText(getIntent().getStringExtra(TEXTO));
    }
}
