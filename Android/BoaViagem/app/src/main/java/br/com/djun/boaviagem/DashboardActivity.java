package br.com.djun.boaviagem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DashboardActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
    }

    public void selecionarOpcao(View view){
        TextView textView = (TextView) view;
        switch (view.getId()){
            case R.id.nova_viagem:
                startActivity(new Intent(this,ViagemActivity.class));
                break;
        }
    }
}
