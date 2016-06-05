package br.com.djun.boaviagem.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import br.com.djun.boaviagem.Constantes;
import br.com.djun.boaviagem.DatePickerDialogButton;
import br.com.djun.boaviagem.R;
import br.com.djun.boaviagem.domain.Gasto;
import br.com.djun.boaviagem.domain.Viagem;
import br.com.djun.boaviagem.repositories.GastoRepository;
import br.com.djun.boaviagem.repositories.ViagemRepository;

public class GastoActivity extends Activity {
    private GastoRepository repository;
    private ViagemRepository viagemRepository;
    private SimpleDateFormat dateFormat;
    private DatePickerDialogButton dataDialog;
    private TextView destinoTextView;
    private Button dataGastoButton;
    private Spinner categoriaSpinner;
    private EditText valorEditText;
    private EditText descricaoEditText;
    private EditText localEditText;
    private long viagemId;
    private long gastoId;

    public GastoActivity(){
        repository = new GastoRepository(this);
        viagemRepository = new ViagemRepository(this);
        dateFormat	=  new SimpleDateFormat("dd/MM/yyyy");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gasto);
        settingReferencesViews();
        dataDialog = new DatePickerDialogButton(this, dataGastoButton);

        String destino = getIntent().getStringExtra(Constantes.VIAGEM_DESTINO);
        viagemId = getIntent().getLongExtra(Constantes.VIAGEM_ID, -1);
        gastoId = getIntent().getLongExtra(Constantes.GASTO_ID, -1);

        if(destino != null){
            destinoTextView.setText(destino);
        }else{
            destinoTextView.setVisibility(View.GONE);
        }

        if(gastoId > -1){
            preparaEdicao();
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categoria_gasto, android.R.layout.simple_spinner_item);
        categoriaSpinner.setAdapter(adapter);
    }

    private void settingReferencesViews(){
        dataGastoButton = (Button) findViewById(R.id.dataGastoButton);
        categoriaSpinner = (Spinner) findViewById(R.id.categoriaSpinner);
        valorEditText = (EditText) findViewById(R.id.valor);
        descricaoEditText = (EditText) findViewById(R.id.descricaoEditText);
        localEditText = (EditText) findViewById(R.id.localEditText);
        destinoTextView = (TextView) findViewById(R.id.destinoTextView);
    }

    private void preparaEdicao() {
        Gasto g = repository.findById(gastoId);
        Viagem v = viagemRepository.findById(g.getViagemId());
        destinoTextView.setText(v.getDestino());
        destinoTextView.setVisibility(View.VISIBLE);
        dataGastoButton.setText(dateFormat.format(g.getData()));
        String[] categorias = getResources().getStringArray(R.array.categoria_gasto);
        for(int i =0 ; i < categorias.length; i++){
            if(categorias[i].equals(g.getCategoria())){
                categoriaSpinner.setSelection(i);
                break;
            }
        }
        valorEditText.setText(g.getValor().toString());
        descricaoEditText.setText(g.getDescricao());
        localEditText.setText(g.getLocal());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gasto_menu,menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        return true;
    }

    public void selecionarData(View view){
        dataDialog.show();
    }

    public void registrarGasto(View view){
        Gasto gasto = new Gasto();
        gasto.setCategoria(categoriaSpinner.getSelectedItem().toString());
        gasto.setData(dataDialog.getDate());
        gasto.setValor(Double.valueOf(valorEditText.getText().toString()));
        gasto.setDescricao(descricaoEditText.getText().toString());
        gasto.setLocal(localEditText.getText().toString());

        if (viagemId > -1){
            gasto.setViagemId(viagemId);
        }

        long result;
        if(gastoId < 0){
            result = repository.save(gasto);
        }else{
            result = repository.update(gasto);
        }
        if(result != -1){
            Toast.makeText(this,getString(R.string.registro_salvo),Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,getString(R.string.erro_salvar),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        repository.close();
        viagemRepository.close();
        super.onDestroy();
    }

}
