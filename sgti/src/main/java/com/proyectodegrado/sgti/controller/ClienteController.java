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
import com.proyectodegrado.sgti.fachada.FachadaCliente;

@Controller
@RequestMapping("/desktop/cliente")
public class ClienteController {
	
	private FachadaCliente fachadaCliente;
	
	//ingreso de un nuevo cliente.
	@RequestMapping(value = "/ingresar", method = RequestMethod.POST)
	public String ingresarCliente(Model model, @RequestParam("clienteNombre") final String nombre, @RequestParam("clienteDireccion") final String direccion, @RequestParam("clienteTelefono") final String telefono)
	{	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaCliente = (FachadaCliente) context.getBean("fachadaCliente");
		String mensaje = "El cliente fue ingresado correctamente";
		try {
			fachadaCliente.insertarCliente(nombre, direccion, telefono);
			
		} catch (IOException | SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			mensaje = "Ha ocurrido un error";
			return "/desktop/clientes";
		} catch (SgtiException e) {
			e.printStackTrace();
			mensaje = e.getMessage();
			return "desktop/clientes";
		}finally{
			model.addAttribute("message", mensaje);
			context.close();
		}
		return "desktop/clientes";
	}
	
	//Carga la tabla de clientes, donde se permitirá editarlos.
	@RequestMapping(value = "/tabla", method = RequestMethod.GET)
	public String cargarTablaCliente(Model model)
	{	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaCliente = (FachadaCliente) context.getBean("fachadaCliente");
		try {
			model.addAttribute("clientes", fachadaCliente.verClientes());
			
		} catch (IOException | SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			//mensaje = e.getMessage();
			return "desktop/tablaClientes";
		}finally{
			//model.addAttribute("message", mensaje);
			context.close();
		}
		return "desktop/tablaClientes";
	}
	
	//Carga la página de editar cliente, con el seleccionado en la tabla.
	@RequestMapping(value="/editar", method = RequestMethod.POST)
	public String cargarCliente(Model model, 
			@RequestParam("nombre") final String nombre){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaCliente = (FachadaCliente) context.getBean("fachadaCliente");
		String mensaje = "El cliente fue cargado para modificar correctamente";
		try {			
			model.addAttribute("cliente", fachadaCliente.verClientePorNombre(nombre));
			
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			mensaje = e.getMessage();
			return "desktop/editarCliente";
		}finally{
			model.addAttribute("message", mensaje);
			context.close();
		}
		return "desktop/editarCliente";
	}
	
	@RequestMapping(value = "/editarClienteOk", method = RequestMethod.POST)
	public String editarCliente(Model model, @RequestParam("clienteNombre") final String nombre, @RequestParam("clienteDireccion") final String direccion, @RequestParam("clienteTelefono") final String telefono)
	{	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaCliente = (FachadaCliente) context.getBean("fachadaCliente");
		String mensaje = "El cliente fue modificado correctamente";
		try {
			fachadaCliente.editarCliente(nombre, direccion, telefono);
			
			
		} catch (IOException | SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			mensaje = e.getMessage();
			return cargarTablaCliente(model);
		}finally{
			model.addAttribute("message", mensaje);
			context.close();
		}
		return cargarTablaCliente(model);
	}
	

	public FachadaCliente getFachadaCliente() {
		return fachadaCliente;
	}

	public void setFachadaCliente(FachadaCliente fachadaCliente) {
		this.fachadaCliente = fachadaCliente;
	}
}

