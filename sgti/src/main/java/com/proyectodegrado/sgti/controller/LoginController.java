package com.proyectodegrado.sgti.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyectodegrado.sgti.fachada.FachadaActividad;
import com.proyectodegrado.sgti.fachada.FachadaContrato;
import com.proyectodegrado.sgti.fachada.FachadaContratoTecnicos;
import com.proyectodegrado.sgti.fachada.FachadaHora;
import com.proyectodegrado.sgti.fachada.FachadaTipoHora;
import com.proyectodegrado.sgti.fachada.FachadaNotificacion;
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
	private FachadaContratoTecnicos fachadaContratoTecnicos;
	
	private FachadaNotificacion fachadaNotificacion;
	
	@RequestMapping(value = "/loguearse", method = RequestMethod.POST)
	public String login(Model model, HttpServletRequest request, @RequestParam("idUsuario") final String idUsuario, @RequestParam("passwordUsuario") final String contrasena){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		boolean esContraparte = false;
		
		try {
			Usuario usuario = fachadaUsuario.seleccionarUsuario(idUsuario);
			String contrasenaHash = fachadaUsuario.get_MD5_SecurePassword(contrasena);
			
			if(usuario.getContrasena() != null && usuario.getContrasena().toLowerCase().equals(contrasenaHash.toLowerCase())){
				request.getSession().setAttribute("usuario", idUsuario);
				request.getSession().setAttribute("tipoUsuario", usuario.getTipo());
//				notificar(model, request, context);
				
				if (usuario.getTipo().equalsIgnoreCase("contraparte"))
					esContraparte = true;
			}else{
				model.addAttribute("errorMessage", "El usuario o contraseña no es correcto");
				return "/desktop/login2";
			}
		} catch (ClassNotFoundException | IOException | SQLException | /*ParseException |*/ NoSuchAlgorithmException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "Ocurrió un error al tratar de loguear el usuario");
			return "/desktop/login2";
		}finally{
			context.close();
		}
		
		if (!esContraparte)
			return cargarPagina(model, request);
		else
			return cargarTablaContratosPorContraparte(model, request);
	}
	
	public String cargarPagina(Model model, HttpServletRequest request){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaHora = (FachadaHora) context.getBean("fachadaHora");
		fachadaTipoHora = (FachadaTipoHora) context.getBean("fachadaTipoHora");
		fachadaActividad = (FachadaActividad) context.getBean("fachadaActividad");
		fachadaContrato = (FachadaContrato) context.getBean("fachadaContrato");
		fachadaContratoTecnicos = (FachadaContratoTecnicos) context.getBean("fachadaContratoTecnicos");
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		String idUsuario = (String) request.getSession().getAttribute("usuario");
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_YEAR, -60);
		
		Date fechaDesdeFiltro = cal.getTime();
		
		boolean esTecnico = false;
		
		
		try {
			if (!fachadaUsuario.usuarioEsSocio(idUsuario)){
				esTecnico = true;
			}
			
			model.addAttribute("tipoHoras", fachadaTipoHora.verTiposDeHora());
			
			if (esTecnico){
				model.addAttribute("contratos", fachadaContratoTecnicos.listarContratosPorTecnicoTodos(idUsuario));
				model.addAttribute("horasRegistradas", fachadaHora.seleccionarHorasPorUsuario(idUsuario));
				model.addAttribute("actividades", fachadaActividad.seleccionarActividadesPorUsuario(idUsuario));
				model.addAttribute("filtroUsuario", idUsuario);
				model.addAttribute("todosTiposHora", fachadaTipoHora.verContratoTiposHoraParaGestionarHorasPorTecnico(idUsuario));
			}
			else{
				model.addAttribute("contratos", fachadaContrato.seleccionarContratosVigentes());
				model.addAttribute("horasRegistradas", fachadaHora.seleccionarHorasConFechaDesde(fechaDesdeFiltro));
				model.addAttribute("actividades", fachadaActividad.seleccionarActividades());
				model.addAttribute("todosTiposHora", fachadaTipoHora.verContratoTiposHoraParaGestionarHoras());
				
			}
			
			
			model.addAttribute("fechaDesdeFiltro", fechaDesdeFiltro);
			model.addAttribute("filtroValidada", "Todas");
			
			
		} catch (ClassNotFoundException | IOException | SQLException
				| ParseException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			
			//if (!esTecnico)
				return "desktop/tablaHoras";
			//else
				//return "desktop/tablaHorasTecnicos";
		}finally{
			context.close();
		}
		
		//if (!esTecnico)
			return "desktop/tablaHoras";
		//else
			//return "desktop/tablaHorasTecnicos";
	}
	
	public String cargarTablaContratosPorContraparte(Model model, HttpServletRequest request)
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
	
	private void notificar(Model model, HttpServletRequest request, ClassPathXmlApplicationContext context) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException {
		fachadaNotificacion = (FachadaNotificacion) context.getBean("fachadaNotificacion");
		int dias = 7;
		String tipoUsuario = (String) request.getSession().getAttribute("tipoUsuario");
		String idUsuario = (String) request.getSession().getAttribute("usuario");
		model.addAttribute("notificaciones", fachadaNotificacion.notificar(tipoUsuario, idUsuario, dias).size());
		model.addAttribute("despliegeNotificaciones", fachadaNotificacion.notificar(tipoUsuario, idUsuario, dias));
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Model model, HttpServletRequest request){
		request.getSession().invalidate();
		return "/desktop/login2";
	}

}
