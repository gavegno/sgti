package com.proyectodegrado.sgti.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

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
	
	private static final String MENSAJE_ERROR = "Ha ocurrido un error";

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
			model.addAttribute("errorMessage", MENSAJE_ERROR);
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
			model.addAttribute("message", mensaje);
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			mensaje = MENSAJE_ERROR;
			model.addAttribute("errorMessage", mensaje);
			return cargarPagina(model);
		} catch (SgtiException e) {
			e.printStackTrace();
			mensaje = e.getMessage();
			model.addAttribute("errorMessage", mensaje);
			return cargarPagina(model);
		}finally{
			model.addAttribute("idContrato", id);
			context.close();
		}
		return "desktop/precio";
	}
	
	//Carga la tabla de contratos, donde se permitirá editarlos.
		@RequestMapping(value = "/tabla", method = RequestMethod.GET)
		public String cargarTablaContratosPorContraparte(Model model, HttpServletRequest request)//, @RequestParam("id") final String idContraparte)
		{	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
			fachadaContrato = (FachadaContrato) context.getBean("fachadaContrato");
			String idUsuario = (String) request.getSession().getAttribute("usuario");
			try {
				model.addAttribute("contratos", fachadaContrato.verContratosPorContraparte(idUsuario));
				
			} catch (IOException | SQLException | ClassNotFoundException e) {
				e.printStackTrace();
				model.addAttribute("errorMessage", MENSAJE_ERROR);
				return "desktop/tablaContratosCliente";
			}finally{
				context.close();
			}
			return "desktop/tablaContratosCliente";
		}
	
}
