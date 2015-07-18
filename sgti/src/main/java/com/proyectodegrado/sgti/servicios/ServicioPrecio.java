package com.proyectodegrado.sgti.servicios;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.proyectodegrado.sgti.modelo.Precio;

public interface ServicioPrecio {

	public abstract void insertar(Precio precio, String idContrato)
			throws FileNotFoundException, IOException, SQLException, ClassNotFoundException;

	public abstract List<Precio> seleccionarPrecios(String idContrato)
			throws FileNotFoundException, IOException, SQLException, ClassNotFoundException;

	public abstract Precio seleccionarPrecioActual(String idContrato)
			throws FileNotFoundException, IOException, SQLException, ClassNotFoundException;

}