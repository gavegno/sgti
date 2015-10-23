package com.proyectodegrado.sgti.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/desktop/ppal")
public class PrincipalController extends AbstractController{
	
		
	@RequestMapping(value = "/inicio", method = RequestMethod.GET)
	private String principal (Model model, HttpServletRequest request)
	{
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		//String idUsuario = (String) request.getSession().getAttribute("usuario");
		
		ServletContext servletContext = request.getSession().getServletContext();
		String relativeWebPath = "resources/img/logo.png";
		String absoluteDiskPath = servletContext.getRealPath(relativeWebPath);
		
		model.addAttribute("rutaImagen", "http://www.itapua.com.uy/images/logo.png");
		
		
		context.close();
		
		return "desktop/paginaPrincipal";
		
	}

}
