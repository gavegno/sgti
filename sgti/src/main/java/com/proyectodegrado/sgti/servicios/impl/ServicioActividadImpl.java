package com.proyectodegrado.sgti.servicios.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

import com.proyectodegrado.sgti.daos.ActividadDAO;
import com.proyectodegrado.sgti.exceptions.SgtiException;
import com.proyectodegrado.sgti.modelo.Actividad;
import com.proyectodegrado.sgti.servicios.ServicioActividad;
import com.proyectodegrado.sgti.servicios.ServicioUsuario;

public class ServicioActividadImpl implements ServicioActividad {
	
	private ActividadDAO actividadDao;
	
	private ServicioUsuario servicioUsuario;
	
	private ServicioEnvioMail servicioEnvioMail;
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioActividad#agregar(com.proyectodegrado.sgti.modelo.Actividad, java.lang.String, java.lang.String)
	 */
	@Override
	public void agregar(Actividad actividad) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException, SgtiException{
		if(actividadDao.verActividad(actividad.getId()).getId()== null){
			actividadDao.insertarActividad(actividad);
			if(actividad.getIdUsuario() != null){
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				servicioEnvioMail.enviarMail("Nueva Actividad", servicioUsuario.selecionarUsuario(actividad.getIdUsuario()).getEmail(), actividad.getId(), format.format(actividad.getFechaActividad()), actividad.getDescripcion(), actividad.getEstado());
			}
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
	public List<Actividad> seleccionarActividadesPendientes() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		return actividadDao.verActividadesPendientes();
	}
	
	@Override
	public List<Actividad> seleccionarActividadesPendientesPorContrato(String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		return actividadDao.verActividadesPendientesPorContrato(idContrato);
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
	
	@Override
	public List<Actividad> verActividadesPendientesPorUsuarioYContrato(String idUsuario, String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		return actividadDao.verActividadesPendientesPorUsuarioYContrato(idUsuario, idContrato);
	}
	
	@Override
	public List<Actividad> verActividadesPendientesPorUsuario(String idUsuario) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		return actividadDao.verActividadesPendientesPorUsuario(idUsuario);
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
		if (actividadDao.actividadAsignadaAHora(actividad.getId()).size() > 0){
			throw new SgtiException("No se puede borrar: existen horas que usan esta actividad");
		}
		else{
			Actividad actividadParaMail = seleccionarActividad(actividad.getId());
			if(actividadParaMail.getIdUsuario() != null){
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				servicioEnvioMail.enviarMail("Actividad Eliminada", servicioUsuario.selecionarUsuario(actividadParaMail.getIdUsuario()).getEmail(), actividadParaMail.getId(), format.format(actividadParaMail.getFechaActividad()), "Esta actividad NO deberá realizarse", "ELIMINADA");
			}
			actividadDao.borrarActividad(actividad);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioActividad#editar(com.proyectodegrado.sgti.modelo.Actividad, java.lang.String)
	 */
	@Override
	public void editar(Actividad actividad) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException{
		Actividad actividadNoEditada = actividadDao.verActividad(actividad.getId());
		if(!actividadNoEditada.getIdUsuario().equalsIgnoreCase(actividad.getIdUsuario())){
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			servicioEnvioMail.enviarMail("Actividad Eliminada", servicioUsuario.selecionarUsuario(actividadNoEditada.getIdUsuario()).getEmail(), actividadNoEditada.getId(), format.format(actividadNoEditada.getFechaActividad()), "Esta actividad NO deberá realizarse", actividadNoEditada.getEstado());
			if(!StringUtils.isEmpty(actividad.getIdUsuario())){
				servicioEnvioMail.enviarMail("Nueva Actividad", servicioUsuario.selecionarUsuario(actividad.getIdUsuario()).getEmail(), actividad.getId(), format.format(actividad.getFechaActividad()), actividad.getDescripcion(), actividad.getEstado());
			}
		}else{
			if(actividad.getIdUsuario() != null){
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				servicioEnvioMail.enviarMail("Actividad Modificada", servicioUsuario.selecionarUsuario(actividad.getIdUsuario()).getEmail(), actividad.getId(), format.format(actividad.getFechaActividad()), actividad.getDescripcion(), actividad.getEstado());
			}
		}
		actividadDao.editarActividad(actividad);
		
		
	}
	
	@Override
	public void cambiarEstadoActividad (Actividad actividad) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, SgtiException
	{
		if (actividadDao.actividadAsignadaAHora(actividad.getId()).size() > 0){
			Actividad actividadCompleta = actividadDao.verActividad(actividad.getId());
			if(actividadCompleta.getPeriodo() > 0){
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(actividadCompleta.getFechaActividad());
				calendar.add(Calendar.DATE, actividadCompleta.getPeriodo());
				actividadCompleta.setFechaActividad(calendar.getTime());
				editar(actividadCompleta);
			}else{
				actividadDao.cambiarEstadoActividad(actividad);
			}
		}
		else{
			throw new SgtiException("La actividad no fue usada en ninguna hora, no puede marcarse como Realizada");
		}
	}

	public ActividadDAO getActividadDao() {
		return actividadDao;
	}

	public void setActividadDao(ActividadDAO actividadDao) {
		this.actividadDao = actividadDao;
	}

	public ServicioUsuario getServicioUsuario() {
		return servicioUsuario;
	}

	public void setServicioUsuario(ServicioUsuario servicioUsuario) {
		this.servicioUsuario = servicioUsuario;
	}

	public ServicioEnvioMail getServicioEnvioMail() {
		return servicioEnvioMail;
	}

	public void setServicioEnvioMail(ServicioEnvioMail servicioEnvioMail) {
		this.servicioEnvioMail = servicioEnvioMail;
	}
	
}
