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
import com.proyectodegrado.sgti.fachada.FachadaContrato;
import com.proyectodegrado.sgti.fachada.FachadaUsuario;

@Controller
@RequestMapping("/contrato")
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		context.close();
		return "contrato";
	}
	
	@RequestMapping(value="/ingresarContrato", method = RequestMethod.POST)
	public String cargarContrato(Model model, @RequestParam("id") final String id, @RequestParam("cliente") final String nombreCliente, @RequestParam("contraparte") final String idContraparte){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaContrato = (FachadaContrato) context.getBean("fachadaContrato");
		try {
			fachadaContrato.ingresarContrato(id, idContraparte, nombreCliente);
			model.addAttribute("idContrato", id);
		} catch (ClassNotFoundException | IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		context.close();
		return "precio";
	}
	
}
