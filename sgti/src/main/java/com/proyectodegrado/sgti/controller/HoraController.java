package com.proyectodegrado.sgti.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

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
import com.proyectodegrado.sgti.fachada.FachadaHora;
import com.proyectodegrado.sgti.fachada.FachadaTipoHora;
import com.proyectodegrado.sgti.modelo.Hora;


@Controller
@RequestMapping("/desktop/hora")
public class HoraController {
	
	private static final String MENSAJE_ERROR = "Ha ocurrido un error";

	private FachadaHora fachadaHora;
	
	private FachadaTipoHora fachadaTipoHora;
	
	private FachadaActividad fachadaActividad;
	
	private FachadaContrato fachadaContrato;
	
	@RequestMapping(value="/ingresar", method = RequestMethod.GET)
	public String cargarPagina(Model model, HttpServletRequest request){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaHora = (FachadaHora) context.getBean("fachadaHora");
		fachadaTipoHora = (FachadaTipoHora) context.getBean("fachadaTipoHora");
		fachadaActividad = (FachadaActividad) context.getBean("fachadaActividad");
		fachadaContrato = (FachadaContrato) context.getBean("fachadaContrato");
		String idUsuario = (String) request.getSession().getAttribute("usuario");
		try {
			model.addAttribute("tipoHoras", fachadaTipoHora.verTiposDeHora());
			model.addAttribute("actividades", fachadaActividad.seleccionarActividades());
			model.addAttribute("contratos", fachadaContrato.seleccionarContratosVigentes());
			model.addAttribute("horasRegistradas", fachadaHora.seleccionarHorasPorUsuario(idUsuario));
		} catch (ClassNotFoundException | IOException | SQLException
				| ParseException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return "desktop/tablaHoras";
		}finally{
			context.close();
		}
		return "desktop/tablaHoras";
	}
	
	@RequestMapping(value="/ingresarHora", method = RequestMethod.POST)
	public String insertarHora(Model model, HttpServletRequest request, @RequestParam("fechadesde") final String fechaDesde, @RequestParam("fechahasta") final String fechaHasta, 
			@RequestParam("tipohora") final String tipoHora, @RequestParam("remoto") final String remoto, @RequestParam("contrato") final String idContrato,
			@RequestParam(required=false, value="actividad") String idActividad, @RequestParam("descripcion") final String descripcion, @RequestParam("comentario") final String comentario){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaHora = (FachadaHora) context.getBean("fachadaHora");
		String mensaje = "La Hora fue ingresada correctamente";
		try {
			String idUsuario = (String) request.getSession().getAttribute("usuario");
			
			if (idActividad.matches("nulo"))
				idActividad = null;
			
			fachadaHora.registrarHora(fechaDesde, fechaHasta, tipoHora, remoto, idUsuario, idContrato, idActividad, descripcion, comentario);
			int duracion = fachadaHora.diferenciaEnMinutos(fechaDesde, fechaHasta);
			mensaje = mensaje + ", duración: " + duracion + " minutos";
			model.addAttribute("message", mensaje);
			
		} catch (ClassNotFoundException | SQLException | IOException | ParseException e) {
			e.printStackTrace();
			mensaje = e.getMessage();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			cargarPagina(model, request);
		}
		catch (SgtiException e) {
			e.printStackTrace();
			mensaje = e.getMessage();
			model.addAttribute("errorMessage", mensaje);
			return cargarPagina(model, request);
		}finally{
			context.close();
		}
		return cargarPagina(model, request);
	}
	
	@RequestMapping(value="/editarHora", method = RequestMethod.POST)
	public String editarHora(Model model, HttpServletRequest request, @RequestParam("fechadesde") final String fechaDesde, @RequestParam("fechahasta") final String fechaHasta, 
			@RequestParam("tipohora") final String tipoHora, @RequestParam("remoto") final String remoto, @RequestParam("contrato") final String idContrato,
			@RequestParam("actividad") String idActividad, @RequestParam("descripcion") final String descripcion, @RequestParam("comentario") final String comentario, 
			@RequestParam("id") final String id, @RequestParam("fechainformar") final String fechaInformar, @RequestParam("fechafacturar") final String fechaFacturar, 
			@RequestParam("fechacomputar") final String fechaComputar){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaHora = (FachadaHora) context.getBean("fachadaHora");
		String mensaje = "La hora fue editada correctamente";
		try {

			if (idActividad.matches("nulo"))
				idActividad = null;
			
			fachadaHora.editarHora(fechaDesde, fechaHasta, tipoHora, remoto, idContrato, idActividad, descripcion, comentario, id, fechaInformar, fechaFacturar, fechaComputar);
			int duracion = fachadaHora.diferenciaEnMinutos(fechaDesde, fechaHasta);
			mensaje = mensaje + ", duración: " + duracion + " minutos";
			model.addAttribute("message", mensaje);
		} catch (ClassNotFoundException | SQLException | IOException | ParseException e) {
			e.printStackTrace();
			mensaje = MENSAJE_ERROR;
			model.addAttribute("errorMessage", mensaje);
			cargarPagina(model, request);
		}finally{
			context.close();
		}
		return cargarPagina(model, request);
	}
	
	@RequestMapping(value="/detalleHora", method = RequestMethod.POST)
	public String detalleHora(Model model, HttpServletRequest request, @RequestParam("id") final int idHora){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaHora = (FachadaHora) context.getBean("fachadaHora");
		
		try {
			
			Hora hora = fachadaHora.seleccionarHora(idHora);
			
			model.addAttribute("hora", fachadaHora.seleccionarHora(idHora));
			model.addAttribute("fechasInforme",fachadaHora.proximas3FechasInforme(hora.getIdContrato()));
			model.addAttribute("fechasFacturacion",fachadaHora.proximas3FechasFacturacion(hora.getIdContrato()));
			model.addAttribute("fechasComputacion",fachadaHora.proximas3FechasFacturacion(hora.getIdContrato()));
			
		} catch (ClassNotFoundException | SQLException | IOException
				| ParseException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return "desktop/editarHora";
		}finally{
			context.close();
		}
		return "desktop/editarHora";
	}
	
	@RequestMapping(value="/detalleHoraOk", method = RequestMethod.POST)
	public String detalleHoraOk(Model model, HttpServletRequest request, 
			@RequestParam("id") final int idHora, 
			@RequestParam("fechaInformar") final String fechaInformar, 
			@RequestParam("fechaFacturar") final String fechaFacturar, 
			@RequestParam("fechaComputar") final String fechaComputar){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaHora = (FachadaHora) context.getBean("fachadaHora");
		String mensaje = "Cambios guardados con éxito";
		try {
			
			fachadaHora.editarHoraDetalle(idHora, fechaInformar, fechaFacturar, fechaComputar);
			
			
		} catch (ClassNotFoundException | SQLException | IOException
				| ParseException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return cargarPagina(model, request);
		}finally{
			model.addAttribute("message", mensaje);
			context.close();
		}
		return cargarPagina(model, request);
	}
	
	@RequestMapping(value="/validarHora", method = RequestMethod.POST)
	public String validarHora(Model model, HttpServletRequest request, @RequestParam("id") final int id){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaHora = (FachadaHora) context.getBean("fachadaHora");
		String mensaje = "Validación cambiada correctamente";
		try {
			fachadaHora.cambiarValidacionHora(id);
			model.addAttribute("message", mensaje);
		} catch (ClassNotFoundException | SQLException | IOException | ParseException e) {
			e.printStackTrace();
			mensaje = MENSAJE_ERROR;
			model.addAttribute("errorMessage", mensaje);
			detalleHora(model, request, id);
		}finally{
			context.close();
		}
		return detalleHora(model, request, id);
	}
	
	@RequestMapping(value="/copiarHora", method = RequestMethod.POST)
	public String copiarHora(Model model, HttpServletRequest request, @RequestParam("id") final int id){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaHora = (FachadaHora) context.getBean("fachadaHora");
		String mensaje = "La Hora fue ingresada correctamente";
		try {
			model.addAttribute("horaCopiada", fachadaHora.seleccionarHora(id));
		} catch (ClassNotFoundException | SQLException | IOException
				| ParseException e) {
			e.printStackTrace();
			mensaje = MENSAJE_ERROR;
			model.addAttribute("errorMessage", mensaje);
			cargarPagina(model, request);
		}finally{
			context.close();
		}
		return cargarPagina(model, request);
	}

}