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
import com.proyectodegrado.sgti.fachada.FachadaCliente;
import com.proyectodegrado.sgti.fachada.FachadaConfiguracion;
import com.proyectodegrado.sgti.fachada.FachadaContrato;
import com.proyectodegrado.sgti.fachada.FachadaContratoTecnicos;
import com.proyectodegrado.sgti.fachada.FachadaPrecio;
import com.proyectodegrado.sgti.fachada.FachadaNotificacion;
import com.proyectodegrado.sgti.fachada.FachadaTipoHora;
import com.proyectodegrado.sgti.fachada.FachadaUsuario;

@Controller
@RequestMapping("/desktop/contrato")
public class ContratoController extends AbstractController{

	private static final String MENSAJE_ERROR = "Ha ocurrido un error";

	private FachadaContrato fachadaContrato;
	private FachadaCliente fachadaCliente;
	private FachadaUsuario fachadaUsuario;
	private FachadaConfiguracion fachadaConfiguracion;
	private FachadaPrecio fachadaPrecio;
	private FachadaNotificacion fachadaNotificacion;
	private FachadaContratoTecnicos fachadaContratoTecnicos;
	private FachadaTipoHora fachadaTipoHora;
	
	@RequestMapping(value="/ingresar", method = RequestMethod.GET)
	public String cargarPagina(Model model){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		fachadaCliente = (FachadaCliente) context.getBean("fachadaCliente");
		try {
			model.addAttribute("contrapartes", fachadaUsuario.verUsuariosContraparte());
			model.addAttribute("clientes", fachadaCliente.verClientes());
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return "desktop/contrato";
		} finally {
			context.close();
		}
		return "desktop/contrato";
	}

	@RequestMapping(value = "/ingresarContrato", method = RequestMethod.POST)
	public String cargarPagina(Model model, @RequestParam("id") final String id, @RequestParam("cliente") final String nombreCliente,
			@RequestParam("contraparte") final String idContraparte) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaContrato = (FachadaContrato) context.getBean("fachadaContrato");
		String mensaje = "El contrato fue ingresado correctamente";
		try {
			fachadaContrato.ingresarContrato(id, idContraparte, nombreCliente);
			model.addAttribute("message", mensaje);
			model.addAttribute("contrato", fachadaContrato.verContrato(id));
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			mensaje = MENSAJE_ERROR;
			model.addAttribute("errorMessage", mensaje);
			return cargarPagina(model);
		} catch (SgtiException e) {
			e.printStackTrace();
			mensaje = e.getMessage();
			model.addAttribute("errorMessage", mensaje);
			return cargarPagina(model);
		} finally {
			model.addAttribute("idContrato", id);
			context.close();
		}
		return "desktop/precio";
	}
	
	//Carga la tabla de contratos para las Contrapartes.
		@RequestMapping(value = "/tablaContraparte", method = RequestMethod.GET)
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

	@RequestMapping(value = "/configuracionVencer/redireccionarNotificacion", method = RequestMethod.GET)
	public String redireccionarNotificacionConfiruacionesVencer(Model model,
			HttpServletRequest request) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaNotificacion = (FachadaNotificacion) context.getBean("fachadaNotificacion");
		fachadaContrato = (FachadaContrato) context.getBean("fachadaContrato");
		fachadaConfiguracion = (FachadaConfiguracion) context.getBean("fachadaConfiguracion");
		fachadaPrecio = (FachadaPrecio) context.getBean("fachadaPrecio");
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		String idUsuario = (String) request.getSession().getAttribute("usuario");
		try {
			//Para comprobar por más seguridad, por si ingresan al link directamente.
			if (fachadaUsuario.usuarioEsSocio(idUsuario))
			{
				model.addAttribute("contratos", fachadaNotificacion.contratosConConfiguracionesAVencer(7));
				model.addAttribute("configuraciones", fachadaConfiguracion.seleccionarConfiguracionActualTodos());
				model.addAttribute("precios", fachadaPrecio.seleccionarPrecioActualTodos());
			}
			else
			
				return "/desktop/login2";
			
		} catch (IOException | SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return "desktop/tablaContratosSocio";
		}finally{
			context.close();
		}
		return "desktop/tablaContratosSocio";
	}

	@RequestMapping(value = "/precioVencer/redireccionarNotificacion", method = RequestMethod.GET)
	public String redireccionarNotificacionPreciosVencer(Model model,
			HttpServletRequest request) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaNotificacion = (FachadaNotificacion) context.getBean("fachadaNotificacion");
		fachadaContrato = (FachadaContrato) context.getBean("fachadaContrato");
		fachadaConfiguracion = (FachadaConfiguracion) context.getBean("fachadaConfiguracion");
		fachadaPrecio = (FachadaPrecio) context.getBean("fachadaPrecio");
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		String idUsuario = (String) request.getSession().getAttribute("usuario");
		try {
			//Para comprobar por más seguridad, por si ingresan al link directamente.
			if (fachadaUsuario.usuarioEsSocio(idUsuario))
			{
				model.addAttribute("contratos", fachadaNotificacion.contratosConPreciosAVencer(7));
				model.addAttribute("configuraciones", fachadaConfiguracion.seleccionarConfiguracionActualTodos());
				model.addAttribute("precios", fachadaPrecio.seleccionarPrecioActualTodos());
			}
			else
			
				return "/desktop/login2";
			
		} catch (IOException | SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return "desktop/tablaContratosSocio";
		}finally{
			context.close();
		}
		return "desktop/tablaContratosSocio";
	}

	@RequestMapping(value = "/horasInformar/redireccionarNotificacion", method = RequestMethod.GET)
	public String redireccionarNotificacionHorasInformar(Model model, HttpServletRequest request) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaNotificacion = (FachadaNotificacion) context.getBean("fachadaNotificacion");
		fachadaContrato = (FachadaContrato) context.getBean("fachadaContrato");
		fachadaConfiguracion = (FachadaConfiguracion) context.getBean("fachadaConfiguracion");
		fachadaPrecio = (FachadaPrecio) context.getBean("fachadaPrecio");
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		String idUsuario = (String) request.getSession().getAttribute("usuario");
		try {
			//Para comprobar por más seguridad, por si ingresan al link directamente.
			if (fachadaUsuario.usuarioEsSocio(idUsuario))
			{
				model.addAttribute("contratos", fachadaNotificacion.contratosConHorasAInformar(0));
				model.addAttribute("configuraciones", fachadaConfiguracion.seleccionarConfiguracionActualTodos());
				model.addAttribute("precios", fachadaPrecio.seleccionarPrecioActualTodos());
			}
			else
			
				return "/desktop/login2";
			
		} catch (IOException | SQLException | ClassNotFoundException | ParseException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return "desktop/tablaContratosSocio";
		}finally{
			context.close();
		}
		return "desktop/tablaContratosInformarFacturar";
	}

	@RequestMapping(value = "/tablaSocioInformarFacturar", method = RequestMethod.GET)
	public String cargarTablaContratosInformarFacturar(Model model, HttpServletRequest request)
	{	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaContrato = (FachadaContrato) context.getBean("fachadaContrato");
		fachadaConfiguracion = (FachadaConfiguracion) context.getBean("fachadaConfiguracion");
		fachadaPrecio = (FachadaPrecio) context.getBean("fachadaPrecio");
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		String idUsuario = (String) request.getSession().getAttribute("usuario");
		
		try {
			//Para comprobar por más seguridad, por si ingresan al link directamente.
			if (fachadaUsuario.usuarioEsSocio(idUsuario))
			{
				model.addAttribute("contratos", fachadaContrato.seleccionarContratos());
				model.addAttribute("configuraciones", fachadaConfiguracion.seleccionarConfiguracionActualTodos());
				model.addAttribute("precios", fachadaPrecio.seleccionarPrecioActualTodos());
			}
			else
			
				return "/desktop/login2";
			
		} catch (IOException | SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return "desktop/tablaContratosInformarFacturar";
		}finally{
			context.close();
		}
		return "desktop/tablaContratosInformarFacturar";
	}
	
	//Carga la tabla de contratos para las Contrapartes.
	@RequestMapping(value = "/tablaSocio", method = RequestMethod.GET)
	public String cargarTablaContratosSocio(Model model, HttpServletRequest request)
	{	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaContrato = (FachadaContrato) context.getBean("fachadaContrato");
		fachadaConfiguracion = (FachadaConfiguracion) context.getBean("fachadaConfiguracion");
		fachadaPrecio = (FachadaPrecio) context.getBean("fachadaPrecio");
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		String idUsuario = (String) request.getSession().getAttribute("usuario");
		
		try {
			//Para comprobar por más seguridad, por si ingresan al link directamente.
			if (fachadaUsuario.usuarioEsSocio(idUsuario))
			{
				model.addAttribute("contratos", fachadaContrato.seleccionarContratos());
				model.addAttribute("configuraciones", fachadaConfiguracion.seleccionarConfiguracionActualTodos());
				model.addAttribute("precios", fachadaPrecio.seleccionarPrecioActualTodos());
			}
			else
			
				return "/desktop/login2";
			
		} catch (IOException | SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return "desktop/tablaContratosSocio";
		}finally{
			context.close();
		}
		return "desktop/tablaContratosSocio";
	}
	
	
	@RequestMapping(value = "/tecnicosEnContrato", method = RequestMethod.POST)
	public String cargarTecnicosDelContrato(Model model, HttpServletRequest request,
			@RequestParam("id") final String idContrato)
	{	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaContratoTecnicos = (FachadaContratoTecnicos) context.getBean("fachadaContratoTecnicos");
		fachadaContrato = (FachadaContrato) context.getBean("fachadaContrato");
		
		String idUsuario = (String) request.getSession().getAttribute("usuario");
		
		try {
			//Para comprobar por más seguridad, por si ingresan al link directamente.
			if (fachadaUsuario.usuarioEsSocio(idUsuario))
			{
				model.addAttribute("tecnicos", fachadaContratoTecnicos.listarTecnicosPorContratoTodos(idContrato));
				model.addAttribute("tecnicosCandidatos", fachadaContratoTecnicos.listarTecnicosCandidatosPorContrato(idContrato));
				model.addAttribute("idContrato", idContrato);
				model.addAttribute("contrato", fachadaContrato.verContrato(idContrato));
			}
			else
			
				return "/desktop/login2";
			
		} catch (IOException | SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return "desktop/tablaTecnicosContrato";
		}finally{
			context.close();
		}
		return "desktop/tablaTecnicosContrato";
	}
	
	@RequestMapping(value = "/sacarTecnico", method = RequestMethod.POST)
	public String sacarTecnicoDelContrato(Model model, HttpServletRequest request,
			@RequestParam("id") final String idContrato, @RequestParam("idUsuario") final String idTecnico)
	{	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaContratoTecnicos = (FachadaContratoTecnicos) context.getBean("fachadaContratoTecnicos");
		fachadaContrato = (FachadaContrato) context.getBean("fachadaContrato");
		
		String idUsuario = (String) request.getSession().getAttribute("usuario");
		String mensaje = "El técnico se ha sacado del contrato correctamente.";
		
		try {
			//Para comprobar por más seguridad, por si ingresan al link directamente.
			if (fachadaUsuario.usuarioEsSocio(idUsuario))
			{
				fachadaContratoTecnicos.eliminarTecnicoDeContrato(idContrato, idTecnico);
				model.addAttribute("message", mensaje);
				model.addAttribute("idContrato", idContrato);
				model.addAttribute("contrato", fachadaContrato.verContrato(idContrato));
			}
			else
			
				return "/desktop/login2";
			
		} catch (IOException | SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return cargarTecnicosDelContrato(model, request, idContrato);
		}finally{
			context.close();
		}
		return cargarTecnicosDelContrato(model, request, idContrato);
	}
	
	@RequestMapping(value = "/asignarTecnico", method = RequestMethod.POST)
	public String asignarTecnicoAlContrato(Model model, HttpServletRequest request,
			@RequestParam("id") final String idContrato, @RequestParam("tecnicoAgregar") final String idTecnico)
	{	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaContratoTecnicos = (FachadaContratoTecnicos) context.getBean("fachadaContratoTecnicos");
		fachadaContrato = (FachadaContrato) context.getBean("fachadaContrato");
		
		String idUsuario = (String) request.getSession().getAttribute("usuario");
		String mensaje = "El técnico se ha agregado al contrato correctamente.";
		
		try {
			//Para comprobar por más seguridad, por si ingresan al link directamente.
			if (fachadaUsuario.usuarioEsSocio(idUsuario))
			{
				fachadaContratoTecnicos.asignarTecnicoAContrato(idTecnico, idContrato);
				model.addAttribute("message", mensaje);
				model.addAttribute("idContrato", idContrato);
				model.addAttribute("contrato", fachadaContrato.verContrato(idContrato));
			}
			else
			
				return "/desktop/login2";
			
		} catch (IOException | SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return cargarTecnicosDelContrato(model, request, idContrato);
		}finally{
			context.close();
		}
		return cargarTecnicosDelContrato(model, request, idContrato);
	}
	
	@RequestMapping(value = "/tecnicosEnContratoDesdeFlow", method = RequestMethod.POST)
	public String cargarTecnicosDelContratoDesdeFlow(Model model, HttpServletRequest request,
			@RequestParam("idContrato") final String idContrato)
	{	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaContratoTecnicos = (FachadaContratoTecnicos) context.getBean("fachadaContratoTecnicos");
		fachadaContrato = (FachadaContrato) context.getBean("fachadaContrato");
		fachadaPrecio = (FachadaPrecio) context.getBean("fachadaPrecio");
		fachadaTipoHora = (FachadaTipoHora) context.getBean("fachadaTipoHora");
		
		String idUsuario = (String) request.getSession().getAttribute("usuario");
		
		try {
			//Para comprobar por más seguridad, por si ingresan al link directamente.
			if (fachadaUsuario.usuarioEsSocio(idUsuario))
			{
				model.addAttribute("tecnicos", fachadaContratoTecnicos.listarTecnicosPorContratoTodos(idContrato));
				model.addAttribute("tecnicosCandidatos", fachadaContratoTecnicos.listarTecnicosCandidatosPorContrato(idContrato));
				model.addAttribute("idContrato", idContrato);
				model.addAttribute("contrato", fachadaContrato.verContrato(idContrato));
				
				model.addAttribute("tiposDeHoraAsignados", fachadaTipoHora.verTiposDeHoraPorContrato(idContrato));
				model.addAttribute("contrato", fachadaContrato.verContrato(idContrato));
				model.addAttribute("precios", fachadaPrecio.seleccionarPreciosPorContrato(idContrato));
			}
			else
			
				return "/desktop/login2";
			
		} catch (IOException | SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return "desktop/tecnicosContratoFlow";
		}finally{
			context.close();
		}
		return "desktop/tecnicosContratoFlow";
	}
		
	@RequestMapping(value = "/asignarTecnicoDesdeFlow", method = RequestMethod.POST)
	public String asignarTecnicoAlContratoDesdeFlow(Model model, HttpServletRequest request,
			@RequestParam("id") final String idContrato, 
			@RequestParam("tecnicoAgregar") final String idTecnico)
	{	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaContratoTecnicos = (FachadaContratoTecnicos) context.getBean("fachadaContratoTecnicos");
		fachadaContrato = (FachadaContrato) context.getBean("fachadaContrato");
		
		String idUsuario = (String) request.getSession().getAttribute("usuario");
		String mensaje = "El técnico se ha agregado al contrato correctamente.";
		
		try {
			//Para comprobar por más seguridad, por si ingresan al link directamente.
			if (fachadaUsuario.usuarioEsSocio(idUsuario))
			{
				fachadaContratoTecnicos.asignarTecnicoAContrato(idTecnico, idContrato);
				model.addAttribute("message", mensaje);
				model.addAttribute("idContrato", idContrato);
				model.addAttribute("contrato", fachadaContrato.verContrato(idContrato));
				
				model.addAttribute("tiposDeHoraAsignados", fachadaTipoHora.verTiposDeHoraPorContrato(idContrato));
				model.addAttribute("contrato", fachadaContrato.verContrato(idContrato));
				model.addAttribute("precios", fachadaPrecio.seleccionarPreciosPorContrato(idContrato));
			}
			else
			
				return "/desktop/login2";
			
		} catch (IOException | SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return cargarTecnicosDelContratoDesdeFlow(model, request, idContrato);
		}finally{
			context.close();
		}
		return cargarTecnicosDelContratoDesdeFlow(model, request, idContrato);
	}
	
	@RequestMapping(value = "/sacarTecnicoDesdeFlow", method = RequestMethod.POST)
	public String sacarTecnicoDelContratoDesdeFlow(Model model, HttpServletRequest request,
			@RequestParam("id") final String idContrato, @RequestParam("idUsuario") final String idTecnico)
	{	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaContratoTecnicos = (FachadaContratoTecnicos) context.getBean("fachadaContratoTecnicos");
		fachadaContrato = (FachadaContrato) context.getBean("fachadaContrato");
		
		String idUsuario = (String) request.getSession().getAttribute("usuario");
		String mensaje = "El técnico se ha sacado del contrato correctamente.";
		
		try {
			//Para comprobar por más seguridad, por si ingresan al link directamente.
			if (fachadaUsuario.usuarioEsSocio(idUsuario))
			{
				fachadaContratoTecnicos.eliminarTecnicoDeContrato(idContrato, idTecnico);
				model.addAttribute("message", mensaje);
				model.addAttribute("idContrato", idContrato);
				model.addAttribute("contrato", fachadaContrato.verContrato(idContrato));
				
				model.addAttribute("tiposDeHoraAsignados", fachadaTipoHora.verTiposDeHoraPorContrato(idContrato));
				model.addAttribute("contrato", fachadaContrato.verContrato(idContrato));
				model.addAttribute("precios", fachadaPrecio.seleccionarPreciosPorContrato(idContrato));
			}
			else
			
				return "/desktop/login2";
			
		} catch (IOException | SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return cargarTecnicosDelContratoDesdeFlow(model, request, idContrato);
		}finally{
			context.close();
		}
		return cargarTecnicosDelContratoDesdeFlow(model, request, idContrato);
	}
			
}
