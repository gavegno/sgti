package com.itapua.app.sgti;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AnalogClock;
import android.widget.Button;
import android.widget.DigitalClock;
import android.widget.TextView;

import com.itapua.app.sgti.Constants.SgtiConstants;

/**
 * Created by guillermoa on 04/12/15.
 */
public class PaginaPrincipalNFCActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paginainicial_nfc);

        TextView cliente = (TextView) findViewById(R.id.txtCliente);
        TextView horaInicial = (TextView) findViewById(R.id.txtHoraInicial);
        DigitalClock relojInicial = (DigitalClock) findViewById(R.id.digitalClock);

        Bundle bundle = getIntent().getExtras();
        cliente.setText("Cliente: " + bundle.getString(SgtiConstants.CLIENTE));
        horaInicial.setText(SgtiConstants.HORA_ENTRADA);
        relojInicial.setText(bundle.getString(SgtiConstants.HORA_INICIAL));


        Button btnAceptar = (Button) findViewById(R.id.btnAceptar);
       btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });

    }
}
