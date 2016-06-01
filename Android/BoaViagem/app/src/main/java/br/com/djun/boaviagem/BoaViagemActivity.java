package br.com.djun.boaviagem;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class BoaViagemActivity extends Activity {
    private static final String MANTER_CONECTADO = "manter_conectado";
    private EditText usuarioEditText;
    private EditText senhaEditText;
    private CheckBox manterConectado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        usuarioEditText = (EditText)findViewById(R.id.usuarioEditText);
        senhaEditText = (EditText)findViewById(R.id.senhaEditText);
        manterConectado = (CheckBox) findViewById(R.id.manterConectado);
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean conectado = preferences.getBoolean(MANTER_CONECTADO, false);
        if(conectado){
            startActivity(new Intent(this,DashboardActivity.class));
        }

    }

    public void entrarOnClick(View view){
        String usuario = usuarioEditText.getText().toString();
        String senha = senhaEditText.getText().toString();


        if("leitor".equalsIgnoreCase(usuario) && "123".equals(senha)){
            SharedPreferences preferences = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(MANTER_CONECTADO,manterConectado.isChecked());
            editor.commit();
            startActivity(new Intent(this,DashboardActivity.class));
        }else{
            String msg = getString(R.string.erro_autenticao);
            Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
            toast.show();
        }


    }
}
