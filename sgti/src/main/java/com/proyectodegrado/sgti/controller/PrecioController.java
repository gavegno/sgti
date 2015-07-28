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

import com.proyectodegrado.sgti.fachada.FachadaPrecio;
import com.proyectodegrado.sgti.fachada.FachadaUsuario;

@Controller
@RequestMapping("/precio")
public class PrecioController {
	
	private FachadaPrecio fachadaPrecio;
	
	private FachadaUsuario fachadaUsuario;
	
	@RequestMapping(value="/ingresarPrecio", method = RequestMethod.POST)
	public String cargarPrecio(Model model, @RequestParam("fechaDesde") final String fechaDesde, @RequestParam("fechaHasta") final String fechaHasta, @RequestParam("precio") final double precioAgregar, @RequestParam("idContrato") final String idContrato){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaPrecio = (FachadaPrecio) context.getBean("fachadaPrecio");
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		model.addAttribute("idContrato", idContrato);
		try {
			fachadaPrecio.insertarPrecio(fechaDesde, fechaHasta, precioAgregar, idContrato);
			model.addAttribute("tiposDeHora", fachadaUsuario.verTiposDeHora());
		} catch (ClassNotFoundException | IOException | SQLException | ParseException e) {
			e.printStackTrace();
			return "return:/CounterWebApp/precio?status=fail";
		}finally{
			context.close();
		}
		return "tipoHoraComputo";
	}

}
