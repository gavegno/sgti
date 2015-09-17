package com.proyectodegrado.sgti.servicios;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.proyectodegrado.sgti.exceptions.SgtiException;
import com.proyectodegrado.sgti.modelo.Precio;

public interface ServicioPrecio {

	public abstract void insertar(Precio precio, String idContrato)
			throws FileNotFoundException, IOException, SQLException, ClassNotFoundException, SgtiException;

	public abstract List<Precio> seleccionarPrecios(String idContrato)
			throws FileNotFoundException, IOException, SQLException, ClassNotFoundException;

	public abstract Precio seleccionarPrecioActual(String idContrato)
			throws FileNotFoundException, IOException, SQLException, ClassNotFoundException;

	List<Precio> seleccionarPrecioActualTodos() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException;

	List<Precio> seleccionarPreciosPorContrato(String idContrato)
			throws FileNotFoundException, ClassNotFoundException, IOException,
			SQLException;

	Precio verPrecioExacto(String idContrato, double precio, String fechaDesde,
			String fechaHasta) throws FileNotFoundException,
			ClassNotFoundException, IOException, SQLException;

	void borrarPrecio(String idContrato, Precio precio)
			throws FileNotFoundException, ClassNotFoundException, IOException,
			SQLException;

	void editarPrecio(Precio precioNuevo, Precio precioViejo, String idContrato)
			throws FileNotFoundException, IOException, SQLException,
			ClassNotFoundException, SgtiException;

	boolean tienePrecioPostVencer(Date fecha, String idContrato)
			throws FileNotFoundException, ClassNotFoundException, IOException,
			SQLException;

}