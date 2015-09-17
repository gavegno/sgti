package com.proyectodegrado.sgti.dto;

public class DataNotificacion {
	
	private String mensaje;
	
	private int cantidad;
	
	private String url;

	public DataNotificacion(String mensaje, int cantidad, String url) {
		super();
		this.mensaje = mensaje;
		this.cantidad = cantidad;
		this.url = url;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
