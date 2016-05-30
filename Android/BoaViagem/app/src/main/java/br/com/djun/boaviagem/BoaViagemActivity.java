package br.com.djun.boaviagem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class BoaViagemActivity extends Activity {
    private EditText usuarioEditText;
    private EditText senhaEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        usuarioEditText = (EditText)findViewById(R.id.usuarioEditText);
        senhaEditText = (EditText)findViewById(R.id.senhaEditText);
    }

    public void entrarOnClick(View view){
        String usuario = usuarioEditText.getText().toString();
        String senha = senhaEditText.getText().toString();

        if("leitor".equalsIgnoreCase(usuario) && "123".equals(senha)){
            startActivity(new Intent(this,DashboardActivity.class));
        }else{
            String msg = getString(R.string.erro_autenticao);
            Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
            toast.show();
        }

    }
}
