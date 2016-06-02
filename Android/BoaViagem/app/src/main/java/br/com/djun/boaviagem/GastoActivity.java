package br.com.djun.boaviagem;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import java.util.Calendar;

public class GastoActivity extends Activity {
    int ano,mes,dia;
    private Button dataGastoButton;
    private Spinner categoriaSpinner;
    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            ano = year;
            mes = monthOfYear;
            dia = dayOfMonth;
            dataGastoButton.setText(dia+"/"+(mes+1)+"/"+ano);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gasto);
        dataGastoButton = (Button) findViewById(R.id.dataGastoButton);
        categoriaSpinner = (Spinner) findViewById(R.id.categoriaSpinner);

        Calendar calendar = Calendar.getInstance();
        ano = calendar.get(Calendar.YEAR);
        mes = calendar.get(Calendar.MONTH);
        dia = calendar.get(Calendar.DAY_OF_MONTH);
        dataGastoButton.setText(dia + "/" + (mes+1) + "/" + ano);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categoria_gasto, android.R.layout.simple_spinner_item);
        categoriaSpinner.setAdapter(adapter);
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
        showDialog(view.getId());
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(R.id.dataGastoButton == id){
            return new DatePickerDialog(this,listener, ano,mes,dia);
        }
        return null;
    }

}
