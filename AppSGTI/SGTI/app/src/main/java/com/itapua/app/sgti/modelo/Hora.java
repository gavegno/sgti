package com.itapua.app.sgti.modelo;

import java.util.Date;

/**
 * Created by Usuario on 20/11/2015.
 */
public class Hora {
    private int id;
    private Date fechaDesde;
    private Date fechaHasta;
    private String idContrato;
    private String idUsuario;
    private String idActividad;
    private String nombreTipoHora;
    private String descripcion;
    private String comentario;

    public Hora() {
    }

    public Hora(int id, Date fechaDesde, Date fechaHasta, String idContrato, String idUsuario, String idActividad, String nombreTipoHora, String descripcion, String comentario) {
        this.id = id;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
        this.idContrato = idContrato;
        this.idUsuario = idUsuario;
        this.idActividad = idActividad;
        this.nombreTipoHora = nombreTipoHora;
        this.descripcion = descripcion;
        this.comentario = comentario;
    }

    @Override
    public String toString() {
        return "Hora{" +
                "id=" + id +
                ", fechaDesde=" + fechaDesde +
                ", fechaHasta=" + fechaHasta +
                ", idContrato='" + idContrato + '\'' +
                ", idUsuario='" + idUsuario + '\'' +
                ", idActividad='" + idActividad + '\'' +
                ", nombreTipoHora='" + nombreTipoHora + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", comentario='" + comentario + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
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

    public String getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(String idActividad) {
        this.idActividad = idActividad;
    }

    public String getNombreTipoHora() {
        return nombreTipoHora;
    }

    public void setNombreTipoHora(String nombreTipoHora) {
        this.nombreTipoHora = nombreTipoHora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
