package com.proyectodegrado.sgti.modelo;

import java.util.Date;

public class ComputoAcumulado {
	
	private int id;
	
	private String idContrato;
	
	private Date fecha;
	
	private double computos;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(String idContrato) {
		this.idContrato = idContrato;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public double getComputos() {
		return computos;
	}

	public void setComputos(double computos) {
		this.computos = computos;
	}
}
