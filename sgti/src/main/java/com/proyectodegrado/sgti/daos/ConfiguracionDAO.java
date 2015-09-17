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
			 String tiempoRespuesta, String idHorarioLaboral, String idContrato) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{

		consultasConfiguracion.insertarConfiguracion(new java.sql.Date(fechaInicio.getTime()), new java.sql.Date(fechaFin.getTime()), renovacion, periodoRenovacion, tipoContrato, computosPaquete, 
				peroidoValidezMes, periodoValidezDia, acumulacion, periodoAcumulacion, frecuenciaInforme, frecuenciaFacturacion, frecuenciaComputosExtra, 
				tiempoRespuesta, idHorarioLaboral, idContrato);
	}
	
	public Configuracion verConfiguracion(int id) throws FileNotFoundException, SQLException, IOException, ClassNotFoundException{
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
			configuracion.setIdContrato(resultSet.getString("id_contrato"));
		}
		return configuracion;
	}
	
	public String verHorarioLaboralDeConfiguracion(int id) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException
	{
		String horario = "";
		ResultSet resultSet = consultasConfiguracion.verConfiguracion(id);
		if(resultSet.next()){
			horario = resultSet.getString("id_horariolaboral");
		}
		return horario;
	}
	
	public List<Configuracion> verConfiguracionesEntreFechasFin(Date fechaFinMayorQue, Date fechaFinMenorQue) throws FileNotFoundException, SQLException, IOException, ClassNotFoundException{
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
			configuracion.setIdContrato(resultSet.getString("id_contrato"));
			configuraciones.add(configuracion);
		}
		return configuraciones;
	}
	
	public Configuracion verConfiguracionActual(String idContrato) throws FileNotFoundException, SQLException, IOException, ClassNotFoundException{
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
			configuracion.setIdContrato(resultSet.getString("id_contrato"));
		}
		return configuracion;
	}
	
	public List<Configuracion> verConfiguracionActualTodos() throws FileNotFoundException, SQLException, IOException, ClassNotFoundException{
		List<Configuracion> configuraciones = new ArrayList<Configuracion>();
		ResultSet resultSet = consultasConfiguracion.verConfiguracionActualTodos();
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
			configuracion.setIdContrato(resultSet.getString("id_contrato"));
			configuraciones.add(configuracion);
		}
		return configuraciones;
	}
	
	public List<Configuracion> verConfiguracionPorFecha(Date fecha, String idContrato) throws FileNotFoundException, SQLException, IOException, ClassNotFoundException{
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
			configuracion.setIdContrato(resultSet.getString("id_contrato"));
			configuraciones.add(configuracion);
		}
		return configuraciones;
	}
	
	public List<Configuracion> verConfiguracionesPorContrato(String idContrato) throws FileNotFoundException, SQLException, IOException, ClassNotFoundException{
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
			configuracion.setIdContrato(resultSet.getString("id_contrato"));
			configuraciones.add(configuracion);
		}
		return configuraciones;
	}
	
	public void editarConfiguracion(int id, Date fechaInicio, Date fechaFin, String renovacion, int periodoRenovacion,
			 String tipoContrato, int computosPaquete, int periodoValidezMes, int periodoValidezDia, boolean acumulacion,
			 int periodoAcumulacion, int frecuenciaInforme, int frecuenciaFacturacion, int frecuenciaComputosExtra,
			 String tiempoRespuesta, String idHorarioLaboral) throws SQLException, FileNotFoundException, IOException, ClassNotFoundException{
		
		
		consultasConfiguracion.editarConfiguracion(id, new java.sql.Date(fechaInicio.getTime()), new java.sql.Date(fechaFin.getTime()), renovacion, periodoRenovacion, tipoContrato, computosPaquete, periodoValidezMes, 
				periodoValidezDia, acumulacion, periodoAcumulacion, frecuenciaInforme, frecuenciaFacturacion, frecuenciaComputosExtra, tiempoRespuesta, idHorarioLaboral);
	}
	
	public List<Configuracion> saberSiEsPosibleInsertarNuevaConfig(Date inicio, Date fin, String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{		
		List<Configuracion> configuraciones = new ArrayList<Configuracion>();
		ResultSet resultSet = consultasConfiguracion.saberSiEsPosibleInsertarNuevaConfig(
				new java.sql.Date(inicio.getTime()), 
				new java.sql.Date(fin.getTime()), idContrato);
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
			configuracion.setIdContrato(resultSet.getString("id_contrato"));
			configuraciones.add(configuracion);
		}
		return configuraciones;
	}
	
	public List<Configuracion> saberSiEsPosibleEditarConfig(Date inicio, Date fin, String idContrato, int idConfiguracion) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{		
		List<Configuracion> configuraciones = new ArrayList<Configuracion>();
		ResultSet resultSet = consultasConfiguracion.saberSiEsPosibleEditarConfig(
				new java.sql.Date(inicio.getTime()), 
				new java.sql.Date(fin.getTime()), idContrato, idConfiguracion);
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
			configuracion.setIdContrato(resultSet.getString("id_contrato"));
			configuraciones.add(configuracion);
		}
		return configuraciones;
	}
	
	
	public void borrarConfiguracion(int id) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		consultasConfiguracion.borrarConfiguracion(id);
	}

	public ConsultasConfiguracion getConsultasConfiguracion() {
		return consultasConfiguracion;
	}

	public void setConsultasConfiguracion(
			ConsultasConfiguracion consultasConfiguracion) {
		this.consultasConfiguracion = consultasConfiguracion;
	}
}
