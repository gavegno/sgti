package com.proyectodegrado.sgti.modelo;

import java.util.Date;

public class HoraYUsuario {
	public HoraYUsuario() {
		super();
	}


	private String fechaDesde;
	private String fechaHasta;
	private String tipoHora;
	private String actividad;
	private String descripcion;
	private String comentario;
	private String idContrato;
	private String idUsuario;
	private String imei;
	
	
	public String getFechaDesde() {
		return fechaDesde;
	}


	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}


	public String getFechaHasta() {
		return fechaHasta;
	}


	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}


	public String getTipoHora() {
		return tipoHora;
	}


	public void setTipoHora(String tipoHora) {
		this.tipoHora = tipoHora;
	}


	public String getActividad() {
		return actividad;
	}


	public void setActividad(String actividad) {
		this.actividad = actividad;
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


	public String getImei() {
		return imei;
	}


	public void setImei(String imei) {
		this.imei = imei;
	}


	public HoraYUsuario(String fechaDesde, String fechaHasta, String tipoHora,
			String actividad, String descripcion, String comentario,
			String idUsuario, String imei) {
		super();
		this.fechaDesde = fechaDesde;
		this.fechaHasta = fechaHasta;
		this.tipoHora = tipoHora;
		this.actividad = actividad;
		this.descripcion = descripcion;
		this.comentario = comentario;
		this.idUsuario = idUsuario;
		this.imei = imei;
	}
}
