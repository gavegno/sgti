package com.proyectodegrado.sgti.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
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
import com.proyectodegrado.sgti.fachada.FachadaUsuario;
import com.proyectodegrado.sgti.modelo.Usuario;

@Controller
@RequestMapping("/desktop/actividad")
public class ActividadController {
	
	private static final String MENSAJE_ERROR = "Ha ocurrido un error";

	private FachadaUsuario fachadaUsuario;
	
	private FachadaContrato fachadaContrato;
	
	private FachadaActividad fachadaActividad;
	
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
			model.addAttribute("message", e.getMessage());
			return "desktop/actividad";
		}finally{
			context.close();
		}
		return "desktop/actividad";
	}
	
	@RequestMapping(value="/ingresarActividad", method = RequestMethod.POST)
	public String insertarActividad(Model model, @RequestParam("id") final String id, @RequestParam("tipo") final String tipo, 
			@RequestParam("periodo") final int periodo, @RequestParam("fecha") final String fecha, @RequestParam("usuario") final String usuario,
			@RequestParam("contrato") final String contrato){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaActividad = (FachadaActividad) context.getBean("fachadaActividad");
		String mensaje = "La actividad fue ingresada correctamente";
		try {
			fachadaActividad.ingresarActividad(id, tipo, periodo, fecha, usuario, contrato);
		} catch (ClassNotFoundException | IOException | SQLException | ParseException e) {
			e.printStackTrace();
			mensaje = MENSAJE_ERROR;
			return cargarPagina(model);
		} catch (SgtiException e) {
			e.printStackTrace();
			mensaje = e.getMessage();
			return cargarPagina(model);
		}finally{
			model.addAttribute("message", mensaje);
			context.close();
		}
		return cargarPagina(model);
	}
	
	@RequestMapping(value="/editarActividad", method = RequestMethod.POST)
	public String editarActividad(Model model, @RequestParam("id") final String id, @RequestParam("tipo") final String tipo, 
			@RequestParam("periodo") final int periodo, @RequestParam("fecha") final String fecha, @RequestParam("usuario") final String usuario,
			@RequestParam("contrato") final String contrato){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaActividad = (FachadaActividad) context.getBean("fachadaActividad");
		String mensaje = "La actividad fue editada correctamente";
		try {
			fachadaActividad.editarActividad(id, tipo, periodo, fecha, usuario, contrato);
		} catch (ClassNotFoundException | IOException | SQLException | ParseException e) {
			e.printStackTrace();
			mensaje = MENSAJE_ERROR;
			return cargarTablaActividades(model);
		}finally{
			model.addAttribute("message", mensaje);
			context.close();
		}
		return cargarTablaActividades(model);
	}
	
	@RequestMapping(value="/eliminarActividad", method = RequestMethod.POST)
	public String eliminarActividad(Model model, @RequestParam("id") final String id){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaActividad = (FachadaActividad) context.getBean("fachadaActividad");
		String mensaje = "La actividad fue eliminada correctamente";
		try {
			fachadaActividad.borrarActividad(id);
		} catch (ClassNotFoundException | IOException | SQLException | ParseException e) {
			e.printStackTrace();
			mensaje = MENSAJE_ERROR;
			return cargarTablaActividades(model);
		}finally{
			model.addAttribute("message", mensaje);
			context.close();
		}
		return cargarTablaActividades(model);
	}
	
	@RequestMapping(value="/ver", method = RequestMethod.GET)
	public String cargarTablaActividades(Model model){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaActividad = (FachadaActividad) context.getBean("fachadaActividad");
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		try {
			List<Usuario> usuarios = fachadaUsuario.seleccionarUsuariosSocio();
			usuarios.addAll(fachadaUsuario.seleccionarUsuariosTecnico());
			model.addAttribute("actividades", fachadaActividad.seleccionarActividades());
			model.addAttribute("usuarios", usuarios);
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			model.addAttribute("message", MENSAJE_ERROR);
			return cargarPagina(model);
		}finally{
			context.close();
		}
		return "/desktop/tablaActividad";
	}
	
	@RequestMapping(value="/verPorUsuario", method = RequestMethod.GET)
	public String cargarTablaActividadesPorUsuaraio(Model model, HttpServletRequest request){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaActividad = (FachadaActividad) context.getBean("fachadaActividad");
		try {
			String idUsuario = (String) request.getSession().getAttribute("usuario");
			model.addAttribute("actividades", fachadaActividad.seleccionarActividadesPorUsuario(idUsuario));
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			model.addAttribute("message", MENSAJE_ERROR);
			return "/desktop/tablaActividad";
		}finally{
			context.close();
		}
		return "/desktop/tablaActividadUsuario";
	}

}
