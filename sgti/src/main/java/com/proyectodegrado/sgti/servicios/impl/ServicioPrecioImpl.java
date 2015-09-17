package com.proyectodegrado.sgti.servicios.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.proyectodegrado.sgti.daos.PrecioDAO;
import com.proyectodegrado.sgti.exceptions.SgtiException;
import com.proyectodegrado.sgti.modelo.Precio;
import com.proyectodegrado.sgti.servicios.ServicioPrecio;

public class ServicioPrecioImpl implements ServicioPrecio {
	
	private PrecioDAO precioDao;
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioPrecio#insertar(com.proyectodegrado.sgti.modelo.Precio, java.lang.String)
	 */
	@Override
	public void insertar(Precio precio, String idContrato) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException, SgtiException{
		if (precioInicioMenorAFin(precio))
		{
			if(esPosibleInsertar(precio, idContrato)){
				precioDao.insertarPrecio(precio.getPrecio(), precio.getPrecioExtra(), precio.getFechaDesde(), precio.getFechaHasta(), idContrato);
			}else
				throw new SgtiException("El precio ingresado se superpone con otro precio");
		}
		else
			throw new SgtiException("La fecha de inicio debe ser menor a la fecha de fin");
	}
	
	@Override
	public void editarPrecio(Precio precioNuevo, Precio precioViejo, String idContrato) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException, SgtiException{
		if (precioInicioMenorAFin(precioNuevo))
		{
			if (esPosibleEditar(precioNuevo, precioViejo, idContrato))
			{
				borrarPrecio(idContrato, precioViejo);
				insertar(precioNuevo, idContrato);
			}
			else
				throw new SgtiException("El precio que quiere guardar se superpone con otro precio");
		}
		else
			throw new SgtiException("La fecha de inicio debe ser menor a la fecha de fin");
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioPrecio#verPrecios(java.lang.String)
	 */
	@Override
	public List<Precio> seleccionarPrecios (String idContrato) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		return precioDao.verPrecios(idContrato);
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioPrecio#verPrecioActual(java.lang.String)
	 */
	@Override
	public Precio seleccionarPrecioActual (String idContrato) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		return precioDao.verPrecioActual(idContrato);
	}
	
	@Override
	public List<Precio> seleccionarPrecioActualTodos() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		return precioDao.verPrecioActualTodos();
	}
	
	@Override
	public List<Precio> seleccionarPreciosPorContrato(String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		return precioDao.verPrecios(idContrato);
	}
	
	@Override
	public Precio verPrecioExacto (String idContrato, double precio, String fechaDesde, String fechaHasta) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		return precioDao.verPrecioExacto(idContrato, precio, fechaDesde, fechaHasta);
	}
	
	@Override
	public void borrarPrecio(String idContrato, Precio precio) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException 
	{
		precioDao.borrarPrecio(idContrato, precio.getPrecio(), precio.getFechaDesde(), precio.getFechaHasta());
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioPrecio#tienePrecioPostVencer(java.util.Date, java.lang.String)
	 */
	@Override
	public boolean tienePrecioPostVencer(Date fecha, String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		List<Precio> precios = precioDao.verPreciosPorFecha(idContrato, fecha);
		if(CollectionUtils.isEmpty(precios)){
			return false;
		}
		return true;
	}
	
	private boolean esPosibleInsertar(Precio precio, String idContrato) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		boolean resultado = false;
		List<Precio> precios = precioDao.saberSiPuedoInsertarNuevoPrecio(idContrato, precio.getPrecio(), precio.getFechaDesde(), precio.getFechaHasta());
		if (precios.size() == 0)
		{
				resultado = true;
		}
		return resultado;
	}
	
	private boolean esPosibleEditar(Precio precioNuevo, Precio precioViejo, String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		boolean resultado = false;
		List<Precio> precios = precioDao.saberSiPuedoEditarPrecio(idContrato, precioViejo.getPrecio(), precioViejo.getFechaDesde(), precioViejo.getFechaHasta(), precioNuevo.getFechaDesde(), precioNuevo.getFechaHasta());
		if (precios.size() == 0)
		{
				resultado = true;
		}
		return resultado;
	}
	
	private boolean precioInicioMenorAFin (Precio p)
	{
		return fechasInicioMenorAFin(p.getFechaDesde(), p.getFechaHasta());
	}
	
	private boolean fechasInicioMenorAFin(Date fechaInicio, Date fechaFin)
	{
		return fechaInicio.before(fechaFin);
	}

	public PrecioDAO getPrecioDao() {
		return precioDao;
	}

	public void setPrecioDao(PrecioDAO precioDao) {
		this.precioDao = precioDao;
	}
}
