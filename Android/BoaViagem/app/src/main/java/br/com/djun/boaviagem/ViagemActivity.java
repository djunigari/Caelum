package br.com.djun.boaviagem;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ViagemActivity extends Activity{
    private DatabaseHelper helper;
    private EditText destinoEditText,orcamentoEditText,quantidadePessoasEditText;
    private RadioGroup tipoViagemRadioGroup;
    private DatePickerDialogButton dataChegadaDialog,dataSaidaDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viagem);
        settingReferencesViews();
        helper = new DatabaseHelper(this);
        dataChegadaDialog = new DatePickerDialogButton(this,(Button) findViewById(R.id.dataChegadaButton));
        dataSaidaDialog = new DatePickerDialogButton(this,(Button) findViewById(R.id.dataSaidaButton));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.viagem_menu,menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()){
            case R.id.novo_gasto:
                startActivity(new Intent(this,GastoActivity.class));
                break;
            case R.id.remover:
                return true;
            default:
                return super.onMenuItemSelected(featureId,item);
        }
        return true;
    }

    private void settingReferencesViews(){
        destinoEditText= (EditText) findViewById(R.id.destinoEditText);
        quantidadePessoasEditText= (EditText) findViewById(R.id.quantidadePessoasEditText);
        orcamentoEditText= (EditText) findViewById(R.id.orcamentoEditText);
        tipoViagemRadioGroup= (RadioGroup) findViewById(R.id.tipoViagemRadioGroup);
    }

    public void salvarViagem(View view){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("destino",destinoEditText.getText().toString());
        values.put("data_chegada",dataChegadaDialog.getDate().getTime());
        values.put("data_saida",dataSaidaDialog.getDate().getTime());
        values.put("orcamento",orcamentoEditText.getText().toString());
        values.put("quantidade_pessoas",quantidadePessoasEditText.getText().toString());
        int	tipo = tipoViagemRadioGroup.getCheckedRadioButtonId();
        if(tipo	== R.id.lazerRadioButton)	{
            values.put("tipo_viagem", Constantes.VIAGEM_LAZER);
        } else {
            values.put("tipo_viagem", Constantes.VIAGEM_NEGOCIOS);
        }

        long result = db.insert("viagem", null, values);
        if(result != -1){
            Toast.makeText(this,getString(R.string.registro_salvo),Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,getString(R.string.erro_salvar),Toast.LENGTH_SHORT).show();
        }
    }

   public void selecionarData(View view){
       if(view.getId() == R.id.dataChegadaButton){
           dataChegadaDialog.show();
           return;
       }
       if(view.getId() == R.id.dataSaidaButton){
           dataSaidaDialog.show();
           return;
       }
   }
    @Override
    protected void onDestroy() {
        helper.close();
        super.onDestroy();
    }

}
