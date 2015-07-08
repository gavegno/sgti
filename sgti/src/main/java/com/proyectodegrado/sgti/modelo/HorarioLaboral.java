package com.proyectodegrado.sgti.modelo;

import java.util.List;

public class HorarioLaboral {
	
	private String id;
	private List<Dia> dias;
	
	public HorarioLaboral(String id) {
		super();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Dia> getDias() {
		return dias;
	}

	public void setDias(List<Dia> dias) {
		this.dias = dias;
	}
}
