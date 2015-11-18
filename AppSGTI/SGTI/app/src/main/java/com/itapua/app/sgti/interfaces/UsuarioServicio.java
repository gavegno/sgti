package com.itapua.app.sgti.interfaces;

import com.itapua.app.sgti.modelo.Cliente;
import com.itapua.app.sgti.modelo.Usuario;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Usuario on 09/11/2015.
 */
public interface UsuarioServicio {

    @POST("/desktop/movil/authorized")
    void getAutorizacion(@Body Usuario usuario, Callback<Boolean> callback);

    @POST("/desktop/movil/desasociar")
    void desasociarCelular(@Body Usuario usuario, Callback<Boolean> callback);
}
