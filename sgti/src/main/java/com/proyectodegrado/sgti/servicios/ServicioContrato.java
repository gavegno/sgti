package com.proyectodegrado.sgti.servicios;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.proyectodegrado.sgti.modelo.Contrato;

public interface ServicioContrato {

	public abstract void insertar(Contrato contrato)
			throws FileNotFoundException, IOException, SQLException, ClassNotFoundException;

	public abstract void insertarCompleto(Contrato contrato) throws FileNotFoundException,
			IOException, SQLException, ClassNotFoundException;
	
	public abstract List<Contrato> verContratosPorCliente (int idCliente) 
			throws FileNotFoundException, IOException, SQLException, ClassNotFoundException;
	
	public abstract List<Contrato> verContratosPorContraparte (String idContraparte) 
			throws FileNotFoundException, IOException, SQLException, ClassNotFoundException;

}