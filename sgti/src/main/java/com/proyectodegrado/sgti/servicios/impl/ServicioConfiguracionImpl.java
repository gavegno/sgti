package com.proyectodegrado.sgti.servicios.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import com.proyectodegrado.sgti.daos.ConfiguracionDAO;
import com.proyectodegrado.sgti.modelo.Configuracion;
import com.proyectodegrado.sgti.servicios.ServicioConfiguracion;

public class ServicioConfiguracionImpl implements ServicioConfiguracion {
	
	private ConfiguracionDAO configuracionDao;
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioConfiguracion#insertar(com.proyectodegrado.sgti.modelo.Configuracion)
	 */
	@Override
	public void insertar(Configuracion configuracion) throws FileNotFoundException, IOException, SQLException{
		configuracionDao.insertarConfiguracion(configuracion.getFechaInicio(), configuracion.getFechaFin(), configuracion.getRenovacion(), 
				configuracion.getPeriodoRenovacion(), configuracion.getTipoContrato(), configuracion.getComputosPaquete(), configuracion.getPeriodoValidezMes(),
				configuracion.getPeriodoValidezDia(), configuracion.isAcumulacion(), configuracion.getPeriodoAcumulacion(), configuracion.getFrecuenciaInforme(), 
				configuracion.getFrecuenciaFacturacion(), configuracion.getFrecuenciaComputosExtra(), configuracion.getTiempoRespuesta(), 
				configuracion.getHorarioLaboral().getId(), "");
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioConfiguracion#seleccionarConfiguracion(int)
	 */
	@Override
	public Configuracion seleccionarConfiguracion(int id) throws FileNotFoundException, SQLException, IOException{
		return configuracionDao.verConfiguracion(id);
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioConfiguracion#editarConfiguracion(com.proyectodegrado.sgti.modelo.Configuracion)
	 */
	@Override
	public void editarConfiguracion(Configuracion configuracion) throws FileNotFoundException, SQLException, IOException{
		configuracionDao.editarConfiguracion(configuracion.getId(), configuracion.getFechaInicio(), configuracion.getFechaFin(), configuracion.getRenovacion(),
				configuracion.getPeriodoRenovacion(), configuracion.getTipoContrato(), configuracion.getComputosPaquete(), configuracion.getPeriodoValidezMes(),
				configuracion.getPeriodoValidezDia(), configuracion.isAcumulacion(), configuracion.getPeriodoAcumulacion(), configuracion.getFrecuenciaInforme(),
				configuracion.getFrecuenciaFacturacion(), configuracion.getFrecuenciaComputosExtra(), configuracion.getTiempoRespuesta(), configuracion.getHorarioLaboral().getId());
	}

	public ConfiguracionDAO getConfiguracionDao() {
		return configuracionDao;
	}

	public void setConfiguracionDao(ConfiguracionDAO configuracionDao) {
		this.configuracionDao = configuracionDao;
	}
}