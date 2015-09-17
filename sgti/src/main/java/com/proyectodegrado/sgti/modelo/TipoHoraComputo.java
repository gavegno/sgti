package com.proyectodegrado.sgti.modelo;

public class TipoHoraComputo {
	
	private TipoHora tipoHora;
	
	private double computo;

	public TipoHoraComputo() {
		super();
	}

	public TipoHoraComputo(TipoHora tipoHora, double computo) {
		super();
		this.tipoHora = tipoHora;
		this.computo = computo;
	}

	public TipoHora getTipoHora() {
		return tipoHora;
	}

	public void setTipoHora(TipoHora tipoHora) {
		this.tipoHora = tipoHora;
	}

	public double getComputo() {
		return computo;
	}

	public void setComputo(double computo) {
		this.computo = computo;
	}
}
