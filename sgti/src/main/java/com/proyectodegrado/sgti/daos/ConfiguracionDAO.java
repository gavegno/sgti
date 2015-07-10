package com.proyectodegrado.sgti.daos;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.proyectodegrado.sgti.consultas.ConsultasConfiguracion;
import com.proyectodegrado.sgti.modelo.Configuracion;

public class ConfiguracionDAO {
	
	private ConsultasConfiguracion consultasConfiguracion;
	
	public void insertarConfiguracion(Date fechaInicio, Date fechaFin, String renovacion, int periodoRenovacion,
			 String tipoContrato, int computosPaquete, int peroidoValidezMes, int periodoValidezDia, boolean acumulacion,
			 int periodoAcumulacion, int frecuenciaInforme, int frecuenciaFacturacion, int frecuenciaComputosExtra,
			 String tiempoRespuesta, String idHorarioLaboral, String idContrato) throws FileNotFoundException, IOException, SQLException{

		consultasConfiguracion.insertarConfiguracion(new java.sql.Date(fechaInicio.getTime()), new java.sql.Date(fechaFin.getTime()), renovacion, periodoRenovacion, tipoContrato, computosPaquete, 
				peroidoValidezMes, periodoValidezDia, acumulacion, periodoAcumulacion, frecuenciaInforme, frecuenciaFacturacion, frecuenciaComputosExtra, 
				tiempoRespuesta, idHorarioLaboral, idContrato);
	}
	
	public Configuracion verConfiguracion(int id) throws FileNotFoundException, SQLException, IOException{
		Configuracion configuracion = new Configuracion();
		ResultSet resultSet = consultasConfiguracion.verConfiguracion(id);
		if(resultSet.next()){
			configuracion.setId(resultSet.getInt("id"));
			configuracion.setFechaInicio(resultSet.getDate("fechainicio"));
			configuracion.setFechaFin(resultSet.getDate("fechafin"));
			configuracion.setRenovacion(resultSet.getString("renovacion"));
			configuracion.setPeriodoRenovacion(resultSet.getInt("periodorenovacion"));
			configuracion.setTipoContrato(resultSet.getString("tipocontrato"));
			configuracion.setComputosPaquete(resultSet.getInt("computospaquete"));
			configuracion.setPeriodoValidezMes(resultSet.getInt("periodovalidezmes"));
			configuracion.setPeriodoValidezDia(resultSet.getInt("periodovalidezdia"));
			configuracion.setAcumulacion(resultSet.getBoolean("acumulacion"));
			configuracion.setPeriodoAcumulacion(resultSet.getInt("periodoacumulacion"));
			configuracion.setFrecuenciaInforme(resultSet.getInt("frecuenciainforme"));
			configuracion.setFrecuenciaFacturacion(resultSet.getInt("frecuenciafacturacion"));
			configuracion.setFrecuenciaComputosExtra(resultSet.getInt("frecuenciacomputosextra"));
			configuracion.setTiempoRespuesta(resultSet.getString("tiemporespuesta"));
		}
		return configuracion;
	}
	
	public List<Configuracion> verConfiguracionesEntreFechasFin(Date fechaFinMayorQue, Date fechaFinMenorQue) throws FileNotFoundException, SQLException, IOException{
		List<Configuracion> configuraciones = new ArrayList<Configuracion>();
		ResultSet resultSet = consultasConfiguracion.verConfiguracionesEntreFechasFin(new java.sql.Date(fechaFinMayorQue.getTime()), new java.sql.Date(fechaFinMenorQue.getTime()));
		while(resultSet.next()){
			Configuracion configuracion = new Configuracion();
			configuracion.setId(resultSet.getInt("id"));
			configuracion.setFechaInicio(resultSet.getDate("fechainicio"));
			configuracion.setFechaFin(resultSet.getDate("fechafin"));
			configuracion.setRenovacion(resultSet.getString("renovacion"));
			configuracion.setPeriodoRenovacion(resultSet.getInt("periodorenovacion"));
			configuracion.setTipoContrato(resultSet.getString("tipocontrato"));
			configuracion.setComputosPaquete(resultSet.getInt("computospaquete"));
			configuracion.setPeriodoValidezMes(resultSet.getInt("periodovalidezmes"));
			configuracion.setPeriodoValidezDia(resultSet.getInt("periodovalidezdia"));
			configuracion.setAcumulacion(resultSet.getBoolean("acumulacion"));
			configuracion.setPeriodoAcumulacion(resultSet.getInt("periodoacumulacion"));
			configuracion.setFrecuenciaInforme(resultSet.getInt("frecuenciainforme"));
			configuracion.setFrecuenciaFacturacion(resultSet.getInt("frecuenciafacturacion"));
			configuracion.setFrecuenciaComputosExtra(resultSet.getInt("frecuenciacomputosextra"));
			configuracion.setTiempoRespuesta(resultSet.getString("tiemporespuesta"));
			configuraciones.add(configuracion);
		}
		return configuraciones;
	}
	
	public Configuracion verConfiguracionActual(String idContrato) throws FileNotFoundException, SQLException, IOException{
		Configuracion configuracion = new Configuracion();
		ResultSet resultSet = consultasConfiguracion.verConfiguracionActual(idContrato);
		if(resultSet.next()){
			configuracion.setId(resultSet.getInt("id"));
			configuracion.setFechaInicio(resultSet.getDate("fechainicio"));
			configuracion.setFechaFin(resultSet.getDate("fechafin"));
			configuracion.setRenovacion(resultSet.getString("renovacion"));
			configuracion.setPeriodoRenovacion(resultSet.getInt("periodorenovacion"));
			configuracion.setTipoContrato(resultSet.getString("tipocontrato"));
			configuracion.setComputosPaquete(resultSet.getInt("computospaquete"));
			configuracion.setPeriodoValidezMes(resultSet.getInt("periodovalidezmes"));
			configuracion.setPeriodoValidezDia(resultSet.getInt("periodovalidezdia"));
			configuracion.setAcumulacion(resultSet.getBoolean("acumulacion"));
			configuracion.setPeriodoAcumulacion(resultSet.getInt("periodoacumulacion"));
			configuracion.setFrecuenciaInforme(resultSet.getInt("frecuenciainforme"));
			configuracion.setFrecuenciaFacturacion(resultSet.getInt("frecuenciafacturacion"));
			configuracion.setFrecuenciaComputosExtra(resultSet.getInt("frecuenciacomputosextra"));
			configuracion.setTiempoRespuesta(resultSet.getString("tiemporespuesta"));
		}
		return configuracion;
	}
	
	public List<Configuracion> verConfiguracionPorFecha(Date fecha, String idContrato) throws FileNotFoundException, SQLException, IOException{
		List<Configuracion> configuraciones = new ArrayList<Configuracion>();
		ResultSet resultSet = consultasConfiguracion.verConfiguracionPorFecha(new java.sql.Date(fecha.getTime()), idContrato);
		while(resultSet.next()){
			Configuracion configuracion = new Configuracion();
			configuracion.setId(resultSet.getInt("id"));
			configuracion.setFechaInicio(resultSet.getDate("fechainicio"));
			configuracion.setFechaFin(resultSet.getDate("fechafin"));
			configuracion.setRenovacion(resultSet.getString("renovacion"));
			configuracion.setPeriodoRenovacion(resultSet.getInt("periodorenovacion"));
			configuracion.setTipoContrato(resultSet.getString("tipocontrato"));
			configuracion.setComputosPaquete(resultSet.getInt("computospaquete"));
			configuracion.setPeriodoValidezMes(resultSet.getInt("periodovalidezmes"));
			configuracion.setPeriodoValidezDia(resultSet.getInt("periodovalidezdia"));
			configuracion.setAcumulacion(resultSet.getBoolean("acumulacion"));
			configuracion.setPeriodoAcumulacion(resultSet.getInt("periodoacumulacion"));
			configuracion.setFrecuenciaInforme(resultSet.getInt("frecuenciainforme"));
			configuracion.setFrecuenciaFacturacion(resultSet.getInt("frecuenciafacturacion"));
			configuracion.setFrecuenciaComputosExtra(resultSet.getInt("frecuenciacomputosextra"));
			configuracion.setTiempoRespuesta(resultSet.getString("tiemporespuesta"));
			configuraciones.add(configuracion);
		}
		return configuraciones;
	}
	
	public List<Configuracion> verConfiguracionesPorContrato(String idContrato) throws FileNotFoundException, SQLException, IOException{
		List<Configuracion> configuraciones = new ArrayList<Configuracion>();
		ResultSet resultSet = consultasConfiguracion.verConfiguracionesPorContrato(idContrato);
		while(resultSet.next()){
			Configuracion configuracion = new Configuracion();
			configuracion.setId(resultSet.getInt("id"));
			configuracion.setFechaInicio(resultSet.getDate("fechainicio"));
			configuracion.setFechaFin(resultSet.getDate("fechafin"));
			configuracion.setRenovacion(resultSet.getString("renovacion"));
			configuracion.setPeriodoRenovacion(resultSet.getInt("periodorenovacion"));
			configuracion.setTipoContrato(resultSet.getString("tipocontrato"));
			configuracion.setComputosPaquete(resultSet.getInt("computospaquete"));
			configuracion.setPeriodoValidezMes(resultSet.getInt("periodovalidezmes"));
			configuracion.setPeriodoValidezDia(resultSet.getInt("periodovalidezdia"));
			configuracion.setAcumulacion(resultSet.getBoolean("acumulacion"));
			configuracion.setPeriodoAcumulacion(resultSet.getInt("periodoacumulacion"));
			configuracion.setFrecuenciaInforme(resultSet.getInt("frecuenciainforme"));
			configuracion.setFrecuenciaFacturacion(resultSet.getInt("frecuenciafacturacion"));
			configuracion.setFrecuenciaComputosExtra(resultSet.getInt("frecuenciacomputosextra"));
			configuracion.setTiempoRespuesta(resultSet.getString("tiemporespuesta"));
			configuraciones.add(configuracion);
		}
		return configuraciones;
	}
	
	public void editarConfiguracion(int id, Date fechaInicio, Date fechaFin, String renovacion, int periodoRenovacion,
			 String tipoContrato, int computosPaquete, int periodoValidezMes, int periodoValidezDia, boolean acumulacion,
			 int periodoAcumulacion, int frecuenciaInforme, int frecuenciaFacturacion, int frecuenciaComputosExtra,
			 String tiempoRespuesta, String idHorarioLaboral) throws SQLException, FileNotFoundException, IOException{
		consultasConfiguracion.editarConfiguracion(id, new java.sql.Date(fechaInicio.getTime()), new java.sql.Date(fechaFin.getTime()), renovacion, periodoRenovacion, tipoContrato, computosPaquete, periodoValidezMes, 
				periodoValidezDia, acumulacion, periodoAcumulacion, frecuenciaInforme, frecuenciaFacturacion, frecuenciaComputosExtra, tiempoRespuesta, idHorarioLaboral);
	}

	public ConsultasConfiguracion getConsultasConfiguracion() {
		return consultasConfiguracion;
	}

	public void setConsultasConfiguracion(
			ConsultasConfiguracion consultasConfiguracion) {
		this.consultasConfiguracion = consultasConfiguracion;
	}
}
