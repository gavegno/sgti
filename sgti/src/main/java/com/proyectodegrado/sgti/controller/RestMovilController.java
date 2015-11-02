package com.proyectodegrado.sgti.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proyectodegrado.sgti.modelo.Cliente;


@Controller
@RequestMapping("/desktop/movil")
public class RestMovilController {
	
	@RequestMapping(value = "/cliente", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Cliente usuarioWebService()
	{	Cliente cliente = new Cliente();
		cliente.setNombre("Portezuelo");
		return cliente;
	}

}
