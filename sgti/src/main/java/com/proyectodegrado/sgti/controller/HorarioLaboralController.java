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
		model.addAttribute("idHorarioLaboral", id);
		model.addAttribute("idContrato", idContrato);
		try {
			fachadaHorarioLaboral.insertarDiaHorarioLaboral(id, nombreDia, horaDesde, horaHasta);
		} catch (ClassNotFoundException | IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			context.close();
		}
		
		return "dias";
	}

}
