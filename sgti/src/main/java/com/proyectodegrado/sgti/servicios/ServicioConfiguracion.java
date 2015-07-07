package com.proyectodegrado.sgti.servicios;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import com.proyectodegrado.sgti.modelo.Configuracion;

public interface ServicioConfiguracion {

	public abstract void insertar(Configuracion configuracion)
			throws FileNotFoundException, IOException, SQLException;

	public abstract Configuracion seleccionarConfiguracion(int id)
			throws FileNotFoundException, SQLException, IOException;

	public abstract void editarConfiguracion(Configuracion configuracion)
			throws FileNotFoundException, SQLException, IOException;

}