package com.proyectodegrado.sgti.fachada;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.proyectodegrado.sgti.exceptions.SgtiException;
import com.proyectodegrado.sgti.modelo.Actividad;
import com.proyectodegrado.sgti.servicios.ServicioActividad;

public class FachadaActividad {
	
	private ServicioActividad servicioActividad;
	
	public void ingresarActividad(final String id, final String tipo, final int periodo, final String fecha, final String usuario, final String contrato, final String descripcion, final String estado) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException, ParseException, SgtiException{
		SimpleDateFormat simpleFateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Actividad actividad = new Actividad(id,tipo,contrato,usuario,new Date(),simpleFateFormat.parse(fecha),periodo,descripcion,estado);
		servicioActividad.agregar(actividad);
	}
	
	public void borrarActividad(final String id) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException, ParseException, SgtiException{
		Actividad actividad = new Actividad();
		actividad.setId(id);
		servicioActividad.borrar(actividad);
	}
	
	public void asignarActividad(String id, String idUsuario) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		servicioActividad.asignarActividad(id, idUsuario);
	}
	
	public void editarActividad(final String id, final String tipo, final int periodo, final String fecha, final String usuario, final String contrato, final String descripcion, final String estado) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException, ParseException{
		SimpleDateFormat simpleFateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Actividad actividad = new Actividad(id,tipo,contrato,usuario,new Date(),simpleFateFormat.parse(fecha),periodo,descripcion,estado);
		servicioActividad.editar(actividad);
	}
	
	public void cambiarEstadoActividad (final String id) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, SgtiException
	{
		String estadoNuevo;
		Actividad act = servicioActividad.seleccionarActividad(id);
		estadoNuevo = act.getEstado();
		
		if ((estadoNuevo.equalsIgnoreCase("pendiente")))
			estadoNuevo = "REALIZADA";
		else
			estadoNuevo = "PENDIENTE";
		
		Actividad actCambiada = new Actividad();
		actCambiada.setId(id);
		actCambiada.setEstado(estadoNuevo);
		servicioActividad.cambiarEstadoActividad(actCambiada);
	}
	
	public List<Actividad> seleccionarActividades() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		return servicioActividad.seleccionarActividades();
	}
	
	public List<Actividad> verActividadesDeTecnicoYSusContratos(String idUsuario, Date fechaDesde) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException {
	
		
		return servicioActividad.verActividadesDeTecnicoYSusContratos(idUsuario,fechaDesde);
	}
	
	public List<Actividad> seleccionarActividadesConFechaDesde(Date fechaDesde) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		return servicioActividad.seleccionarActividadesConFechaDesde(fechaDesde);
	}
	
	public List<Actividad> seleccionarActividadesPorUsuario(String idUsuario) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		return servicioActividad.seleccionarActividadesPorUsuario(idUsuario);
	}

	public ServicioActividad getServicioActividad() {
		return servicioActividad;
	}

	public void setServicioActividad(ServicioActividad servicioActividad) {
		this.servicioActividad = servicioActividad;
	}
	
	public List<Actividad> seleccionarActividadesFiltradas(Date fechaDesde, String validadas, String usuario) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		return servicioActividad.seleccionarActividadesConFechaDesde(fechaDesde);
	}
}
