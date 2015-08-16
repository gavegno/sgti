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

import com.proyectodegrado.sgti.fachada.FachadaActividad;
import com.proyectodegrado.sgti.fachada.FachadaContrato;
import com.proyectodegrado.sgti.fachada.FachadaHora;
import com.proyectodegrado.sgti.fachada.FachadaTipoHora;
import com.proyectodegrado.sgti.modelo.Hora;


@Controller
@RequestMapping("/desktop/hora")
public class HoraController {
	
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
			model.addAttribute("message", e.getMessage());
			return "desktop/tablaHoras";
		}finally{
			context.close();
		}
		return "desktop/tablaHoras";
	}
	
	@RequestMapping(value="/ingresarHora", method = RequestMethod.POST)
	public String insertarHora(Model model, HttpServletRequest request, @RequestParam("fechadesde") final String fechaDesde, @RequestParam("fechahasta") final String fechaHasta, 
			@RequestParam("tipohora") final String tipoHora, @RequestParam("remoto") final String remoto, @RequestParam("contrato") final String idContrato,
			@RequestParam(required=false, value="actividad") final String idActividad, @RequestParam("descripcion") final String descripcion){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaHora = (FachadaHora) context.getBean("fachadaHora");
		String mensaje = "La Hora fue ingresada correctamente";
		try {
			String idUsuario = (String) request.getSession().getAttribute("usuario");
			fachadaHora.registrarHora(fechaDesde, fechaHasta, tipoHora, remoto, idUsuario, idContrato, idActividad, descripcion);
		} catch (ClassNotFoundException | SQLException | IOException
				| ParseException e) {
			e.printStackTrace();
			mensaje = e.getMessage();
			cargarPagina(model, request);
		}finally{
			model.addAttribute("message", mensaje);
			context.close();
		}
		return cargarPagina(model, request);
	}
	
	@RequestMapping(value="/editarHora", method = RequestMethod.POST)
	public String editarHora(Model model, HttpServletRequest request, @RequestParam("fechadesde") final String fechaDesde, @RequestParam("fechahasta") final String fechaHasta, 
			@RequestParam("tipohora") final String tipoHora, @RequestParam("remoto") final String remoto, @RequestParam("contrato") final String idContrato,
			@RequestParam("actividad") final String idActividad, @RequestParam("descripcion") final String descripcion, @RequestParam("id") final String id, 
			@RequestParam("fechainformar") final String fechaInformar, @RequestParam("fechafacturar") final String fechaFacturar, 
			@RequestParam("fechacomputar") final String fechaComputar){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaHora = (FachadaHora) context.getBean("fachadaHora");
		String mensaje = " ";
		try {
			fachadaHora.editarHora(fechaDesde, fechaHasta, tipoHora, remoto, idContrato, idActividad, descripcion, id, fechaInformar, fechaFacturar, fechaComputar);
		} catch (ClassNotFoundException | SQLException | IOException
				| ParseException e) {
			e.printStackTrace();
			mensaje = e.getMessage();
			cargarPagina(model, request);
		}finally{
			model.addAttribute("message", mensaje);
			context.close();
		}
		return cargarPagina(model, request);
	}
	
	@RequestMapping(value="/detalleHora", method = RequestMethod.POST)
	public String detalleHora(Model model, HttpServletRequest request, @RequestParam("id") final int idHora){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaHora = (FachadaHora) context.getBean("fachadaHora");
		String mensaje = " ";
		
		try {
			
			Hora hora = fachadaHora.seleccionarHora(idHora);
			
			model.addAttribute("hora", fachadaHora.seleccionarHora(idHora));
			model.addAttribute("fechasInforme",fachadaHora.proximas3FechasInforme(hora.getIdContrato()));
			model.addAttribute("fechasFacturacion",fachadaHora.proximas3FechasFacturacion(hora.getIdContrato()));
			model.addAttribute("fechasComputacion",fachadaHora.proximas3FechasFacturacion(hora.getIdContrato()));
			
		} catch (ClassNotFoundException | SQLException | IOException
				| ParseException e) {
			e.printStackTrace();
			mensaje = "Ha ocurrido un error";
			return "desktop/editarHora";
		}finally{
			model.addAttribute("message", mensaje);
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
		String mensaje = " ";
		
		try {
			
			fachadaHora.editarHoraDetalle(idHora, fechaInformar, fechaFacturar, fechaComputar);
			
			
		} catch (ClassNotFoundException | SQLException | IOException
				| ParseException e) {
			e.printStackTrace();
			mensaje = "Ha ocurrido un error";
			return cargarPagina(model, request);
		}finally{
			model.addAttribute("message", mensaje);
			context.close();
		}
		return cargarPagina(model, request);
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
			mensaje = e.getMessage();
			cargarPagina(model, request);
		}finally{
			model.addAttribute("message", mensaje);
			context.close();
		}
		return cargarPagina(model, request);
	}

}