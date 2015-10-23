package com.proyectodegrado.sgti.servicios.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.proyectodegrado.sgti.daos.ActividadDAO;
import com.proyectodegrado.sgti.exceptions.SgtiException;
import com.proyectodegrado.sgti.modelo.Actividad;
import com.proyectodegrado.sgti.servicios.ServicioActividad;

public class ServicioActividadImpl implements ServicioActividad {
	
	private ActividadDAO actividadDao;
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioActividad#agregar(com.proyectodegrado.sgti.modelo.Actividad, java.lang.String, java.lang.String)
	 */
	@Override
	public void agregar(Actividad actividad) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException, SgtiException{
		if(actividadDao.verActividad(actividad.getId()).getId()== null){
			actividadDao.insertarActividad(actividad);
		}else{
			throw new SgtiException("Ya existe una actividad con el id ingresado");
		}
	}
	
	@Override
	public void asignarActividad(String id, String idUsuario) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		actividadDao.asignarActividad(id, idUsuario);
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioActividad#seleccionarActividades()
	 */
	@Override
	public List<Actividad> seleccionarActividades() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		return actividadDao.verActividades();
	}
	
	
	@Override
	public List<Actividad> verActividadesDeTecnicoYSusContratos(String idUsuario, Date fechaDesde) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		return actividadDao.verActividadesDeTecnicoYSusContratos(idUsuario, fechaDesde);
	}
	
	@Override
	public List<Actividad> seleccionarActividadesConFechaDesde(Date fechaDesde) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		return actividadDao.verActividadesConFechaDesde(fechaDesde);
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioActividad#seleccionarActividadesPorUsuario(java.lang.String)
	 */
	@Override
	public List<Actividad> seleccionarActividadesPorUsuario(String idUsuario) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		return actividadDao.verActividadesPorUsuario(idUsuario);
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioActividad#seleccionarActividadesPorContrato(java.lang.String)
	 */
	@Override
	public List<Actividad> seleccionarActividadesPorContrato(String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		return actividadDao.verActividadesPorContrato(idContrato);
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioActividad#seleccionarActividad(java.lang.String)
	 */
	@Override
	public Actividad seleccionarActividad(String idActividad) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		return actividadDao.verActividad(idActividad);
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioActividad#borrar(com.proyectodegrado.sgti.modelo.Actividad, java.lang.String)
	 */
	@Override
	public void borrar(Actividad actividad) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException, SgtiException{
		if (actividadDao.actividadAsignadaAHora(actividad.getId()).size() > 0)
			throw new SgtiException("No se puede borrar: existen horas que usan esta actividad");
		else	
			actividadDao.borrarActividad(actividad);
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioActividad#editar(com.proyectodegrado.sgti.modelo.Actividad, java.lang.String)
	 */
	@Override
	public void editar(Actividad actividad) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException{
		actividadDao.editarActividad(actividad);
	}
	
	@Override
	public void cambiarEstadoActividad (Actividad actividad) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, SgtiException
	{
		if (actividadDao.actividadAsignadaAHora(actividad.getId()).size() > 0)
			actividadDao.cambiarEstadoActividad(actividad);
		else
			throw new SgtiException("La actividad no fue usada en ninguna hora, no puede marcarse como Realizada");
	}

	public ActividadDAO getActividadDao() {
		return actividadDao;
	}

	public void setActividadDao(ActividadDAO actividadDao) {
		this.actividadDao = actividadDao;
	}
}
