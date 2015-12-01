package com.itapua.app.sgti.pickers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.itapua.app.sgti.R;

import java.util.Calendar;

/**
 * Created by Usuario on 14/11/2015.
 */
public class DateDialogs extends AppCompatDialogFragment implements DatePickerDialog.OnDateSetListener {
    private Button quien;
    private int dia;
    private int mes;
    private int anio;

    public DateDialogs(Button quien, int dia, int mes, int anio) {
        this.quien = quien;
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
    }

    public DateDialogs() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int year;
        int month;
        int day;

        if (anio >= 0 && mes >= 0 && dia >= 0)
        {
            year = anio;
            month = mes-1;
            day = dia;
        }
        else
        {
            Calendar calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }


        DatePickerDialog dialog;

        dialog = new DatePickerDialog(getActivity(), this, year, month, day);

        return dialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        int mes = monthOfYear+1;
        quien.setText(dayOfMonth + "/" + mes + "/" + year);

        if (quien.getId() == getActivity().findViewById(R.id.fechaDesde).getId())
        {
            Button button = (Button) getActivity().findViewById(R.id.fechaHasta);
            button.setText(dayOfMonth + "/" + mes + "/" + year);
        }
    }
}
