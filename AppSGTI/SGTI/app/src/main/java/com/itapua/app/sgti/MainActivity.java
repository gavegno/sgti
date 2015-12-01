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
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
            this.finish();

        }
        //Si el usuario ya se registró en la aplicación:
        /*
        else
        {
            Intent intent = new Intent(this, CargahoraActivity.class);
            startActivity(intent);

        }
        */

        final ImageButton nuevaHora = (ImageButton) findViewById(R.id.imageButtonNuevaHora);
        nuevaHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SeleccionContratoActivity.class);
                startActivity(intent);
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
}
