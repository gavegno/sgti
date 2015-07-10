package com.proyectodegrado.sgti.modelo;

import java.util.Date;

public class Precio {
	
	private double precio;
	private Date fechaDesde;
	private Date fechaHasta;
	
	public Precio() {
		super();
	}

	public Precio(double precio, Date fechaDesde, Date fechaHasta) {
		super();
		this.precio = precio;
		this.fechaDesde = fechaDesde;
		this.fechaHasta = fechaHasta;
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
