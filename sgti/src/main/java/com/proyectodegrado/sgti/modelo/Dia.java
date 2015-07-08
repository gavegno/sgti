package com.proyectodegrado.sgti.modelo;

public class Dia {
	
	private String nombre;
	private String horaDesde;
	private String horaHasta;
	
	public Dia() {
		super();
	}
	
	public Dia(String nombre, String horaDesde, String horaHasta) {
		super();
		this.nombre = nombre;
		this.horaDesde = horaDesde;
		this.horaHasta = horaHasta;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getHoraDesde() {
		return horaDesde;
	}
	public void setHoraDesde(String horaDesde) {
		this.horaDesde = horaDesde;
	}
	public String getHoraHasta() {
		return horaHasta;
	}
	public void setHoraHasta(String horaHasta) {
		this.horaHasta = horaHasta;
	}
	
	

}
