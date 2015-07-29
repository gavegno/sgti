package com.proyectodegrado.sgti.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyectodegrado.sgti.fachada.FachadaHorarioLaboral;

@Controller
@RequestMapping("/dias")
public class HorarioLaboralController {
	
	private FachadaHorarioLaboral fachadaHorarioLaboral;
	
	@RequestMapping(value="/ingresardia", method = RequestMethod.POST)
	public String ingresarDiaHorarioLaboral(Model model, @RequestParam("idHorarioLaboral") final String id, @RequestParam("nombreDia") final String nombreDia, 
			@RequestParam("horaDesde") final String horaDesde, @RequestParam("horaHasta") final String horaHasta, @RequestParam("idContrato") final String idContrato){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaHorarioLaboral = (FachadaHorarioLaboral) context.getBean("fachadaHorarioLaboral");
		try {
			fachadaHorarioLaboral.insertarDiaHorarioLaboral(id, nombreDia, horaDesde, horaHasta);
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			return "dias?status=fail";
		}finally{
			model.addAttribute("idHorarioLaboral", id);
			model.addAttribute("idContrato", idContrato);
			context.close();
		}
		return "dias";
	}
	
	@RequestMapping(value="/ingresarHorarioLaboral", method = RequestMethod.POST)
	public String ingresarHorarioLaboral(Model model, @RequestParam("idHorarioLaboral") final String id, @RequestParam("nombreDia") final String nombreDia, 
			@RequestParam("horaDesde") final String horaDesde, @RequestParam("horaHasta") final String horaHasta){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaHorarioLaboral = (FachadaHorarioLaboral) context.getBean("fachadaHorarioLaboral");
		try {
			fachadaHorarioLaboral.insertarDiaHorarioLaboral(id, nombreDia, horaDesde, horaHasta);
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			return "agregarHorarioLaboral?status=fail";
		}finally{
			model.addAttribute("idHorarioLaboral", id);
			context.close();
		}
		return "agregarHorarioLaboral";
	}
	
	@RequestMapping(value="/ingresar", method = RequestMethod.POST)
	public String cargarPagina(Model model, @RequestParam("idContrato") final String idContrato){
		model.addAttribute("idContrato", idContrato);
		return "dias";
	}

}
