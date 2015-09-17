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
import com.proyectodegrado.sgti.fachada.FachadaContrato;
import com.proyectodegrado.sgti.fachada.FachadaPrecio;
import com.proyectodegrado.sgti.fachada.FachadaTipoHora;
import com.proyectodegrado.sgti.fachada.FachadaUsuario;

@Controller
@RequestMapping("/desktop/tiposDeHora")
public class TipoHoraController {
	private static final String MENSAJE_ERROR = "Ha ocurrido un error";
	
	private FachadaPrecio fachadaPrecio;
	private FachadaTipoHora fachadaTipoHora;
	private FachadaContrato fachadaContrato;
	private FachadaUsuario fachadaUsuario;
	
	@RequestMapping(value = "/ingresar", method = RequestMethod.POST)
	public String ingresarTipoHora(Model model,
			@RequestParam("tipoHora") final String tipoHora){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaTipoHora = (FachadaTipoHora) context.getBean("fachadaTipoHora");
		String mensaje = "El tipo de hora fue ingresado correctamente";
		try {
			fachadaTipoHora.insertarTipoHora(tipoHora);
			model.addAttribute("message", mensaje);
			
		}catch (IOException | SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			mensaje = "Ha ocurrido un error";
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return "desktop/tiposDeHora";
		} catch (SgtiException e) {
			e.printStackTrace();
			mensaje = e.getMessage();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return "desktop/tiposDeHora";
		}finally{
			context.close();
		}
		return "desktop/tiposDeHora";
	}
	
	@RequestMapping(value = "/ingresarComputo", method = RequestMethod.POST)
	public String ingresarComputoTipoDeHora(Model model, 
			@RequestParam("tipoHora") final String nombreTipoHora, 
			@RequestParam("computos") final double computo, 
			@RequestParam("idContrato") final String idContrato){
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaTipoHora = (FachadaTipoHora) context.getBean("fachadaTipoHora");
		fachadaContrato = (FachadaContrato) context.getBean("fachadaContrato");
		fachadaPrecio = (FachadaPrecio) context.getBean("fachadaPrecio");
		
		String mensaje = "Se ha asignado correctamente el cómputo al tipo de hora";
		try {
			fachadaTipoHora.insertarContratoTipoHora(idContrato, nombreTipoHora, computo);
			model.addAttribute("tiposDeHora", fachadaTipoHora.verTiposDeHoraQueNoTengaEnUsoContrato(idContrato));
			model.addAttribute("tiposDeHoraAsignados", fachadaTipoHora.verTiposDeHoraPorContrato(idContrato));
			model.addAttribute("contrato", fachadaContrato.verContrato(idContrato));
			model.addAttribute("precios", fachadaPrecio.seleccionarPreciosPorContrato(idContrato));
			model.addAttribute("message", mensaje);
			
			
			
		}catch (IOException | SQLException | ClassNotFoundException e) {
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return "desktop/tipoHoraComputo";
		} catch (SgtiException e) {
			mensaje = e.getMessage();
			model.addAttribute("errorMessage", mensaje);
		}finally{
			model.addAttribute("idContrato", idContrato);
			try {
				model.addAttribute("tiposDeHoraAsignados", fachadaTipoHora.verTiposDeHoraPorContrato(idContrato));
				model.addAttribute("contrato", fachadaContrato.verContrato(idContrato));
				model.addAttribute("precios", fachadaPrecio.seleccionarPreciosPorContrato(idContrato));
			} catch (ClassNotFoundException | IOException | SQLException e) {

			}
			
			context.close();
		}
		return "desktop/tipoHoraComputo";
	}
	
	@RequestMapping(value = "/tabla", method = RequestMethod.GET)
	public String listarTiposDeHora(Model model, HttpServletRequest request){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaTipoHora = (FachadaTipoHora) context.getBean("fachadaTipoHora");
		try {
			model.addAttribute("tipos", fachadaTipoHora.verTiposDeHora());

		}catch (IOException | SQLException | ClassNotFoundException e) {
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return "desktop/tablaTipoHora";
		} finally{
			context.close();
		}
		return "desktop/tablaTipoHora";
	}
	
	@RequestMapping(value = "/editar", method = RequestMethod.POST)
	public String editarTipoHora(Model model, HttpServletRequest request, 
			@RequestParam("tipo") final String tipo, @RequestParam("id") final int id){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaTipoHora = (FachadaTipoHora) context.getBean("fachadaTipoHora");
		String mensaje = "El tipo de hora se ha editado correctamente";
		try {
			fachadaTipoHora.editarTipoHora(id, tipo);
			model.addAttribute("message", mensaje);
		}catch (IOException | SQLException | ClassNotFoundException e) {
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return listarTiposDeHora(model, request);
		}finally{
			context.close();
		}
		return listarTiposDeHora(model, request);
	}
	
	@RequestMapping(value = "/borrar", method = RequestMethod.POST)
	public String borrarTipoHora(Model model, HttpServletRequest request, 
			@RequestParam("id") final int id){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaTipoHora = (FachadaTipoHora) context.getBean("fachadaTipoHora");
		String mensaje = "El tipo de hora se ha eliminado correctamente";
		try {
			fachadaTipoHora.borrarTipoHora(id);
			model.addAttribute("message", mensaje);
		}catch (IOException | SQLException | ClassNotFoundException e) {
			String mensajeError = e.getMessage();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			
			if (mensajeError.contains("foreign"))
				model.addAttribute("errorMessage", MENSAJE_ERROR+ ": el tipo de hora está en uso");
			
			return listarTiposDeHora(model, request);
		}finally{
			context.close();
		}
		return listarTiposDeHora(model, request);
	}
	
	@RequestMapping(value="/tablaHorasContrato", method = RequestMethod.POST)
	public String cargarTablaTiposHoraDesdeContrato(Model model, 
			HttpServletRequest request, 
			@RequestParam("id") final String idContrato){
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		fachadaContrato = (FachadaContrato) context.getBean("fachadaContrato");
		fachadaTipoHora = (FachadaTipoHora) context.getBean("fachadaTipoHora");
		
		String idUsuario = (String) request.getSession().getAttribute("usuario");
		
		try {
			//Para comprobar por más seguridad, por si ingresan al link directamente.
			if (fachadaUsuario.usuarioEsSocio(idUsuario))
			{
				model.addAttribute("tiposHoraContrato", fachadaTipoHora.verTiposDeHoraPorContrato(idContrato));
				model.addAttribute("id", idContrato);
				model.addAttribute("contrato", fachadaContrato.verContrato(idContrato));
			}
			else
				return "/desktop/login2";
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return "desktop/tablaContratosTiposHora";
		}finally{
			context.close();
		}
		return "desktop/tablaContratosTiposHora";
	}
	
	@RequestMapping(value="/editarContratoTipoHora", method = RequestMethod.POST)
	public String editarContratoTipoHora(Model model, 
			HttpServletRequest request, 
			@RequestParam("tipoId") final int idTipoHora,
			@RequestParam("id") final String idContrato,
			@RequestParam("factorComputo") final double computos){
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		fachadaContrato = (FachadaContrato) context.getBean("fachadaContrato");
		fachadaTipoHora = (FachadaTipoHora) context.getBean("fachadaTipoHora");
		
		String mensaje = "El tipo de hora del contrato se ha editado correctamente";
		
		String idUsuario = (String) request.getSession().getAttribute("usuario");
		
		try {
			//Para comprobar por más seguridad, por si ingresan al link directamente.
			if (fachadaUsuario.usuarioEsSocio(idUsuario))
			{
				fachadaTipoHora.editarComputosDeTipoHora(idContrato, idTipoHora, computos);
				model.addAttribute("message", mensaje);
			}else
				
				return "/desktop/login2";
			
	
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return cargarTablaTiposHoraDesdeContrato(model, request, idContrato);
		}finally{
			context.close();
		}
		return cargarTablaTiposHoraDesdeContrato(model, request, idContrato);
	}
	
	
	@RequestMapping(value="/agregarContratoTipoHora", method = RequestMethod.POST)
	public String cargarFormNueva(Model model, 
			HttpServletRequest request, 
			@RequestParam("id") final String idContrato){
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		fachadaContrato = (FachadaContrato) context.getBean("fachadaContrato");
		fachadaTipoHora = (FachadaTipoHora) context.getBean("fachadaTipoHora");
		
		String idUsuario = (String) request.getSession().getAttribute("usuario");
		
		try {
			//Para comprobar por más seguridad, por si ingresan al link directamente.
			if (fachadaUsuario.usuarioEsSocio(idUsuario))
			{
				model.addAttribute("idContrato", idContrato);
				model.addAttribute("contrato", fachadaContrato.verContrato(idContrato));
				model.addAttribute("tiposDeHora", fachadaTipoHora.verTiposDeHoraQueNoTengaEnUsoContrato(idContrato));
			}
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return "desktop/nuevoTipoHoraComputo";
		} catch (SgtiException e) {
			e.printStackTrace();
			String mensaje = e.getMessage();
			model.addAttribute("errorMessage", mensaje);
			return cargarTablaTiposHoraDesdeContrato(model, request, idContrato);
		}finally{
			context.close();
		}
		return "desktop/nuevoTipoHoraComputo";
	}
	
	@RequestMapping(value="/agregarContratoTipoHoraOk", method = RequestMethod.POST)
	public String guardarNuevoTipoHoraContrato(Model model, 
			HttpServletRequest request, 
			@RequestParam("id") final String idContrato,
			@RequestParam("tipoHora") final String nombreTipoHora, 
			@RequestParam("computos") final double computo){
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		fachadaContrato = (FachadaContrato) context.getBean("fachadaContrato");
		fachadaTipoHora = (FachadaTipoHora) context.getBean("fachadaTipoHora");
		
		String idUsuario = (String) request.getSession().getAttribute("usuario");
		String mensaje = "El tipo de hora del contrato se ha agregado correctamente";
		
		try {
			//Para comprobar por más seguridad, por si ingresan al link directamente.
			if (fachadaUsuario.usuarioEsSocio(idUsuario))
			{
				fachadaTipoHora.insertarContratoTipoHora(idContrato, nombreTipoHora, computo);
				model.addAttribute("idContrato", idContrato);
				model.addAttribute("contrato", fachadaContrato.verContrato(idContrato));
				model.addAttribute("message", mensaje);
			}
			
	
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return cargarTablaTiposHoraDesdeContrato(model, request, idContrato);
		}finally{
			context.close();
		}
		return cargarTablaTiposHoraDesdeContrato(model, request, idContrato);
	}
	
	
	
	
	
	

	public FachadaTipoHora getFachadaTipoHora() {
		return fachadaTipoHora;
	}

	public void setFachadaTipoHora(FachadaTipoHora fachadaTipoHora) {
		this.fachadaTipoHora = fachadaTipoHora;
	}
}