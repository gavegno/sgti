package com.itapua.app.sgti.modelo;

/**
 * Created by Usuario on 18/11/2015.
 */
public class Contrato {
    private String id;
    private Cliente cliente;

    public Contrato() {
    }

    public Contrato(String id, Cliente cliente) {
        this.id = id;
        this.cliente = cliente;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "Contrato{" +
                "id='" + id + '\'' +
                ", cliente=" + cliente +
                '}';
    }
}
