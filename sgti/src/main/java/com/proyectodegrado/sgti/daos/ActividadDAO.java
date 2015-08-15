package com.proyectodegrado.sgti.daos;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.proyectodegrado.sgti.consultas.ConsultasActividad;
import com.proyectodegrado.sgti.modelo.Actividad;

public class ActividadDAO {
	
	private ConsultasActividad consultasActividad;
	
	public void insertarActividad(Actividad actividad) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException{
		consultasActividad.insertarActividad(actividad.getId(), actividad.getTipo(), new java.sql.Date(actividad.getFechaCreacion().getTime()), new java.sql.Date(actividad.getFechaActividad().getTime()), actividad.getIdUsuario(), actividad.getIdContrato(), actividad.getPeriodo());
	}
	
	public Actividad verActividad(String id) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		ResultSet resultSet = consultasActividad.verActividad(id);
		Actividad actividad = new Actividad();
		if(resultSet.next()){
			actividad.setId(resultSet.getString("id"));
			actividad.setTipo(resultSet.getString("tipo"));
			actividad.setFechaCreacion(resultSet.getDate("fechacreacion"));
			actividad.setFechaActividad(resultSet.getDate("fechaactividad"));
			actividad.setPeriodo(resultSet.getInt("periodo"));
			actividad.setIdContrato(resultSet.getString("contrato"));
			actividad.setIdUsuario(resultSet.getString("usuario"));
		}
		return actividad;		
	}
	
	public List<Actividad> verActividades() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		ResultSet resultSet = consultasActividad.verActividades();
		List<Actividad> actividades = new ArrayList<Actividad>();
		while(resultSet.next()){
			Actividad actividad = new Actividad();
			actividad.setId(resultSet.getString("id"));
			actividad.setTipo(resultSet.getString("tipo"));
			actividad.setFechaCreacion(resultSet.getDate("fechacreacion"));
			actividad.setFechaActividad(resultSet.getDate("fechaactividad"));
			actividad.setPeriodo(resultSet.getInt("periodo"));
			actividad.setIdContrato(resultSet.getString("contrato"));
			actividad.setIdUsuario(resultSet.getString("usuario"));
			actividades.add(actividad);
		}
		return actividades;
	}
	
	public List<Actividad> verActividadesPorUsuario(String idUsuario) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		ResultSet resultSet = consultasActividad.verActividadesPorUsuario(idUsuario);
		List<Actividad> actividades = new ArrayList<Actividad>();
		while(resultSet.next()){
			Actividad actividad = new Actividad();
			actividad.setId(resultSet.getString("id"));
			actividad.setTipo(resultSet.getString("tipo"));
			actividad.setFechaCreacion(resultSet.getDate("fechacreacion"));
			actividad.setFechaActividad(resultSet.getDate("fechaactividad"));
			actividad.setPeriodo(resultSet.getInt("periodo"));
			actividad.setIdContrato(resultSet.getString("contrato"));
			actividad.setIdUsuario(resultSet.getString("usuario"));
			actividades.add(actividad);
		}
		return actividades;
	}
	
	public List<Actividad> verActividadesPorContrato(String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		ResultSet resultSet = consultasActividad.verActividadesPorContrato(idContrato);
		List<Actividad> actividades = new ArrayList<Actividad>();
		while(resultSet.next()){
			Actividad actividad = new Actividad();
			actividad.setId(resultSet.getString("id"));
			actividad.setTipo(resultSet.getString("tipo"));
			actividad.setFechaCreacion(resultSet.getDate("fechacreacion"));
			actividad.setFechaActividad(resultSet.getDate("fechaactividad"));
			actividad.setPeriodo(resultSet.getInt("periodo"));
			actividad.setIdContrato(resultSet.getString("contrato"));
			actividad.setIdUsuario(resultSet.getString("usuario"));
			actividades.add(actividad);
		}
		return actividades;
	}
	
	public void borrarActividad(Actividad actividad) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException{
		consultasActividad.borrarActividad(actividad.getId());
	}
	
	public void editarActividad(Actividad actividad) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException{
		consultasActividad.editarActividad(actividad.getId(), new java.sql.Date(actividad.getFechaActividad().getTime()), actividad.getIdUsuario(), actividad.getPeriodo());
	}

	public ConsultasActividad getConsultasActividad() {
		return consultasActividad;
	}

	public void setConsultasActividad(ConsultasActividad consultasActividad) {
		this.consultasActividad = consultasActividad;
	}
	
}
