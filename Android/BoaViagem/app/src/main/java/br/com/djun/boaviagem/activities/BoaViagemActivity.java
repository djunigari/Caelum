package br.com.djun.boaviagem.activities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.api.client.googleapis.extensions.android.accounts.GoogleAccountManager;

import java.io.IOException;

import br.com.djun.boaviagem.Constantes;
import br.com.djun.boaviagem.R;

public class BoaViagemActivity extends Activity {
    private EditText usuarioEditText;
    private EditText senhaEditText;
    private CheckBox manterConectado;

    private GoogleAccountManager accountManager;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        accountManager = new GoogleAccountManager(this);
        usuarioEditText = (EditText)findViewById(R.id.usuarioEditText);
        senhaEditText = (EditText)findViewById(R.id.senhaEditText);
        manterConectado = (CheckBox) findViewById(R.id.manterConectado);
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean conectado = preferences.getBoolean(Constantes.MANTER_CONECTADO, false);
        if(conectado){
            iniciarDashboard();
        }

    }

    private void iniciarDashboard(){
        startActivity(new Intent(this,DashboardActivity.class));
    }

    public void entrarOnClick(View view){
        String usuario = usuarioEditText.getText().toString();
        String senha = senhaEditText.getText().toString();

        autenticar(usuario,senha);
    }

    private void autenticar(String user, String password) {
        Account account = accountManager.getAccountByName(user);
        if(account == null){
            Toast.makeText(this,R.string.conta_inexistente,Toast.LENGTH_SHORT).show();
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putString(AccountManager.KEY_ACCOUNT_NAME,user);
        bundle.putString(AccountManager.KEY_PASSWORD,password);

        accountManager.getAccountManager().confirmCredentials(account,bundle,this,new AutenticacaoCallback(),null);
    }

    private class AutenticacaoCallback implements AccountManagerCallback<Bundle>{

        @Override
        public void run(AccountManagerFuture<Bundle> future) {
            try {
                Bundle result = future.getResult();

                if(result.getBoolean(AccountManager.KEY_BOOLEAN_RESULT)){
                    iniciarDashboard();
                }else{
                    Toast.makeText(getBaseContext(),R.string.erro_autenticao,Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (OperationCanceledException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (AuthenticatorException e) {
                e.printStackTrace();
            }
        }
    }
}
