package com.proyectodegrado.sgti.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyectodegrado.sgti.fachada.FachadaConfiguracion;
import com.proyectodegrado.sgti.fachada.FachadaHorarioLaboral;

@Controller
@RequestMapping("desktop/configuracion")
public class ConfiguracionController {
	
	private FachadaConfiguracion fachadaConfiguracion;
	
	private FachadaHorarioLaboral fachadaHorarioLaboral;
	
	@RequestMapping(value="/ingresarConfiguracion", method = RequestMethod.POST)
	public String cargarPrecio(Model model, @RequestParam("fechaDesde") final String fechaDesde, @RequestParam("fechaHasta") final String fechaHasta, 
			@RequestParam("periodoRenovacion") final int periodoRenovacion, @RequestParam("tipoRenovacion") final String tipoRenovacion, 
			@RequestParam("tipoContrato") final String tipoContrato, @RequestParam("computos") final int computos, 
			@RequestParam("unidadValidez") final String unidadValidez, @RequestParam("periodoValidez") final int periodoValidez, 
			@RequestParam("acumulacion") final String acumulacion, @RequestParam("periodoAcumulacion") final int periodoAcumulacion, 
			@RequestParam("frecuenciaInforme") final int frecuenciaInforme, @RequestParam("frecuenciaFacturacion") final int frecuenciaFacturacion, 
			@RequestParam("frecuenciaExtra") final int frecuenciaComputosExtra, @RequestParam("respuesta") final String tiempoRespuesta, 
			@RequestParam("horarioLaboral") final String horarioLaboral, @RequestParam("idContrato") final String idContrato){
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaConfiguracion = (FachadaConfiguracion) context.getBean("fachadaConfiguracion");
		String mensaje = "La configuración se ha creado correctamente";
		try {
			fachadaConfiguracion.insertarConfiguracion(fechaDesde, fechaHasta, periodoRenovacion, tipoRenovacion, tipoContrato, computos, unidadValidez, periodoValidez, convertirBoolean(acumulacion), periodoAcumulacion, frecuenciaInforme, frecuenciaFacturacion, frecuenciaComputosExtra, tiempoRespuesta, horarioLaboral, idContrato);
		} catch (ClassNotFoundException | IOException | SQLException| ParseException e) {
			e.printStackTrace();
			model.addAttribute("idContrato", idContrato);
			mensaje = e.getMessage();
			return cargarPagina(model, idContrato);
		}finally{
			model.addAttribute("message", mensaje);
			context.close();
		}
		return "redirect:/desktop/paginaPrincipal.jsp?status=success";
	}
	
	@RequestMapping(value="/ingresar", method = RequestMethod.POST)
	public String cargarPagina(Model model, @RequestParam("idContrato") final String idContrato){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaHorarioLaboral = (FachadaHorarioLaboral) context.getBean("fachadaHorarioLaboral");
		try {
			model.addAttribute("horariosLaborales", fachadaHorarioLaboral.verHorariosLaborales());
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			return "desktop/configuracion";
		}finally{
			model.addAttribute("idContrato", idContrato);
			context.close();
		}
		return "desktop/configuracion";
	}
	
	private boolean convertirBoolean(String booleanString){
		if("true".equalsIgnoreCase(booleanString)){
			return true;
		}else{
			return false;
		}
	}
	
}
