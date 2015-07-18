package com.proyectodegrado.sgti.servicios;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import com.proyectodegrado.sgti.modelo.Contrato;

public interface ServicioContrato {

	public abstract void insertar(Contrato contrato)
			throws FileNotFoundException, IOException, SQLException, ClassNotFoundException;

	void insertarCompleto(Contrato contrato) throws FileNotFoundException,
			IOException, SQLException, ClassNotFoundException;

}