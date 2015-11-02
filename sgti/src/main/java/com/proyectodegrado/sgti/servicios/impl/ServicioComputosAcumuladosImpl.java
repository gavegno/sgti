package com.proyectodegrado.sgti.servicios.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.proyectodegrado.sgti.daos.ComputosAcumuladosDAO;
import com.proyectodegrado.sgti.modelo.ComputoAcumulado;
import com.proyectodegrado.sgti.servicios.ServicioComputosAcumulados;

public class ServicioComputosAcumuladosImpl implements ServicioComputosAcumulados {
	
	private ComputosAcumuladosDAO computosAcumuladosDao;
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioCOmputosAcumulados#insertar(com.proyectodegrado.sgti.modelo.ComputoAcumulado)
	 */
	@Override
	public void insertar(ComputoAcumulado computoAcumulado) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		computosAcumuladosDao.agregar(computoAcumulado.getFecha(), computoAcumulado.getIdContrato(), computoAcumulado.getComputos());
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioCOmputosAcumulados#verPrecios(java.lang.String)
	 */
	@Override
	public List<ComputoAcumulado> verComputosAcumulados (String idContrato) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		return computosAcumuladosDao.verComputosAcumulados(idContrato);
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioCOmputosAcumulados#verPrecios(java.lang.String, java.math.int)
	 */
	@Override
	public double verComputosAcumuladosUsables (String idContrato, int periodoAcumulacion) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		double computosAcumulados = 0.0;
		Calendar fechaAComparar = Calendar.getInstance();
		fechaAComparar.setTime(new Date());
		fechaAComparar.add(Calendar.MONTH, -periodoAcumulacion);
		for(ComputoAcumulado computoAcumulado : verComputosAcumulados(idContrato)){
			if(computoAcumulado.getFecha().after(fechaAComparar.getTime())){
				computosAcumulados += computoAcumulado.getComputos();
			}
		}
		return computosAcumulados;
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioCOmputosAcumulados#borrar(com.proyectodegrado.sgti.modelo.ComputoAcumulado)
	 */
	@Override
	public void borrar(ComputoAcumulado computoAcumulado) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		computosAcumuladosDao.borrar(computoAcumulado.getId());
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioCOmputosAcumulados#editar(com.proyectodegrado.sgti.modelo.ComputoAcumulado)
	 */
	@Override
	public void editar(ComputoAcumulado computoAcumulado) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		computosAcumuladosDao.editar(computoAcumulado.getId(), computoAcumulado.getComputos());
	}

	public ComputosAcumuladosDAO getComputosAcumuladosDao() {
		return computosAcumuladosDao;
	}

	public void setComputosAcumuladosDao(ComputosAcumuladosDAO computosAcumuladosDao) {
		this.computosAcumuladosDao = computosAcumuladosDao;
	}
	
}
