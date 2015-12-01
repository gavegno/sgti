package com.itapua.app.sgti;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.itapua.app.sgti.interfaces.ContratoServicio;
import com.itapua.app.sgti.modelo.Contrato;
import com.itapua.app.sgti.modelo.Usuario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SeleccionContratoActivity extends AppCompatActivity {
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> listaParaMostrar;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_contrato);
        final Context contexto = this;

        final ListView listViewContratos = (ListView) findViewById(R.id.listViewContratos);

        SharedPreferences archivo = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        String url = archivo.getString("url", "http://192.168.0.203:8080/CounterWebApp");
        String idUsuario = archivo.getString("usuario","");

        TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);


        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(url).build();
        ContratoServicio contratoServicio = restAdapter.create(ContratoServicio.class);

        Usuario usuarioParaEnviar = new Usuario();
        usuarioParaEnviar.setId(idUsuario);
        usuarioParaEnviar.setImei(mngr.getDeviceId().toString());

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Espere por favor");
        progressDialog.setMessage("Recuperando información...");
        progressDialog.setCancelable(false);
        progressDialog.show();


        final List<Contrato> contratosObtenidos = new ArrayList<Contrato>();

        contratoServicio.getContratosPorTecnico(usuarioParaEnviar, new Callback<List<Contrato>>() {
            @Override
            public void success(List<Contrato> contratos, Response response) {
                listaParaMostrar = new ArrayList<String>();
                Iterator it = contratos.iterator();
                while (it.hasNext()) {

                    Contrato c = (Contrato) it.next();
                    contratosObtenidos.add(c);
                    listaParaMostrar.add(c.getCliente().getNombre() + " - " + c.getId());
                }


                arrayAdapter = new ArrayAdapter<String>(contexto, android.R.layout.simple_list_item_1, listaParaMostrar);
                listViewContratos.setAdapter(arrayAdapter);

                progressDialog.dismiss();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(contexto, "ERROR, no se ha podido obtener la información", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }




        });

        //Al seleccionar un contrato de la lista, pasará a la pantalla de carga de horas.
        listViewContratos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contrato con = contratosObtenidos.get(position);

                Intent intent = new Intent(SeleccionContratoActivity.this, CargahoraActivity.class);
                intent.putExtra("contrato", con.getId());
                intent.putExtra("cliente", con.getCliente().getId());
                startActivity(intent);


            }
        });

    }
}
