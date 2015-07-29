package com.proyectodegrado.sgti.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaTipoHora = (FachadaTipoHora) context.getBean("fachadaTipoHora");
		try {
			fachadaTipoHora.insertarTipoHora(tipoHora);
		}catch (IOException | SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/tiposDeHora.jsp?status=fail");
		}finally{
			context.close();
		}
		return new ModelAndView("redirect:/tiposDeHora.jsp?status=success");
	}
	
	@RequestMapping(value = "/ingresarComputo", method = RequestMethod.POST)
	public String ingresarComputoTipoDeHora(Model model, @RequestParam("tipoHora") final String nombreTipoHora, @RequestParam("computos") final int computo, @RequestParam("idContrato") final String idContrato){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaTipoHora = (FachadaTipoHora) context.getBean("fachadaTipoHora");
		try {
			fachadaTipoHora.insertarContratoTipoHora(idContrato, nombreTipoHora, computo);
			model.addAttribute("tiposDeHora", fachadaTipoHora.verTiposDeHora());
		}catch (IOException | SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return "tipoHoraComputo?status=fail";
		}finally{
			model.addAttribute("idContrato", idContrato);
			context.close();
		}
		return "tipoHoraComputo";
	}

	public FachadaTipoHora getFachadaTipoHora() {
		return fachadaTipoHora;
	}

	public void setFachadaTipoHora(FachadaTipoHora fachadaTipoHora) {
		this.fachadaTipoHora = fachadaTipoHora;
	}
}