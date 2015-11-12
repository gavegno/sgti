package com.itapua.app.sgti.interfaces;

import com.itapua.app.sgti.modelo.Cliente;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by  on 08/11/2015.
 */
public interface ClienteServicio {

    @GET("/desktop/movil/cliente")
    void getCliente(Callback<Cliente> callback);
}
