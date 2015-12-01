package com.itapua.app.sgti;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itapua.app.sgti.interfaces.ActividadServicio;
import com.itapua.app.sgti.interfaces.HoraYUsuarioServicio;
import com.itapua.app.sgti.interfaces.TipoHoraServicio;
import com.itapua.app.sgti.modelo.Actividad;
import com.itapua.app.sgti.modelo.Hora;
import com.itapua.app.sgti.modelo.HoraYUsuario;
import com.itapua.app.sgti.modelo.TipoHora;
import com.itapua.app.sgti.modelo.Usuario;
import com.itapua.app.sgti.pickers.DateDialogs;
import com.itapua.app.sgti.pickers.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

public class CargahoraActivity extends AppCompatActivity {
    private int year;
    private int month;
    private int day;
    private Context contexto = this;
    private Activity act = this;

    ArrayAdapter<String> arrayAdapterTiposHora;
    ArrayAdapter<String> arrayAdapterActividades;

    ArrayList<String> listaParaMostrarTipoHora;

    ArrayList<String> listaParaMostrarActividades;

    String url;
    String idUsuario;

    Button fechaDesde;
    Button fechaHasta;
    Button horaDesde;
    Button horaHasta;
    TextView textViewContrato;
    Spinner spinnerTipoHora;
    Spinner spinnerActividad;
    TextView comentarios;
    TextView descripcion;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cargahora, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Date fechaDesdeDate;
        Date fechaHastaDate;

        switch (id)
        {
            case R.id.mnuCancelarHora:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                this.finish();
                break;

            case R.id.mnuGuardarHora:
                fechaDesde = (Button) findViewById(R.id.fechaDesde);
                fechaHasta = (Button) findViewById(R.id.fechaHasta);
                horaDesde = (Button) findViewById(R.id.horaDesde);
                horaHasta = (Button) findViewById(R.id.horaHasta);
                textViewContrato = (TextView) findViewById(R.id.textViewContrato);

                spinnerTipoHora = (Spinner) findViewById(R.id.spinnerTipoHora);
                spinnerActividad = (Spinner) findViewById(R.id.spinnerActividades);
                comentarios = (TextView) findViewById(R.id.textComentario);
                descripcion = (TextView) findViewById(R.id.textDescripcion);

                //INICIO BLOQUE DE COMPROBACIONES ******************************
                SimpleDateFormat simpleFateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                String fechaDesdeAux = fechaDesde.getText().toString().trim() + " " + horaDesde.getText().toString().trim();
                String fechaHastaAux = fechaHasta.getText().toString().trim() + " " + horaHasta.getText().toString().trim();

                boolean chequeado = false;
                String mensajeError = "0";
                long duracionAux = 0;
                Calendar cal = Calendar.getInstance();

                try {
                    fechaDesdeDate = simpleFateFormat.parse(fechaDesdeAux);
                    fechaHastaDate = simpleFateFormat.parse(fechaHastaAux);

                    Date fechaActual = cal.getTime();

                    if ((fechaHastaDate.after(fechaDesdeDate)) && (fechaHastaDate != fechaDesdeDate) && (fechaHastaDate.before(fechaActual))) {
                        chequeado = true;
                        duracionAux = ((fechaHastaDate.getTime() / 60000) - (fechaDesdeDate.getTime() / 60000));
                    }
                    else mensajeError = "Las fechas no son válidas";

                } catch (ParseException e) {
                    mensajeError = "Debe elegir fechas y horas";
                }

                //FIN BLOQUE DE COMPROBACIONES ********************************

                if (chequeado) {
                    //Si la duración no es múltiplo de quince, se preguntará si está seguro.
                    if (duracionAux % 15 != 0) {
                        AlertDialog.Builder builderDialogo = new AlertDialog.Builder(this);
                        builderDialogo
                                .setTitle("Duración de la hora")
                                .setMessage("La duración no es múltiplo de quince minutos ("+duracionAux+" min.), ¿continuar de todas formas?")
                                .setIcon(android.R.drawable.ic_dialog_info)
                                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        enviarHora();
                                    }
                                })
                                .setNegativeButton("Corregir", null)
                                .show();

                    } else
                    //La hora es múltiplo de quince.
                    { enviarHora(); }
                }
                else
                    Toast.makeText(contexto, mensajeError, Toast.LENGTH_LONG).show();

                break;
        }

        return super.onOptionsItemSelected(item);
    }


    private void enviarHora()
    {
        Date fechaDesdeDate;
        Date fechaHastaDate;

        fechaDesde = (Button) findViewById(R.id.fechaDesde);
        fechaHasta = (Button) findViewById(R.id.fechaHasta);
        horaDesde = (Button) findViewById(R.id.horaDesde);
        horaHasta = (Button) findViewById(R.id.horaHasta);
        textViewContrato = (TextView) findViewById(R.id.textViewContrato);

        spinnerTipoHora = (Spinner) findViewById(R.id.spinnerTipoHora);
        spinnerActividad = (Spinner) findViewById(R.id.spinnerActividades);
        comentarios = (TextView) findViewById(R.id.textComentario);
        descripcion = (TextView) findViewById(R.id.textDescripcion);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Espere por favor");
        progressDialog.setMessage("Enviando datos...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        //INICIO BLOQUE DE COMPROBACIONES ******************************
        SimpleDateFormat simpleFateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        String fechaDesdeAux = fechaDesde.getText().toString().trim() + " " + horaDesde.getText().toString().trim();
        String fechaHastaAux = fechaHasta.getText().toString().trim() + " " + horaHasta.getText().toString().trim();

        boolean chequeado = false;
        String mensajeError = "0";
        long duracionAux = 0;


        try {
            fechaDesdeDate = simpleFateFormat.parse(fechaDesdeAux);
            fechaHastaDate = simpleFateFormat.parse(fechaHastaAux);
        }    catch (ParseException e) {  }

        TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        HoraYUsuario horaYUsuario = new HoraYUsuario();

        horaYUsuario.setIdUsuario(idUsuario);
        horaYUsuario.setImei(mngr.getDeviceId().toString());


        if (spinnerActividad.getSelectedItemPosition() == 0)
            horaYUsuario.setActividad(null);
        else
            horaYUsuario.setActividad((String) spinnerActividad.getSelectedItem());

        horaYUsuario.setIdContrato(textViewContrato.getText().toString());
        horaYUsuario.setComentario(comentarios.getText().toString());
        horaYUsuario.setDescripcion(descripcion.getText().toString());

        horaYUsuario.setTipoHora((String) spinnerTipoHora.getSelectedItem());


        fechaDesdeAux = fechaDesdeAux.replace("/", "-");
        fechaHastaAux = fechaHastaAux.replace("/", "-");

        horaYUsuario.setFechaDesde(fechaDesdeAux);
        horaYUsuario.setFechaHasta(fechaHastaAux);


        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(url)
                .build();

        HoraYUsuarioServicio horaYUsuarioServicio = restAdapter.create(HoraYUsuarioServicio.class);

        horaYUsuarioServicio.insertarHora(horaYUsuario, new Callback<Integer>() {
            @Override
            public void success(Integer integer, Response response) {
                progressDialog.dismiss();

                switch (integer) {
                    case -1:
                        Toast.makeText(contexto, "Error. Esta hora genera conflicto con una ya existente", Toast.LENGTH_LONG).show();
                        break;
                    case 0:
                        Toast.makeText(contexto, "Error al ingresar la hora", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(contexto, "CONFIRMADA. Duración de la hora ingresada: " + integer.toString() + " minutos", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(act, MainActivity.class);
                        startActivity(intent);
                        act.finish();
                }

            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.dismiss();
                Toast.makeText(contexto, "ERROR: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargahora);



        Calendar calendar = Calendar.getInstance();


        fechaDesde = (Button) findViewById(R.id.fechaDesde);
        fechaHasta = (Button) findViewById(R.id.fechaHasta);
        horaDesde = (Button) findViewById(R.id.horaDesde);
        horaHasta = (Button) findViewById(R.id.horaHasta);
        textViewContrato = (TextView) findViewById(R.id.textViewContrato);

        spinnerTipoHora = (Spinner) findViewById(R.id.spinnerTipoHora);
        spinnerActividad = (Spinner) findViewById(R.id.spinnerActividades);
        comentarios = (TextView) findViewById(R.id.textComentario);
        descripcion = (TextView) findViewById(R.id.textDescripcion);




        SharedPreferences archivo = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        url = archivo.getString("url", "http://192.168.0.203:8080/CounterWebApp");
        idUsuario = archivo.getString("usuario","");

        TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        Usuario usuarioParaEnviar = new Usuario();
        usuarioParaEnviar.setId(idUsuario);
        usuarioParaEnviar.setImei(mngr.getDeviceId().toString());

        Bundle extras = getIntent().getExtras();

        if (extras != null)
        {
            textViewContrato.setText(extras.getString("contrato"));
        //Consulto webservice que retorna los tipos de hora para el usuario y el contrato.
            //Le cargamos el contrato.
            usuarioParaEnviar.setNombre(extras.getString("contrato"));

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd")
                    .create();

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(url)
                    .setConverter(new GsonConverter(gson))
                    .build();

            TipoHoraServicio tipoHoraServicio = restAdapter.create(TipoHoraServicio.class);


            tipoHoraServicio.getTiposHoraParaContrato(usuarioParaEnviar, new Callback<List<TipoHora>>() {
                @Override
                public void success(List<TipoHora> tipoHoras, Response response) {
                    listaParaMostrarTipoHora = new ArrayList<String>();

                    Iterator it = tipoHoras.iterator();

                    while (it.hasNext()) {
                        TipoHora t = (TipoHora) it.next();

                        listaParaMostrarTipoHora.add(t.getTipo());

                        arrayAdapterTiposHora = new ArrayAdapter<String>(contexto, android.R.layout.simple_spinner_dropdown_item, listaParaMostrarTipoHora);
                        spinnerTipoHora.setAdapter(arrayAdapterTiposHora);

                    }


                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(contexto, "WS tipohora: " + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

            ActividadServicio actividadServicio = restAdapter.create(ActividadServicio.class);

            listaParaMostrarActividades = new ArrayList<String>();
            listaParaMostrarActividades.add("(Ninguna)");

            actividadServicio.getTiposHoraParaContrato(usuarioParaEnviar, new Callback<List<Actividad>>() {
                @Override
                public void success(List<Actividad> actividads, Response response) {


                    Iterator it = actividads.iterator();

                    while (it.hasNext()) {
                        Actividad a = (Actividad) it.next();
                        listaParaMostrarActividades.add(a.getId());

                        arrayAdapterActividades = new ArrayAdapter<String>(contexto, android.R.layout.simple_spinner_dropdown_item, listaParaMostrarActividades);
                        spinnerActividad.setAdapter(arrayAdapterActividades);

                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(contexto, "WS actividades: " + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
        else
            Toast.makeText(this, "No se recibió el contrato seleccionado.", Toast.LENGTH_LONG).show();



        fechaDesde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fechaDesde = (Button) findViewById(R.id.fechaDesde);
                int d, m, a;

                try {
                    d = Integer.parseInt(fechaDesde.getText().toString().substring(0, 2));
                    m = Integer.parseInt(fechaDesde.getText().toString().substring(3, 5));
                    a = Integer.parseInt(fechaDesde.getText().toString().substring(6, 10));
                }
                catch (NumberFormatException e)
                {
                    d = -1;
                    m = -1;
                    a = -1;
                }

                DateDialogs picker = new DateDialogs(fechaDesde, d, m, a);
                picker.show(getSupportFragmentManager(), "date_picker");

            }
        });

        fechaHasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fechaHasta = (Button) findViewById(R.id.fechaHasta);
                int d, m, a;

                try {
                    d = Integer.parseInt(fechaHasta.getText().toString().substring(0, 2));
                    m = Integer.parseInt(fechaHasta.getText().toString().substring(3, 5));
                    a = Integer.parseInt(fechaHasta.getText().toString().substring(6, 10));
                }
                catch (NumberFormatException e)
                {
                    d = -1;
                    m = -1;
                    a = -1;
                }

                DateDialogs picker = new DateDialogs(fechaHasta, d, m, a);
                picker.show(getSupportFragmentManager(), "date_picker");
            }
        });

        horaDesde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                horaDesde = (Button) findViewById(R.id.horaDesde);
                int hr;
                int mn;

                try {
                    hr = Integer.parseInt(horaDesde.getText().toString().substring(0, 2));
                    mn = Integer.parseInt(horaDesde.getText().toString().substring(3, 5));
                }catch (NumberFormatException e)
                {
                    hr = -1;
                    mn = -1;
                }

                TimePicker picker = new TimePicker(horaDesde, hr, mn);
                picker.show(getSupportFragmentManager(), "TimePicker");
            }
        });

        horaHasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                horaHasta = (Button) findViewById(R.id.horaHasta);
                int hr;
                int mn;
                try {
                    hr = Integer.parseInt(horaHasta.getText().toString().substring(0, 2));
                    mn = Integer.parseInt(horaHasta.getText().toString().substring(3, 5));
                }catch (NumberFormatException e)
                {
                    hr = -1;
                    mn = -1;
                }

                TimePicker picker = new TimePicker(horaHasta, hr, mn);
                picker.show(getSupportFragmentManager(), "TimePicker");
            }
        });
    }



}
