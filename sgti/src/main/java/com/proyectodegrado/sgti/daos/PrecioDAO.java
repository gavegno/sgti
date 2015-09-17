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
	
	public void insertarPrecio (double precio, double precioExtra, Date fechadesde, Date fechahasta, String idContrato) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		consultasPrecio.insertarPrecio(precio, precioExtra, new java.sql.Date(fechadesde.getTime()), new java.sql.Date(fechahasta.getTime()), idContrato);
	}
	
	public List<Precio> verPrecios (String idContrato) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		List<Precio> precios = new ArrayList<Precio>();
		ResultSet resultSet = consultasPrecio.verPrecios(idContrato);
		while(resultSet.next()){
			Precio precio = new Precio();
			precio.setPrecio(resultSet.getDouble("precio"));
			precio.setPrecioExtra(resultSet.getDouble("precioExtra"));
			precio.setFechaDesde(resultSet.getDate("fechadesde"));
			precio.setFechaHasta(resultSet.getDate("fechahasta"));
			precio.setIdContrato(resultSet.getString("idcontrato"));
			precios.add(precio);
		}
		return precios;
	}
	
	public Precio verPrecioActual (String idContrato) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Precio precio = new Precio();
		ResultSet resultSet = consultasPrecio.verPrecioActual(idContrato);
		if(resultSet.next()){
			precio.setPrecio(resultSet.getDouble("precio"));
			precio.setPrecioExtra(resultSet.getDouble("precioExtra"));
			precio.setFechaDesde(resultSet.getDate("fechadesde"));
			precio.setFechaHasta(resultSet.getDate("fechahasta"));
			precio.setIdContrato(resultSet.getString("idcontrato"));
		}
		return precio;
	}
	
	public Precio verPrecioExacto (String idContrato, double precio, String fechaDesde, String fechaHasta) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Precio p = new Precio();
		ResultSet resultSet = consultasPrecio.verPrecioExacto(idContrato, precio, fechaDesde, fechaHasta);
		if(resultSet.next()){
			p.setPrecio(resultSet.getDouble("precio"));
			p.setPrecioExtra(resultSet.getDouble("precioExtra"));
			p.setFechaDesde(resultSet.getDate("fechadesde"));
			p.setFechaHasta(resultSet.getDate("fechahasta"));
			p.setIdContrato(resultSet.getString("idcontrato"));
		}
		return p;
	}
	
	public List<Precio> verPrecioActualTodos () throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		List<Precio> precios = new ArrayList<Precio>();
		ResultSet resultSet = consultasPrecio.verPrecioActualTodos();
		while(resultSet.next()){
			Precio precio = new Precio();
			precio.setPrecio(resultSet.getDouble("precio"));
			precio.setPrecioExtra(resultSet.getDouble("precioExtra"));
			precio.setFechaDesde(resultSet.getDate("fechadesde"));
			precio.setFechaHasta(resultSet.getDate("fechahasta"));
			precio.setIdContrato(resultSet.getString("idcontrato"));
			precios.add(precio);
		}
		return precios;
	}
	
	public List<Precio> verPreciosPorFecha (String idContrato, Date fecha) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		List<Precio> precios = new ArrayList<Precio>();
		ResultSet resultSet = consultasPrecio.verPreciosPorFecha(idContrato, new java.sql.Date(fecha.getTime()));
		while(resultSet.next()){
			Precio precio = new Precio();
			precio.setPrecio(resultSet.getDouble("precio"));
			precio.setPrecioExtra(resultSet.getDouble("precioExtra"));
			precio.setFechaDesde(resultSet.getDate("fechadesde"));
			precio.setFechaHasta(resultSet.getDate("fechahasta"));
			precio.setIdContrato(resultSet.getString("idcontrato"));
			precios.add(precio);
		}
		return precios;
	}
	
	public List<Precio> saberSiPuedoInsertarNuevoPrecio (String idContrato, double precioValor, Date fechaFuturaInicio, Date fechaFuturaFin) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		List<Precio> precios = new ArrayList<Precio>();
		ResultSet resultSet = consultasPrecio.saberSiPuedoInsertarNuevoPrecio(idContrato, 
				precioValor, new java.sql.Date(fechaFuturaInicio.getTime()), 
				new java.sql.Date(fechaFuturaFin.getTime()));
		
		while(resultSet.next()){
			Precio precio = new Precio();
			precio.setPrecio(resultSet.getDouble("precio"));
			precio.setPrecioExtra(resultSet.getDouble("precioExtra"));
			precio.setFechaDesde(resultSet.getDate("fechadesde"));
			precio.setFechaHasta(resultSet.getDate("fechahasta"));
			precio.setIdContrato(resultSet.getString("idcontrato"));
			precios.add(precio);
		}
		return precios;
	}
	
	public List<Precio> saberSiPuedoEditarPrecio (String idContrato, double precioValor, Date fechadesdeActual, Date fechahastaActual, Date fechaFuturaInicio, Date fechaFuturaFin) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		List<Precio> precios = new ArrayList<Precio>();
		ResultSet resultSet = consultasPrecio.saberSiPuedoEditarPrecio(idContrato, 
				precioValor, new java.sql.Date(fechadesdeActual.getTime()), 
				new java.sql.Date(fechahastaActual.getTime()), 
				new java.sql.Date(fechaFuturaInicio.getTime()),
				new java.sql.Date(fechaFuturaFin.getTime()));
		
		while(resultSet.next()){
			Precio precio = new Precio();
			precio.setPrecio(resultSet.getDouble("precio"));
			precio.setPrecioExtra(resultSet.getDouble("precioExtra"));
			precio.setFechaDesde(resultSet.getDate("fechadesde"));
			precio.setFechaHasta(resultSet.getDate("fechahasta"));
			precio.setIdContrato(resultSet.getString("idcontrato"));
			precios.add(precio);
		}
		return precios;
	}
	
	public void borrarPrecio(String idContrato, double precio, Date fechaDesde, Date fechaHasta) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		consultasPrecio.borrarPrecio(idContrato, precio, new java.sql.Date(fechaDesde.getTime()), new java.sql.Date(fechaHasta.getTime()));
	}

	public ConsultasPrecio getConsultasPrecio() {
		return consultasPrecio;
	}

	public void setConsultasPrecio(ConsultasPrecio consultasPrecio) {
		this.consultasPrecio = consultasPrecio;
	}
}
