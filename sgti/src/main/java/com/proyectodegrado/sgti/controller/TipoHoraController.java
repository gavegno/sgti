package com.proyectodegrado.sgti.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.proyectodegrado.sgti.fachada.FachadaTipoHora;

@Controller
@RequestMapping("/tiposDeHora")
public class TipoHoraController {
	
	private FachadaTipoHora fachadaTipoHora;
	
	@RequestMapping(value = "/ingresar", method = RequestMethod.POST)
	public ModelAndView ingresarTipoHora(@RequestParam("tipoHora") final String tipoHora){
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
			fachadaTipoHora = (FachadaTipoHora) context.getBean("fachadaTipoHora");
			fachadaTipoHora.insertarTipoHora(tipoHora);
			context.close();
			
		} catch (IOException | SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/tiposDeHora.jsp");
	}

	public FachadaTipoHora getFachadaTipoHora() {
		return fachadaTipoHora;
	}

	public void setFachadaTipoHora(FachadaTipoHora fachadaTipoHora) {
		this.fachadaTipoHora = fachadaTipoHora;
	}
}
