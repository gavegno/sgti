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

import com.proyectodegrado.sgti.fachada.FachadaUsuario;
import com.proyectodegrado.sgti.modelo.Usuario;

@Controller
@RequestMapping("/desktop/login")
public class LoginController {
	
	private FachadaUsuario fachadaUsuario;
	
	@RequestMapping(value = "/loguearse", method = RequestMethod.POST)
	public String login(Model model, HttpServletRequest request, @RequestParam("idUsuario") final String idUsuario, @RequestParam("passwordUsuario") final String contrasena){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		try {
			Usuario usuario = fachadaUsuario.seleccionarUsuario(idUsuario);
			if(usuario.getContrasena() != null && usuario.getContrasena().equals(contrasena)){
				request.getSession().setAttribute("usuario", idUsuario);
				request.getSession().setAttribute("tipoUsuario", usuario.getTipo());
			}else{
				model.addAttribute("mensaje", "El usuario o contraseña no es correcto");
				return "/desktop/login2";
			}
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			model.addAttribute("mensaje", "Ocurrió un error al tratar de loguear el usuario");
			return "/desktop/login2";
		}finally{
			context.close();
		}
		return "/desktop/paginaPrincipal";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Model model, HttpServletRequest request){
		request.getSession().invalidate();
		return "/desktop/login2";
	}

}
