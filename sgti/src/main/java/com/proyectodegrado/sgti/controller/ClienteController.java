package com.proyectodegrado.sgti.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyectodegrado.sgti.fachada.FachadaCliente;

@Controller
@RequestMapping("/desktop/cliente")
public class ClienteController {
	
	private FachadaCliente fachadaCliente;
	
	@RequestMapping(value = "/ingresar", method = RequestMethod.POST)
	public String ingresarCliente(Model model, @RequestParam("clienteNombre") final String nombre, @RequestParam("clienteDireccion") final String direccion, @RequestParam("clienteTelefono") final String telefono)
	{	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaCliente = (FachadaCliente) context.getBean("fachadaCliente");
		String mensaje = "El cliente fue ingresado correctamente";
		try {
			fachadaCliente.insertarCliente(nombre, direccion, telefono);
			
		} catch (IOException | SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			mensaje = e.getMessage();
			return "/desktop/clientes";
		}finally{
			model.addAttribute("message", mensaje);
			context.close();
		}
		return "/desktop/clientes";
	}
	
	

	public FachadaCliente getFachadaCliente() {
		return fachadaCliente;
	}

	public void setFachadaCliente(FachadaCliente fachadaCliente) {
		this.fachadaCliente = fachadaCliente;
	}
}

