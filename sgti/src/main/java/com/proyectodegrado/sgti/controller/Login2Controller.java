package com.proyectodegrado.sgti.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login2")
public class Login2Controller {
	
	@RequestMapping(value = "/loguearse", method = RequestMethod.POST)
	public void testLogin(@RequestParam("idUsuario") final String idUsuario, @RequestParam("passwordUsuario") final String contrasena){
		System.out.println("USUARIO: " + idUsuario + " " + contrasena);
	}

}
