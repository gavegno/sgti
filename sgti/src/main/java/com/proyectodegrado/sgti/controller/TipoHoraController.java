package com.proyectodegrado.sgti.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyectodegrado.sgti.exceptions.SgtiException;
import com.proyectodegrado.sgti.fachada.FachadaTipoHora;

@Controller
@RequestMapping("/desktop/tiposDeHora")
public class TipoHoraController {
	
	private FachadaTipoHora fachadaTipoHora;
	
	@RequestMapping(value = "/ingresar", method = RequestMethod.POST)
	public String ingresarTipoHora(Model model,@RequestParam("tipoHora") final String tipoHora){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaTipoHora = (FachadaTipoHora) context.getBean("fachadaTipoHora");
		String mensaje = "El tipo de hora fue ingresado correctamente";
		try {
			fachadaTipoHora.insertarTipoHora(tipoHora);
			model.addAttribute("message", mensaje);
		}catch (IOException | SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			mensaje = "Ha ocurrido un error";
			model.addAttribute("errorMessage", mensaje);
			return "desktop/tiposDeHora";
		} catch (SgtiException e) {
			e.printStackTrace();
			mensaje = e.getMessage();
			model.addAttribute("errorMessage", mensaje);
			return "desktop/tiposDeHora";
		}finally{
			context.close();
		}
		return "desktop/tiposDeHora";
	}
	
	@RequestMapping(value = "/ingresarComputo", method = RequestMethod.POST)
	public String ingresarComputoTipoDeHora(Model model, @RequestParam("tipoHora") final String nombreTipoHora, @RequestParam("computos") final int computo, @RequestParam("idContrato") final String idContrato){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaTipoHora = (FachadaTipoHora) context.getBean("fachadaTipoHora");
		String mensaje = "Se ha asignado correctamente el cómputo al tipo de hora";
		try {
			fachadaTipoHora.insertarContratoTipoHora(idContrato, nombreTipoHora, computo);
			model.addAttribute("tiposDeHora", fachadaTipoHora.verTiposDeHora());
			model.addAttribute("message", mensaje);
		}catch (IOException | SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			mensaje = e.getMessage();
			model.addAttribute("errorMessage", mensaje);
			return "desktop/tipoHoraComputo";
		}finally{
			model.addAttribute("idContrato", idContrato);
			context.close();
		}
		return "desktop/tipoHoraComputo";
	}

	public FachadaTipoHora getFachadaTipoHora() {
		return fachadaTipoHora;
	}

	public void setFachadaTipoHora(FachadaTipoHora fachadaTipoHora) {
		this.fachadaTipoHora = fachadaTipoHora;
	}
}