package com.proyectodegrado.sgti.servicios;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.proyectodegrado.sgti.modelo.Dia;
import com.proyectodegrado.sgti.modelo.HorarioLaboral;

public interface ServicioHorarioLaboral {

	public abstract void insertar(HorarioLaboral horarioLaboral)
			throws FileNotFoundException, IOException, SQLException, ClassNotFoundException;
	
	public abstract void insertarDíaAHorarioLaboral(HorarioLaboral horarioLaboral, Dia dia)
			throws FileNotFoundException, IOException, SQLException, ClassNotFoundException;

	public abstract HorarioLaboral seleccionarHorarioLaboral(
			String idHorarioLaboral) throws FileNotFoundException, IOException,
			SQLException, ClassNotFoundException;

	public abstract void editarDiaDeHorarioLaboral(String idHorarioLboral,
			Dia dia) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException;

	public abstract void borrarDiaDeHorarioLaboral(String idHorarioLaboral,
			Dia dia) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException;

	public List<HorarioLaboral> seleccionarHorariosLaborales()
			throws FileNotFoundException, IOException, SQLException,
			ClassNotFoundException;

	void borrarHorarioLaboral(String idHorarioLaboral)
			throws FileNotFoundException, IOException, SQLException,
			ClassNotFoundException;

	boolean horarioLaboralEnUso(String idHorarioLaboral)
			throws FileNotFoundException, ClassNotFoundException, IOException,
			SQLException;

}