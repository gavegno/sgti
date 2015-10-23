package com.proyectodegrado.sgti.servicios;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.proyectodegrado.sgti.exceptions.SgtiException;
import com.proyectodegrado.sgti.modelo.Actividad;

public interface ServicioActividad {

	public abstract void agregar(Actividad actividad) throws FileNotFoundException,
			ClassNotFoundException, SQLException, IOException, SgtiException;

	public abstract List<Actividad> seleccionarActividades()
			throws FileNotFoundException, ClassNotFoundException, IOException,
			SQLException;

	public abstract List<Actividad> seleccionarActividadesPorUsuario(
			String idUsuario) throws FileNotFoundException,
			ClassNotFoundException, IOException, SQLException;

	public abstract List<Actividad> seleccionarActividadesPorContrato(
			String idContrato) throws FileNotFoundException,
			ClassNotFoundException, IOException, SQLException;

	public abstract Actividad seleccionarActividad(String idActividad)
			throws FileNotFoundException, ClassNotFoundException, IOException,
			SQLException;

	public abstract void editar(Actividad actividad)
			throws FileNotFoundException, ClassNotFoundException, SQLException,
			IOException;

	void borrar(Actividad actividad) throws FileNotFoundException,
			ClassNotFoundException, SQLException, IOException, SgtiException;

	List<Actividad> seleccionarActividadesConFechaDesde(Date fechaDesde)
			throws FileNotFoundException, ClassNotFoundException, IOException,
			SQLException;

	void asignarActividad(String id, String idUsuario)
			throws FileNotFoundException, ClassNotFoundException, IOException,
			SQLException;

	List<Actividad> verActividadesDeTecnicoYSusContratos(String idUsuario,
			Date fechaDesde) throws FileNotFoundException,
			ClassNotFoundException, IOException, SQLException;

	void cambiarEstadoActividad(Actividad actividad)
			throws FileNotFoundException, ClassNotFoundException, IOException,
			SQLException, SgtiException;

}