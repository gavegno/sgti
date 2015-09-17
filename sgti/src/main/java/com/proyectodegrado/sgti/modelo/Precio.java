package com.proyectodegrado.sgti.modelo;

import java.util.Date;

public class Precio {
	
	private double precio;
	private double precioExtra;
	private Date fechaDesde;
	private Date fechaHasta;
	private String idContrato;
	
	public String getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(String idContrato) {
		this.idContrato = idContrato;
	}

	public Precio() {
		super();
	}

	public Precio(double precio, double precioExtra, Date fechaDesde, Date fechaHasta) {
		super();
		this.precio = precio;
		this.precioExtra = precioExtra;
		this.fechaDesde = fechaDesde;
		this.fechaHasta = fechaHasta;
	}

	public double getPrecioExtra() {
		return precioExtra;
	}

	public void setPrecioExtra(double precioExtra) {
		this.precioExtra = precioExtra;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
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
}
