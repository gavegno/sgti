package com.proyectodegrado.sgti.modelo;

public class Informe {
	
	private int minutosConsumidos;
	private double computosConsumidos;
	private Double precioTotal;
	private Double precioExtraTotal;
	
	public int getMinutosConsumidos() {
		return minutosConsumidos;
	}
	public void setMinutosConsumidos(int minutosConsumidos) {
		this.minutosConsumidos = minutosConsumidos;
	}
	public Double getPrecioTotal() {
		return precioTotal;
	}
	public void setPrecioTotal(Double precioTotal) {
		this.precioTotal = precioTotal;
	}
	public Double getPrecioExtraTotal() {
		return precioExtraTotal;
	}
	public void setPrecioExtraTotal(Double precioExtraTotal) {
		this.precioExtraTotal = precioExtraTotal;
	}
	public double getComputosConsumidos() {
		return computosConsumidos;
	}
	public void setComputosConsumidos(double computosConsumidos) {
		this.computosConsumidos = computosConsumidos;
	}
	
}
