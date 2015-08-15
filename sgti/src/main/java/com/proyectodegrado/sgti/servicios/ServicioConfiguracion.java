package com.proyectodegrado.sgti.servicios;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import com.proyectodegrado.sgti.exceptions.SgtiException;
import com.proyectodegrado.sgti.modelo.Configuracion;

public interface ServicioConfiguracion {

	public abstract void insertar(Configuracion configuracion, String idContrato)
			throws FileNotFoundException, IOException, SQLException, ClassNotFoundException, SgtiException;

	public abstract Configuracion seleccionarConfiguracion(int id)
			throws FileNotFoundException, SQLException, IOException, ClassNotFoundException;
	
	public abstract Configuracion seleccionarConfiguracionActual(String idContrato)
			throws FileNotFoundException, SQLException, IOException, ClassNotFoundException;

	public abstract void editarConfiguracion(Configuracion configuracion)
			throws FileNotFoundException, SQLException, IOException, ClassNotFoundException;

}