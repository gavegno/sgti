package com.proyectodegrado.sgti.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.proyectodegrado.sgti.fachada.FachadaCliente;

@Controller
@RequestMapping("/cliente")
public class ClienteController {
	
	private FachadaCliente fachadaCliente;
	
	@RequestMapping(value = "/ingresar", method = RequestMethod.POST)
	public ModelAndView ingresarCliente(
			@RequestParam("clienteNombre") final String nombre,
			@RequestParam("clienteDireccion") final String direccion,
			@RequestParam("clienteTelefono") final String telefono)
	{
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
			fachadaCliente = (FachadaCliente) context.getBean("fachadaCliente");
			fachadaCliente.insertarCliente(nombre, direccion, telefono);
			context.close();
			
		} catch (IOException | SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/clientes.jsp");
	}
	
	

	public FachadaCliente getFachadaCliente() {
		return fachadaCliente;
	}

	public void setFachadaCliente(FachadaCliente fachadaCliente) {
		this.fachadaCliente = fachadaCliente;
	}
}

