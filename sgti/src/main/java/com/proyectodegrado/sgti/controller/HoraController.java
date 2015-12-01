package com.proyectodegrado.sgti.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyectodegrado.sgti.exceptions.SgtiException;
import com.proyectodegrado.sgti.fachada.FachadaActividad;
import com.proyectodegrado.sgti.fachada.FachadaContrato;
import com.proyectodegrado.sgti.fachada.FachadaContratoTecnicos;
import com.proyectodegrado.sgti.fachada.FachadaHora;
import com.proyectodegrado.sgti.fachada.FachadaTipoHora;
import com.proyectodegrado.sgti.fachada.FachadaUsuario;
import com.proyectodegrado.sgti.modelo.Hora;


@Controller
@RequestMapping("/desktop/hora")
public class HoraController extends AbstractController{
	
	private static final String MENSAJE_ERROR = "Ha ocurrido un error";

	private FachadaHora fachadaHora;
	
	private FachadaTipoHora fachadaTipoHora;
	
	private FachadaActividad fachadaActividad;
	
	private FachadaContrato fachadaContrato;
	private FachadaContratoTecnicos fachadaContratoTecnicos;
	private FachadaUsuario fachadaUsuario;
	
	@RequestMapping(value="/principal", method = RequestMethod.GET)
	public String irAPaginaPrincipal(){
		return "desktop/tiposDeHora";
	}
	
	@RequestMapping(value="/ingresar", method = RequestMethod.GET)
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
		cal.add(Calendar.DAY_OF_YEAR, -45);
		
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
				model.addAttribute("actividades", fachadaActividad.verActividadesPendientesPorUsuario(idUsuario));
				model.addAttribute("filtroUsuario", idUsuario);
				model.addAttribute("todosTiposHora", fachadaTipoHora.verContratoTiposHoraParaGestionarHorasPorTecnico(idUsuario));
			}
			else{
				model.addAttribute("contratos", fachadaContrato.seleccionarContratosVigentes());
				model.addAttribute("horasRegistradas", fachadaHora.seleccionarHorasConFechaDesde(fechaDesdeFiltro));
				model.addAttribute("actividades", fachadaActividad.seleccionarActividadesPendientes());
				model.addAttribute("todosTiposHora", fachadaTipoHora.verContratoTiposHoraParaGestionarHoras());
				
			}
			
			
			model.addAttribute("fechaDesdeFiltro", fechaDesdeFiltro);
			model.addAttribute("filtroValidada", "Todas");
			
			
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
	
	@RequestMapping(value="/tablaHorasContraparte", method = RequestMethod.POST)
	public String cargarPaginaHorasContraparte(Model model, HttpServletRequest request,
			@RequestParam("id") final String idContrato){
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaHora = (FachadaHora) context.getBean("fachadaHora");
		fachadaTipoHora = (FachadaTipoHora) context.getBean("fachadaTipoHora");
		fachadaActividad = (FachadaActividad) context.getBean("fachadaActividad");
		fachadaContrato = (FachadaContrato) context.getBean("fachadaContrato");
		//String idUsuario = (String) request.getSession().getAttribute("usuario");
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_YEAR, -45);
		
		Date fechaDesdeFiltro = cal.getTime();
		
		try {
			model.addAttribute("tipoHoras", fachadaTipoHora.verTiposDeHora());
			model.addAttribute("actividades", fachadaActividad.seleccionarActividadesConFechaDesde(fechaDesdeFiltro));
			model.addAttribute("contratos", fachadaContrato.seleccionarContratosVigentes());
			model.addAttribute("horasRegistradas", fachadaHora.seleccionarHorasPorContratoValidadas(idContrato));
			model.addAttribute("fechaDesdeFiltro", fechaDesdeFiltro);
			model.addAttribute("id", idContrato);
		} catch (ClassNotFoundException | IOException | SQLException
				| ParseException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return "desktop/tablaHorasContraparte";
		}finally{
			context.close();
		}
		return "desktop/tablaHorasContraparte";
	}
	

	@RequestMapping(value="/tablaFiltrada", method = RequestMethod.POST)
	public String cargarPaginaFiltrada(Model model, HttpServletRequest request,
			@RequestParam("fechaDesdeFiltro") final String fechaDesdeFiltro,
			@RequestParam("filtroUsuario") String filtroUsuario,
			@RequestParam("filtroValidada") final String filtroValidada){
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaHora = (FachadaHora) context.getBean("fachadaHora");
		fachadaTipoHora = (FachadaTipoHora) context.getBean("fachadaTipoHora");
		fachadaActividad = (FachadaActividad) context.getBean("fachadaActividad");
		fachadaContrato = (FachadaContrato) context.getBean("fachadaContrato");
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		fachadaContratoTecnicos = (FachadaContratoTecnicos) context.getBean("fachadaContratoTecnicos");
		
		String idUsuario = (String) request.getSession().getAttribute("usuario");
		Date fechaDesde = null;
		boolean esTecnico = false;
		
		
		try {
			if (!fachadaUsuario.usuarioEsSocio(idUsuario)){
				filtroUsuario = idUsuario;
				esTecnico = true;
			}
			
			try{
				SimpleDateFormat simpleFateFormat = new SimpleDateFormat("yyyy-MM-dd");
				fechaDesde = simpleFateFormat.parse(fechaDesdeFiltro);
				}
				catch (ParseException e) {
					Calendar cal = Calendar.getInstance();
					cal.set(2010, 01, 01);
					
					fechaDesde = cal.getTime();
				}
			
			model.addAttribute("tipoHoras", fachadaTipoHora.verTiposDeHora());
			
			if (esTecnico){
				model.addAttribute("contratos", fachadaContratoTecnicos.listarContratosPorTecnicoTodos(idUsuario));
				model.addAttribute("actividades", fachadaActividad.verActividadesPendientesPorUsuario(idUsuario));
				model.addAttribute("todosTiposHora", fachadaTipoHora.verContratoTiposHoraParaGestionarHorasPorTecnico(idUsuario));
			}
			else{
				model.addAttribute("contratos", fachadaContrato.seleccionarContratosVigentes());
				model.addAttribute("actividades", fachadaActividad.seleccionarActividadesPendientes());
				model.addAttribute("todosTiposHora", fachadaTipoHora.verContratoTiposHoraParaGestionarHoras());
			}
			
			model.addAttribute("horasRegistradas", fachadaHora.seleccionarHorasFiltradas(fechaDesde, filtroUsuario, filtroValidada));
			model.addAttribute("fechaDesdeFiltro", fechaDesde);
			model.addAttribute("filtroUsuario", filtroUsuario);
			model.addAttribute("filtroValidada", filtroValidada);
			
		} catch (ClassNotFoundException | IOException | SQLException
				| ParseException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return "desktop/tablaHoras";
		}finally{
			context.close();
		}
		//if (!esTecnico)
			return "desktop/tablaHoras";
		//else
			//return "desktop/tablaHorasTecnicos";
	}
	
	@RequestMapping(value="/tablaFiltradaContraparte", method = RequestMethod.POST)
	public String cargarPaginaFiltradaContraparte(Model model, HttpServletRequest request,
			@RequestParam("id") final String idContrato,
			@RequestParam("fechaDesdeFiltro") final String fechaFiltrar){
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaHora = (FachadaHora) context.getBean("fachadaHora");
		fachadaTipoHora = (FachadaTipoHora) context.getBean("fachadaTipoHora");
		fachadaActividad = (FachadaActividad) context.getBean("fachadaActividad");
		fachadaContrato = (FachadaContrato) context.getBean("fachadaContrato");
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		Date fechaDesde = null;
		
		
		try {
			
			try{
				SimpleDateFormat simpleFateFormat = new SimpleDateFormat("yyyy-MM-dd");
				fechaDesde = simpleFateFormat.parse(fechaFiltrar);
				}
				catch (ParseException e) {
					Calendar cal = Calendar.getInstance();
					cal.set(2010, 01, 01);
					
					fechaDesde = cal.getTime();
				}
			
			model.addAttribute("horasRegistradas", fachadaHora.seleccionarHorasRegistradasPorContratoDesdeFecha(idContrato, fechaDesde));
			model.addAttribute("fechaDesdeFiltro", fechaDesde);
			model.addAttribute("id", idContrato);
			
		} catch (ClassNotFoundException | IOException | SQLException
				| ParseException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return "desktop/tablaHoras";
		}finally{
			context.close();
		}
		return "desktop/tablaHorasContraparte";
	}
	
	@RequestMapping(value="/ingresarHora", method = RequestMethod.POST)
	public String insertarHora(Model model, HttpServletRequest request, 
			@RequestParam("fechaDesdeFiltro") final String fechaFiltrar,
			@RequestParam("filtroUsuario") String filtroUsuario,
			@RequestParam("filtroValidada") final String filtroValidada,
			@RequestParam("fechadesde") final String fechaDesde, @RequestParam("fechahasta") final String fechaHasta, 
			@RequestParam("tipohora") final String tipoHora, @RequestParam("remoto") final String remoto, @RequestParam(required=false, value="contrato") final String idContrato,
			@RequestParam(required=false, value="actividad") String idActividad, @RequestParam("descripcion") final String descripcion, @RequestParam("comentario") final String comentario){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaHora = (FachadaHora) context.getBean("fachadaHora");
		String mensaje = "La hora fue ingresada correctamente";
		//required=false, value=
		try {
			String idUsuario = (String) request.getSession().getAttribute("usuario");
			
			if (idActividad.matches("nulo"))
				idActividad = null;
			
			if (tipoHora.isEmpty())
				mensaje = "No se pudo registrar la hora, debe seleccionar un tipo de hora";
			
			System.out.println("Tipo de hora: " + tipoHora);
			
			fachadaHora.registrarHora(fechaDesde, fechaHasta, tipoHora, remoto, idUsuario, idContrato, idActividad, descripcion, comentario);
			int duracion = fachadaHora.diferenciaEnMinutos(fechaDesde, fechaHasta);
			mensaje = mensaje + ", duración: " + duracion + " minutos";
			model.addAttribute("fechaDesdeFiltro", fechaFiltrar);
			model.addAttribute("filtroUsuario", filtroUsuario);
			model.addAttribute("filtroValidada", filtroValidada);
			model.addAttribute("message", mensaje);
			
		} catch (ClassNotFoundException | SQLException | IOException | ParseException e) {
			e.printStackTrace();
			mensaje = e.getMessage();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			cargarPagina(model, request);
		}
		catch (SgtiException e) {
			e.printStackTrace();
			mensaje = e.getMessage();
			model.addAttribute("errorMessage", mensaje);
			return cargarPagina(model, request);
		}finally{
			context.close();
		}
		return cargarPagina(model, request);
	}
	
	@RequestMapping(value="/editarHora", method = RequestMethod.POST)
	public String editarHora(Model model, HttpServletRequest request, 
			@RequestParam("fechaDesdeFiltro") final String fechaFiltrar,
			@RequestParam("filtroUsuario") String filtroUsuario,
			@RequestParam("filtroValidada") final String filtroValidada,
			@RequestParam("fechadesde") final String fechaDesde, @RequestParam("fechahasta") final String fechaHasta, 
			@RequestParam("tipohora") final String tipoHora, @RequestParam("remoto") final String remoto, @RequestParam("contrato") final String idContrato,
			@RequestParam("actividad") String idActividad, @RequestParam("descripcion") final String descripcion, @RequestParam("comentario") final String comentario, 
			@RequestParam("id") final String id, @RequestParam("fechainformar") final String fechaInformar, @RequestParam("fechafacturar") final String fechaFacturar, 
			@RequestParam("fechacomputar") final String fechaComputar){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaHora = (FachadaHora) context.getBean("fachadaHora");
		String mensaje = "La hora fue editada correctamente";
		try {

			if (idActividad.matches("nulo"))
				idActividad = null;
			
			fachadaHora.editarHora(fechaDesde, fechaHasta, tipoHora, remoto, idContrato, idActividad, descripcion, comentario, id, fechaInformar, fechaFacturar, fechaComputar);
			int duracion = fachadaHora.diferenciaEnMinutos(fechaDesde, fechaHasta);
			mensaje = mensaje + ", duración: " + duracion + " minutos";
			model.addAttribute("message", mensaje);
			model.addAttribute("fechaDesdeFiltro", fechaFiltrar);
			model.addAttribute("filtroUsuario", filtroUsuario);
			model.addAttribute("filtroValidada", filtroValidada);
		} catch (ClassNotFoundException | SQLException | IOException | ParseException e) {
			e.printStackTrace();
			mensaje = MENSAJE_ERROR;
			model.addAttribute("errorMessage", mensaje);
			cargarPagina(model, request);
		}finally{
			context.close();
		}
		return cargarPagina(model, request);
	}
	
	@RequestMapping(value="/detalleHora", method = RequestMethod.POST)
	public String detalleHora(Model model, HttpServletRequest request, @RequestParam("id") final int idHora){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaHora = (FachadaHora) context.getBean("fachadaHora");
		try {
			Hora hora = fachadaHora.seleccionarHora(idHora);
			model.addAttribute("hora", fachadaHora.seleccionarHora(idHora));
			model.addAttribute("fechasInforme",fachadaHora.proximas3FechasInforme(hora.getIdContrato()));
			model.addAttribute("fechasFacturacion",fachadaHora.proximas3FechasFacturacion(hora.getIdContrato()));
			model.addAttribute("fechasComputacion",fachadaHora.proximas3FechasFacturacion(hora.getIdContrato()));
		} catch (ClassNotFoundException | SQLException | IOException | ParseException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return "desktop/editarHora";
		}finally{
			context.close();
		}
		return "desktop/editarHora";
	}
	
	@RequestMapping(value="/detalleHoraOk", method = RequestMethod.POST)
	public String detalleHoraOk(Model model, HttpServletRequest request, 
			@RequestParam("id") final int idHora, 
			@RequestParam("fechaInformar") final String fechaInformar, 
			@RequestParam("fechaFacturar") final String fechaFacturar, 
			@RequestParam("fechaComputar") final String fechaComputar){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaHora = (FachadaHora) context.getBean("fachadaHora");
		String mensaje = "Cambios guardados con éxito";
		try {
			fachadaHora.editarHoraDetalle(idHora, fechaInformar, fechaFacturar, fechaComputar);
			model.addAttribute("message", mensaje);
			
		} catch (ClassNotFoundException | SQLException | IOException | ParseException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return cargarPagina(model, request);
		}finally{
			context.close();
		}
		return cargarPagina(model, request);
	}
	
	@RequestMapping(value="/validarHora", method = RequestMethod.POST)
	public String validarHora(Model model, HttpServletRequest request, 
			@RequestParam("fechaDesdeFiltro") final String fechaFiltrar,
			@RequestParam("filtroUsuario") String filtroUsuario,
			@RequestParam("filtroValidada") final String filtroValidada,
			@RequestParam("id") final int id){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaHora = (FachadaHora) context.getBean("fachadaHora");
		String mensaje = "Validación cambiada correctamente";
		Date fechaDesde = null;
		
		try {
			try{
				SimpleDateFormat simpleFateFormat = new SimpleDateFormat("yyyy-MM-dd");
				fechaDesde = simpleFateFormat.parse(fechaFiltrar);
				}
				catch (ParseException e) {
					Calendar cal = Calendar.getInstance();
					cal.set(2010, 00, 01);
					
					fechaDesde = cal.getTime();
				}
			
			fachadaHora.cambiarValidacionHora(id);
			model.addAttribute("message", mensaje);
			model.addAttribute("fechaDesdeFiltro", fechaDesde);
			model.addAttribute("todosTiposHora", fachadaTipoHora.verContratoTiposHoraParaGestionarHoras());
			
		} catch (ClassNotFoundException | SQLException | IOException | ParseException e) {
			e.printStackTrace();
			mensaje = MENSAJE_ERROR;
			model.addAttribute("errorMessage", mensaje);
			return cargarPaginaFiltrada(model, request, fechaFiltrar, filtroUsuario, filtroValidada);
		}finally{
			context.close();
		}
		return cargarPaginaFiltrada(model, request, fechaFiltrar, filtroUsuario, filtroValidada);
	}
	
	@RequestMapping(value="/copiarHora", method = RequestMethod.POST)
	public String copiarHora(Model model, HttpServletRequest request, 
			@RequestParam("fechaDesdeFiltro") final String fechaFiltrar,
			@RequestParam("filtroUsuario") String filtroUsuario,
			@RequestParam("filtroValidada") final String filtroValidada,
			@RequestParam("id") final int id){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaHora = (FachadaHora) context.getBean("fachadaHora");
		fachadaTipoHora = (FachadaTipoHora) context.getBean("fachadaTipoHora");
		fachadaContrato = (FachadaContrato) context.getBean("fachadaContrato");
		String mensaje = "La Hora fue ingresada correctamente";
		Date fechaDesde = null;
		
		try {
			try{
				SimpleDateFormat simpleFateFormat = new SimpleDateFormat("yyyy-MM-dd");
				fechaDesde = simpleFateFormat.parse(fechaFiltrar);
				}
				catch (ParseException e) {
					Calendar cal = Calendar.getInstance();
					cal.set(2010, 00, 01);
					
					fechaDesde = cal.getTime();
				}
			
			model.addAttribute("horaCopiada", fachadaHora.seleccionarHora(id));
			model.addAttribute("fechaDesdeFiltro", fechaDesde);
			model.addAttribute("filtroUsuario", filtroUsuario);
			model.addAttribute("filtroValidada", filtroValidada);
			model.addAttribute("todosTiposHora", fachadaTipoHora.verContratoTiposHoraParaGestionarHoras());
			model.addAttribute("contratosFiltrar", fachadaContrato.seleccionarContratos());
			

		} catch (ClassNotFoundException | SQLException | IOException
				| ParseException e) {
			e.printStackTrace();
			mensaje = MENSAJE_ERROR;
			model.addAttribute("errorMessage", mensaje);
			return cargarPaginaFiltrada(model, request, fechaFiltrar, filtroUsuario, filtroValidada);
		}finally{
			context.close();
		}
		return cargarPaginaFiltrada(model, request, fechaFiltrar, filtroUsuario, filtroValidada);
	}
	
	@RequestMapping(value="/borrar", method = RequestMethod.POST)
	public String borrarHora(Model model, HttpServletRequest request, 
			@RequestParam("id") final int id){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaHora = (FachadaHora) context.getBean("fachadaHora");
		
		String mensaje = "La hora ha sido eliminada correctamente";
		try {
			fachadaHora.borrar(id);
			model.addAttribute("message", mensaje);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
			mensaje = MENSAJE_ERROR;
			model.addAttribute("errorMessage", mensaje);
			return cargarPagina(model, request);
		}finally{
			context.close();
		}
		return cargarPagina(model, request);
	}
	
	@RequestMapping(value="/prueba", method = RequestMethod.POST)
	public String pruebaHora(Model model, HttpServletRequest request, 
			@RequestParam("select2") final String idHora){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaHora = (FachadaHora) context.getBean("fachadaHora");
		
		String mensaje = "La hora ha sido eliminada correctamente";
		
			model.addAttribute("message", mensaje);
		
			context.close();
		
		return "#";
	}

}