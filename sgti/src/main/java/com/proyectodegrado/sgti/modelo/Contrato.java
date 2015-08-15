package com.proyectodegrado.sgti.modelo;

import java.util.Date;
import java.util.List;

public class Contrato {
	
	private String id;
	
	private List<Configuracion> configuraciones;
	
	private List<Precio> precio;
	
	private List<TipoHoraComputo> tipoHoraComputo;
	
	private Cliente cliente;
	
	private Usuario contraparte;
	
	private Date ultimaFechaInforme;
	
	private Date ultimaFechaFacturacion;
	
	private Date ultimaFechaComputacion;

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

	public List<Precio> getPrecio() {
		return precio;
	}

	public void setPrecio(List<Precio> precio) {
		this.precio = precio;
	}

	public List<TipoHoraComputo> getTipoHoraComputo() {
		return tipoHoraComputo;
	}

	public void setTipoHoraComputo(List<TipoHoraComputo> tipoHoraComputo) {
		this.tipoHoraComputo = tipoHoraComputo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Usuario getContraparte() {
		return contraparte;
	}

	public void setContraparte(Usuario contraparte) {
		this.contraparte = contraparte;
	}

	public Date getUltimaFechaInforme() {
		return ultimaFechaInforme;
	}

	public void setUltimaFechaInforme(Date ultimaFechaInforme) {
		this.ultimaFechaInforme = ultimaFechaInforme;
	}

	public Date getUltimaFechaFacturacion() {
		return ultimaFechaFacturacion;
	}

	public void setUltimaFechaFacturacion(Date ultimaFechaFacturacion) {
		this.ultimaFechaFacturacion = ultimaFechaFacturacion;
	}

	public Date getUltimaFechaComputacion() {
		return ultimaFechaComputacion;
	}

	public void setUltimaFechaComputacion(Date ultimaFechaComputacion) {
		this.ultimaFechaComputacion = ultimaFechaComputacion;
	}
}
