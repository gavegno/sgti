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

import com.proyectodegrado.sgti.exceptions.SgtiException;
import com.proyectodegrado.sgti.fachada.FachadaPrecio;
import com.proyectodegrado.sgti.fachada.FachadaTipoHora;

@Controller
@RequestMapping("/desktop/precio")
public class PrecioController {
	
	private FachadaPrecio fachadaPrecio;
	
	private FachadaTipoHora fachadaTipoHora;
	
	@RequestMapping(value="/ingresarPrecio", method = RequestMethod.POST)
	public String cargarPrecio(Model model, @RequestParam("fechaDesde") final String fechaDesde, @RequestParam("fechaHasta") final String fechaHasta, @RequestParam("precio") final double precioAgregar, @RequestParam("idContrato") final String idContrato){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaPrecio = (FachadaPrecio) context.getBean("fachadaPrecio");
		fachadaTipoHora = (FachadaTipoHora) context.getBean("fachadaTipoHora");
		String mensaje = "El precio fue ingresado correctamente";
		try {
			fachadaPrecio.insertarPrecio(fechaDesde, fechaHasta, precioAgregar, idContrato);
			model.addAttribute("tiposDeHora", fachadaTipoHora.verTiposDeHora());
		} catch (ClassNotFoundException | IOException | SQLException | ParseException e) {
			e.printStackTrace();
			mensaje = "Ha ocurrido un error";
			return "desktop/precio";
		} catch (SgtiException e) {
			e.printStackTrace();
			mensaje = e.getMessage();
		}finally{
			model.addAttribute("idContrato", idContrato);
			model.addAttribute("message", mensaje);
			context.close();
		}
		return "desktop/tipoHoraComputo";
	}

}
