package com.proyectodegrado.sgti.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyectodegrado.sgti.fachada.FachadaActividad;
import com.proyectodegrado.sgti.fachada.FachadaContrato;
import com.proyectodegrado.sgti.fachada.FachadaHora;
import com.proyectodegrado.sgti.fachada.FachadaTipoHora;
import com.proyectodegrado.sgti.fachada.FachadaUsuario;
import com.proyectodegrado.sgti.modelo.Usuario;

@Controller
@RequestMapping("/desktop/login")
public class LoginController {
	private static final String MENSAJE_ERROR = "Ha ocurrido un error";
	
	private FachadaUsuario fachadaUsuario;
	
	private FachadaHora fachadaHora;
	
	private FachadaTipoHora fachadaTipoHora;
	
	private FachadaActividad fachadaActividad;
	
	private FachadaContrato fachadaContrato;
	
	@RequestMapping(value = "/loguearse", method = RequestMethod.POST)
	public String login(Model model, HttpServletRequest request, @RequestParam("idUsuario") final String idUsuario, @RequestParam("passwordUsuario") final String contrasena){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		try {
			Usuario usuario = fachadaUsuario.seleccionarUsuario(idUsuario);
			String contrasenaHash = fachadaUsuario.get_MD5_SecurePassword(contrasena);
			
			if(usuario.getContrasena() != null && usuario.getContrasena().toLowerCase().equals(contrasenaHash.toLowerCase())){
				request.getSession().setAttribute("usuario", idUsuario);
				request.getSession().setAttribute("tipoUsuario", usuario.getTipo());
			}else{
				model.addAttribute("message", "El usuario o contraseña no es correcto");
				return "/desktop/login2";
			}
		} catch (ClassNotFoundException | IOException | SQLException | NoSuchAlgorithmException e) {
			e.printStackTrace();
			model.addAttribute("message", "Ocurrió un error al tratar de loguear el usuario");
			return "/desktop/login2";
		}finally{
			context.close();
		}
		return cargarPagina(model, request);
	}
	
	public String cargarPagina(Model model, HttpServletRequest request){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaHora = (FachadaHora) context.getBean("fachadaHora");
		fachadaTipoHora = (FachadaTipoHora) context.getBean("fachadaTipoHora");
		fachadaActividad = (FachadaActividad) context.getBean("fachadaActividad");
		fachadaContrato = (FachadaContrato) context.getBean("fachadaContrato");
		String idUsuario = (String) request.getSession().getAttribute("usuario");
		try {
			model.addAttribute("tipoHoras", fachadaTipoHora.verTiposDeHora());
			model.addAttribute("actividades", fachadaActividad.seleccionarActividades());
			model.addAttribute("contratos", fachadaContrato.seleccionarContratosVigentes());
			model.addAttribute("horasRegistradas", fachadaHora.seleccionarHorasPorUsuario(idUsuario));
		} catch (ClassNotFoundException | IOException | SQLException
				| ParseException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return "desktop/tablaHoras";
		}finally{
			context.close();
		}
		return "desktop/tablaHoras";
	}
	
	
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Model model, HttpServletRequest request){
		request.getSession().invalidate();
		return "/desktop/login2";
	}

}
