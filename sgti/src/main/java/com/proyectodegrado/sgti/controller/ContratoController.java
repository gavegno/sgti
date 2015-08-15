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
import com.proyectodegrado.sgti.fachada.FachadaContrato;
import com.proyectodegrado.sgti.fachada.FachadaUsuario;

@Controller
@RequestMapping("/desktop/contrato")
public class ContratoController {
	
	private FachadaContrato fachadaContrato;
	
	private FachadaCliente fachadaCliente;
	
	private FachadaUsuario fachadaUsuario;
	
	@RequestMapping(value="/ingresar", method = RequestMethod.GET)
	public String cargarPagina(Model model){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		fachadaCliente = (FachadaCliente) context.getBean("fachadaCliente");
		try {
			model.addAttribute("contrapartes", fachadaUsuario.verUsuariosContraparte());
			model.addAttribute("clientes", fachadaCliente.verClientes());
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			return "desktop/contrato";
		}finally{
			context.close();
		}
		return "desktop/contrato";
	}
	
	@RequestMapping(value="/ingresarContrato", method = RequestMethod.POST)
	public String cargarPagina(Model model, @RequestParam("id") final String id, @RequestParam("cliente") final String nombreCliente, @RequestParam("contraparte") final String idContraparte){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaContrato = (FachadaContrato) context.getBean("fachadaContrato");
		String mensaje = "El contrato fue ingresado correctamente";
		try {
			fachadaContrato.ingresarContrato(id, idContraparte, nombreCliente);
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			mensaje = "Ha ocurrido un error";
			return cargarPagina(model);
		} catch (SgtiException e) {
			e.printStackTrace();
			mensaje = e.getMessage();
			return cargarPagina(model);
		}finally{
			model.addAttribute("idContrato", id);
			model.addAttribute("message", mensaje);
			context.close();
		}
		return "desktop/precio";
	}
	
	//Carga la tabla de clientes, donde se permitirá editarlos.
		@RequestMapping(value = "/tabla", method = RequestMethod.GET)
		public String cargarTablaContratosPorContraparte(Model model)//, @RequestParam("id") final String idContraparte)
		{	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
			fachadaContrato = (FachadaContrato) context.getBean("fachadaContrato");
			String idContraparteHARDCODED = "petecoJr";
			try {
				model.addAttribute("contratos", fachadaContrato.verContratosPorContraparte(idContraparteHARDCODED));
				
			} catch (IOException | SQLException | ClassNotFoundException e) {
				e.printStackTrace();
				//mensaje = e.getMessage();
				return "desktop/tablaContratosCliente";
			}finally{
				//model.addAttribute("message", mensaje);
				context.close();
			}
			return "desktop/tablaContratosCliente";
		}
	
}
