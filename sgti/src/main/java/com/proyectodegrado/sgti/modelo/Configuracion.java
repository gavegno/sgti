package com.proyectodegrado.sgti.modelo;

import java.util.Date;


public class Configuracion {
	
	private int id; 
	private Date fechaInicio; 
	private Date fechaFin; 
	private String renovacion; 
	private int periodoRenovacion;
	private String tipoContrato; 
	private int computosPaquete; 
	private int periodoValidezMes; 
	private int periodoValidezDia; 
	private boolean acumulacion;
	private int periodoAcumulacion; 
	private int frecuenciaInforme; 
	private int frecuenciaFacturacion; 
	private int frecuenciaComputosExtra;
	private String tiempoRespuesta; 
	private HorarioLaboral horarioLaboral;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public String getRenovacion() {
		return renovacion;
	}
	public void setRenovacion(String renovacion) {
		this.renovacion = renovacion;
	}
	public int getPeriodoRenovacion() {
		return periodoRenovacion;
	}
	public void setPeriodoRenovacion(int periodoRenovacion) {
		this.periodoRenovacion = periodoRenovacion;
	}
	public String getTipoContrato() {
		return tipoContrato;
	}
	public void setTipoContrato(String tipoContrato) {
		this.tipoContrato = tipoContrato;
	}
	public int getComputosPaquete() {
		return computosPaquete;
	}
	public void setComputosPaquete(int computosPaquete) {
		this.computosPaquete = computosPaquete;
	}
	public int getPeriodoValidezMes() {
		return periodoValidezMes;
	}
	public void setPeriodoValidezMes(int peridoValidezMes) {
		this.periodoValidezMes = peridoValidezMes;
	}
	public int getPeriodoValidezDia() {
		return periodoValidezDia;
	}
	public void setPeriodoValidezDia(int periodoValidezDia) {
		this.periodoValidezDia = periodoValidezDia;
	}
	public boolean isAcumulacion() {
		return acumulacion;
	}
	public void setAcumulacion(boolean acumulacion) {
		this.acumulacion = acumulacion;
	}
	public int getPeriodoAcumulacion() {
		return periodoAcumulacion;
	}
	public void setPeriodoAcumulacion(int periodoAcumulacion) {
		this.periodoAcumulacion = periodoAcumulacion;
	}
	public int getFrecuenciaInforme() {
		return frecuenciaInforme;
	}
	public void setFrecuenciaInforme(int frecuenciaInforme) {
		this.frecuenciaInforme = frecuenciaInforme;
	}
	public int getFrecuenciaFacturacion() {
		return frecuenciaFacturacion;
	}
	public void setFrecuenciaFacturacion(int frecuenciaFacturacion) {
		this.frecuenciaFacturacion = frecuenciaFacturacion;
	}
	public int getFrecuenciaComputosExtra() {
		return frecuenciaComputosExtra;
	}
	public void setFrecuenciaComputosExtra(int frecuenciaComputosExtra) {
		this.frecuenciaComputosExtra = frecuenciaComputosExtra;
	}
	public String getTiempoRespuesta() {
		return tiempoRespuesta;
	}
	public void setTiempoRespuesta(String tiempoRespuesta) {
		this.tiempoRespuesta = tiempoRespuesta;
	}
	public HorarioLaboral getHorarioLaboral() {
		return horarioLaboral;
	}
	public void setHorarioLaboral(HorarioLaboral idHorarioLaboral) {
		this.horarioLaboral = idHorarioLaboral;
	}
	
	

}
