package com.proyectodegrado.sgti.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.proyectodegrado.sgti.dto.DataNotificacion;
import com.proyectodegrado.sgti.fachada.FachadaNotificacion;

public class AbstractController {
	
	private FachadaNotificacion fachadaNotificacion;
	
	@ModelAttribute("notificaciones")
	public int notificaciones()
	{
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaNotificacion = (FachadaNotificacion) context.getBean("fachadaNotificacion");
		int dias = 7;
		String tipoUsuario = (String) attr.getRequest().getSession().getAttribute("tipoUsuario");
		String idUsuario = (String) attr.getRequest().getSession().getAttribute("usuario");
		try {
			return fachadaNotificacion.notificar(tipoUsuario, idUsuario, dias).size();
		} catch (ClassNotFoundException | IOException | SQLException
				| ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			context.close();
		}
		return 0;
	}
	
	@ModelAttribute("despliegeNotificaciones")
	public List<DataNotificacion> despliegeNotificaciones()
	{
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaNotificacion = (FachadaNotificacion) context.getBean("fachadaNotificacion");
		int dias = 7;
		String tipoUsuario = (String) attr.getRequest().getSession().getAttribute("tipoUsuario");
		String idUsuario = (String) attr.getRequest().getSession().getAttribute("usuario");
		try {
			return fachadaNotificacion.notificar(tipoUsuario, idUsuario, dias);
		} catch (ClassNotFoundException | IOException | SQLException
				| ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			context.close();
		}
		return new ArrayList<DataNotificacion>();
	}

}
