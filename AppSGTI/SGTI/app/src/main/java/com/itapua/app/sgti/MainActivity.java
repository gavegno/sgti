package com.itapua.app.sgti;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DigitalClock;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.itapua.app.sgti.Constants.SgtiConstants;
import com.itapua.app.sgti.interfaces.UsuarioServicio;
import com.itapua.app.sgti.modelo.Usuario;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);


        //Obtengo el id de usuario almacenado en el archivo Shared Preferences.
        //**********************
        String usuario;
        SharedPreferences archivo = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = archivo.edit();
        usuario = archivo.getString("usuario","");

        //Si no hay id de usuario ingresado:
        if (usuario.isEmpty()) {

            Intent intent = new Intent(this, RegistroActivity.class);
            startActivity(intent);
            this.finish();

        }
        //Si el usuario ya se registró en la aplicación:

        else
        {
            if (getIntent() != null) {
                processIntent(getIntent());
            }

        }


        final ImageButton nuevaHora = (ImageButton) findViewById(R.id.imageButtonNuevaHora);
        nuevaHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SeleccionContratoActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //Limpiar preferencias.
        if (id == R.id.action_settings) {

            SharedPreferences archivo = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
            String url = archivo.getString("url", "");
            String nombreUsuario = archivo.getString("usuario", "");
            TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);


            RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(url).build();

            UsuarioServicio usuarioServicio = restAdapter.create(UsuarioServicio.class);
            Usuario usuarioParaEnviar = new Usuario();
            usuarioParaEnviar.setImei(mngr.getDeviceId().toString());
            usuarioParaEnviar.setId(nombreUsuario);

            usuarioServicio.desasociarCelular(usuarioParaEnviar, new Callback<Boolean>() {
                @Override
                public void success(Boolean aBoolean, Response response) {
                    if (aBoolean == true) {

                        SharedPreferences archivo = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = archivo.edit();
                        editor.putString("usuario", "");
                        editor.commit();

                        Toast.makeText(getApplicationContext(), "Este dispositivo ha sido desasociado del sistema SGTI", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "ERROR: No se ha podido desasociar este dispositivo", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getApplicationContext(), "ERROR: No se ha podido desasociar este dispositivo", Toast.LENGTH_SHORT).show();
                }
            });





        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        //Evaluamos si se viene de un Intent
        if (getIntent() != null) {
            //Llamamos la método processIntent para procesar el Intent
            processIntent(getIntent());
        }
    }

    private void processIntent(Intent intent) {
        //Log.v(APP_TAG, "onResume");

        //Declaramos una variable de tipo Array de Mensajes NFC (NdefMessage)
        NdefMessage[] msgs;

        //Decalramos un variable para capturar el texto escrito en la etiqueta NFC
        //(llamado también Paypload data)
        String nfcTxtPayload = null;

        //Declaramos un objeto Tag para capturar el objeto Etiqueta NFC que viene
        //en Intent bajo la clave NfcAdapter.EXTRA_TAG
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

        //Validamos que tenemos la acción del Intent sea de tipo NfcAdapter.ACTION_NDEF_DISCOVERED
        //ya que esta es la acctión definida en el nodo intent-filter que insertamos en el AndroidManifest.xml
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction()) && tag != null) {
            //Capturamos el arreglo de mensajes que pueda contener la etiqeuta NFC
            //en un objeto array de tipo Parcelable que nos permita luego construir
            //un arreglo de mensajes NdefMessage
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

            if (rawMsgs != null) {
                //Construimos el objeto array de mensajes NdefMessage
                msgs = new NdefMessage[rawMsgs.length];

                //Iteramos sobre todos los mensajes del arreglo de menasjes que puedan
                //venir en la Etiqueta NFC
                for (int i = 0; i < rawMsgs.length; i++) {
                    //Obtenemos el mensaje según el indice de la iteración
                    msgs[i] = (NdefMessage) rawMsgs[i];

                    //Obtenemos el primber registro NdefRecord del Mensaje en curso NdefMessage
                    NdefRecord nfcRecord = msgs[i].getRecords()[0];

                    //Constuimos un objeto String con el mensaje embebido en la Etiqueta en forma de Paypload data
                    nfcTxtPayload = new String(nfcRecord.getPayload());

                    //Omitimos los 2 primeros caracteres por que en ellos nos viene código ISO
                    //del lenguaje en el que viene el mensaje
                    nfcTxtPayload = nfcTxtPayload.substring(3);

                    //Actualizamos el TextView de la pantalla con el texto embebido en la
                    //Etiqueta en forma de Paypload data
                    if(!nfcTxtPayload.isEmpty()) {
                        Date fechaActual = new Date();
                        SimpleDateFormat formatoCompleto = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");

                        chequearEntradaSalida(nfcTxtPayload, fechaActual, formatoCompleto, formatoHora);

                    }
                }
            }
        }
    }

    private void chequearEntradaSalida(String nfcTxtPayload, Date fechaActual, SimpleDateFormat formatoCompleto, SimpleDateFormat formatoHora) {
        if(esEntrada(nfcTxtPayload, fechaActual, formatoCompleto, formatoHora)){
            Intent intent1 = new Intent(this, PaginaPrincipalNFCActivity.class);
            intent1.putExtra(SgtiConstants.CLIENTE, nfcTxtPayload);
            intent1.putExtra(SgtiConstants.HORA_INICIAL, formatoHora.format(fechaActual));
            startActivity(intent1);
            this.finish();
        }else{
            final ImageButton nuevaHora = (ImageButton) findViewById(R.id.imageButtonNuevaHora);
            TextView horaSalida = (TextView) findViewById(R.id.txtHoraSalida);
            TextView relojSalida = (TextView) findViewById(R.id.digitalClockSalida);

            horaSalida.setText(SgtiConstants.HORA_SALIDA);
            relojSalida.setText(formatoHora.format(fechaActual));
            nuevaHora.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), SeleccionContratoActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

    private boolean esEntrada(String nfcTxtPayload, Date fecha, SimpleDateFormat formatoCompleto, SimpleDateFormat formatoHora) {
        SharedPreferences archivo = getSharedPreferences(SgtiConstants.PREFERENCIAS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = archivo.edit();
        if(!archivo.contains(SgtiConstants.ENTRADA)){
            editor.putString(SgtiConstants.CLIENTE, nfcTxtPayload);
            editor.putString(SgtiConstants.ENTRADA, formatoCompleto.format(fecha));
            editor.putString(SgtiConstants.HORA_ENTRADA, formatoHora.format(fecha));
            editor.commit();
            return true;
        }else{
            editor.putString(SgtiConstants.SALIDA, formatoCompleto.format(fecha));
            editor.putString(SgtiConstants.HORA_SALIDA, formatoHora.format(fecha));
            editor.commit();
            return false;
        }

    }
}
