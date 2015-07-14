package com.proyectodegrado.sgti.modelo;

public class TipoHoraComputo {
	
	private TipoHora tipoHora;
	
	private int computo;

	public TipoHoraComputo() {
		super();
	}

	public TipoHoraComputo(TipoHora tipoHora, int computo) {
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

	public int getComputo() {
		return computo;
	}

	public void setComputo(int computo) {
		this.computo = computo;
	}
}
