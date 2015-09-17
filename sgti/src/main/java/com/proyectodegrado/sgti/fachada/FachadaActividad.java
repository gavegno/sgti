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
	
	public void ingresarActividad(final String id, final String tipo, final int periodo, final String fecha, final String usuario, final String contrato, final String descripcion) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException, ParseException, SgtiException{
		SimpleDateFormat simpleFateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Actividad actividad = new Actividad(id,tipo,contrato,usuario,new Date(),simpleFateFormat.parse(fecha),periodo,descripcion);
		servicioActividad.agregar(actividad);
	}
	
	public void borrarActividad(final String id) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException, ParseException, SgtiException{
		Actividad actividad = new Actividad();
		actividad.setId(id);;
		servicioActividad.borrar(actividad);
	}
	
	public void editarActividad(final String id, final String tipo, final int periodo, final String fecha, final String usuario, final String contrato, final String descripcion) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException, ParseException{
		SimpleDateFormat simpleFateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Actividad actividad = new Actividad(id,tipo,contrato,usuario,new Date(),simpleFateFormat.parse(fecha),periodo,descripcion);
		servicioActividad.editar(actividad);
	}
	
	public List<Actividad> seleccionarActividades() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException {
		return servicioActividad.seleccionarActividades();
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
}
