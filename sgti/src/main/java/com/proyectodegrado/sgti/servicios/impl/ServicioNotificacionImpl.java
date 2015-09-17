package com.proyectodegrado.sgti.servicios.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.proyectodegrado.sgti.dto.DataNotificacion;
import com.proyectodegrado.sgti.modelo.Actividad;
import com.proyectodegrado.sgti.modelo.Configuracion;
import com.proyectodegrado.sgti.modelo.Contrato;
import com.proyectodegrado.sgti.modelo.Hora;
import com.proyectodegrado.sgti.modelo.Precio;
import com.proyectodegrado.sgti.servicios.ServicioActividad;
import com.proyectodegrado.sgti.servicios.ServicioConfiguracion;
import com.proyectodegrado.sgti.servicios.ServicioContrato;
import com.proyectodegrado.sgti.servicios.ServicioHora;
import com.proyectodegrado.sgti.servicios.ServicioNotificacion;
import com.proyectodegrado.sgti.servicios.ServicioPrecio;

public class ServicioNotificacionImpl implements ServicioNotificacion {
	
	private ServicioContrato servicioContrato;
	
	private ServicioPrecio servicioPrecio;
	
	private ServicioConfiguracion servicioConfiguracion;
	
	private ServicioHora servicioHora;
	
	private ServicioActividad servicioActividad;
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioNotificacion#notificar(java.lang.String, java.lang.String, int)
	 */
	@Override
	public List<DataNotificacion> notificar(String tipoUsuario, String idUsuario, int dias) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException{
		List<DataNotificacion> notificaciones = new ArrayList<DataNotificacion>();
		if(tipoUsuario.equalsIgnoreCase("SOCIO")){
			int cantidadNotificaciones = cantidadNotificacionesSocio(idUsuario, dias);
			if(cantidadNotificaciones > 0){
				int cantConfVencer = verConfiguracionesAVencer(dias).size();
				int cantPreciosVencer = verPreciosAVencer(dias).size();
				int cantContratosConHorasInformar = verContratosConHorasAInformar(dias).size();
				int cantContratosHorasFacturar = verContratosConHorasAFacturar(dias).size();
				int cantidadActividadesRealizar = verActividadesARealizar(idUsuario, 0).size();
				
				if(cantConfVencer > 0){
					DataNotificacion notificacion = new DataNotificacion("Configuracion(es) a vencer", cantConfVencer, "/CounterWebApp/desktop/contrato/configuracionVencer/redireccionarNotificacion");
					notificaciones.add(notificacion);
				}
				if(cantPreciosVencer > 0){
					DataNotificacion notificacion = new DataNotificacion("Precio(s) a vencer", cantPreciosVencer, "/CounterWebApp/desktop/contrato/precioVencer/redireccionarNotificacion");
					notificaciones.add(notificacion);
				}
				if(cantContratosConHorasInformar > 0){
					DataNotificacion notificacion = new DataNotificacion("Contrato(s) con horas a informar", cantContratosConHorasInformar, "/CounterWebApp/desktop/contrato/horasInformar/redireccionarNotificacion");
					notificaciones.add(notificacion);
				}
				if(cantContratosHorasFacturar > 0){
					DataNotificacion notificacion = new DataNotificacion("Contrato(s) con horas a facturar", cantContratosHorasFacturar, "/CounterWebApp/desktop/contrato/horasFacturar/redireccionarNotificacion");
					notificaciones.add(notificacion);
				}
				if(cantidadActividadesRealizar > 0){
					DataNotificacion notificacion = new DataNotificacion("Actividad(es) a realizar", cantidadActividadesRealizar, "/CounterWebApp/desktop/actividad/redireccionarNotificacion");
					notificaciones.add(notificacion);
				}
			}
		}else if(tipoUsuario.equalsIgnoreCase("TECNICO")){
			int cantidadNotificaciones = verActividadesARealizar(idUsuario, 0).size();
			if(cantidadNotificaciones > 0){
				DataNotificacion notificacion = new DataNotificacion("Actividad(es) a realizar", cantidadNotificaciones, "/CounterWebApp/desktop/actividad/redireccionarNotificacion");
				notificaciones.add(notificacion);
			}
		}
		
		return notificaciones;
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioNotificacion#cantidadNotificaciones(int)
	 */
	@Override
	public int cantidadNotificacionesSocio(String idUsuario, int dias) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException{
		int cantidad = 0;
		if(!CollectionUtils.isEmpty(verPreciosAVencer(dias))){
			cantidad++;
		}
		if(!CollectionUtils.isEmpty(verConfiguracionesAVencer(dias))){
			cantidad++;
		}
		if(!CollectionUtils.isEmpty(verContratosConHorasAInformar(dias))){
			cantidad++;
		}
		if(!CollectionUtils.isEmpty(verContratosConHorasAFacturar(dias))){
			cantidad++;
		}
		if(!CollectionUtils.isEmpty(verActividadesARealizar(idUsuario, dias))){
			cantidad++;
		}
		return cantidad;
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioNotificacion#verPreciosAVencer(int)
	 */
	@Override
	public List<Precio> verPreciosAVencer(int dias) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		List<Precio> preciosAVencer = new ArrayList<Precio>();
		for(Contrato contrato : servicioContrato.seleccionarContratosVigentes()){
			Precio precio = servicioPrecio.seleccionarPrecioActual(contrato.getId());
			if(precio.getFechaHasta() != null){
				Calendar fechaVencimiento = Calendar.getInstance();
				Calendar proximaVencimiento = Calendar.getInstance();
				fechaVencimiento.setTime(precio.getFechaHasta());
				fechaVencimiento.add(Calendar.DATE, -dias);
				proximaVencimiento.add(Calendar.DATE, 7);
				if(fechaVencimiento.getTime().before(new Date()) && !servicioPrecio.tienePrecioPostVencer(proximaVencimiento.getTime(), contrato.getId())){
					preciosAVencer.add(precio);
				}
			}
		}
		return preciosAVencer;
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioNotificacion#verContratosConPreciosAVencer(int)
	 */
	@Override
	public List<Contrato> verContratosConPreciosAVencer(int dias) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		List<Contrato> contratos = new ArrayList<Contrato>();
		for(Contrato contrato : servicioContrato.seleccionarContratosVigentes()){
			Precio precio = servicioPrecio.seleccionarPrecioActual(contrato.getId());
			if(precio.getFechaHasta() != null){
				Calendar fechaVencimiento = Calendar.getInstance();
				Calendar proximaVencimiento = Calendar.getInstance();
				fechaVencimiento.setTime(precio.getFechaHasta());
				fechaVencimiento.add(Calendar.DATE, -dias);
				proximaVencimiento.add(Calendar.DATE, 7);
				if(fechaVencimiento.getTime().before(new Date()) && !servicioPrecio.tienePrecioPostVencer(proximaVencimiento.getTime(), contrato.getId())){
					contratos.add(contrato);
				}
			}
		}
		return contratos;
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioNotificacion#verConfiguracionesAVencer(int)
	 */
	@Override
	public List<Configuracion> verConfiguracionesAVencer(int dias) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		List<Configuracion> configuracionesAVencer = new ArrayList<Configuracion>();
		for(Contrato contrato : servicioContrato.seleccionarContratosVigentes()){
			Configuracion configuracion = servicioConfiguracion.seleccionarConfiguracionActual(contrato.getId());
			if(configuracion.getId() != 0){
				Calendar fechaVencimiento = new GregorianCalendar();
				Calendar proximaFecha = new GregorianCalendar();
				fechaVencimiento.setTime(configuracion.getFechaFin());
				fechaVencimiento.add(Calendar.DATE, -dias);
				proximaFecha.add(Calendar.DATE, 7);
				if(fechaVencimiento.getTime().before(new Date()) && !servicioConfiguracion.tieneConfiguracionPostVencer(proximaFecha.getTime(), contrato.getId())){
					configuracionesAVencer.add(configuracion);
				}
			}
		}
		return configuracionesAVencer;
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioNotificacion#verContratosConConfiguracionesAVencer(int)
	 */
	@Override
	public List<Contrato> verContratosConConfiguracionesAVencer(int dias) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		List<Contrato> contratos = new ArrayList<Contrato>();
		for(Contrato contrato : servicioContrato.seleccionarContratosVigentes()){
			Configuracion configuracion = servicioConfiguracion.seleccionarConfiguracionActual(contrato.getId());
			if(configuracion.getId() != 0){
				Calendar fechaVencimiento = new GregorianCalendar();
				Calendar proximaFecha = new GregorianCalendar();
				fechaVencimiento.setTime(configuracion.getFechaFin());
				fechaVencimiento.add(Calendar.DATE, -dias);
				proximaFecha.add(Calendar.DATE, 7);
				if(fechaVencimiento.getTime().before(new Date()) && !servicioConfiguracion.tieneConfiguracionPostVencer(proximaFecha.getTime(), contrato.getId())){
					contratos.add(contrato);
				}
			}
		}
		return contratos;
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioNotificacion#cantidadDeContratosConHorasAInformar(int)
	 */
	@Override
	public List<Contrato> verContratosConHorasAInformar(int dias) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException{
		List<Contrato> contratos = new ArrayList<Contrato>();
		for(Contrato contrato : servicioContrato.seleccionarContratosVigentes()){
			if(!CollectionUtils.isEmpty(horasAInformar(contrato.getId(), dias))){
				contratos.add(contrato);
			}
		}
		return contratos;
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioNotificacion#cantidadDeContratosConHorasAFacturar(int)
	 */
	@Override
	public List<Contrato> verContratosConHorasAFacturar(int dias) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException{
		List<Contrato> contratos = new ArrayList<Contrato>();
		for(Contrato contrato : servicioContrato.seleccionarContratosVigentes()){
			if(!CollectionUtils.isEmpty(horasAFacturar(contrato.getId(), dias))){
				contratos.add(contrato);
			}
		}
		return contratos;
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioNotificacion#horasAInformar(java.lang.String, int)
	 */
	@Override
	public List<Hora> horasAInformar(String idContrato, int dias) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException{
		List<Hora> horasAInformar = new ArrayList<Hora>();
		for(Hora hora : servicioHora.seleccionarHorasRegistradasNoInformadas(idContrato)){
			if(hora.getFechaInformar() != null){
				Calendar fechaVencimiento = Calendar.getInstance();
				fechaVencimiento.setTime(hora.getFechaInformar());
				fechaVencimiento.add(Calendar.DATE, -dias);
				if(fechaVencimiento.getTime().before(new Date())){
					horasAInformar.add(hora);
				}
			}
		}
		return horasAInformar;
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioNotificacion#horasAFacturar(java.lang.String, int)
	 */
	@Override
	public List<Hora> horasAFacturar(String idContrato, int dias) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException{
		List<Hora> horasAFacturar = new ArrayList<Hora>();
		for(Hora hora : servicioHora.seleccionarHorasRegistradasNoFacturadas(idContrato)){
			if(hora.getFechaFacturar() != null){
				Calendar fechaVencimiento = Calendar.getInstance();
				fechaVencimiento.setTime(hora.getFechaFacturar());
				fechaVencimiento.add(Calendar.DATE, -dias);
				if(fechaVencimiento.getTime().before(new Date())){
					horasAFacturar.add(hora);
				}
			}
		}
		return horasAFacturar;
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioNotificacion#actividadesARealizar(java.lang.String, int)
	 */
	@Override
	public List<Actividad> verActividadesARealizar(String idUsuario, int dias) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		List<Actividad> actividadesARealizar = new ArrayList<Actividad>();
		SimpleDateFormat simpleFormatDay = new SimpleDateFormat("yyyy/MM/dd");
		for(Actividad actividad : servicioActividad.seleccionarActividadesPorUsuario(idUsuario)){
			Calendar fechaActividad = Calendar.getInstance();
			fechaActividad.setTime(actividad.getFechaActividad());
			fechaActividad.add(Calendar.DATE, -dias);
			if(simpleFormatDay.format(fechaActividad.getTime()).equalsIgnoreCase(simpleFormatDay.format(new Date()))){
				actividadesARealizar.add(actividad);
			}
		}
		return actividadesARealizar;
	}

	public ServicioContrato getServicioContrato() {
		return servicioContrato;
	}

	public void setServicioContrato(ServicioContrato servicioContrato) {
		this.servicioContrato = servicioContrato;
	}

	public ServicioPrecio getServicioPrecio() {
		return servicioPrecio;
	}

	public void setServicioPrecio(ServicioPrecio servicioPrecio) {
		this.servicioPrecio = servicioPrecio;
	}

	public ServicioConfiguracion getServicioConfiguracion() {
		return servicioConfiguracion;
	}

	public void setServicioConfiguracion(ServicioConfiguracion servicioConfiguracion) {
		this.servicioConfiguracion = servicioConfiguracion;
	}

	public ServicioHora getServicioHora() {
		return servicioHora;
	}

	public void setServicioHora(ServicioHora servicioHora) {
		this.servicioHora = servicioHora;
	}

	public ServicioActividad getServicioActividad() {
		return servicioActividad;
	}

	public void setServicioActividad(ServicioActividad servicioActividad) {
		this.servicioActividad = servicioActividad;
	}
	
}
