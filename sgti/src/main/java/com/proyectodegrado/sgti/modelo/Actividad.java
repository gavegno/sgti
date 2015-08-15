package com.proyectodegrado.sgti.modelo;

import java.util.Date;

public class Actividad {
	
	private String id;
	private String tipo;
	private String idContrato;
	private String idUsuario;
	private Date fechaCreacion;
	private Date fechaActividad;
	private int periodo;
	
	public Actividad() {
		super();
	}

	public Actividad(String id, String tipo, String idContrato, String idUsuario, Date fechaCreacion, Date fechaActividad, int periodo) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.idContrato = idContrato;
		this.idUsuario = idUsuario;
		this.fechaCreacion = fechaCreacion;
		this.fechaActividad = fechaActividad;
		this.periodo = periodo;
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
	
}
