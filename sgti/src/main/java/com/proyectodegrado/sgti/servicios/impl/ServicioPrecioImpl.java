package com.proyectodegrado.sgti.servicios.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.proyectodegrado.sgti.daos.PrecioDAO;
import com.proyectodegrado.sgti.modelo.Precio;
import com.proyectodegrado.sgti.servicios.ServicioPrecio;

public class ServicioPrecioImpl implements ServicioPrecio {
	
	private PrecioDAO precioDao;
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioPrecio#insertar(com.proyectodegrado.sgti.modelo.Precio, java.lang.String)
	 */
	@Override
	public void insertar(Precio precio, String idContrato) throws FileNotFoundException, IOException, SQLException{
		if(esPosibleInsertar(precio,idContrato)){
			precioDao.insertarPrecio(precio.getPrecio(), precio.getFechaDesde(), precio.getFechaHasta(), idContrato);
		}else{
			throw new SQLException("El precio ingresado se superpone con otro precio");
		}
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioPrecio#verPrecios(java.lang.String)
	 */
	@Override
	public List<Precio> seleccionarPrecios (String idContrato) throws FileNotFoundException, IOException, SQLException{
		return precioDao.verPrecios(idContrato);
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioPrecio#verPrecioActual(java.lang.String)
	 */
	@Override
	public Precio seleccionarPrecioActual (String idContrato) throws FileNotFoundException, IOException, SQLException{
		return precioDao.verPrecioActual(idContrato);
	}
	
	private boolean esPosibleInsertar(Precio precio, String idContrato) throws FileNotFoundException, IOException, SQLException{
		return precioDao.verPreciosPorFecha(idContrato, precio.getFechaDesde()).size() == 0 && precioDao.verPreciosPorFecha(idContrato, precio.getFechaHasta()).size() == 0;
	}

	public PrecioDAO getPrecioDao() {
		return precioDao;
	}

	public void setPrecioDao(PrecioDAO precioDao) {
		this.precioDao = precioDao;
	}
}
