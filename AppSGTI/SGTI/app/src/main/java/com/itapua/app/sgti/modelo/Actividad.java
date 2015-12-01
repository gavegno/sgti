package com.itapua.app.sgti.modelo;

import java.util.Date;

/**
 * Created by Usuario on 19/11/2015.
 */
public class Actividad {

    private String id;
    private String tipo;
    private String idContrato;
    private String idUsuario;
    private Date fechaCreacion;
    private Date fechaActividad;
    private int periodo;
    private String descripcion;
    private String estado;

    public Actividad() {
    }

    public Actividad(String id, String tipo, String idContrato, String idUsuario, Date fechaCreacion, Date fechaActividad, int periodo, String descripcion, String estado) {
        this.id = id;
        this.tipo = tipo;
        this.idContrato = idContrato;
        this.idUsuario = idUsuario;
        this.fechaCreacion = fechaCreacion;
        this.fechaActividad = fechaActividad;
        this.periodo = periodo;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(String idContrato) {
        this.idContrato = idContrato;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaActividad() {
        return fechaActividad;
    }

    public void setFechaActividad(Date fechaActividad) {
        this.fechaActividad = fechaActividad;
    }

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Actividad{" +
                "id='" + id + '\'' +
                ", tipo='" + tipo + '\'' +
                ", idContrato='" + idContrato + '\'' +
                ", idUsuario='" + idUsuario + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaActividad=" + fechaActividad +
                ", periodo=" + periodo +
                ", descripcion='" + descripcion + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}
