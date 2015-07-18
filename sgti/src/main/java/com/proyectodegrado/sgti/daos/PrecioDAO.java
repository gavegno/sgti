package com.proyectodegrado.sgti.daos;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.proyectodegrado.sgti.consultas.ConsultasPrecio;
import com.proyectodegrado.sgti.modelo.Precio;

public class PrecioDAO {
	
	private ConsultasPrecio consultasPrecio;
	
	public void insertarPrecio (double precio, Date fechadesde, Date fechahasta, String idContrato) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		consultasPrecio.insertarPrecio(precio, new java.sql.Date(fechadesde.getTime()), new java.sql.Date(fechahasta.getTime()), idContrato);
	}
	
	public List<Precio> verPrecios (String idContrato) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		List<Precio> precios = new ArrayList<Precio>();
		ResultSet resultSet = consultasPrecio.verPrecios(idContrato);
		while(resultSet.next()){
			Precio precio = new Precio();
			precio.setPrecio(resultSet.getDouble("precio"));
			precio.setFechaDesde(resultSet.getDate("fechadesde"));
			precio.setFechaHasta(resultSet.getDate("fechahasta"));
			precios.add(precio);
		}
		return precios;
	}
	
	public Precio verPrecioActual (String idContrato) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Precio precio = new Precio();
		ResultSet resultSet = consultasPrecio.verPrecioActual(idContrato);
		if(resultSet.next()){
			precio.setPrecio(resultSet.getDouble("precio"));
			precio.setFechaDesde(resultSet.getDate("fechadesde"));
			precio.setFechaHasta(resultSet.getDate("fechahasta"));
		}
		return precio;
	}
	
	public List<Precio> verPreciosPorFecha (String idContrato, Date fecha) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		List<Precio> precios = new ArrayList<Precio>();
		ResultSet resultSet = consultasPrecio.verPreciosPorFecha(idContrato, new java.sql.Date(fecha.getTime()));
		while(resultSet.next()){
			Precio precio = new Precio();
			precio.setPrecio(resultSet.getDouble("precio"));
			precio.setFechaDesde(resultSet.getDate("fechadesde"));
			precio.setFechaHasta(resultSet.getDate("fechahasta"));
			precios.add(precio);
		}
		return precios;
	}

	public ConsultasPrecio getConsultasPrecio() {
		return consultasPrecio;
	}

	public void setConsultasPrecio(ConsultasPrecio consultasPrecio) {
		this.consultasPrecio = consultasPrecio;
	}
}
