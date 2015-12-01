package com.itapua.app.sgti.pickers;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.format.DateFormat;
import android.widget.Button;

import java.util.Calendar;

/**
 * Created by Usuario on 14/11/2015.
 */
public class TimePicker extends AppCompatDialogFragment implements TimePickerDialog.OnTimeSetListener {
    private Button llamador;
    private int hora;
    private int minuto;

    public TimePicker(Button llamador, int hora, int minuto) {
        this.llamador = llamador;
        this.hora = hora;
        this.minuto = minuto;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int hour;
        int minute;

        if (hora >= 0 && minuto >= 0)
        {
            hour = hora;
            minute = minuto;
        }
        else {
            Calendar calendar = Calendar.getInstance();
            hour = calendar.get(Calendar.HOUR_OF_DAY);
            minute = calendar.get(Calendar.MINUTE);
        }

        Dialog dialog;

        dialog = new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));

        return dialog;
    }

    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        String minutoAMostrar;
        String horaAMostrar;

        switch (minute) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9: minutoAMostrar = "0"+minute;
                break;
            default: minutoAMostrar = ""+minute;
        }

        switch (hourOfDay) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9: horaAMostrar = "0"+hourOfDay;
                break;
            default: horaAMostrar = ""+hourOfDay;
        }
        llamador.setText(horaAMostrar+":"+minutoAMostrar);
    }
}
