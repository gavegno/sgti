package com.proyectodegrado.sgti.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.proyectodegrado.sgti.fachada.FachadaUsuario;

@Controller
@RequestMapping("/tecnicos")
public class UsuarioController {
	
	private FachadaUsuario fachadaUsuario;
	
	@RequestMapping(value = "/ingresarUsuario", method = RequestMethod.POST)
	public ModelAndView ingresarUsuario(@RequestParam("id") final String id, @RequestParam("contrasena") final String contrasena, @RequestParam("nombre") final String nombre, @RequestParam("apellido") final String apellido, @RequestParam("email") final String email, @RequestParam("telefono") final String telefono, @RequestParam("tipo") final String tipo, @RequestParam("tipoHora") final List<String> tipoHora){
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
			fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
			fachadaUsuario.ingresarUsuario(id, nombre, apellido, contrasena, email, telefono, tipo, tipoHora);
			context.close();
			return new ModelAndView("redirect:/tecnicos/ingresar?status=success");
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			return new ModelAndView("redirect:/tecnicos/ingresar?status=fail");
			
		}
	}
	
	@RequestMapping(value = "/ingresar", method = RequestMethod.GET)
	public String cargarPagina(Model model){
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
			fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
			List<String> list = fachadaUsuario.verTiposDeHora();
			context.close();
			model.addAttribute("tipos", list);
			
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			
		}
		return "tecnicos";
	}
	
	public FachadaUsuario getFachadaUsuario() {
		return fachadaUsuario;
	}

	public void setFachadaUsuario(FachadaUsuario fachadaUsuario) {
		this.fachadaUsuario = fachadaUsuario;
	}
}
