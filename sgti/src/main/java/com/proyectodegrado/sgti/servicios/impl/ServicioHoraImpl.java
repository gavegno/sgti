package com.proyectodegrado.sgti.servicios.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.proyectodegrado.sgti.daos.HoraDAO;
import com.proyectodegrado.sgti.modelo.Hora;
import com.proyectodegrado.sgti.servicios.ServicioConfiguracion;
import com.proyectodegrado.sgti.servicios.ServicioContrato;
import com.proyectodegrado.sgti.servicios.ServicioHora;

public class ServicioHoraImpl implements ServicioHora {
	private ServicioContrato servicioContrato;
	private ServicioConfiguracion servicioConfiguracion;

	private HoraDAO horaDao;
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioHora#agregar(com.proyectodegrado.sgti.modelo.Hora)
	 */
	@Override
	public void agregar(Hora hora) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException{
		horaDao.insertarHora(hora);
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioHora#seleccionarHorasRegistradas()
	 */
	@Override
	public List<Hora> seleccionarHorasRegistradas() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException{
		return horaDao.verHorasRegistradas();
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioHora#seleccionarHorasRegistradasPorUsuario(java.lang.String)
	 */
	@Override
	public List<Hora> seleccionarHorasRegistradasPorUsuario(String idUsuario) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException{
		return horaDao.verHorasRegistradasPorUsuario(idUsuario);
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioHora#seleccionarHora(java.math.Integer)
	 */
	@Override
	public Hora seleccionarHora(int id) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException{
		return horaDao.verHora(id);
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioHora#seleccionarHorasRegistradasPorContrato(java.lang.String)
	 */
	@Override
	public List<Hora> seleccionarHorasRegistradasPorContrato(String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException{
		return horaDao.verHorasRegistradasPorContrato(idContrato);
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioHora#seleccionarHorasRegistradasNoFacturadas(java.lang.String)
	 */
	@Override
	public List<Hora> seleccionarHorasRegistradasNoFacturadas(String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException{
		return horaDao.verHorasRegistradasNoFacturadas(idContrato);
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioHora#seleccionarHorasRegistradasNoInformadas(java.lang.String)
	 */
	@Override
	public List<Hora> seleccionarHorasRegistradasNoInformadas(String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException{
		return horaDao.verHorasRegistradasNoInformadas(idContrato);
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioHora#editar(com.proyectodegrado.sgti.modelo.Hora)
	 */
	@Override
	public void editar(Hora hora) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException{
		horaDao.editarHora(hora);
	}
	
	@Override
	public void editarDetalle(Hora hora) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException{
		horaDao.editarHoraDetalle(hora);
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioHora#borrar(int)
	 */
	@Override
	public void borrar(int id) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		horaDao.borrar(id);
	}
	
	@Override
	public List<Date> proximas3FechasInforme(String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		Date fechaPrimera = servicioContrato.proximaFechaInforme(idContrato);
		int frecuenciaMeses = servicioConfiguracion.seleccionarConfiguracionActual(idContrato).getFrecuenciaInforme();
		
		List<Date> fechas = new ArrayList<Date>();
		Calendar calendar = Calendar.getInstance();
		
		fechas.add(fechaPrimera);
		
		calendar.setTime(fechaPrimera);
		
		calendar.add(Calendar.MONTH, frecuenciaMeses);
		fechas.add(calendar.getTime());
		
		calendar.add(Calendar.MONTH, frecuenciaMeses);
		fechas.add(calendar.getTime());
		
		return fechas;
		
	}
	
	@Override
	public List<Date> proximas3FechasFacturacion(String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		Date fechaPrimera = servicioContrato.proximaFechaFacturacion(idContrato);
		int frecuenciaMeses = servicioConfiguracion.seleccionarConfiguracionActual(idContrato).getFrecuenciaFacturacion();
		
		List<Date> fechas = new ArrayList<Date>();
		Calendar calendar = Calendar.getInstance();
		
		fechas.add(fechaPrimera);
		
		calendar.setTime(fechaPrimera);
		
		calendar.add(Calendar.MONTH, frecuenciaMeses);
		fechas.add(calendar.getTime());
		
		calendar.add(Calendar.MONTH, frecuenciaMeses);
		fechas.add(calendar.getTime());
		
		return fechas;
		
	}

	public HoraDAO getHoraDao() {
		return horaDao;
	}

	public void setHoraDao(HoraDAO horaDao) {
		this.horaDao = horaDao;
	}

	public ServicioContrato getServicioContrato() {
		return servicioContrato;
	}

	public void setServicioContrato(ServicioContrato servicioContrato) {
		this.servicioContrato = servicioContrato;
	}

	public ServicioConfiguracion getServicioConfiguracion() {
		return servicioConfiguracion;
	}

	public void setServicioConfiguracion(ServicioConfiguracion servicioConfiguracion) {
		this.servicioConfiguracion = servicioConfiguracion;
	}
	
	
}
