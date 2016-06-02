package br.com.djun.boaviagem;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

public class DatePickerDialogButton implements DatePickerDialog.OnDateSetListener {
    private Button button;
    private DatePickerDialog dialog;
    int ano,mes,dia;

    public DatePickerDialogButton(Context context, Button button){
        this.button = button;

        Calendar calendar = Calendar.getInstance();
        ano  = calendar.get(Calendar.YEAR);
        mes = calendar.get(Calendar.MONTH);
        dia = calendar.get(Calendar.DAY_OF_MONTH);
        button.setText(dia+"/"+(mes+1)+"/"+ano);
        dialog = new DatePickerDialog(context,this, ano,mes,dia);
    }
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        button.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
    }

    public void show(){
        dialog.show();
    }

    public Button getButton() {
        return button;
    }

    public Date getDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(ano, mes, dia);
        return calendar.getTime();
    }
}
