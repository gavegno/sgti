package com.itapua.app.sgti;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.itapua.app.sgti.interfaces.UsuarioServicio;
import com.itapua.app.sgti.modelo.Usuario;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RegistroActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        final Button buttonRegistro = (Button) findViewById(R.id.button);
        final EditText textoUsuario = (EditText) findViewById(R.id.editText);
        final EditText textoClave = (EditText) findViewById(R.id.editText2);

        final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://192.168.0.203:8080/CounterWebApp").build();

        buttonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (v.getId() == buttonRegistro.getId()) {
                    final String usuario = textoUsuario.getText().toString();
                    String clave = textoClave.getText().toString();

                    UsuarioServicio usuarioServicio = restAdapter.create(UsuarioServicio.class);
                    Usuario usuarioParaEnviar = new Usuario();

                    if (usuario.trim().isEmpty() || clave.trim().isEmpty())
                        Toast.makeText(v.getContext(), "Usuario o clave no pueden ser vacíos", Toast.LENGTH_SHORT).show();
                    else {
                        TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

                        usuarioParaEnviar.setId(usuario);
                        usuarioParaEnviar.setContrasena(clave);
                        usuarioParaEnviar.setImei(mngr.getDeviceId().toString());

                        usuarioServicio.getAutorizacion(usuarioParaEnviar, new Callback<Boolean>() {
                                    SharedPreferences archivo = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = archivo.edit();

                                    @Override
                                    public void success(Boolean aBoolean, Response response) {
                                        if (aBoolean == true) {


                                            //Registro en el archivo el dato de id de usuario.
                                            editor.putString("usuario", usuario);
                                            editor.commit();
                                            Toast.makeText(v.getContext(), "¡ Bienvenido al sistema !", Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(v.getContext(), MainActivity.class);
                                            startActivity(intent);


                                        } else {
                                            Toast.makeText(v.getContext(), "Usuario y/o contraseña incorrectos.", Toast.LENGTH_SHORT).show();
                                            //Por las dudas, limpio los datos del archivo.
                                            editor.putString("usuario", "");
                                            editor.commit();
                                        }
                                    }


                                    @Override
                                    public void failure(RetrofitError error) {
                                        Toast.makeText(v.getContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show();

                                    }
                                }

                        );
                    }



/*

                    if (usuario.equalsIgnoreCase("1") && clave.equalsIgnoreCase("1")) {
                        //Sería el caso que el WS da autorización


                        editor.putString("usuario", usuario);
                        editor.commit();

                        Toast.makeText(v.getContext(), "Credenciales autenticadas", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        startActivity(intent);
                    }
                        else {
                        Toast.makeText(v.getContext(), "Usuario y/o contraseña incorrecta", Toast.LENGTH_SHORT).show();
                        editor.putString("usuario", "");
                        editor.commit();
                    }
                }*/
                    //Llamar al webservice que controla la identificación y autenticación.
                }
            }
        });
    }
}
