package br.com.djun.boaviagem.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import br.com.djun.boaviagem.Constantes;
import br.com.djun.boaviagem.DatePickerDialogButton;
import br.com.djun.boaviagem.R;
import br.com.djun.boaviagem.domain.Viagem;
import br.com.djun.boaviagem.repositories.ViagemRepository;

public class ViagemActivity extends Activity{
    private ViagemRepository repository;
    private SimpleDateFormat dateFormat;

    private Long viagemId;

    private EditText destinoEditText,orcamentoEditText,quantidadePessoasEditText;
    private RadioGroup tipoViagemRadioGroup;
    private DatePickerDialogButton dataChegadaDialog,dataSaidaDialog;
    private Button dataChegadaButton,dataSaidaButton;

    public ViagemActivity(){
        repository = new ViagemRepository(this);
        dateFormat	=  new	SimpleDateFormat("dd/MM/yyyy");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viagem);
        settingReferencesViews();

        viagemId = getIntent().getLongExtra(Constantes.VIAGEM_ID,-1);
        if(viagemId > -1){
            preparaEdicao();
        }
    }
    private void settingReferencesViews(){
        destinoEditText= (EditText) findViewById(R.id.destinoEditText);
        quantidadePessoasEditText= (EditText) findViewById(R.id.quantidadePessoasEditText);
        orcamentoEditText= (EditText) findViewById(R.id.orcamentoEditText);
        tipoViagemRadioGroup= (RadioGroup) findViewById(R.id.tipoViagemRadioGroup);
        dataChegadaButton=(Button) findViewById(R.id.dataChegadaButton);
        dataSaidaButton=(Button) findViewById(R.id.dataSaidaButton);
        dataChegadaDialog = new DatePickerDialogButton(this,dataChegadaButton);
        dataSaidaDialog = new DatePickerDialogButton(this,dataSaidaButton);
    }

    private void preparaEdicao() {
        Viagem v = repository.findById(viagemId);

        if(v.getTipoViagem() == Constantes.VIAGEM_LAZER){
            tipoViagemRadioGroup.check(R.id.lazerRadioButton);
        }	else	{
            tipoViagemRadioGroup.check(R.id.negocios);
        }
        destinoEditText.setText(v.getDestino());
        dataChegadaButton.setText(dateFormat.format(v.getDataChegada()));
        dataSaidaButton.setText(dateFormat.format(v.getDataSaida()));
        quantidadePessoasEditText.setText(v.getQuantidadePessoas().toString());
        orcamentoEditText.setText(v.getOrcamento().toString());
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

    public void salvarViagem(View view){
        Viagem viagem = new Viagem();
        viagem.setDestino(destinoEditText.getText().toString());
        viagem.setDataChegada(dataChegadaDialog.getDate());
        viagem.setDataSaida(dataSaidaDialog.getDate());
        viagem.setOrcamento(Double.valueOf(orcamentoEditText.getText().toString()));
        viagem.setQuantidadePessoas(Integer.valueOf(quantidadePessoasEditText.getText().toString()));
        int	tipo = tipoViagemRadioGroup.getCheckedRadioButtonId();
        if(tipo	== R.id.lazerRadioButton)	{
            viagem.setTipoViagem(Constantes.VIAGEM_LAZER);
        } else {
            viagem.setTipoViagem(Constantes.VIAGEM_NEGOCIOS);
        }
        long result = -1;
        if(viagemId < 0){
            result = repository.save(viagem);
        }else{
            viagem.setId(viagemId);
            result = repository.update(viagem);
        }

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
        repository.close();
        super.onDestroy();
    }

}
