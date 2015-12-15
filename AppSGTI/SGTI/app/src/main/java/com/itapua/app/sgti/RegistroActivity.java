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

import com.itapua.app.sgti.Constants.SgtiConstants;
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
        final EditText textoUrl = (EditText) findViewById(R.id.editTextUrl);

        SharedPreferences archivo = getSharedPreferences(SgtiConstants.PREFERENCIAS, Context.MODE_PRIVATE);

        String url = archivo.getString(SgtiConstants.URL, "http://192.168.0.203:8080/Sgti");
        textoUrl.setText(url);

        buttonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {



                if (v.getId() == buttonRegistro.getId()) {

                    final String usuario = textoUsuario.getText().toString();
                    String clave = textoClave.getText().toString();

                    RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(textoUrl.getText().toString()).build();

                    UsuarioServicio usuarioServicio = restAdapter.create(UsuarioServicio.class);
                    Usuario usuarioParaEnviar = new Usuario();

                    if (usuario.trim().isEmpty() || clave.trim().isEmpty() || textoUrl.getText().toString().trim().isEmpty())
                        Toast.makeText(v.getContext(), "Usuario o clave no pueden ser vacíos", Toast.LENGTH_SHORT).show();

                    else {
                        TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

                        usuarioParaEnviar.setId(usuario);
                        usuarioParaEnviar.setContrasena(clave);
                        usuarioParaEnviar.setImei(mngr.getDeviceId().toString());

                        Toast.makeText(v.getContext(), "Cargando...", Toast.LENGTH_SHORT).show();

                        usuarioServicio.getAutorizacion(usuarioParaEnviar, new Callback<Boolean>() {
                                    SharedPreferences archivo = getSharedPreferences(SgtiConstants.PREFERENCIAS, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = archivo.edit();

                                    @Override
                                    public void success(Boolean aBoolean, Response response) {
                                        if (aBoolean == true) {


                                            //Registro en el archivo el dato de id de usuario.
                                            editor.putString(SgtiConstants.USUARIO, usuario);
                                            editor.putString(SgtiConstants.URL, textoUrl.getText().toString());
                                            editor.commit();

                                            Toast.makeText(v.getContext(), "¡ Bienvenido al sistema !", Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(v.getContext(), MainActivity.class);
                                            startActivity(intent);
                                            finish();

                                        } else {
                                            Toast.makeText(v.getContext(), "Usuario y/o contraseña incorrectos.", Toast.LENGTH_SHORT).show();

                                            //Por las dudas, limpio los datos del archivo.
                                            editor.putString(SgtiConstants.USUARIO, "");
                                            editor.commit();
                                        }
                                    }


                                    @Override
                                    public void failure(RetrofitError error) {
                                        if (error.getMessage().contains("Network is unreachable"))
                                            Toast.makeText(v.getContext(), "ERROR: Compruebe su conexión a internet", Toast.LENGTH_LONG).show();
                                        else {
                                            if ((error.getMessage().contains("404")) || (error.getMessage().contains("failed to connect")))
                                                Toast.makeText(v.getContext(), "ERROR: Compruebe el link de acceso al servidor", Toast.LENGTH_LONG).show();
                                            else
                                                Toast.makeText(v.getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                                        }

                                    }
                                }

                        );
                    }
                }
            }
        });
    }
}
