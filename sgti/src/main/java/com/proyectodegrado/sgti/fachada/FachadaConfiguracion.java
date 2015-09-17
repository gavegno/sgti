package com.proyectodegrado.sgti.fachada;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.proyectodegrado.sgti.exceptions.SgtiException;
import com.proyectodegrado.sgti.modelo.Configuracion;
import com.proyectodegrado.sgti.modelo.HorarioLaboral;
import com.proyectodegrado.sgti.servicios.ServicioConfiguracion;
import com.proyectodegrado.sgti.servicios.ServicioHorarioLaboral;

public class FachadaConfiguracion {
	
	private ServicioConfiguracion servicioConfiguracion;
	
	private ServicioHorarioLaboral servicioHorarioLaboral;
	
	public void insertarConfiguracion(final String fechaDesde, final String fechaHasta, 
			final int periodoRenovacion, final String tipoRenovacion, 
			final String tipoContrato, final int computos, 
			final String unidadValidez, final int periodoValidez, 
			final boolean acumulacion, final int periodoAcumulacion, 
			final int frecuenciaInforme, final int frecuenciaFacturacion, 
			int frecuenciaComputosExtra, final String tiempoRespuesta, 
			final String horarioLaboral, final String idContrato) 
					throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException, SgtiException{
		SimpleDateFormat simpleFateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Configuracion configuracion = new Configuracion();
		configuracion.setFechaInicio(simpleFateFormat.parse(fechaDesde));
		configuracion.setFechaFin(simpleFateFormat.parse(fechaHasta));
		configuracion.setRenovacion(tipoRenovacion);
		configuracion.setPeriodoRenovacion(periodoRenovacion);
		configuracion.setTipoContrato(tipoContrato);
		configuracion.setComputosPaquete(computos);
		if(unidadValidez.equalsIgnoreCase("DIA")){
			configuracion.setPeriodoValidezDia(periodoValidez);
		}else{
			configuracion.setPeriodoValidezMes(periodoValidez);
		}
		configuracion.setAcumulacion(acumulacion);
		configuracion.setPeriodoAcumulacion(periodoAcumulacion);
		configuracion.setFrecuenciaInforme(frecuenciaInforme);
		configuracion.setFrecuenciaFacturacion(frecuenciaFacturacion);
		configuracion.setFrecuenciaComputosExtra(frecuenciaComputosExtra);
		configuracion.setTiempoRespuesta(tiempoRespuesta);
		configuracion.setHorarioLaboral(servicioHorarioLaboral.seleccionarHorarioLaboral(horarioLaboral));
		servicioConfiguracion.insertar(configuracion, idContrato);
		
	}
	
	public void editarConfiguracion(final int idConfiguracion, 
			final String fechaDesde, final String fechaHasta, 
			final int periodoRenovacion, final String tipoRenovacion, 
			final String tipoContrato, final int computos, 
			final String unidadValidez, final int periodoValidez, 
			final boolean acumulacion, final int periodoAcumulacion, 
			final int frecuenciaInforme, final int frecuenciaFacturacion, 
			final int frecuenciaComputosExtra, final String tiempoRespuesta, 
			final String horarioLaboral, final String idContrato) 
					throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException, SgtiException{
		SimpleDateFormat simpleFateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Configuracion configuracion = new Configuracion();
		configuracion.setId(idConfiguracion);
		configuracion.setFechaInicio(simpleFateFormat.parse(fechaDesde));
		configuracion.setFechaFin(simpleFateFormat.parse(fechaHasta));
		configuracion.setRenovacion(tipoRenovacion);
		configuracion.setPeriodoRenovacion(periodoRenovacion);
		configuracion.setTipoContrato(tipoContrato);
		configuracion.setComputosPaquete(computos);
		if(unidadValidez.equalsIgnoreCase("DIA")){
			configuracion.setPeriodoValidezDia(periodoValidez);
		}else{
			configuracion.setPeriodoValidezMes(periodoValidez);
		}
		configuracion.setAcumulacion(acumulacion);
		configuracion.setPeriodoAcumulacion(periodoAcumulacion);
		configuracion.setFrecuenciaInforme(frecuenciaInforme);
		configuracion.setFrecuenciaFacturacion(frecuenciaFacturacion);
		configuracion.setFrecuenciaComputosExtra(frecuenciaComputosExtra);
		configuracion.setTiempoRespuesta(tiempoRespuesta);
		configuracion.setHorarioLaboral(servicioHorarioLaboral.seleccionarHorarioLaboral(horarioLaboral));
		
		servicioConfiguracion.editarConfiguracion(configuracion);
		
	}
	
	public Configuracion seleccionarConfiguracionActual (String idContrato) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException
	{
		return servicioConfiguracion.seleccionarConfiguracionActual(idContrato);
	}
	
	public List<Configuracion> seleccionarConfiguracionActualTodos() throws FileNotFoundException, ClassNotFoundException, SQLException, IOException
	{
		return servicioConfiguracion.seleccionarConfiguracionActualTodos();
	}
	
	public List<Configuracion> seleccionarConfiguracionPorContrato(String idContrato) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException
	{
		return servicioConfiguracion.seleccionarConfiguracionesPorContrato(idContrato);
	}
	
	public List<HorarioLaboral> verHorariosLaborales() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		return servicioHorarioLaboral.seleccionarHorariosLaborales();
	}
	
	public Configuracion seleccionarConfiguracionPorId (int idConfiguracion) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException
	{
		return servicioConfiguracion.seleccionarConfiguracion(idConfiguracion);
	}
	
	public String verHorarioLaboralDeConfiguracion(int id) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException
	{
		return servicioConfiguracion.verHorarioLaboralDeConfiguracion(id);
	}
	
	public void borrarConfiguracion (int id) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		servicioConfiguracion.borrarConfiguracion(id);
	}
	
	

	public ServicioConfiguracion getServicioConfiguracion() {
		return servicioConfiguracion;
	}

	public void setServicioConfiguracion(ServicioConfiguracion servicioConfiguracion) {
		this.servicioConfiguracion = servicioConfiguracion;
	}

	public ServicioHorarioLaboral getServicioHorarioLaboral() {
		return servicioHorarioLaboral;
	}

	public void setServicioHorarioLaboral(
			ServicioHorarioLaboral servicioHorarioLaboral) {
		this.servicioHorarioLaboral = servicioHorarioLaboral;
	}
}
