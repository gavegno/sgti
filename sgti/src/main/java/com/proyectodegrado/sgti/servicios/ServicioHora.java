package com.proyectodegrado.sgti.servicios;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import com.proyectodegrado.sgti.modelo.Hora;

public interface ServicioHora {

	public abstract void agregar(Hora hora) throws FileNotFoundException,
			ClassNotFoundException, SQLException, IOException;

	public abstract List<Hora> seleccionarHorasRegistradas()
			throws FileNotFoundException, ClassNotFoundException, IOException,
			SQLException, ParseException;

	public abstract List<Hora> seleccionarHorasRegistradasPorUsuario(
			String idUsuario) throws FileNotFoundException,
			ClassNotFoundException, IOException, SQLException, ParseException;

	public abstract List<Hora> seleccionarHorasRegistradasPorContrato(
			String idContrato) throws FileNotFoundException,
			ClassNotFoundException, IOException, SQLException, ParseException;

	public abstract List<Hora> seleccionarHorasRegistradasNoFacturadas(
			String idContrato) throws FileNotFoundException,
			ClassNotFoundException, IOException, SQLException, ParseException;

	public abstract List<Hora> seleccionarHorasRegistradasNoInformadas(
			String idContrato) throws FileNotFoundException,
			ClassNotFoundException, IOException, SQLException, ParseException;

	public abstract void editar(Hora hora) throws FileNotFoundException,
			ClassNotFoundException, SQLException, IOException;

	void borrar(int id) throws FileNotFoundException, ClassNotFoundException,
			IOException, SQLException;

	Hora seleccionarHora(int id) throws FileNotFoundException,
			ClassNotFoundException, IOException, SQLException, ParseException;

}