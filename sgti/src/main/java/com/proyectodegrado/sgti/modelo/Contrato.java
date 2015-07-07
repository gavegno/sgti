package com.proyectodegrado.sgti.modelo;

import java.util.List;

public class Contrato {
	
	private String id;
	
	private List<Configuracion> configuraciones;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Configuracion> getConfiguraciones() {
		return configuraciones;
	}

	public void setConfiguraciones(List<Configuracion> configuraciones) {
		this.configuraciones = configuraciones;
	}
	
	

}
