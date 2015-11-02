package com.proyectodegrado.sgti.fachada;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.proyectodegrado.sgti.exceptions.SgtiException;
import com.proyectodegrado.sgti.modelo.Hora;
import com.proyectodegrado.sgti.servicios.ServicioContrato;
import com.proyectodegrado.sgti.servicios.ServicioHora;

public class FachadaHora {

	private ServicioHora servicioHora;
	
	private ServicioContrato servicioContrato;
	
	public void registrarHora(final String fechaDesde, final String fechaHasta, final String tipoHora, final String remoto, final String idUsuario, final String idContrato,
			final String idActividad, final String descripcion, final String comentario) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException, ParseException, SgtiException{
		SimpleDateFormat simpleFateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

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
		hora.setComentario(comentario);
		hora.setRemoto(convertirBoolean(remoto));
		hora.setInformada(false);
		hora.setFacturada(false);
		hora.setValidada(false);
		hora.setDuracion(diferenciaEnMinutos(fechaDesde, fechaHasta));
		servicioHora.agregar(hora);
		}

	
	public void editarHora(final String fechaDesde, final String fechaHasta, final String tipoHora, final String remoto, final String idContrato, final String idActividad,
			final String descripcion, final String comentario, final String id, final String fechaInformar, final String fechaFacturar, final String fechaComputar) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException, ParseException{
		SimpleDateFormat simpleFateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
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
		hora.setComentario(comentario);
		hora.setRemoto(convertirBoolean(remoto));
		hora.setDuracion(diferenciaEnMinutos(fechaDesde, fechaHasta));
		hora.setId(Integer.valueOf(id));
		
		servicioHora.editar(hora);
	}
	
	public void editarHoraDetalle(final int id, final String fechaInformar, final String fechaFacturar, final String fechaComputar) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException, ParseException{
		SimpleDateFormat simpleFateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Hora hora = new Hora();
		hora.setId(id);
		//Para pasar el valor nulo, se establece a mano como si fuese 01-01-1900, y desde la consultaHora, se setea si tiene que ser el valor NULL o no.
		
		if (fechaInformar.matches("nulo"))
			hora.setFechaInformar(simpleFateFormat.parse("01-01-1900"));
		else
			hora.setFechaInformar(simpleFateFormat.parse(fechaInformar));
		
		if (fechaFacturar.matches("nulo"))
			hora.setFechaFacturar(simpleFateFormat.parse("01-01-1900"));
		else
			hora.setFechaFacturar(simpleFateFormat.parse(fechaFacturar));
		
		if (fechaComputar.matches("nulo"))
			hora.setFechaComputar(simpleFateFormat.parse("01-01-1900"));
		else
			hora.setFechaComputar(simpleFateFormat.parse(fechaComputar));
		
		servicioHora.editarDetalle(hora);
	}
	
	public List<Hora> seleccionarHorasPorUsuario(final String idUsuario) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException{
		return servicioHora.seleccionarHorasRegistradasPorUsuario(idUsuario);
	}
	
	public List<Hora> seleccionarHorasPorContratoValidadas(final String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException{
		return servicioHora.seleccionarHorasRegistradasPorContratoValidadas(idContrato);
	}
	
	public List<Hora> seleccionarHorasRegistradasPorContratoDesdeFecha(String idContrato, Date fechaDesde) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException{
		return servicioHora.seleccionarHorasRegistradasPorContratoDesdeFecha(idContrato, fechaDesde);
	}
	
	public List<Hora> seleccionarHorasConFechaDesde(final Date fechaDesde) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException{
		return servicioHora.seleccionarHorasHorasConFechaDesde(fechaDesde);
	}
	
	public List<Hora> seleccionarHorasPorValidacion(final boolean validada) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException{
		return servicioHora.seleccionarHorasPorValidacion(validada);
	}
	
	public List<Hora> seleccionarHorasFiltradas(final Date fechaDesde, final String usuario, final String validada) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException{
		if (validada.equalsIgnoreCase("todas"))
			return servicioHora.seleccionarHorasFechaUsuario(fechaDesde, usuario);
		else
		{
			if (validada.equalsIgnoreCase("validadas"))
				return servicioHora.seleccionarHorasFechaUsuarioValidacion(fechaDesde, usuario, true);
			else
				return servicioHora.seleccionarHorasFechaUsuarioValidacion(fechaDesde, usuario, false);
		}
	}
	
	public Hora seleccionarHora(final int id) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException{
		return servicioHora.seleccionarHora(id);
	}
	
	public void cambiarValidacionHora(int id) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException{
		servicioHora.cambiarValidacionHora(id);
	}
	
	public List<Hora> seleccionarHorasInformar(String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException{
		return servicioHora.seleccionarHorasRegistradasNoInformadas(idContrato);
	}
	
	private boolean convertirBoolean(String booleanString){
		if("true".equalsIgnoreCase(booleanString)){
			return true;
		}else{
			return false;
		}
	}

	public int diferenciaEnMinutos (String fechaDesde, String fechaHasta) throws ParseException
	{
		SimpleDateFormat simpleFateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		Date fechaDesdeDate = simpleFateFormat.parse(fechaDesde);
		Date fechaHastaDate = simpleFateFormat.parse(fechaHasta);
		
		return (int) ((fechaHastaDate.getTime()/60000) -  (fechaDesdeDate.getTime()/60000));
	}
	
	public void borrar(int idHora) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		servicioHora.borrar(idHora);
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
