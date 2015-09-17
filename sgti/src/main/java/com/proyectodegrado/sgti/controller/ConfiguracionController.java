package com.proyectodegrado.sgti.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyectodegrado.sgti.exceptions.SgtiException;
import com.proyectodegrado.sgti.fachada.FachadaConfiguracion;
import com.proyectodegrado.sgti.fachada.FachadaContrato;
import com.proyectodegrado.sgti.fachada.FachadaHorarioLaboral;
import com.proyectodegrado.sgti.fachada.FachadaPrecio;
import com.proyectodegrado.sgti.fachada.FachadaTipoHora;
import com.proyectodegrado.sgti.fachada.FachadaUsuario;

@Controller
@RequestMapping("desktop/configuracion")
public class ConfiguracionController {
	
	private static final String MENSAJE_ERROR = "Ha ocurrido un error";

	private FachadaConfiguracion fachadaConfiguracion;
	private FachadaHorarioLaboral fachadaHorarioLaboral;
	private FachadaUsuario fachadaUsuario;
	private FachadaContrato fachadaContrato;
	private FachadaPrecio fachadaPrecio;
	private FachadaTipoHora fachadaTipoHora;
	
	@RequestMapping(value="/ingresarConfiguracion", method = RequestMethod.POST)
	public String ingresarConfiguracion(Model model, 
			@RequestParam("fechaDesde") final String fechaDesde, 
			@RequestParam("fechaHasta") final String fechaHasta, 
			@RequestParam(required=false, value="periodoRenovacion") final Integer periodoRenovacion, 
			@RequestParam("tipoRenovacion") final String tipoRenovacion, 
			@RequestParam("tipoContrato") final String tipoContrato, 
			@RequestParam(required=false, value="computos") final Integer computos, 
			@RequestParam(required=false, value="unidadValidez") final String unidadValidez, 
			@RequestParam(required=false, value="periodoValidez") final Integer periodoValidez, 
			@RequestParam(required=false, value="acumulacion") final String acumulacion, 
			@RequestParam(required=false, value="periodoAcumulacion") final Integer periodoAcumulacion, 
			@RequestParam("frecuenciaInforme") final Integer frecuenciaInforme, 
			@RequestParam(required=false, value="frecuenciaFacturacion") final Integer frecuenciaFacturacion, 
			@RequestParam(required=false, value="respuesta") final String tiempoRespuesta, 
			@RequestParam("horarioLaboral") final String horarioLaboral, 
			@RequestParam("idContrato") final String idContrato){
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaConfiguracion = (FachadaConfiguracion) context.getBean("fachadaConfiguracion");
		String mensaje = "La configuración se ha creado correctamente.";
		try {
			fachadaConfiguracion.insertarConfiguracion(fechaDesde, fechaHasta, periodoRenovacion, tipoRenovacion, tipoContrato, computos, unidadValidez, periodoValidez, convertirBoolean(acumulacion), periodoAcumulacion, frecuenciaInforme, frecuenciaFacturacion, 0, tiempoRespuesta, horarioLaboral, idContrato);
			model.addAttribute("message", mensaje);
		} catch (ClassNotFoundException | IOException | SQLException| ParseException e) {
			e.printStackTrace();
			model.addAttribute("idContrato", idContrato);
			mensaje = MENSAJE_ERROR;
			model.addAttribute("errorMessage", mensaje);
			return cargarPagina(model, idContrato);
		} catch (SgtiException e) {
			e.printStackTrace();
			mensaje = e.getMessage();
			model.addAttribute("errorMessage", mensaje);
			return cargarPagina(model, idContrato);
		}finally{
			context.close();
		}
		return "desktop/paginaPrincipal";
	}
	
	@RequestMapping(value="/ingresar", method = RequestMethod.POST)
	public String cargarPagina(Model model, @RequestParam("idContrato") final String idContrato){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaHorarioLaboral = (FachadaHorarioLaboral) context.getBean("fachadaHorarioLaboral");
		fachadaTipoHora = (FachadaTipoHora) context.getBean("fachadaTipoHora");
		fachadaContrato = (FachadaContrato) context.getBean("fachadaContrato");
		fachadaPrecio = (FachadaPrecio) context.getBean("fachadaPrecio");
		try {
			model.addAttribute("horariosLaborales", fachadaHorarioLaboral.verHorariosLaborales());
			model.addAttribute("tiposDeHoraAsignados", fachadaTipoHora.verTiposDeHoraPorContrato(idContrato));
			model.addAttribute("contrato", fachadaContrato.verContrato(idContrato));
			model.addAttribute("precios", fachadaPrecio.seleccionarPreciosPorContrato(idContrato));
			
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return "desktop/configuracion";
		}finally{
			model.addAttribute("idContrato", idContrato);
			context.close();
		}
		return "desktop/configuracion";
	}
	
	@RequestMapping(value="/tablaConfiguraciones", method = RequestMethod.POST)
	public String cargarTablaConfigDesdeContrato(Model model, 
			HttpServletRequest request, 
			@RequestParam("id") final String idContrato){
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaConfiguracion = (FachadaConfiguracion) context.getBean("fachadaConfiguracion");
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		fachadaContrato = (FachadaContrato) context.getBean("fachadaContrato");
		
		String idUsuario = (String) request.getSession().getAttribute("usuario");
		
		try {
			//Para comprobar por más seguridad, por si ingresan al link directamente.
			if (fachadaUsuario.usuarioEsSocio(idUsuario))
			{
				model.addAttribute("configs", fachadaConfiguracion.seleccionarConfiguracionPorContrato(idContrato));
				model.addAttribute("idContrato", idContrato);
				model.addAttribute("contrato", fachadaContrato.verContrato(idContrato));
			}
			else
				return "/desktop/login2";
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return "desktop/tablaConfigs";
		}finally{
			context.close();
		}
		return "desktop/tablaConfigs";
	}
	
	@RequestMapping(value="/editarConfig", method = RequestMethod.POST)
	public String cargarFormEditar(Model model, 
			HttpServletRequest request, 
			@RequestParam("idConfiguracion") final int idConfig,
			@RequestParam("idContrato") final String idContrato){
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaConfiguracion = (FachadaConfiguracion) context.getBean("fachadaConfiguracion");
		fachadaHorarioLaboral = (FachadaHorarioLaboral) context.getBean("fachadaHorarioLaboral");
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		fachadaContrato = (FachadaContrato) context.getBean("fachadaContrato");
		
		String idUsuario = (String) request.getSession().getAttribute("usuario");
		
		try {
			//Para comprobar por más seguridad, por si ingresan al link directamente.
			if (fachadaUsuario.usuarioEsSocio(idUsuario))
			{
				model.addAttribute("config", fachadaConfiguracion.seleccionarConfiguracionPorId(idConfig));
				model.addAttribute("horariosLaborales", fachadaHorarioLaboral.verHorariosLaborales());
				model.addAttribute("idHorarioLaboralActual", fachadaConfiguracion.verHorarioLaboralDeConfiguracion(idConfig));
				model.addAttribute("idContrato", idContrato);
				model.addAttribute("contrato", fachadaContrato.verContrato(idContrato));
			}
			
	
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return "desktop/editarConfiguracion";
		}finally{
			context.close();
		}
		return "desktop/editarConfiguracion";
	}
	
	@RequestMapping(value="/editarConfigOk", method = RequestMethod.POST)
	public String editarConfigOk(Model model, 
			HttpServletRequest request, @RequestParam("idConfiguracion") final int idConfiguracion,
			@RequestParam("fechaInicio") final String fechaDesde, @RequestParam("fechaFin") final String fechaHasta, 
			@RequestParam("periodoRenovacion") final Integer periodoRenovacion, @RequestParam("renovacion") final String tipoRenovacion, 
			@RequestParam("tipoContrato") final String tipoContrato, @RequestParam("computosPaquete") final Integer computos, 
			@RequestParam("unidadValidez") final String unidadValidez, @RequestParam("periodoValidez") final Integer periodoValidez, 
			@RequestParam("acumulacion") final String acumulacion, @RequestParam(required=false, value="periodoAcumulacion") final Integer periodoAcumulacion, 
			@RequestParam("frecuenciaInforme") final Integer frecuenciaInforme, @RequestParam("frecuenciaFacturacion") final Integer frecuenciaFacturacion, @RequestParam("tiempoRespuesta") final String tiempoRespuesta, 
			@RequestParam("horarioLaboral") final String horarioLaboral, @RequestParam("idContrato") final String idContrato){
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaConfiguracion = (FachadaConfiguracion) context.getBean("fachadaConfiguracion");
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		String mensaje = "La configuración se ha editado correctamente";
		
		String idUsuario = (String) request.getSession().getAttribute("usuario");
		
		try {
			//Para comprobar por más seguridad, por si ingresan al link directamente.
			if (fachadaUsuario.usuarioEsSocio(idUsuario))
			{
				fachadaConfiguracion.editarConfiguracion(idConfiguracion, fechaDesde, fechaHasta, periodoRenovacion, tipoRenovacion, tipoContrato, computos, unidadValidez, periodoValidez, convertirBoolean(acumulacion), periodoAcumulacion, frecuenciaInforme, frecuenciaFacturacion, 0, tiempoRespuesta, horarioLaboral, idContrato);
				model.addAttribute("message", mensaje);
			}else
				
				return "/desktop/login2";
			
	
		} catch (ClassNotFoundException | IOException | SQLException | ParseException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return cargarTablaConfigDesdeContrato(model, request, idContrato);
		} catch (SgtiException e) {
			e.printStackTrace();
			mensaje = e.getMessage();
			model.addAttribute("errorMessage", mensaje);
		}finally{
			context.close();
		}
		return cargarTablaConfigDesdeContrato(model, request, idContrato);
	}
	
	@RequestMapping(value="/copiarConfig", method = RequestMethod.POST)
	public String cargarFormCopiar(Model model, 
			HttpServletRequest request, 
			@RequestParam("idConfiguracion") final int idConfig,
			@RequestParam("idContrato") final String idContrato){
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaConfiguracion = (FachadaConfiguracion) context.getBean("fachadaConfiguracion");
		fachadaHorarioLaboral = (FachadaHorarioLaboral) context.getBean("fachadaHorarioLaboral");
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		fachadaContrato = (FachadaContrato) context.getBean("fachadaContrato");
		
		String idUsuario = (String) request.getSession().getAttribute("usuario");
		
		try {
			//Para comprobar por más seguridad, por si ingresan al link directamente.
			if (fachadaUsuario.usuarioEsSocio(idUsuario))
			{
				model.addAttribute("config", fachadaConfiguracion.seleccionarConfiguracionPorId(idConfig));
				model.addAttribute("horariosLaborales", fachadaHorarioLaboral.verHorariosLaborales());
				model.addAttribute("idHorarioLaboralActual", fachadaConfiguracion.verHorarioLaboralDeConfiguracion(idConfig));
				model.addAttribute("idContrato", idContrato);
				model.addAttribute("contrato", fachadaContrato.verContrato(idContrato));
			}
			
	
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return "desktop/copiarConfiguracion";
		}finally{
			context.close();
		}
		return "desktop/copiarConfiguracion";
	}
	
	@RequestMapping(value="/copiarConfigOk", method = RequestMethod.POST)
	public String copiarConfigOk(Model model, HttpServletRequest request, @RequestParam("fechaDesde") final String fechaDesde, @RequestParam("fechaHasta") final String fechaHasta, 
			@RequestParam("periodoRenovacion") final Integer periodoRenovacion, @RequestParam("tipoRenovacion") final String tipoRenovacion, 
			@RequestParam("tipoContrato") final String tipoContrato, @RequestParam("computos") final Integer computos, 
			@RequestParam("unidadValidez") final String unidadValidez, @RequestParam("periodoValidez") final Integer periodoValidez, 
			@RequestParam("acumulacion") final String acumulacion, @RequestParam(required=false, value="periodoAcumulacion") final Integer periodoAcumulacion, 
			@RequestParam("frecuenciaInforme") final Integer frecuenciaInforme, @RequestParam("frecuenciaFacturacion") final Integer frecuenciaFacturacion, 
			@RequestParam("respuesta") final String tiempoRespuesta, 
			@RequestParam("horarioLaboral") final String horarioLaboral, @RequestParam("idContrato") final String idContrato){
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaConfiguracion = (FachadaConfiguracion) context.getBean("fachadaConfiguracion");
		String mensaje = "La configuración se ha creado correctamente";
		try {
			fachadaConfiguracion.insertarConfiguracion(fechaDesde, fechaHasta, periodoRenovacion, tipoRenovacion, tipoContrato, computos, unidadValidez, periodoValidez, convertirBoolean(acumulacion), periodoAcumulacion, frecuenciaInforme, frecuenciaFacturacion, 0, tiempoRespuesta, horarioLaboral, idContrato);
			model.addAttribute("message", mensaje);
		} catch (ClassNotFoundException | IOException | SQLException| ParseException e) {
			model.addAttribute("idContrato", idContrato);
			mensaje = MENSAJE_ERROR;
			model.addAttribute("errorMessage", mensaje);
		}catch (SgtiException e) {
			e.printStackTrace();
			mensaje = e.getMessage();
			model.addAttribute("errorMessage", mensaje);
		} finally{
			context.close();
			
		}
		return  cargarTablaConfigDesdeContrato(model, request, idContrato);
	}
	
	
	@RequestMapping(value="/borrar", method = RequestMethod.POST)
	public String borrarConfiguracion(Model model, 
			HttpServletRequest request, 
			@RequestParam("idConfiguracion") final int idConfig,
			@RequestParam("idContrato") final String idContrato){
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaConfiguracion = (FachadaConfiguracion) context.getBean("fachadaConfiguracion");
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		String mensaje = "La configuración se ha borrado correctamente";
		
		String idUsuario = (String) request.getSession().getAttribute("usuario");
		
		try {
			//Para comprobar por más seguridad, por si ingresan al link directamente.
			if (fachadaUsuario.usuarioEsSocio(idUsuario))
			{
				//System.out.println("Se pide al controller borrar la " + idConfig);
				fachadaConfiguracion.borrarConfiguracion(idConfig);
				model.addAttribute("message", mensaje);
				model.addAttribute("idConfiguracion", idConfig);
			}else
				
				return "/desktop/login2";
			
	
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return cargarTablaConfigDesdeContrato(model, request, idContrato);
		}finally{
			context.close();
		}
		return  cargarTablaConfigDesdeContrato(model, request, idContrato);
	}
	
	@RequestMapping(value="/agregarConfig", method = RequestMethod.POST)
	public String cargarFormNueva(Model model, 
			HttpServletRequest request, 
			@RequestParam("idContrato") final String idContrato){
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		fachadaContrato = (FachadaContrato) context.getBean("fachadaContrato");
		fachadaHorarioLaboral = (FachadaHorarioLaboral) context.getBean("fachadaHorarioLaboral");
		
		String idUsuario = (String) request.getSession().getAttribute("usuario");
		
		try {
			//Para comprobar por más seguridad, por si ingresan al link directamente.
			if (fachadaUsuario.usuarioEsSocio(idUsuario))
			{
				model.addAttribute("idContrato", idContrato);
				model.addAttribute("contrato", fachadaContrato.verContrato(idContrato));
				model.addAttribute("horariosLaborales", fachadaHorarioLaboral.verHorariosLaborales());
			}
			
	
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return "desktop/nuevaConfiguracion";
		}finally{
			context.close();
		}
		return "desktop/nuevaConfiguracion";
	}
	
	@RequestMapping(value="/agregarConfigOk", method = RequestMethod.POST)
	public String agregarConfigOk(Model model, HttpServletRequest request,
			@RequestParam("fechaDesde") final String fechaDesde, @RequestParam("fechaHasta") final String fechaHasta, 
			@RequestParam("periodoRenovacion") final Integer periodoRenovacion, @RequestParam("tipoRenovacion") final String tipoRenovacion, 
			@RequestParam("tipoContrato") final String tipoContrato, @RequestParam("computos") final Integer computos, 
			@RequestParam("unidadValidez") final String unidadValidez, @RequestParam("periodoValidez") final Integer periodoValidez, 
			@RequestParam("acumulacion") final String acumulacion, @RequestParam(required=false, value="periodoAcumulacion") final Integer periodoAcumulacion, 
			@RequestParam("frecuenciaInforme") final Integer frecuenciaInforme, @RequestParam("frecuenciaFacturacion") final Integer frecuenciaFacturacion, 
			@RequestParam("respuesta") final String tiempoRespuesta, 
			@RequestParam("horarioLaboral") final String horarioLaboral, @RequestParam("idContrato") final String idContrato){
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaConfiguracion = (FachadaConfiguracion) context.getBean("fachadaConfiguracion");
		String mensaje = "La configuración se ha creado correctamente";
		try {
			fachadaConfiguracion.insertarConfiguracion(fechaDesde, fechaHasta, periodoRenovacion, tipoRenovacion, tipoContrato, computos, unidadValidez, periodoValidez, convertirBoolean(acumulacion), periodoAcumulacion, frecuenciaInforme, frecuenciaFacturacion, 0, tiempoRespuesta, horarioLaboral, idContrato);
			model.addAttribute("message", mensaje);
		} catch (ClassNotFoundException | IOException | SQLException| ParseException e) {
			e.printStackTrace();
			model.addAttribute("idContrato", idContrato);
			mensaje = MENSAJE_ERROR;
			model.addAttribute("errorMessage", mensaje);
			return "desktop/tablaConfigs";
		} catch (SgtiException e) {
			e.printStackTrace();
			mensaje = e.getMessage();
			model.addAttribute("errorMessage", mensaje);
			return cargarTablaConfigDesdeContrato(model, request, idContrato);
		}finally{
			context.close();
		}
		return cargarTablaConfigDesdeContrato(model, request, idContrato);
	}
	
	
	private boolean convertirBoolean(String booleanString){
		if("true".equalsIgnoreCase(booleanString)){
			return true;
		}else{
			return false;
		}
	}
	
	
}
