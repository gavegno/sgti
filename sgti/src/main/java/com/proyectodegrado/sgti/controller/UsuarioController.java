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
		return "desktop/tecnicos";
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
	
	//Carga la tabla de usuarios, donde se permitirá editarlos.
		@RequestMapping(value = "/tabla", method = RequestMethod.GET)
		public String cargarTablaUsuarios(Model model)
		{	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
			fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
			String mensaje = "";
			try {
				model.addAttribute("usuarios", fachadaUsuario.seleccionarUsuarios());
				
			} catch (IOException | SQLException | ClassNotFoundException e) {
				e.printStackTrace();
				mensaje = e.getMessage();
				return "desktop/tablaUsuarios";
			}finally{
				model.addAttribute("message", mensaje);
				context.close();
			}
			return "desktop/tablaUsuarios";
		}
		
		//Carga la página de editar usuario, con el seleccionado en la tabla.
		@RequestMapping(value="/editar", method = RequestMethod.POST)
		public String cargarUsuario(Model model, 
				@RequestParam("id") final String id){
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
			fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
			String mensaje = "El usuario fue cargado para modificar correctamente";
			try {			
				model.addAttribute("usuario", fachadaUsuario.seleccionarUsuario(id));
				
			} catch (ClassNotFoundException | IOException | SQLException e) {
				e.printStackTrace();
				mensaje = e.getMessage();
				return "desktop/editarTecnicos";
			}finally{
				model.addAttribute("message", mensaje);
				context.close();
			}
			return "desktop/editarTecnicos";
		}
		
		@RequestMapping(value = "/editarUsuarioOk", method = RequestMethod.POST)
		public String editarUsuario(Model model, 
				@RequestParam("id") final String id, 
				@RequestParam("nombre") final String nombre, 
				@RequestParam("apellido") final String apellido, 
				@RequestParam("email") final String email, 
				@RequestParam("telefono") final String telefono){
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
			fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
			String mensaje = "Se enviaron los datos de Usuario a modificar";
			try {			
				fachadaUsuario.editarUsuario(id, nombre, apellido, email, telefono);
				
			} catch (ClassNotFoundException | IOException | SQLException e) {
				e.printStackTrace();
				mensaje = e.getMessage();
				return cargarTablaUsuarios(model);
			}finally{
				model.addAttribute("message", mensaje);
				context.close();
			}
			return cargarTablaUsuarios(model);
		}
		
	public FachadaUsuario getFachadaUsuario() {
		return fachadaUsuario;
	}

	public void setFachadaUsuario(FachadaUsuario fachadaUsuario) {
		this.fachadaUsuario = fachadaUsuario;
	}
}
