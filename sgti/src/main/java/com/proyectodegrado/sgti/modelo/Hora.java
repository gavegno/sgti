package com.proyectodegrado.sgti.modelo;

import java.util.Date;

public class Hora {
	
	private int id;
	private Date fechaDesde;
	private Date fechaHasta;
	private Date fechaInformar;
	private Date fechaFacturar;
	private Date fechaComputar;
	private String idContrato;
	private String idUsuario;
	private String idActividad;
	private String nombreTipoHora;
	private String descripcion;
	private boolean remoto;
	private boolean informada;
	private boolean facturada;
	private boolean validada;
	
	public Hora() {
		super();
	}

	public Hora(int id, Date fechaDesde, Date fechaHasta, Date fechaInformar, Date fechaFcturar, Date fechaComputar, String idContrato, String idUsuario, String idActividad,
			String nombreTipoHora, String descripcion, boolean remoto, boolean informada, boolean facturada, boolean validada) {
		super();
		this.id = id;
		this.fechaDesde = fechaDesde;
		this.fechaHasta = fechaHasta;
		this.fechaInformar = fechaInformar;
		this.fechaFacturar = fechaFcturar;
		this.fechaComputar = fechaComputar;
		this.idContrato = idContrato;
		this.idUsuario = idUsuario;
		this.idActividad = idActividad;
		this.nombreTipoHora = nombreTipoHora;
		this.descripcion = descripcion;
		this.remoto = remoto;
		this.informada = informada;
		this.facturada = facturada;
		this.validada = validada;
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

	public Date getFechaInformar() {
		return fechaInformar;
	}

	public void setFechaInformar(Date fechaInformar) {
		this.fechaInformar = fechaInformar;
	}

	public Date getFechaFacturar() {
		return fechaFacturar;
	}

	public void setFechaFacturar(Date fechaFcturar) {
		this.fechaFacturar = fechaFcturar;
	}

	public Date getFechaComputar() {
		return fechaComputar;
	}

	public void setFechaComputar(Date fechaComputar) {
		this.fechaComputar = fechaComputar;
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

	public boolean isRemoto() {
		return remoto;
	}

	public void setRemoto(boolean remoto) {
		this.remoto = remoto;
	}

	public boolean isInformada() {
		return informada;
	}

	public void setInformada(boolean informada) {
		this.informada = informada;
	}

	public boolean isFacturada() {
		return facturada;
	}

	public void setFacturada(boolean facturada) {
		this.facturada = facturada;
	}

	public boolean isValidada() {
		return validada;
	}

	public void setValidada(boolean validada) {
		this.validada = validada;
	}
	
}
