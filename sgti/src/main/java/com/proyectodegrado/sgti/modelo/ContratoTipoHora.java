package com.proyectodegrado.sgti.modelo;

public class ContratoTipoHora {
	
	private int id;
	private String tipo;
	private String idContrato;
	
	public ContratoTipoHora(int id, String tipo, String idContrato) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.idContrato = idContrato;
	}

	public ContratoTipoHora() {
		super();
	}

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

	public String getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(String idContrato) {
		this.idContrato = idContrato;
	}


}
