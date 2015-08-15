package com.proyectodegrado.sgti.exceptions;

public class SgtiException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//private String mensaje;
	
	public SgtiException(String mensaje) throws SgtiException {
		super(mensaje);
	}
}
