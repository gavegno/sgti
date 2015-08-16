package com.proyectodegrado.sgti.fachada;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.proyectodegrado.sgti.modelo.Hora;
import com.proyectodegrado.sgti.servicios.ServicioContrato;
import com.proyectodegrado.sgti.servicios.ServicioHora;

public class FachadaHora {

	private ServicioHora servicioHora;
	
	private ServicioContrato servicioContrato;
	
	public void registrarHora(final String fechaDesde, final String fechaHasta, final String tipoHora, final String remoto, final String idUsuario, final String idContrato,
			final String idActividad, final String descripcion) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException, ParseException{
		SimpleDateFormat simpleFateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		Hora hora = new Hora();
		hora.setFechaDesde(simpleFateFormat.parse(fechaDesde));
		hora.setFechaHasta(simpleFateFormat.parse(fechaHasta));
		hora.setFechaInformar(servicioContrato.proximaFechaInforme(idContrato));
		hora.setFechaFacturar(servicioContrato.proximaFechaFacturacion(idContrato));
		hora.setFechaComputar(servicioContrato.proximaFechaFacturacion(idContrato));
		hora.setIdContrato(idContrato);
		hora.setIdUsuario(idUsuario);
		hora.setIdActividad(idActividad);
		hora.setNombreTipoHora(tipoHora);
		hora.setDescripcion(descripcion);
		hora.setRemoto(convertirBoolean(remoto));
		hora.setInformada(false);
		hora.setFacturada(false);
		hora.setValidada(false);
		servicioHora.agregar(hora);
	}
	
	public void editarHora(final String fechaDesde, final String fechaHasta, final String tipoHora, final String remoto, final String idContrato, final String idActividad,
			final String descripcion, final String id, final String fechaInformar, final String fechaFacturar, final String fechaComputar) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException, ParseException{
		SimpleDateFormat simpleFateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		Hora hora = new Hora();
		hora.setFechaDesde(simpleFateFormat.parse(fechaDesde));
		hora.setFechaHasta(simpleFateFormat.parse(fechaHasta));
		hora.setFechaInformar(servicioContrato.proximaFechaInforme(idContrato));
		hora.setFechaFacturar(servicioContrato.proximaFechaFacturacion(idContrato));
		hora.setFechaComputar(servicioContrato.proximaFechaFacturacion(idContrato));
		hora.setIdContrato(idContrato);
		hora.setIdActividad(idActividad);
		hora.setNombreTipoHora(tipoHora);
		hora.setDescripcion(descripcion);
		hora.setRemoto(convertirBoolean(remoto));
		servicioHora.editar(hora);
	}
	
	public void editarHoraDetalle(final int id, final String fechaInformar, final String fechaFacturar, final String fechaComputar) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException, ParseException{
		SimpleDateFormat simpleFateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Hora hora = new Hora();
		hora.setId(id);
		
		
		hora.setFechaInformar(simpleFateFormat.parse(fechaInformar));
		hora.setFechaFacturar(simpleFateFormat.parse(fechaFacturar));
		hora.setFechaComputar(simpleFateFormat.parse(fechaComputar));
		servicioHora.editarDetalle(hora);
	}
	
	public List<Hora> seleccionarHorasPorUsuario(final String idUsuario) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException{
		return servicioHora.seleccionarHorasRegistradasPorUsuario(idUsuario);
	}
	
	public Hora seleccionarHora(final int id) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException{
		return servicioHora.seleccionarHora(id);
	}
	
	private boolean convertirBoolean(String booleanString){
		if("true".equalsIgnoreCase(booleanString)){
			return true;
		}else{
			return false;
		}
	}

	public ServicioHora getServicioHora() {
		return servicioHora;
	}

	public void setServicioHora(ServicioHora servicioHora) {
		this.servicioHora = servicioHora;
	}

	public ServicioContrato getServicioContrato() {
		return servicioContrato;
	}

	public void setServicioContrato(ServicioContrato servicioContrato) {
		this.servicioContrato = servicioContrato;
	}
	
	public List<Date> proximas3FechasInforme(String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		return servicioHora.proximas3FechasInforme(idContrato);
	}
	
	public List<Date> proximas3FechasFacturacion(String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		return servicioHora.proximas3FechasFacturacion(idContrato);
	}
}
