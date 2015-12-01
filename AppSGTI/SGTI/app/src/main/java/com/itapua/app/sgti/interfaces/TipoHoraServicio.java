package com.itapua.app.sgti.interfaces;

import com.itapua.app.sgti.modelo.TipoHora;
import com.itapua.app.sgti.modelo.Usuario;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by Usuario on 19/11/2015.
 */
public interface TipoHoraServicio {

    @POST("/desktop/movil/tiposHoraParaContrato")
    void getTiposHoraParaContrato(@Body Usuario usuario, Callback<List<TipoHora>> callback);


}
