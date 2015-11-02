package com.proyectodegrado.sgti.servicios;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.proyectodegrado.sgti.modelo.ComputoAcumulado;

public interface ServicioComputosAcumulados {

	public abstract void insertar(ComputoAcumulado computoAcumulado)
			throws FileNotFoundException, ClassNotFoundException, IOException,
			SQLException;

	public abstract List<ComputoAcumulado> verComputosAcumulados(String idContrato)
			throws FileNotFoundException, IOException, SQLException,
			ClassNotFoundException;

	public abstract void borrar(ComputoAcumulado computoAcumulado)
			throws FileNotFoundException, ClassNotFoundException, IOException,
			SQLException;

	public abstract void editar(ComputoAcumulado computoAcumulado)
			throws FileNotFoundException, ClassNotFoundException, IOException,
			SQLException;

	double verComputosAcumuladosUsables(String idContrato,
			int periodoAcumulacion) throws FileNotFoundException, IOException,
			SQLException, ClassNotFoundException;

}