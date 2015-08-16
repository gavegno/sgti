package com.proyectodegrado.sgti.fachada;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.proyectodegrado.sgti.exceptions.SgtiException;
import com.proyectodegrado.sgti.modelo.Configuracion;
import com.proyectodegrado.sgti.modelo.HorarioLaboral;
import com.proyectodegrado.sgti.servicios.ServicioConfiguracion;
import com.proyectodegrado.sgti.servicios.ServicioHorarioLaboral;

public class FachadaConfiguracion {
	
	private ServicioConfiguracion servicioConfiguracion;
	
	private ServicioHorarioLaboral servicioHorarioLaboral;
	
	public void insertarConfiguracion(@RequestParam("fechaDesde") final String fechaDesde, @RequestParam("fechaHasta") final String fechaHasta, 
			@RequestParam("periodoRenovacion") final int periodoRenovacion, @RequestParam("tipoRenovacion") final String tipoRenovacion, 
			@RequestParam("tipoContrato") final String tipoContrato, @RequestParam("computos") final int computos, 
			@RequestParam("unidadValidez") final String unidadValidez, @RequestParam("periodoValidez") final int periodoValidez, 
			@RequestParam("acumulacion") final boolean acumulacion, @RequestParam("periodoAcumulacion") final int periodoAcumulacion, 
			@RequestParam("frecuenciaInforme") final int frecuenciaInforme, @RequestParam("frecuenciaFacturacion") final int frecuenciaFacturacion, 
			@RequestParam("frecuenciaExtra") final int frecuenciaComputosExtra, @RequestParam("respuesta") final String tiempoRespuesta, 
			@RequestParam("horarioLaboral") final String horarioLaboral, @RequestParam("idContrato") final String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException, SgtiException{
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
	
	
	
	public List<HorarioLaboral> verHorariosLaborales() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		return servicioHorarioLaboral.seleccionarHorariosLaborales();
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
