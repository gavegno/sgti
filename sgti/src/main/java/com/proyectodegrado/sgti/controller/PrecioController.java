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
import com.proyectodegrado.sgti.fachada.FachadaContrato;
import com.proyectodegrado.sgti.fachada.FachadaPrecio;
import com.proyectodegrado.sgti.fachada.FachadaTipoHora;
import com.proyectodegrado.sgti.fachada.FachadaUsuario;

@Controller
@RequestMapping("desktop/precio")
public class PrecioController extends AbstractController{
	
	private static final String MENSAJE_ERROR = "Ha ocurrido un error";
	
	private FachadaPrecio fachadaPrecio;
	private FachadaUsuario fachadaUsuario;
	private FachadaContrato fachadaContrato;
	private FachadaTipoHora fachadaTipoHora;
	
	@RequestMapping(value="/ingresarPrecio", method = RequestMethod.POST)
	public String cargarPrecio(Model model, 
			HttpServletRequest request, 
			@RequestParam("fechaDesde") final String fechaDesde, 
			@RequestParam("fechaHasta") final String fechaHasta, 
			@RequestParam("precio") final double precioAgregar, 
			@RequestParam("precioExtra") final double precioExtraAgregar,
			@RequestParam("idContrato") final String idContrato){
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaPrecio = (FachadaPrecio) context.getBean("fachadaPrecio");
		fachadaTipoHora = (FachadaTipoHora) context.getBean("fachadaTipoHora");
		fachadaContrato = (FachadaContrato) context.getBean("fachadaContrato");
		String mensaje = "El precio fue ingresado correctamente";
		try {
			fachadaPrecio.insertarPrecio(fechaDesde, fechaHasta, precioAgregar, precioExtraAgregar, idContrato);
			model.addAttribute("tiposDeHora", fachadaTipoHora.verTiposDeHoraQueNoTengaEnUsoContrato(idContrato));
			model.addAttribute("message", mensaje);
			model.addAttribute("contrato", fachadaContrato.verContrato(idContrato));
			model.addAttribute("precios", fachadaPrecio.seleccionarPreciosPorContrato(idContrato));
		} catch (ClassNotFoundException | IOException | SQLException | ParseException e) {
			
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return "desktop/precio";
		} catch (SgtiException e) {
			
			mensaje = e.getMessage();
			model.addAttribute("errorMessage", mensaje);
		}finally{
			model.addAttribute("idContrato", idContrato);
			context.close();
		}
		return "desktop/tipoHoraComputo";
	}
	
	@RequestMapping(value="/ingresarPrecioNuevo", method = RequestMethod.POST)
	public String agregarPrecioNuevo(Model model, 
			HttpServletRequest request, 
			@RequestParam("fechaDesde") final String fechaDesde, 
			@RequestParam("fechaHasta") final String fechaHasta, 
			@RequestParam("precio") final double precioAgregar, 
			@RequestParam("precioExtra") final double precioExtraAgregar,
			@RequestParam("idContrato") final String idContrato){
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		fachadaPrecio = (FachadaPrecio) context.getBean("fachadaPrecio");
		String mensaje = "El precio fue ingresado correctamente";
		
		String idUsuario = (String) request.getSession().getAttribute("usuario");
		
		try {
			//Para comprobar por más seguridad, por si ingresan al link directamente.
			if (fachadaUsuario.usuarioEsSocio(idUsuario))
			{
				fachadaPrecio.insertarPrecio(fechaDesde, fechaHasta, precioAgregar, precioExtraAgregar, idContrato);
				model.addAttribute("message", mensaje);
			}
			else
				return "/desktop/login2";
		} catch (ClassNotFoundException | IOException | SQLException | ParseException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return cargarTablaPreciosDesdeContrato(model, request, idContrato);
		} catch (SgtiException e) {
			mensaje = e.getMessage();
			model.addAttribute("errorMessage", mensaje);
			return cargarTablaPreciosDesdeContrato(model, request, idContrato);
		}finally{
			model.addAttribute("idContrato", idContrato);
			context.close();
		}
		return cargarTablaPreciosDesdeContrato(model, request, idContrato);
	}
	
	@RequestMapping(value="/tablaPrecios", method = RequestMethod.POST)
	public String cargarTablaPreciosDesdeContrato(Model model, 
			HttpServletRequest request, 
			@RequestParam("id") final String idContrato){
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		fachadaContrato = (FachadaContrato) context.getBean("fachadaContrato");
		fachadaPrecio = (FachadaPrecio) context.getBean("fachadaPrecio");
		
		String idUsuario = (String) request.getSession().getAttribute("usuario");
		try {
			//Para comprobar por más seguridad, por si ingresan al link directamente.
			if (fachadaUsuario.usuarioEsSocio(idUsuario))
			{
				model.addAttribute("precios", fachadaPrecio.seleccionarPreciosPorContrato(idContrato));
				model.addAttribute("id", idContrato);
				model.addAttribute("contrato", fachadaContrato.verContrato(idContrato));
			}
			else
				return "/desktop/login2";
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return "desktop/tablaDePrecios";
		}finally{
			context.close();
		}
		return "desktop/tablaDePrecios";
	}
	
	@RequestMapping(value="/editarPrecio", method = RequestMethod.POST)
	public String cargarFormEditarPrecio(Model model, 
			HttpServletRequest request, 
			@RequestParam("id") final String idContrato,
			@RequestParam("valorPrecio") final String precio,
			@RequestParam("fechaDesde") final String fechaDesde,
			@RequestParam("fechaHasta") final String fechaHasta
			){
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaPrecio = (FachadaPrecio) context.getBean("fachadaPrecio");
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		fachadaContrato = (FachadaContrato) context.getBean("fachadaContrato");
		
		String idUsuario = (String) request.getSession().getAttribute("usuario");
		
		try {
			//Para comprobar por más seguridad, por si ingresan al link directamente.
			if (fachadaUsuario.usuarioEsSocio(idUsuario))
			{
				model.addAttribute("precio", fachadaPrecio.verPrecioExacto(idContrato, Double.valueOf(precio), fechaDesde, fechaHasta)); 
				model.addAttribute("idContrato", idContrato);
				model.addAttribute("contrato", fachadaContrato.verContrato(idContrato));
			}
			
	
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return "desktop/editarPrecio";
		}finally{
			context.close();
		}
		return "desktop/editarPrecio";
	}
	
	@RequestMapping(value="/editarPrecioOk", method = RequestMethod.POST)
	public String editarPrecioOk(Model model, 
			HttpServletRequest request, 
			@RequestParam("idContrato") final String idContrato,
			@RequestParam("valorPrecio") final String precio,
			@RequestParam("valorPrecioExtra") final String precioExtraAgregar,
			@RequestParam("fechaDesde") final String fechaDesde,
			@RequestParam("fechaHasta") final String fechaHasta,
			@RequestParam("valorPrecioOriginal") final String precioOriginal,
			@RequestParam("valorPrecioExtraOriginal") final String precioExtraOriginal,
			@RequestParam("fechaDesdeOriginal") final String fechaDesdeOriginal,
			@RequestParam("fechaHastaOriginal") final String fechaHastaOriginal
			){
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaPrecio = (FachadaPrecio) context.getBean("fachadaPrecio");
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		fachadaContrato = (FachadaContrato) context.getBean("fachadaContrato");
		
		String idUsuario = (String) request.getSession().getAttribute("usuario");
		String mensaje = "El precio se ha editado correctamente";
		
		try {
			//Para comprobar por más seguridad, por si ingresan al link directamente.
			if (fachadaUsuario.usuarioEsSocio(idUsuario))
			{
				fachadaPrecio.editarPrecio(fechaDesde, fechaHasta, Double.valueOf(precio), Double.valueOf(precioExtraAgregar), fechaDesdeOriginal, fechaHastaOriginal, Double.valueOf(precioOriginal), Double.valueOf(precioExtraOriginal), idContrato);
				model.addAttribute("message", mensaje);
			}else
				
				return "/desktop/login2";
			
		} catch (ClassNotFoundException | IOException | SQLException | ParseException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return cargarTablaPreciosDesdeContrato(model, request, idContrato);
		} catch (SgtiException e) {
			e.printStackTrace();
			mensaje = e.getMessage();
			model.addAttribute("errorMessage", mensaje);
		}finally{
			context.close();
		}
		return cargarTablaPreciosDesdeContrato(model, request, idContrato);
	}
	
	
	@RequestMapping(value="/borrar", method = RequestMethod.POST)
	public String borrarPrecio(Model model, 
			HttpServletRequest request, 
			@RequestParam("idContrato") final String idContrato,
			@RequestParam("valorPrecioOriginal") final String precioOriginal,
			@RequestParam("valorPrecioExtraOriginal") final String precioExtraOriginal,
			@RequestParam("fechaDesdeOriginal") final String fechaDesdeOriginal,
			@RequestParam("fechaHastaOriginal") final String fechaHastaOriginal){
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaPrecio = (FachadaPrecio) context.getBean("fachadaPrecio");
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		fachadaContrato = (FachadaContrato) context.getBean("fachadaContrato");
		String mensaje = "El precio se ha borrado correctamente";
		
		String idUsuario = (String) request.getSession().getAttribute("usuario");
		
		try {
			//Para comprobar por más seguridad, por si ingresan al link directamente.
			if (fachadaUsuario.usuarioEsSocio(idUsuario))
			{
				fachadaPrecio.borrarPrecio(fechaDesdeOriginal, fechaHastaOriginal, Double.valueOf(precioOriginal), Double.valueOf(precioExtraOriginal), idContrato);
				model.addAttribute("message", mensaje);
			}else
				
				return "/desktop/login2";
			
		} catch (ClassNotFoundException | IOException | SQLException | ParseException | NumberFormatException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return cargarTablaPreciosDesdeContrato(model, request, idContrato);
		} finally{
			context.close();
		}
		return cargarTablaPreciosDesdeContrato(model, request, idContrato);
	}
	
	@RequestMapping(value="/agregarPrecio", method = RequestMethod.POST)
	public String cargarPrecioNuevo(Model model, 
			HttpServletRequest request, 
			@RequestParam("idContrato") final String idContrato){
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaContrato = (FachadaContrato) context.getBean("fachadaContrato");
		
		try {
			model.addAttribute("idContrato", idContrato);
			System.out.println("Contrato desde el que parto: "+idContrato);
			model.addAttribute("contrato", fachadaContrato.verContrato(idContrato));
			
		} catch (ClassNotFoundException | IOException | SQLException e) {
			
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			cargarTablaPreciosDesdeContrato(model, request, idContrato);
		} 
		finally{
			context.close();
		}
		
		
		return "desktop/nuevoPrecio";
	}
	
	@RequestMapping(value="/copiarPrecio", method = RequestMethod.POST)
	public String cargarFormCopiarPrecio(Model model, 
			HttpServletRequest request, 
			@RequestParam("id") final String idContrato,
			@RequestParam("valorPrecio") final String precio,
			@RequestParam("fechaDesde") final String fechaDesde,
			@RequestParam("fechaHasta") final String fechaHasta
			){
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaPrecio = (FachadaPrecio) context.getBean("fachadaPrecio");
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		fachadaContrato = (FachadaContrato) context.getBean("fachadaContrato");
		
		String idUsuario = (String) request.getSession().getAttribute("usuario");
		
		try {
			//Para comprobar por más seguridad, por si ingresan al link directamente.
			if (fachadaUsuario.usuarioEsSocio(idUsuario))
			{
				model.addAttribute("precio", fachadaPrecio.verPrecioExacto(idContrato, Double.valueOf(precio), fechaDesde, fechaHasta)); 
				model.addAttribute("idContrato", idContrato);
				model.addAttribute("contrato", fachadaContrato.verContrato(idContrato));
			}
			
	
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return "desktop/copiarPrecio";
		}finally{
			context.close();
		}
		return "desktop/copiarPrecio";
	}
	
	@RequestMapping(value="/copiarPrecioOk", method = RequestMethod.POST)
	public String agregarPrecioNuevoCopiado(Model model, 
			HttpServletRequest request, 
			@RequestParam("idContrato") final String idContrato,
			@RequestParam("valorPrecio") final String precio,
			@RequestParam("valorPrecioExtra") final String precioExtra,
			@RequestParam("fechaDesde") final String fechaDesde,
			@RequestParam("fechaHasta") final String fechaHasta){
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		fachadaPrecio = (FachadaPrecio) context.getBean("fachadaPrecio");
		String mensaje = "El precio fue ingresado correctamente";
		
		String idUsuario = (String) request.getSession().getAttribute("usuario");
		
		try {
			//Para comprobar por más seguridad, por si ingresan al link directamente.
			if (fachadaUsuario.usuarioEsSocio(idUsuario))
			{
				fachadaPrecio.insertarPrecio(fechaDesde, fechaHasta, Double.valueOf(precio), Double.valueOf(precioExtra), idContrato);
				model.addAttribute("message", mensaje);
			}
			else
				return "/desktop/login2";
		} catch (ClassNotFoundException | IOException | SQLException | ParseException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return cargarTablaPreciosDesdeContrato(model, request, idContrato);
		} catch (SgtiException e) {
			mensaje = e.getMessage();
			model.addAttribute("errorMessage", mensaje);
			return cargarTablaPreciosDesdeContrato(model, request, idContrato);
		}finally{
			model.addAttribute("idContrato", idContrato);
			context.close();
		}
		return cargarTablaPreciosDesdeContrato(model, request, idContrato);
	}

}
