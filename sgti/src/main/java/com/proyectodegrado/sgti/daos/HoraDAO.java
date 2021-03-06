package com.proyectodegrado.sgti.daos;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.proyectodegrado.sgti.consultas.ConsultasHora;
import com.proyectodegrado.sgti.modelo.Hora;

public class HoraDAO {
	
	private static final String FORMATO_FECHA_COMPLETO = "dd/MM/yyyy HH:mm";
	private static final String FORMATO_FECHA = "dd/MM/yyyy";
	private ConsultasHora consultasHora;
	private TipoHoraDAO tipoHoraDao;
	
	public void insertarHora(Hora hora) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm ");
		String horaDesde = simpleDateFormat.format(hora.getFechaDesde());
		String horaHasta = simpleDateFormat.format(hora.getFechaHasta());
		consultasHora.insertarHora(
				new java.sql.Date(hora.getFechaDesde().getTime()), 
				new java.sql.Date(hora.getFechaHasta().getTime()), 
				horaDesde, 
				horaHasta, 
				hora.isRemoto(), 
				hora.getIdUsuario(), 
				hora.getIdContrato(), 
				hora.getIdActividad(), 
				new java.sql.Date(hora.getFechaInformar().getTime()), 
				new java.sql.Date(hora.getFechaFacturar().getTime()), 
				new java.sql.Date(hora.getFechaComputar().getTime()), 
				tipoHoraDao.seleccionarPorTipo(hora.getNombreTipoHora()).getId(), 
				hora.getDescripcion(),
				hora.getComentario(),
				hora.isValidada(), 
				hora.isInformada(), 
				hora.isFacturada(), 
				hora.getDuracion());
	}
	
	public void editarHora(Hora hora) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm ");
		String horaDesde = simpleDateFormat.format(hora.getFechaDesde());
		String horaHasta = simpleDateFormat.format(hora.getFechaHasta());
		
		consultasHora.editarHora(
				hora.getId(), 
				new java.sql.Date(hora.getFechaDesde().getTime()), 
				new java.sql.Date(hora.getFechaHasta().getTime()), 
				horaDesde, 
				horaHasta, 
				hora.isRemoto(), 
				hora.getIdActividad(), 
				new java.sql.Date(hora.getFechaInformar().getTime()), 
				new java.sql.Date(hora.getFechaFacturar().getTime()), 
				new java.sql.Date(hora.getFechaComputar().getTime()), 
				tipoHoraDao.seleccionarPorTipo(hora.getNombreTipoHora()).getId(), 
				hora.getDescripcion(), 
				hora.getComentario(),
				hora.isValidada(), 
				hora.isInformada(), 
				hora.isFacturada(), 
				hora.getDuracion());
	}
	
	public List<Hora> verHorasRegistradas() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException{
		
		List<Hora> horas = new ArrayList<Hora>();
		ResultSet resultSet = consultasHora.verHorasRegistradas();
		while(resultSet.next()){
			String stringFechaDesde = prepararFecha(resultSet.getDate("fechadesde"), FORMATO_FECHA);
			String stringFechaHasta = prepararFecha(resultSet.getDate("fechahasta"), FORMATO_FECHA);
			Hora hora = new Hora();
			hora.setId(resultSet.getInt("id"));
			hora.setFechaDesde(prepararFechaCompleta(FORMATO_FECHA_COMPLETO, resultSet.getString("horadesde"), stringFechaDesde));
			hora.setFechaHasta(prepararFechaCompleta(FORMATO_FECHA_COMPLETO, resultSet.getString("horahasta"), stringFechaHasta));
			hora.setRemoto(resultSet.getBoolean("remoto"));
			hora.setIdUsuario(resultSet.getString("usuario"));
			hora.setIdContrato(resultSet.getString("contrato"));
			hora.setFechaInformar(resultSet.getDate("fechainformar"));
			hora.setFechaFacturar(resultSet.getDate("fechafacturar"));
			hora.setFechaComputar(resultSet.getDate("fechacomputar"));
			hora.setValidada(resultSet.getBoolean("validada"));
			hora.setIdActividad(resultSet.getString("actividad"));
			hora.setNombreTipoHora(tipoHoraDao.seleccionarPorId(resultSet.getInt("tipohora")).getTipo());
			hora.setDescripcion(resultSet.getString("descripcion"));
			hora.setComentario(resultSet.getString("comentario"));
			hora.setInformada(resultSet.getBoolean("informada"));
			hora.setFacturada(resultSet.getBoolean("facturada"));
			hora.setDuracion(resultSet.getInt("duracion"));
			horas.add(hora);
		}
		return horas;
	}

	private Date prepararFechaCompleta(String formato, String hora, String stringFechaDesde) throws ParseException, SQLException {
		SimpleDateFormat simpleDateFormatCompleto = new SimpleDateFormat(formato);
		return simpleDateFormatCompleto.parse(stringFechaDesde + " " + hora);
	}

	private String prepararFecha(Date fecha, String formato) throws SQLException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formato);
		java.util.Date fechaDesde = fecha;
		String stringFechaDesde = simpleDateFormat.format(fechaDesde);
		return stringFechaDesde;
	}
	
	public Hora verHora(int id) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException{
		Hora hora = new Hora();
		ResultSet resultSet = consultasHora.verHora(id);
		if(resultSet.next()){
			String stringFechaDesde = prepararFecha(resultSet.getDate("fechadesde"), FORMATO_FECHA);
			String stringFechaHasta = prepararFecha(resultSet.getDate("fechahasta"), FORMATO_FECHA);
			hora.setId(resultSet.getInt("id"));
			hora.setFechaDesde(prepararFechaCompleta(FORMATO_FECHA_COMPLETO, resultSet.getString("horadesde"), stringFechaDesde));
			hora.setFechaHasta(prepararFechaCompleta(FORMATO_FECHA_COMPLETO, resultSet.getString("horahasta"), stringFechaHasta));
			hora.setRemoto(resultSet.getBoolean("remoto"));
			hora.setIdUsuario(resultSet.getString("usuario"));
			hora.setIdContrato(resultSet.getString("contrato"));
			hora.setFechaInformar(resultSet.getDate("fechainformar"));
			hora.setFechaFacturar(resultSet.getDate("fechafacturar"));
			hora.setFechaComputar(resultSet.getDate("fechacomputar"));
			hora.setValidada(resultSet.getBoolean("validada"));
			hora.setIdActividad(resultSet.getString("actividad"));
			hora.setNombreTipoHora(tipoHoraDao.seleccionarPorId(resultSet.getInt("tipohora")).getTipo());
			hora.setDescripcion(resultSet.getString("descripcion"));
			hora.setComentario(resultSet.getString("comentario"));
			hora.setInformada(resultSet.getBoolean("informada"));
			hora.setFacturada(resultSet.getBoolean("facturada"));
			hora.setDuracion(resultSet.getInt("duracion"));
		}
		return hora;
	}
	
	public List<Hora> verHorasRegistradasPorUsuario(String idUsuario) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException{
		List<Hora> horas = new ArrayList<Hora>();
		ResultSet resultSet = consultasHora.verHorasRegistradasPorUsuario(idUsuario);
		while(resultSet.next()){
			String stringFechaDesde = prepararFecha(resultSet.getDate("fechadesde"), FORMATO_FECHA);
			String stringFechaHasta = prepararFecha(resultSet.getDate("fechahasta"), FORMATO_FECHA);
			Hora hora = new Hora();
			hora.setId(resultSet.getInt("id"));
			hora.setFechaDesde(prepararFechaCompleta(FORMATO_FECHA_COMPLETO, resultSet.getString("horadesde"), stringFechaDesde));
			hora.setFechaHasta(prepararFechaCompleta(FORMATO_FECHA_COMPLETO, resultSet.getString("horahasta"), stringFechaHasta));
			hora.setRemoto(resultSet.getBoolean("remoto"));
			hora.setIdUsuario(resultSet.getString("usuario"));
			hora.setIdContrato(resultSet.getString("contrato"));
			hora.setFechaInformar(resultSet.getDate("fechainformar"));
			hora.setFechaFacturar(resultSet.getDate("fechafacturar"));
			hora.setFechaComputar(resultSet.getDate("fechacomputar"));
			hora.setValidada(resultSet.getBoolean("validada"));
			hora.setIdActividad(resultSet.getString("actividad"));
			hora.setNombreTipoHora(tipoHoraDao.seleccionarPorId(resultSet.getInt("tipohora")).getTipo());
			hora.setDescripcion(resultSet.getString("descripcion"));
			hora.setComentario(resultSet.getString("comentario"));
			hora.setInformada(resultSet.getBoolean("informada"));
			hora.setFacturada(resultSet.getBoolean("facturada"));
			hora.setDuracion(resultSet.getInt("duracion"));
			horas.add(hora);
		}
		return horas;
	}
	
	
	public List<Hora> verHorasPorValidacion(boolean validada) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException{
		List<Hora> horas = new ArrayList<Hora>();
		ResultSet resultSet = consultasHora.verHorasPorValidacion(validada);
		while(resultSet.next()){
			String stringFechaDesde = prepararFecha(resultSet.getDate("fechadesde"), FORMATO_FECHA);
			String stringFechaHasta = prepararFecha(resultSet.getDate("fechahasta"), FORMATO_FECHA);
			Hora hora = new Hora();
			hora.setId(resultSet.getInt("id"));
			hora.setFechaDesde(prepararFechaCompleta(FORMATO_FECHA_COMPLETO, resultSet.getString("horadesde"), stringFechaDesde));
			hora.setFechaHasta(prepararFechaCompleta(FORMATO_FECHA_COMPLETO, resultSet.getString("horahasta"), stringFechaHasta));
			hora.setRemoto(resultSet.getBoolean("remoto"));
			hora.setIdUsuario(resultSet.getString("usuario"));
			hora.setIdContrato(resultSet.getString("contrato"));
			hora.setFechaInformar(resultSet.getDate("fechainformar"));
			hora.setFechaFacturar(resultSet.getDate("fechafacturar"));
			hora.setFechaComputar(resultSet.getDate("fechacomputar"));
			hora.setValidada(resultSet.getBoolean("validada"));
			hora.setIdActividad(resultSet.getString("actividad"));
			hora.setNombreTipoHora(tipoHoraDao.seleccionarPorId(resultSet.getInt("tipohora")).getTipo());
			hora.setDescripcion(resultSet.getString("descripcion"));
			hora.setComentario(resultSet.getString("comentario"));
			hora.setInformada(resultSet.getBoolean("informada"));
			hora.setFacturada(resultSet.getBoolean("facturada"));
			hora.setDuracion(resultSet.getInt("duracion"));
			horas.add(hora);
		}
		return horas;
	}
	
	public List<Hora> verHorasConFechaDesde(Date fechaDesde) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException{
		List<Hora> horas = new ArrayList<Hora>();
		ResultSet resultSet = consultasHora.verHorasConFechaDesde(new java.sql.Date(fechaDesde.getTime()));
		while(resultSet.next()){
			String stringFechaDesde = prepararFecha(resultSet.getDate("fechadesde"), FORMATO_FECHA);
			String stringFechaHasta = prepararFecha(resultSet.getDate("fechahasta"), FORMATO_FECHA);
			Hora hora = new Hora();
			hora.setId(resultSet.getInt("id"));
			hora.setFechaDesde(prepararFechaCompleta(FORMATO_FECHA_COMPLETO, resultSet.getString("horadesde"), stringFechaDesde));
			hora.setFechaHasta(prepararFechaCompleta(FORMATO_FECHA_COMPLETO, resultSet.getString("horahasta"), stringFechaHasta));
			hora.setRemoto(resultSet.getBoolean("remoto"));
			hora.setIdUsuario(resultSet.getString("usuario"));
			hora.setIdContrato(resultSet.getString("contrato"));
			hora.setFechaInformar(resultSet.getDate("fechainformar"));
			hora.setFechaFacturar(resultSet.getDate("fechafacturar"));
			hora.setFechaComputar(resultSet.getDate("fechacomputar"));
			hora.setValidada(resultSet.getBoolean("validada"));
			hora.setIdActividad(resultSet.getString("actividad"));
			hora.setNombreTipoHora(tipoHoraDao.seleccionarPorId(resultSet.getInt("tipohora")).getTipo());
			hora.setDescripcion(resultSet.getString("descripcion"));
			hora.setComentario(resultSet.getString("comentario"));
			hora.setInformada(resultSet.getBoolean("informada"));
			hora.setFacturada(resultSet.getBoolean("facturada"));
			hora.setDuracion(resultSet.getInt("duracion"));
			horas.add(hora);
		}
		return horas;
	}
	
	
	public List<Hora> verHorasFechaUsuario(Date fechaDesde, String usuario) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException{
		List<Hora> horas = new ArrayList<Hora>();
		ResultSet resultSet = consultasHora.verHorasFechaUsuario(new java.sql.Date(fechaDesde.getTime()), usuario);
		while(resultSet.next()){
			String stringFechaDesde = prepararFecha(resultSet.getDate("fechadesde"), FORMATO_FECHA);
			String stringFechaHasta = prepararFecha(resultSet.getDate("fechahasta"), FORMATO_FECHA);
			Hora hora = new Hora();
			hora.setId(resultSet.getInt("id"));
			hora.setFechaDesde(prepararFechaCompleta(FORMATO_FECHA_COMPLETO, resultSet.getString("horadesde"), stringFechaDesde));
			hora.setFechaHasta(prepararFechaCompleta(FORMATO_FECHA_COMPLETO, resultSet.getString("horahasta"), stringFechaHasta));
			hora.setRemoto(resultSet.getBoolean("remoto"));
			hora.setIdUsuario(resultSet.getString("usuario"));
			hora.setIdContrato(resultSet.getString("contrato"));
			hora.setFechaInformar(resultSet.getDate("fechainformar"));
			hora.setFechaFacturar(resultSet.getDate("fechafacturar"));
			hora.setFechaComputar(resultSet.getDate("fechacomputar"));
			hora.setValidada(resultSet.getBoolean("validada"));
			hora.setIdActividad(resultSet.getString("actividad"));
			hora.setNombreTipoHora(tipoHoraDao.seleccionarPorId(resultSet.getInt("tipohora")).getTipo());
			hora.setDescripcion(resultSet.getString("descripcion"));
			hora.setComentario(resultSet.getString("comentario"));
			hora.setInformada(resultSet.getBoolean("informada"));
			hora.setFacturada(resultSet.getBoolean("facturada"));
			hora.setDuracion(resultSet.getInt("duracion"));
			horas.add(hora);
		}
		return horas;
	}
	
	public List<Hora> verHorasFechaUsuarioValidacion(Date fechaDesde, String usuario, boolean validacion) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException{
		List<Hora> horas = new ArrayList<Hora>();
		ResultSet resultSet = consultasHora.verHorasFechaUsuarioValidacion(new java.sql.Date(fechaDesde.getTime()), usuario, validacion);
		while(resultSet.next()){
			String stringFechaDesde = prepararFecha(resultSet.getDate("fechadesde"), FORMATO_FECHA);
			String stringFechaHasta = prepararFecha(resultSet.getDate("fechahasta"), FORMATO_FECHA);
			Hora hora = new Hora();
			hora.setId(resultSet.getInt("id"));
			hora.setFechaDesde(prepararFechaCompleta(FORMATO_FECHA_COMPLETO, resultSet.getString("horadesde"), stringFechaDesde));
			hora.setFechaHasta(prepararFechaCompleta(FORMATO_FECHA_COMPLETO, resultSet.getString("horahasta"), stringFechaHasta));
			hora.setRemoto(resultSet.getBoolean("remoto"));
			hora.setIdUsuario(resultSet.getString("usuario"));
			hora.setIdContrato(resultSet.getString("contrato"));
			hora.setFechaInformar(resultSet.getDate("fechainformar"));
			hora.setFechaFacturar(resultSet.getDate("fechafacturar"));
			hora.setFechaComputar(resultSet.getDate("fechacomputar"));
			hora.setValidada(resultSet.getBoolean("validada"));
			hora.setIdActividad(resultSet.getString("actividad"));
			hora.setNombreTipoHora(tipoHoraDao.seleccionarPorId(resultSet.getInt("tipohora")).getTipo());
			hora.setDescripcion(resultSet.getString("descripcion"));
			hora.setComentario(resultSet.getString("comentario"));
			hora.setInformada(resultSet.getBoolean("informada"));
			hora.setFacturada(resultSet.getBoolean("facturada"));
			hora.setDuracion(resultSet.getInt("duracion"));
			horas.add(hora);
		}
		return horas;
	}
	
	public List<Hora> verHorasRegistradasPorContrato(String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException{
		List<Hora> horas = new ArrayList<Hora>();
		ResultSet resultSet = consultasHora.verHorasRegistradasPorContrato(idContrato);
		while(resultSet.next()){
			String stringFechaDesde = prepararFecha(resultSet.getDate("fechadesde"), FORMATO_FECHA);
			String stringFechaHasta = prepararFecha(resultSet.getDate("fechahasta"), FORMATO_FECHA);
			Hora hora = new Hora();
			hora.setId(resultSet.getInt("id"));
			hora.setFechaDesde(prepararFechaCompleta(FORMATO_FECHA_COMPLETO, resultSet.getString("horadesde"), stringFechaDesde));
			hora.setFechaHasta(prepararFechaCompleta(FORMATO_FECHA_COMPLETO, resultSet.getString("horahasta"), stringFechaHasta));
			hora.setRemoto(resultSet.getBoolean("remoto"));
			hora.setIdUsuario(resultSet.getString("usuario"));
			hora.setIdContrato(resultSet.getString("contrato"));
			hora.setFechaInformar(resultSet.getDate("fechainformar"));
			hora.setFechaFacturar(resultSet.getDate("fechafacturar"));
			hora.setFechaComputar(resultSet.getDate("fechacomputar"));
			hora.setValidada(resultSet.getBoolean("validada"));
			hora.setIdActividad(resultSet.getString("actividad"));
			hora.setNombreTipoHora(tipoHoraDao.seleccionarPorId(resultSet.getInt("tipohora")).getTipo());
			hora.setDescripcion(resultSet.getString("descripcion"));
			hora.setComentario(resultSet.getString("comentario"));
			hora.setInformada(resultSet.getBoolean("informada"));
			hora.setFacturada(resultSet.getBoolean("facturada"));
			hora.setDuracion(resultSet.getInt("duracion"));
			horas.add(hora);
		}
		return horas;
	}
	
	
	public List<Hora> verHorasRegistradasPorContratoDesdeFecha(String idContrato, Date fechaDesde) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException{
		List<Hora> horas = new ArrayList<Hora>();
		ResultSet resultSet = consultasHora.verHorasRegistradasPorContratoDesdeFecha(idContrato, new java.sql.Date(fechaDesde.getTime()));
		while(resultSet.next()){
			String stringFechaDesde = prepararFecha(resultSet.getDate("fechadesde"), FORMATO_FECHA);
			String stringFechaHasta = prepararFecha(resultSet.getDate("fechahasta"), FORMATO_FECHA);
			Hora hora = new Hora();
			hora.setId(resultSet.getInt("id"));
			hora.setFechaDesde(prepararFechaCompleta(FORMATO_FECHA_COMPLETO, resultSet.getString("horadesde"), stringFechaDesde));
			hora.setFechaHasta(prepararFechaCompleta(FORMATO_FECHA_COMPLETO, resultSet.getString("horahasta"), stringFechaHasta));
			hora.setRemoto(resultSet.getBoolean("remoto"));
			hora.setIdUsuario(resultSet.getString("usuario"));
			hora.setIdContrato(resultSet.getString("contrato"));
			hora.setFechaInformar(resultSet.getDate("fechainformar"));
			hora.setFechaFacturar(resultSet.getDate("fechafacturar"));
			hora.setFechaComputar(resultSet.getDate("fechacomputar"));
			hora.setValidada(resultSet.getBoolean("validada"));
			hora.setIdActividad(resultSet.getString("actividad"));
			hora.setNombreTipoHora(tipoHoraDao.seleccionarPorId(resultSet.getInt("tipohora")).getTipo());
			hora.setDescripcion(resultSet.getString("descripcion"));
			hora.setComentario(resultSet.getString("comentario"));
			hora.setInformada(resultSet.getBoolean("informada"));
			hora.setFacturada(resultSet.getBoolean("facturada"));
			hora.setDuracion(resultSet.getInt("duracion"));
			horas.add(hora);
		}
		return horas;
	}
	
	public List<Hora> verHorasRegistradasPorContratoValidadas(String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException{
		List<Hora> horas = new ArrayList<Hora>();
		ResultSet resultSet = consultasHora.verHorasRegistradasPorContratoValidadas(idContrato);
		while(resultSet.next()){
			String stringFechaDesde = prepararFecha(resultSet.getDate("fechadesde"), FORMATO_FECHA);
			String stringFechaHasta = prepararFecha(resultSet.getDate("fechahasta"), FORMATO_FECHA);
			Hora hora = new Hora();
			hora.setId(resultSet.getInt("id"));
			hora.setFechaDesde(prepararFechaCompleta(FORMATO_FECHA_COMPLETO, resultSet.getString("horadesde"), stringFechaDesde));
			hora.setFechaHasta(prepararFechaCompleta(FORMATO_FECHA_COMPLETO, resultSet.getString("horahasta"), stringFechaHasta));
			hora.setRemoto(resultSet.getBoolean("remoto"));
			hora.setIdUsuario(resultSet.getString("usuario"));
			hora.setIdContrato(resultSet.getString("contrato"));
			hora.setFechaInformar(resultSet.getDate("fechainformar"));
			hora.setFechaFacturar(resultSet.getDate("fechafacturar"));
			hora.setFechaComputar(resultSet.getDate("fechacomputar"));
			hora.setValidada(resultSet.getBoolean("validada"));
			hora.setIdActividad(resultSet.getString("actividad"));
			hora.setNombreTipoHora(tipoHoraDao.seleccionarPorId(resultSet.getInt("tipohora")).getTipo());
			hora.setDescripcion(resultSet.getString("descripcion"));
			hora.setComentario(resultSet.getString("comentario"));
			hora.setInformada(resultSet.getBoolean("informada"));
			hora.setFacturada(resultSet.getBoolean("facturada"));
			hora.setDuracion(resultSet.getInt("duracion"));
			horas.add(hora);
		}
		return horas;
	}
	
	public List<Hora> verHorasRegistradasNoFacturadas(String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException{
		List<Hora> horas = new ArrayList<Hora>();
		ResultSet resultSet = consultasHora.verHorasRegistradasNoFacturadas(idContrato);
		while(resultSet.next()){
			String stringFechaDesde = prepararFecha(resultSet.getDate("fechadesde"), FORMATO_FECHA);
			String stringFechaHasta = prepararFecha(resultSet.getDate("fechahasta"), FORMATO_FECHA);
			Hora hora = new Hora();
			hora.setId(resultSet.getInt("id"));
			hora.setFechaDesde(prepararFechaCompleta(FORMATO_FECHA_COMPLETO, resultSet.getString("horadesde"), stringFechaDesde));
			hora.setFechaHasta(prepararFechaCompleta(FORMATO_FECHA_COMPLETO, resultSet.getString("horahasta"), stringFechaHasta));
			hora.setRemoto(resultSet.getBoolean("remoto"));
			hora.setIdUsuario(resultSet.getString("usuario"));
			hora.setIdContrato(resultSet.getString("contrato"));
			hora.setFechaInformar(resultSet.getDate("fechainformar"));
			hora.setFechaFacturar(resultSet.getDate("fechafacturar"));
			hora.setFechaComputar(resultSet.getDate("fechacomputar"));
			hora.setValidada(resultSet.getBoolean("validada"));
			hora.setIdActividad(resultSet.getString("actividad"));
			hora.setNombreTipoHora(tipoHoraDao.seleccionarPorId(resultSet.getInt("tipohora")).getTipo());
			hora.setDescripcion(resultSet.getString("descripcion"));
			hora.setComentario(resultSet.getString("comentario"));
			hora.setInformada(resultSet.getBoolean("informada"));
			hora.setFacturada(resultSet.getBoolean("facturada"));
			hora.setDuracion(resultSet.getInt("duracion"));
			horas.add(hora);
		}
		return horas;
	}
	
	public List<Hora> verHorasRegistradasNoInformadas(String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException{
		List<Hora> horas = new ArrayList<Hora>();
		ResultSet resultSet = consultasHora.verHorasRegistradasNoInformadas(idContrato);
		while(resultSet.next()){
			String stringFechaDesde = prepararFecha(resultSet.getDate("fechadesde"), FORMATO_FECHA);
			String stringFechaHasta = prepararFecha(resultSet.getDate("fechahasta"), FORMATO_FECHA);
			Hora hora = new Hora();
			hora.setId(resultSet.getInt("id"));
			hora.setFechaDesde(prepararFechaCompleta(FORMATO_FECHA_COMPLETO, resultSet.getString("horadesde"), stringFechaDesde));
			hora.setFechaHasta(prepararFechaCompleta(FORMATO_FECHA_COMPLETO, resultSet.getString("horahasta"), stringFechaHasta));
			hora.setRemoto(resultSet.getBoolean("remoto"));
			hora.setIdUsuario(resultSet.getString("usuario"));
			hora.setIdContrato(resultSet.getString("contrato"));
			hora.setFechaInformar(resultSet.getDate("fechainformar"));
			hora.setFechaFacturar(resultSet.getDate("fechafacturar"));
			hora.setFechaComputar(resultSet.getDate("fechacomputar"));
			hora.setValidada(resultSet.getBoolean("validada"));
			hora.setIdActividad(resultSet.getString("actividad"));
			hora.setNombreTipoHora(tipoHoraDao.seleccionarPorId(resultSet.getInt("tipohora")).getTipo());
			hora.setDescripcion(resultSet.getString("descripcion"));
			hora.setComentario(resultSet.getString("comentario"));
			hora.setInformada(resultSet.getBoolean("informada"));
			hora.setFacturada(resultSet.getBoolean("facturada"));
			hora.setDuracion(resultSet.getInt("duracion"));
			horas.add(hora);
		}
		return horas;
	}
	
	public List<Hora> esPosibleInsertarHora(Date desde, Date hasta, String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException
	{
		List<Hora> horas = new ArrayList<Hora>();
		ResultSet resultSet = consultasHora.esPosibleInsertarHora(new java.sql.Date(desde.getTime()), 
								new java.sql.Date(hasta.getTime()), idContrato);
		while(resultSet.next()){
			String stringFechaDesde = prepararFecha(resultSet.getDate("fechadesde"), FORMATO_FECHA);
			String stringFechaHasta = prepararFecha(resultSet.getDate("fechahasta"), FORMATO_FECHA);
			Hora hora = new Hora();
			hora.setId(resultSet.getInt("id"));
			hora.setFechaDesde(prepararFechaCompleta(FORMATO_FECHA_COMPLETO, resultSet.getString("horadesde"), stringFechaDesde));
			hora.setFechaHasta(prepararFechaCompleta(FORMATO_FECHA_COMPLETO, resultSet.getString("horahasta"), stringFechaHasta));
			hora.setRemoto(resultSet.getBoolean("remoto"));
			hora.setIdUsuario(resultSet.getString("usuario"));
			hora.setIdContrato(resultSet.getString("contrato"));
			hora.setFechaInformar(resultSet.getDate("fechainformar"));
			hora.setFechaFacturar(resultSet.getDate("fechafacturar"));
			hora.setFechaComputar(resultSet.getDate("fechacomputar"));
			hora.setValidada(resultSet.getBoolean("validada"));
			hora.setIdActividad(resultSet.getString("actividad"));
			hora.setNombreTipoHora(tipoHoraDao.seleccionarPorId(resultSet.getInt("tipohora")).getTipo());
			hora.setDescripcion(resultSet.getString("descripcion"));
			hora.setComentario(resultSet.getString("comentario"));
			hora.setInformada(resultSet.getBoolean("informada"));
			hora.setFacturada(resultSet.getBoolean("facturada"));
			hora.setDuracion(resultSet.getInt("duracion"));
			horas.add(hora);
		}
		return horas;
	}
	
	public void editarHoraDetalle(Hora hora) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException{
		consultasHora.editarHoraFechas(
				hora.getId(), 
				new java.sql.Date(hora.getFechaInformar().getTime()), 
				new java.sql.Date(hora.getFechaFacturar().getTime()), 
				new java.sql.Date(hora.getFechaComputar().getTime()));
	}
	
	public void cambiarValidacionHora(int id) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		consultasHora.cambiarValidacionHora(id);
	}
	
	public void esPosibleInsertarHora(Hora h)
	{
		
	}
	 
	
	public void borrar(int id) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		consultasHora.borrarHora(id);
	}

	public ConsultasHora getConsultasHora() {
		return consultasHora;
	}

	public void setConsultasHora(ConsultasHora consultasHora) {
		this.consultasHora = consultasHora;
	}

	public TipoHoraDAO getTipoHoraDao() {
		return tipoHoraDao;
	}

	public void setTipoHoraDao(TipoHoraDAO tipoHoraDao) {
		this.tipoHoraDao = tipoHoraDao;
	}
	
}
