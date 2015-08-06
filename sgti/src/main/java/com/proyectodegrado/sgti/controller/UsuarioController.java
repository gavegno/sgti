package com.proyectodegrado.sgti.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyectodegrado.sgti.fachada.FachadaTipoHora;
import com.proyectodegrado.sgti.fachada.FachadaUsuario;

@Controller
@RequestMapping("/desktop/tecnicos")
public class UsuarioController {
	
	private FachadaUsuario fachadaUsuario;
	
	private FachadaTipoHora fachadaTipoHora;
	
	@RequestMapping(value = "/ingresarUsuario", method = RequestMethod.POST)
	public String ingresarUsuario(Model model, @RequestParam("id") final String id, @RequestParam("contrasena") final String contrasena, @RequestParam("nombre") final String nombre, @RequestParam("apellido") final String apellido, @RequestParam("email") final String email, @RequestParam("telefono") final String telefono, @RequestParam("tipo") final String tipo, @RequestParam(value = "tipoHora", required = false) final List<String> tipoHora){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		String mensaje = "El usuario ha sido creado correctamente";
		try {
			fachadaUsuario.ingresarUsuario(id, nombre, apellido, contrasena, email, telefono, tipo, tipoHora == null ? new ArrayList<String>() : tipoHora);
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			mensaje = e.getMessage();
			return cargarPagina(model);
			
		}finally{
			model.addAttribute("message", mensaje);
			context.close();
		}
		return "desktop/tecnicos/ingresar";
	}
	
	@RequestMapping(value = "/ingresar", method = RequestMethod.GET)
	public String cargarPagina(Model model){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaTipoHora = (FachadaTipoHora) context.getBean("fachadaTipoHora");
		try {
			model.addAttribute("tipos", fachadaTipoHora.verTiposDeHora());
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
		}finally{
			context.close();
		}
		return "desktop/tecnicos";
	}
	
	public FachadaUsuario getFachadaUsuario() {
		return fachadaUsuario;
	}

	public void setFachadaUsuario(FachadaUsuario fachadaUsuario) {
		this.fachadaUsuario = fachadaUsuario;
	}
}
