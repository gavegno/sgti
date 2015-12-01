package com.itapua.app.sgti.modelo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Usuario on 18/11/2015.
 */
public class TipoHora {
    @SerializedName("id")
    private int id;

    @SerializedName("tipo")
    private String tipo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public TipoHora() {
    }

    public TipoHora(int id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "TipoHora{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
