package com.proyectodegrado.sgti.servicios.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import org.springframework.util.CollectionUtils;
import com.proyectodegrado.sgti.daos.ConfiguracionDAO;
import com.proyectodegrado.sgti.exceptions.SgtiException;
import com.proyectodegrado.sgti.modelo.Configuracion;
import com.proyectodegrado.sgti.servicios.ServicioConfiguracion;
import com.proyectodegrado.sgti.servicios.ServicioHorarioLaboral;

public class ServicioConfiguracionImpl implements ServicioConfiguracion {
	
	private ConfiguracionDAO configuracionDao;
	
	private ServicioHorarioLaboral servicioHorarioLaboral;
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioConfiguracion#insertar(com.proyectodegrado.sgti.modelo.Configuracion)
	 */
	@Override
	public void insertar(Configuracion configuracion, String idContrato) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException, SgtiException{
		if (configInicioMenorAFin(configuracion))
		{
			if(esPosibleInsertar(configuracion, idContrato)){
				configuracionDao.insertarConfiguracion(configuracion.getFechaInicio(), configuracion.getFechaFin(), configuracion.getRenovacion(), 
					configuracion.getPeriodoRenovacion(), configuracion.getTipoContrato(), configuracion.getComputosPaquete(), configuracion.getPeriodoValidezMes(),
					configuracion.getPeriodoValidezDia(), configuracion.isAcumulacion(), configuracion.getPeriodoAcumulacion(), configuracion.getFrecuenciaInforme(), 
					configuracion.getFrecuenciaFacturacion(), configuracion.getFrecuenciaComputosExtra(), configuracion.getTiempoRespuesta(), 
					configuracion.getHorarioLaboral().getId(), idContrato);
			}else
				throw new SgtiException("La configuración ingresada se superpone con otra configuración");
				
		}
		else
			throw new SgtiException("La fecha de inicio debe ser menor a la fecha de fin");	
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioConfiguracion#seleccionarConfiguracion(int)
	 */
	@Override
	public Configuracion seleccionarConfiguracion(int id) throws FileNotFoundException, SQLException, IOException, ClassNotFoundException{
		return configuracionDao.verConfiguracion(id);
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioConfiguracion#seleccionarConfiguracionActual(String)
	 */
	@Override
	public Configuracion seleccionarConfiguracionActual(String idContrato)	throws FileNotFoundException, SQLException, IOException, ClassNotFoundException{
		return configuracionDao.verConfiguracionActual(idContrato);
	}
	

	@Override
	public void editarConfiguracion(Configuracion configuracion) throws FileNotFoundException, SQLException, IOException, ClassNotFoundException, SgtiException{
		if (configInicioMenorAFin(configuracion))
		{
			if (esPosibleEditar(configuracion, configuracion.getIdContrato(), configuracion.getId())){
				configuracionDao.editarConfiguracion(configuracion.getId(), configuracion.getFechaInicio(), configuracion.getFechaFin(), configuracion.getRenovacion(),
						configuracion.getPeriodoRenovacion(), configuracion.getTipoContrato(), configuracion.getComputosPaquete(), configuracion.getPeriodoValidezMes(),
						configuracion.getPeriodoValidezDia(), configuracion.isAcumulacion(), configuracion.getPeriodoAcumulacion(), configuracion.getFrecuenciaInforme(),
						configuracion.getFrecuenciaFacturacion(), configuracion.getFrecuenciaComputosExtra(), configuracion.getTiempoRespuesta(), configuracion.getHorarioLaboral().getId());
				}
		}
		else
			throw new SgtiException("La fecha de inicio debe ser menor a la fecha de fin");	
	}
	
	@Override
	public void borrarConfiguracion (int id) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		configuracionDao.borrarConfiguracion(id);
	}
	
	/*private boolean esPosibleEditar(Configuracion configuracion, String idContrato) throws FileNotFoundException, SQLException, IOException, ClassNotFoundException{
		List<Configuracion> configsInicio = configuracionDao.verConfiguracionPorFecha(configuracion.getFechaInicio(), idContrato);
		List<Configuracion> configsFin = configuracionDao.verConfiguracionPorFecha(configuracion.getFechaFin(), idContrato);
		boolean condicion = false;
		
		
		switch (configsInicio.size()) {
		case 0:
			condicion = true;
			break;
		
		case 1:
			if (configsInicio.get(0).getId() == configuracion.getId())
				condicion = true;
			break;
			
		default:
			break;
		}
		
		if (condicion = true) condicion = false;
		
		switch (configsFin.size()) {
		case 0:
			condicion = true;
			break;
		
		case 1:
			if (configsFin.get(0).getId() == configuracion.getId())
				condicion = true;
			break;
			
		default:
			break;
		}
		
		return condicion;
	}*/

/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioConfiguracion#tieneConfiguracionPorVencer(java.util.Date, java.lang.String)
	 */
	@Override
	public boolean tieneConfiguracionPostVencer(Date fecha, String idContrato) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException{
		List<Configuracion> configuraciones = configuracionDao.verConfiguracionPorFecha(fecha, idContrato);
		if(CollectionUtils.isEmpty(configuraciones)){
			return false;
		}
		return true;
	}
	
	private boolean esPosibleInsertar(Configuracion configuracion, String idContrato) throws FileNotFoundException, SQLException, IOException, ClassNotFoundException{
		return (configuracionDao.saberSiEsPosibleInsertarNuevaConfig(configuracion.getFechaInicio(), configuracion.getFechaFin(), idContrato).size() == 0);
	}
	
	private boolean esPosibleEditar(Configuracion configuracion, String idContrato, int idConfiguracion) throws FileNotFoundException, SQLException, IOException, ClassNotFoundException{
		return (configuracionDao.saberSiEsPosibleEditarConfig(configuracion.getFechaInicio(), configuracion.getFechaFin(), idContrato, idConfiguracion).size() == 0);
	}
	
	private boolean configInicioMenorAFin(Configuracion config)
	{
		return fechasInicioMenorAFin(config.getFechaInicio(), config.getFechaFin());
	}
	
	private boolean fechasInicioMenorAFin(Date fechaInicio, Date fechaFin)
	{
		return fechaInicio.before(fechaFin);
	}
	
	@Override
	public List<Configuracion> seleccionarConfiguracionesPorContrato(String idContrato)	throws FileNotFoundException, SQLException, IOException, ClassNotFoundException{
		return configuracionDao.verConfiguracionesPorContrato(idContrato);
	}
	
	@Override
	public List<Configuracion> seleccionarConfiguracionActualTodos()	throws FileNotFoundException, SQLException, IOException, ClassNotFoundException{
		return configuracionDao.verConfiguracionActualTodos();
	}
	
	@Override
	public String verHorarioLaboralDeConfiguracion(int id) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException
	{
		return configuracionDao.verHorarioLaboralDeConfiguracion(id);
	}
	
	public ConfiguracionDAO getConfiguracionDao() {
		return configuracionDao;
	}

	public void setConfiguracionDao(ConfiguracionDAO configuracionDao) {
		this.configuracionDao = configuracionDao;
	}

	public ServicioHorarioLaboral getServicioHorarioLaboral() {
		return servicioHorarioLaboral;
	}

	public void setServicioHorarioLaboral(
			ServicioHorarioLaboral servicioHorarioLaboral) {
		this.servicioHorarioLaboral = servicioHorarioLaboral;
	}
	
	
}
