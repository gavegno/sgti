package com.proyectodegrado.sgti.servicios.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import com.proyectodegrado.sgti.modelo.Dia;
import com.proyectodegrado.sgti.modelo.HorarioLaboral;

public interface ServicioHorarioLaboral {

	public abstract void insertar(HorarioLaboral horarioLaboral)
			throws FileNotFoundException, IOException, SQLException;

	public abstract HorarioLaboral seleccionarHorarioLaboral(
			String idHorarioLaboral) throws FileNotFoundException, IOException,
			SQLException;

	public abstract void editarDiaDeHorarioLaboral(String idHorarioLboral,
			Dia dia) throws FileNotFoundException, IOException, SQLException;

	public abstract void borrarDiaDeHorarioLaboral(String idHorarioLaboral,
			Dia dia) throws FileNotFoundException, IOException, SQLException;

}