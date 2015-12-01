package com.itapua.app.sgti.interfaces;

import com.itapua.app.sgti.modelo.Contrato;
import com.itapua.app.sgti.modelo.Usuario;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by Usuario on 18/11/2015.
 */
public interface ContratoServicio {

    @POST("/desktop/movil/contratosPorTecnico")
    void getContratosPorTecnico(@Body Usuario usuario, Callback<List<Contrato>> callback);
}
