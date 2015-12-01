package com.itapua.app.sgti.interfaces;

import com.itapua.app.sgti.modelo.HoraYUsuario;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.PUT;

/**
 * Created by Usuario on 20/11/2015.
 */
public interface HoraYUsuarioServicio {

    @PUT("/desktop/movil/ingresoHora")
    void insertarHora(@Body HoraYUsuario horaYUsuario, Callback<Integer> callback);
}
