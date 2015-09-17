package com.proyectodegrado.sgti.fachada;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.proyectodegrado.sgti.exceptions.SgtiException;
import com.proyectodegrado.sgti.modelo.Precio;
import com.proyectodegrado.sgti.servicios.ServicioPrecio;

public class FachadaPrecio {
	
	private ServicioPrecio servicioPrecio;
	
	public void insertarPrecio(final String fechaDesde, final String fechaHasta, final double precioAgregar, final double precioExtraAgregar, final String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException, SgtiException{
		SimpleDateFormat simpleFateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Precio precio = new Precio(precioAgregar, precioExtraAgregar,
				simpleFateFormat.parse(fechaDesde), 
				simpleFateFormat.parse(fechaHasta));
		servicioPrecio.insertar(precio, idContrato);
	}
	
	public void editarPrecio(
			final String fechaDesde, 
			final String fechaHasta, 
			final double precioAgregar, 
			final double precioExtraAgregar,
			final String fechaDesdeOriginal, 
			final String fechaHastaOriginal, 
			final double precioOriginal, 
			final double precioExtraOriginal,
			final String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException, SgtiException{
		
		
		SimpleDateFormat simpleFateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Precio precioNuevo = new Precio(precioAgregar, precioExtraAgregar, simpleFateFormat.parse(fechaDesde), simpleFateFormat.parse(fechaHasta));
		Precio precioViejo = new Precio(precioOriginal, precioExtraOriginal, simpleFateFormat.parse(fechaDesdeOriginal), simpleFateFormat.parse(fechaHastaOriginal));
		
		servicioPrecio.editarPrecio(precioNuevo, precioViejo, idContrato);
	}
	
	public List<Precio> seleccionarPrecioActualTodos() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		return servicioPrecio.seleccionarPrecioActualTodos();
	}
	
	public List<Precio> seleccionarPreciosPorContrato(String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		return servicioPrecio.seleccionarPreciosPorContrato(idContrato);
	}
	
	public Precio verPrecioExacto (String idContrato, double precio, String fechaDesde, String fechaHasta) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		return servicioPrecio.verPrecioExacto(idContrato, precio, fechaDesde, fechaHasta);
	}

	public ServicioPrecio getServicioPrecio() {
		return servicioPrecio;
	}
	
	public void borrarPrecio(final String fechaDesde, final String fechaHasta, final double precioAgregar, final double precioExtraAgregar, final String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException
	{
		SimpleDateFormat simpleFateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Precio precio = new Precio(precioAgregar, precioExtraAgregar, simpleFateFormat.parse(fechaDesde), simpleFateFormat.parse(fechaHasta));
		servicioPrecio.borrarPrecio(idContrato, precio);
	}

	public void setServicioPrecio(ServicioPrecio servicioPrecio) {
		this.servicioPrecio = servicioPrecio;
	}
}
