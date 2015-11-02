package com.proyectodegrado.sgti.servicios;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import com.proyectodegrado.sgti.dto.DataNotificacion;
import com.proyectodegrado.sgti.modelo.Actividad;
import com.proyectodegrado.sgti.modelo.Configuracion;
import com.proyectodegrado.sgti.modelo.Contrato;
import com.proyectodegrado.sgti.modelo.Hora;
import com.proyectodegrado.sgti.modelo.Precio;

public interface ServicioNotificacion {

	public abstract List<Precio> verPreciosAVencer(int dias)
			throws FileNotFoundException, ClassNotFoundException, IOException,
			SQLException;

	public abstract List<Configuracion> verConfiguracionesAVencer(int dias)
			throws FileNotFoundException, ClassNotFoundException, IOException,
			SQLException;

	List<Contrato> verContratosConHorasAInformar(int dias)
			throws FileNotFoundException, ClassNotFoundException, IOException,
			SQLException, ParseException;

	List<Hora> horasAInformar(String idContrato, int dias)
			throws FileNotFoundException, ClassNotFoundException, IOException,
			SQLException, ParseException;

	int cantidadNotificacionesSocio(String idUsuario, int dias) throws FileNotFoundException,
			ClassNotFoundException, IOException, SQLException, ParseException;

	List<Actividad> verActividadesARealizar(String idUsuario, int dias)
			throws FileNotFoundException, ClassNotFoundException, IOException,
			SQLException;

	List<DataNotificacion> notificar(String tipoUsuario, String idUsuario,
			int dias) throws FileNotFoundException, ClassNotFoundException,
			IOException, SQLException, ParseException;

	List<Contrato> verContratosConConfiguracionesAVencer(int dias)
			throws FileNotFoundException, ClassNotFoundException, IOException,
			SQLException;

	List<Contrato> verContratosConPreciosAVencer(int dias)
			throws FileNotFoundException, ClassNotFoundException, IOException,
			SQLException;

}