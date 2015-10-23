package com.proyectodegrado.sgti.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyectodegrado.sgti.exceptions.SgtiException;
import com.proyectodegrado.sgti.fachada.FachadaActividad;
import com.proyectodegrado.sgti.fachada.FachadaContrato;
import com.proyectodegrado.sgti.fachada.FachadaNotificacion;
import com.proyectodegrado.sgti.fachada.FachadaUsuario;
import com.proyectodegrado.sgti.modelo.Usuario;

@Controller
@RequestMapping("/desktop/actividad")
public class ActividadController extends AbstractController{
	
	private static final String MENSAJE_ERROR = "Ha ocurrido un error";

	private FachadaUsuario fachadaUsuario;
	
	private FachadaContrato fachadaContrato;
	
	private FachadaActividad fachadaActividad;
	
	private FachadaNotificacion fachadaNotificacion;
	
	@RequestMapping(value="/ingresar", method = RequestMethod.GET)
	public String cargarPagina(Model model){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		fachadaContrato = (FachadaContrato) context.getBean("fachadaContrato");
		try {
			List<Usuario> usuarios = fachadaUsuario.seleccionarUsuariosSocio();
			usuarios.addAll(fachadaUsuario.seleccionarUsuariosTecnico());
			model.addAttribute("usuarios", usuarios);
			model.addAttribute("contratos", fachadaContrato.seleccionarContratos());
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return "desktop/actividad";
		}finally{
			context.close();
		}
		return "desktop/actividad";
	}
	
	@RequestMapping(value="/redireccionarNotificacion", method = RequestMethod.GET)
	public String redireccionarNotificacion(HttpServletRequest request, Model model){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaNotificacion = (FachadaNotificacion) context.getBean("fachadaNotificacion");
		try {
			int dias = 0;
			String tipoUsuario = (String) request.getSession().getAttribute("tipoUsuario");
			String idUsuario = (String) request.getSession().getAttribute("usuario");
			model.addAttribute("actividades", fachadaNotificacion.actividadesARealizar(tipoUsuario, idUsuario, dias));
		} catch (ClassNotFoundException | IOException | SQLException | ParseException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return "/desktop/tablaActividad";
		}finally{
			context.close();
		}
		return "/desktop/tablaActividadUsuario";
	}
	
	@RequestMapping(value="/ingresarActividad", method = RequestMethod.POST)
	public String insertarActividad(Model model, 
			@RequestParam("id") final String id, 
			@RequestParam("tipo") final String tipo, 
			@RequestParam(required=false, value="periodo") Integer periodo, 
			@RequestParam("fecha") final String fecha, 
			@RequestParam("usuario") final String usuario,
			@RequestParam("contrato") final String contrato,
			@RequestParam("descripcion") final String descripcion){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaActividad = (FachadaActividad) context.getBean("fachadaActividad");
		String mensaje = "La actividad fue ingresada correctamente";
		
		if (periodo == null)
			periodo = 0;
		
		try {
			fachadaActividad.ingresarActividad(id, tipo, periodo, fecha, usuario, contrato, descripcion, "PENDIENTE");
			model.addAttribute("message", mensaje);
		} catch (ClassNotFoundException | IOException | SQLException | ParseException e) {
			e.printStackTrace();
			mensaje = MENSAJE_ERROR;
			model.addAttribute("errorMessage", mensaje);
			return cargarPagina(model);
		} catch (SgtiException e) {
			e.printStackTrace();
			mensaje = e.getMessage();
			model.addAttribute("errorMessage", mensaje);
			return cargarPagina(model);
		}finally{
			context.close();
		}
		return cargarPagina(model);
	}
	
	@RequestMapping(value="/editarActividad", method = RequestMethod.POST)
	public String editarActividad(Model model, HttpServletRequest request,
			@RequestParam("id") final String id, 
			@RequestParam("tipo") final String tipo, 
			@RequestParam(required=false, value="periodo") Integer periodo, 
			@RequestParam("fecha") final String fecha, 
			@RequestParam("usuario") final String usuario,
			@RequestParam("contrato") final String contrato,
			@RequestParam("descripcion") final String descripcion,
			@RequestParam("fechaDesde") final String fechaFiltrar){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaActividad = (FachadaActividad) context.getBean("fachadaActividad");
		String mensaje = "La actividad fue editada correctamente";
		
		if (!(periodo >= 0))
			periodo = 0;
			
		try {
			fachadaActividad.editarActividad(id, tipo, periodo, fecha, usuario, contrato, descripcion, "PENDIENTE");
			model.addAttribute("message", mensaje);
		} catch (ClassNotFoundException | IOException | SQLException | ParseException e) {
			e.printStackTrace();
			mensaje = MENSAJE_ERROR;
			model.addAttribute("errorMessage", mensaje);
			return cargarTablaActividadesFiltradaFechaDesde(model, request, fechaFiltrar);
		}finally{
			context.close();
		}
		return cargarTablaActividadesFiltradaFechaDesde(model, request, fechaFiltrar);
	}
	
	@RequestMapping(value="/eliminarActividad", method = RequestMethod.POST)
	public String eliminarActividad(Model model, HttpServletRequest request, 
			@RequestParam("id") final String id,
			@RequestParam("fechaDesde") final String fechaFiltrar){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaActividad = (FachadaActividad) context.getBean("fachadaActividad");
		String mensaje = "La actividad fue eliminada correctamente";
		
		try {
			fachadaActividad.borrarActividad(id);
			model.addAttribute("message", mensaje);
		} catch (ClassNotFoundException | IOException | SQLException | ParseException e) {
			e.printStackTrace();
			mensaje = MENSAJE_ERROR;
			model.addAttribute("errorMessage", mensaje);
			return cargarTablaActividades(model);
		}catch (SgtiException e) {
			e.printStackTrace();
			mensaje = e.getMessage();
			model.addAttribute("errorMessage", mensaje);
			return cargarTablaActividadesFiltradaFechaDesde(model, request, fechaFiltrar);
		}finally{
			context.close();
		}
		return cargarTablaActividadesFiltradaFechaDesde(model, request, fechaFiltrar);
	}
	
	@RequestMapping(value="/ver", method = RequestMethod.GET)
	public String cargarTablaActividades(Model model){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaActividad = (FachadaActividad) context.getBean("fachadaActividad");
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_YEAR, -45);
		
		Date fechaDesde = cal.getTime();
		
		try {
			List<Usuario> usuarios = fachadaUsuario.seleccionarUsuariosSocio();
			usuarios.addAll(fachadaUsuario.seleccionarUsuariosTecnico());
			model.addAttribute("usuarios", usuarios);
			
			model.addAttribute("fechaDesde", fechaDesde);
			model.addAttribute("actividades", fachadaActividad.seleccionarActividadesConFechaDesde(fechaDesde));
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return cargarPagina(model);
		}finally{
			context.close();
		}
		return "/desktop/tablaActividad";
	}
	
	@RequestMapping(value="/tablaFiltrada", method = RequestMethod.POST)
	public String cargarTablaActividadesFiltradaFechaDesde(Model model, HttpServletRequest request,
			@RequestParam("fechaDesde") final String fechaFiltrar){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaActividad = (FachadaActividad) context.getBean("fachadaActividad");
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		Date fechaDesde = null;
		
		
		try {
			try{
			SimpleDateFormat simpleFateFormat = new SimpleDateFormat("yyyy-MM-dd");
			fechaDesde = simpleFateFormat.parse(fechaFiltrar);
			}
			catch (ParseException e) {
				Calendar cal = Calendar.getInstance();
				cal.set(2010, 01, 01);
				
				fechaDesde = cal.getTime();
			}
			
			
			
			List<Usuario> usuarios = fachadaUsuario.seleccionarUsuariosSocio();
			usuarios.addAll(fachadaUsuario.seleccionarUsuariosTecnico());
			model.addAttribute("usuarios", usuarios);
			
			model.addAttribute("fechaDesde", fechaDesde);
			model.addAttribute("actividades", fachadaActividad.seleccionarActividadesConFechaDesde(fechaDesde));
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return cargarPagina(model);
		} finally{
			context.close();
		}
		return "/desktop/tablaActividad";
	}
	
	@RequestMapping(value="/tablaFiltradaTecnico", method = RequestMethod.POST)
	public String cargarTablaActividadesFiltradaFechaDesdeTecnico(Model model, HttpServletRequest request,
			@RequestParam("fechaDesde") final String fechaFiltrar){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaActividad = (FachadaActividad) context.getBean("fachadaActividad");
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		Date fechaDesde = null;
		String idUsuario = (String) request.getSession().getAttribute("usuario");
		
		try {
			
			try{
			SimpleDateFormat simpleFateFormat = new SimpleDateFormat("yyyy-MM-dd");
			fechaDesde = simpleFateFormat.parse(fechaFiltrar);
			}
			catch (ParseException e) {
				Calendar cal = Calendar.getInstance();
				cal.set(2010, 01, 01);
				
				fechaDesde = cal.getTime();
			}
			
			
			
			List<Usuario> usuarios = fachadaUsuario.seleccionarUsuariosSocio();
			usuarios.addAll(fachadaUsuario.seleccionarUsuariosTecnico());
			model.addAttribute("usuarios", usuarios);
			
			model.addAttribute("fechaDesde", fechaDesde);
			model.addAttribute("actividades", fachadaActividad.verActividadesDeTecnicoYSusContratos(idUsuario, fechaDesde));
		} catch (ClassNotFoundException | IOException | SQLException | ParseException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return cargarPagina(model);
		}  finally{
			context.close();
		}
		return "/desktop/tablaActividadUsuario";
	}
	
	@RequestMapping(value="/verPorUsuario", method = RequestMethod.GET)
	public String cargarTablaActividadesPorUsuaraio(Model model, HttpServletRequest request){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaActividad = (FachadaActividad) context.getBean("fachadaActividad");
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_YEAR, -45);
		
		Date fechaDesde = cal.getTime();
		
		
		try {
			String idUsuario = (String) request.getSession().getAttribute("usuario");
			model.addAttribute("actividades", fachadaActividad.verActividadesDeTecnicoYSusContratos(idUsuario, fechaDesde));
			model.addAttribute("fechaDesde", fechaDesde);
			
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return "/desktop/tablaActividadUsuario";
		} catch (ParseException e) {
			e.printStackTrace();
		}finally{
			context.close();
		}
		return "/desktop/tablaActividadUsuario";
	}
	
	
	@RequestMapping(value="/autoasignarTecnico", method = RequestMethod.POST)
	public String autoasignarActividad(Model model, HttpServletRequest request,
			@RequestParam("id") final String id,
			@RequestParam("fechaDesde") final String fechaFiltrar){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaActividad = (FachadaActividad) context.getBean("fachadaActividad");
		String idUsuario = (String) request.getSession().getAttribute("usuario");
		String mensaje = "Actividad autoasignada correctamente";
		
		
		try {
			
			fachadaActividad.asignarActividad(id, idUsuario);
			model.addAttribute("message", mensaje);
			model.addAttribute("fechaDesde", fechaFiltrar);
			
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return cargarTablaActividadesFiltradaFechaDesdeTecnico(model, request, fechaFiltrar);
		}finally{
			context.close();
		}
		return cargarTablaActividadesFiltradaFechaDesdeTecnico(model, request, fechaFiltrar);
	}
	
	@RequestMapping(value="/cambiarEstado", method = RequestMethod.POST)
	public String cambiarEstadoActividad(Model model, HttpServletRequest request,
			@RequestParam("id") final String id,
			@RequestParam("fechaDesde") final String fechaFiltrar){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaActividad = (FachadaActividad) context.getBean("fachadaActividad");
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		
		String idUsuario = (String) request.getSession().getAttribute("usuario");
		String mensaje = "Estado de actividad cambiado correctamente";
		
		boolean esSocio = false;
		
		try {
			esSocio = fachadaUsuario.usuarioEsSocio(idUsuario);
			
			
			fachadaActividad.cambiarEstadoActividad(id);
			
			model.addAttribute("message", mensaje);
			model.addAttribute("fechaDesde", fechaFiltrar);
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			
			if (esSocio)
				return cargarTablaActividadesFiltradaFechaDesde(model, request, fechaFiltrar);
			else
				return cargarTablaActividadesFiltradaFechaDesdeTecnico(model, request, fechaFiltrar);
			
		} catch (SgtiException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", e.getMessage());
			if (esSocio)
				return cargarTablaActividadesFiltradaFechaDesde(model, request, fechaFiltrar);
			else
				return cargarTablaActividadesFiltradaFechaDesdeTecnico(model, request, fechaFiltrar);
			
		}finally{
			context.close();
		}
		if (esSocio)
			return cargarTablaActividadesFiltradaFechaDesde(model, request, fechaFiltrar);
		else
			return cargarTablaActividadesFiltradaFechaDesdeTecnico(model, request, fechaFiltrar);
	}

}
