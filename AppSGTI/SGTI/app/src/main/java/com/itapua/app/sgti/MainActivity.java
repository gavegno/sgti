package com.itapua.app.sgti;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.itapua.app.sgti.interfaces.ClienteServicio;
import com.itapua.app.sgti.interfaces.UsuarioServicio;
import com.itapua.app.sgti.modelo.Usuario;

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

        final TextView textoWs;
        final TextView textoWsExtra;

        //Obtengo el id de usuario almacenado en el archivo Shared Preferences.
        //**********************
        String usuario;
        SharedPreferences archivo = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = archivo.edit();
        usuario = archivo.getString("usuario","");

        //**********************

        //Si no hay id de usuario ingresado:
        if (usuario.isEmpty()) {

            Intent intent = new Intent(this, RegistroActivity.class);
            startActivity(intent);

        }
        //Si el usuario ya se registró en la aplicación:
        else
        {
            textoWs = (TextView) findViewById(R.id.textViewWS);
            textoWsExtra = (TextView) findViewById(R.id.textViewWSExtra);

            //RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://192.168.0.203:8080/CounterWebApp").build();

            //ClienteServicio servicio = restAdapter.create(ClienteServicio.class);
            //TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            //textoWs.setText(mngr.getDeviceId().toString());

            /*
            servicio.getCliente(new Callback<Cliente>() {
                @Override
                public void success(Cliente cliente, Response response) {
                    textoWs.setText(cliente.toString());
                }

                @Override
                public void failure(RetrofitError error) {
                    textoWs.setText(error.getMessage());
                }
            });
            if (retorno.getId() == u.getId())
                        textoWs.setText("Logueo correcto");
                }
                 textoWs.setText(error.getMessage() + " Usuario incorrecto");


            UsuarioServicio usuarioServicio = restAdapter.create(UsuarioServicio.class);
            Usuario u = new Usuario();
            u.setId("atorreg");
            u.setContrasena("1234");

            usuarioServicio.getAutorizacion(u, new Callback<Boolean>() {
                @Override
                public void success(Boolean aBoolean, Response response) {
                    textoWs.setText(aBoolean.toString());
                    if (aBoolean == true)
                        textoWsExtra.setText("Dio true");
                    else
                        textoWsExtra.setText("Dio false");
                }

                @Override
                public void failure(RetrofitError error) {
                    textoWsExtra.setText(error.getMessage());
                }
            });




*/
        }




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
            SharedPreferences.Editor editor = archivo.edit();
            editor.putString("usuario","");
            editor.commit();
            Toast.makeText(this, "Se limpiaron las preferencias", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
