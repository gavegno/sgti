package com.proyectodegrado.sgti.fachada;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.proyectodegrado.sgti.exceptions.SgtiException;
import com.proyectodegrado.sgti.modelo.Precio;
import com.proyectodegrado.sgti.servicios.ServicioPrecio;

public class FachadaPrecio {
	
	private ServicioPrecio servicioPrecio;
	
	public void insertarPrecio(final String fechaDesde, final String fechaHasta, final double precioAgregar, final String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException, SgtiException{
		SimpleDateFormat simpleFateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Precio precio = new Precio(precioAgregar, simpleFateFormat.parse(fechaDesde), simpleFateFormat.parse(fechaHasta));
		servicioPrecio.insertar(precio, idContrato);
	}

	public ServicioPrecio getServicioPrecio() {
		return servicioPrecio;
	}

	public void setServicioPrecio(ServicioPrecio servicioPrecio) {
		this.servicioPrecio = servicioPrecio;
	}
}
