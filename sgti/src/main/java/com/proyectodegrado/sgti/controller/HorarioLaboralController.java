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

import com.proyectodegrado.sgti.fachada.FachadaContrato;
import com.proyectodegrado.sgti.fachada.FachadaHorarioLaboral;
import com.proyectodegrado.sgti.fachada.FachadaPrecio;
import com.proyectodegrado.sgti.fachada.FachadaTipoHora;
import com.proyectodegrado.sgti.modelo.HorarioLaboral;

@Controller
@RequestMapping("/desktop/dias")
public class HorarioLaboralController {
	
	private static final String MENSAJE_ERROR = "Ha ocurrido un error";
	private FachadaHorarioLaboral fachadaHorarioLaboral;
	private FachadaPrecio fachadaPrecio;
	private FachadaTipoHora fachadaTipoHora;
	private FachadaContrato fachadaContrato;
	
	@RequestMapping(value="/ingresardia", method = RequestMethod.POST)
	public String ingresarDiaHorarioLaboral(Model model, @RequestParam("idHorarioLaboral") final String id, @RequestParam("nombreDia") final String nombreDia, 
			@RequestParam("horaDesde") final String horaDesde, @RequestParam("horaHasta") final String horaHasta, @RequestParam("idContrato") final String idContrato){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaHorarioLaboral = (FachadaHorarioLaboral) context.getBean("fachadaHorarioLaboral");
		String mensaje = "El día se ha asignago correctamente al horario laboral";
		try {
			fachadaHorarioLaboral.insertarDiaHorarioLaboral(id, nombreDia, horaDesde, horaHasta);
			model.addAttribute("message", mensaje);
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			mensaje = MENSAJE_ERROR;
			model.addAttribute("errorMessage", mensaje);
			return cargarPagina(model,idContrato);
		}finally{
			model.addAttribute("idHorarioLaboral", id);
			model.addAttribute("idContrato", idContrato);
			context.close();
		}
		return cargarPagina(model,idContrato);
	}
	
	@RequestMapping(value="/ingresarHorarioLaboral", method = RequestMethod.POST)
	public String ingresarHorarioLaboral(Model model, @RequestParam("idHorarioLaboral") final String id, @RequestParam("nombreDia") final String nombreDia, 
			@RequestParam("horaDesde") final String horaDesde, @RequestParam("horaHasta") final String horaHasta){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaHorarioLaboral = (FachadaHorarioLaboral) context.getBean("fachadaHorarioLaboral");
		String mensaje = "El día se ha asignago correctamente al horario laboral";
		try {
			fachadaHorarioLaboral.insertarDiaHorarioLaboral(id, nombreDia, horaDesde, horaHasta);
			model.addAttribute("message", mensaje);
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			mensaje = MENSAJE_ERROR;
			model.addAttribute("errorMessage", mensaje);
			return "desktop/agregarHorarioLaboral";
		}finally{
			model.addAttribute("idHorarioLaboral", id);
			model.addAttribute("message", mensaje);
			context.close();
		}
		return "desktop/agregarHorarioLaboral";
	}
	
	@RequestMapping(value="/ingresar", method = RequestMethod.POST)
	public String cargarPagina(Model model, @RequestParam("idContrato") final String idContrato){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		fachadaTipoHora = (FachadaTipoHora) context.getBean("fachadaTipoHora");
		fachadaContrato = (FachadaContrato) context.getBean("fachadaContrato");
		fachadaPrecio = (FachadaPrecio) context.getBean("fachadaPrecio");
		
		
		
		try {
			model.addAttribute("contrato", fachadaContrato.verContrato(idContrato));
			model.addAttribute("idContrato", idContrato);
			model.addAttribute("tiposDeHoraAsignados", fachadaTipoHora.verTiposDeHoraPorContrato(idContrato));
			model.addAttribute("precios", fachadaPrecio.seleccionarPreciosPorContrato(idContrato));
		
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
		}finally{
			context.close();
		}
		
		return "desktop/dias";
	}
	
	@RequestMapping(value="/ingresarCompleto", method = RequestMethod.POST)
	public String ingresarHorarioLaboralCompleto(Model model, HttpServletRequest request, 
			@RequestParam("idHorarioLaboral") final String id, 
			@RequestParam("lunes") final String lunes, 
			@RequestParam("lunesdesde") final String lunesDesde, 
			@RequestParam("luneshasta") final String lunesHasta,
			@RequestParam("martes") final String martes, 
			@RequestParam("martesdesde") final String martesDesde, 
			@RequestParam("marteshasta") final String martesHasta,
			@RequestParam("miercoles") final String miercoles, 
			@RequestParam("miercolesdesde") final String miercolesDesde, 
			@RequestParam("miercoleshasta") final String miercolesHasta,
			@RequestParam("jueves") final String jueves, 
			@RequestParam("juevesdesde") final String juevesDesde, 
			@RequestParam("jueveshasta") final String juevesHasta,
			@RequestParam("viernes") final String viernes, 
			@RequestParam("viernesdesde") final String viernesDesde, 
			@RequestParam("vierneshasta") final String viernesHasta,
			@RequestParam("sabado") final String sabado, 
			@RequestParam("sabadodesde") final String sabadoDesde, 
			@RequestParam("sabadohasta") final String sabadoHasta,
			@RequestParam("domingo") final String domingo, 
			@RequestParam("domingodesde") final String domingoDesde, 
			@RequestParam("domingohasta") final String domingoHasta){
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaHorarioLaboral = (FachadaHorarioLaboral) context.getBean("fachadaHorarioLaboral");
		String mensaje = "El horario laboral se guardó exitosamente con ";
		try {
			int cant = fachadaHorarioLaboral.insertarHorarioLaboralCompleto(id, lunes, lunesDesde, lunesHasta, martes, martesDesde, martesHasta, miercoles, miercolesDesde, miercolesHasta, jueves, juevesDesde, juevesHasta, viernes, viernesDesde, viernesHasta, sabado, sabadoDesde, sabadoHasta, domingo, domingoDesde, domingoHasta);
			model.addAttribute("message", mensaje + cant + " días");
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			mensaje = MENSAJE_ERROR;
			model.addAttribute("errorMessage", mensaje);
			return "desktop/detalleDia";
		}finally{
			context.close();
		}
		return "desktop/detalleDia";
	}
	
	@RequestMapping(value="/ingresarCompletoNewCont", method = RequestMethod.POST)
	public String ingresarHorarioLaboralCompletoDesdePasosContrato(Model model, HttpServletRequest request, 
			@RequestParam("idContrato") final String idContrato,
			@RequestParam("idHorarioLaboral") final String id, 
			@RequestParam("lunes") final String lunes, 
			@RequestParam("lunesdesde") final String lunesDesde, 
			@RequestParam("luneshasta") final String lunesHasta,
			@RequestParam("martes") final String martes, 
			@RequestParam("martesdesde") final String martesDesde, 
			@RequestParam("marteshasta") final String martesHasta,
			@RequestParam("miercoles") final String miercoles, 
			@RequestParam("miercolesdesde") final String miercolesDesde, 
			@RequestParam("miercoleshasta") final String miercolesHasta,
			@RequestParam("jueves") final String jueves, 
			@RequestParam("juevesdesde") final String juevesDesde, 
			@RequestParam("jueveshasta") final String juevesHasta,
			@RequestParam("viernes") final String viernes, 
			@RequestParam("viernesdesde") final String viernesDesde, 
			@RequestParam("vierneshasta") final String viernesHasta,
			@RequestParam("sabado") final String sabado, 
			@RequestParam("sabadodesde") final String sabadoDesde, 
			@RequestParam("sabadohasta") final String sabadoHasta,
			@RequestParam("domingo") final String domingo, 
			@RequestParam("domingodesde") final String domingoDesde, 
			@RequestParam("domingohasta") final String domingoHasta){
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaHorarioLaboral = (FachadaHorarioLaboral) context.getBean("fachadaHorarioLaboral");
		fachadaTipoHora = (FachadaTipoHora) context.getBean("fachadaTipoHora");
		fachadaContrato = (FachadaContrato) context.getBean("fachadaContrato");
		fachadaPrecio = (FachadaPrecio) context.getBean("fachadaPrecio");
		String mensaje = "El horario laboral se guardó exitosamente con ";
		try {
			int cant = fachadaHorarioLaboral.insertarHorarioLaboralCompleto(id, lunes, lunesDesde, lunesHasta, martes, martesDesde, martesHasta, miercoles, miercolesDesde, miercolesHasta, jueves, juevesDesde, juevesHasta, viernes, viernesDesde, viernesHasta, sabado, sabadoDesde, sabadoHasta, domingo, domingoDesde, domingoHasta);
			model.addAttribute("message", mensaje + cant + " días");
			model.addAttribute("idContrato", idContrato);
			model.addAttribute("horariosLaborales", fachadaHorarioLaboral.verHorariosLaborales());
			model.addAttribute("tiposDeHoraAsignados", fachadaTipoHora.verTiposDeHoraPorContrato(idContrato));
			model.addAttribute("contrato", fachadaContrato.verContrato(idContrato));
			model.addAttribute("precios", fachadaPrecio.seleccionarPreciosPorContrato(idContrato));
			model.addAttribute("idHorarioLaboralNuevo", id);
			
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			mensaje = MENSAJE_ERROR;
			model.addAttribute("errorMessage", mensaje);
			return "desktop/configuracion";
		}finally{
			context.close();
		}
		return "desktop/configuracion";
	}
	
	@RequestMapping(value = "/tabla", method = RequestMethod.GET)
	public String listarHorarios(Model model, HttpServletRequest request){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaHorarioLaboral = (FachadaHorarioLaboral) context.getBean("fachadaHorarioLaboral");
		try {
			model.addAttribute("horarios", fachadaHorarioLaboral.verHorariosLaborales());

		}catch (IOException | SQLException | ClassNotFoundException e) {
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return "desktop/tablaHorariosLaborales";
		} finally{
			context.close();
		}
		return "desktop/tablaHorariosLaborales";
	}
	
	@RequestMapping(value = "/cargarVer", method = RequestMethod.POST)
	public String editarTipoHora(Model model, HttpServletRequest request, 
			@RequestParam("id") final String id){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaHorarioLaboral = (FachadaHorarioLaboral) context.getBean("fachadaHorarioLaboral");
		
		
		//String mensaje = "El horario laboral se ha editado correctamente";
		try {
			
			model.addAttribute("idHorarioLaboral", id);
			
			HorarioLaboral horario = fachadaHorarioLaboral.verDiasDeHorarioLaboral(id);

			for(int i=0;i<horario.getDias().size();i++){
				switch (horario.getDias().get(i).getNombre())
				{
					case "Lunes":
						model.addAttribute("lunesdesde", horario.getDias().get(i).getHoraDesde());
						model.addAttribute("luneshasta", horario.getDias().get(i).getHoraHasta());
						break;
					case "Martes":
						model.addAttribute("martesdesde", horario.getDias().get(i).getHoraDesde());
						model.addAttribute("marteshasta", horario.getDias().get(i).getHoraHasta());
						break;
					case "Miércoles":
						model.addAttribute("miercolesdesde", horario.getDias().get(i).getHoraDesde());
						model.addAttribute("miercoleshasta", horario.getDias().get(i).getHoraHasta());
						break;
					case "Jueves":
						model.addAttribute("juevesdesde", horario.getDias().get(i).getHoraDesde());
						model.addAttribute("jueveshasta", horario.getDias().get(i).getHoraHasta());
						break;
					case "Viernes":
						model.addAttribute("viernesdesde", horario.getDias().get(i).getHoraDesde());
						model.addAttribute("vierneshasta", horario.getDias().get(i).getHoraHasta());
						break;
					case "Sábado":
						model.addAttribute("sabadodesde", horario.getDias().get(i).getHoraDesde());
						model.addAttribute("sabadohasta", horario.getDias().get(i).getHoraHasta());
						break;
					case "Domingo":
						model.addAttribute("domingodesde", horario.getDias().get(i).getHoraDesde());
						model.addAttribute("domingohasta", horario.getDias().get(i).getHoraHasta());
						break;
					
				}
			}
			
		}catch (IOException | SQLException | ClassNotFoundException e) {
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return listarHorarios(model, request);
		}finally{
			context.close();
		}
		return "desktop/detalleVerHorario";
	}
	
	@RequestMapping(value = "/borrar", method = RequestMethod.POST)
	public String borrarTipoHora(Model model, HttpServletRequest request, 
			@RequestParam("id") final String id){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaHorarioLaboral = (FachadaHorarioLaboral) context.getBean("fachadaHorarioLaboral");
		String mensaje = "El horario laboral se ha eliminado correctamente";
		try {
			if (fachadaHorarioLaboral.horarioLaboralEnUso(id))
				model.addAttribute("errorMessage", "El horario laboral está en uso, no puede ser eliminado");
			else
			{
				fachadaHorarioLaboral.borrarHorarioLaboral(id);
				model.addAttribute("message", mensaje);
			}
		}catch (IOException | SQLException | ClassNotFoundException e) {
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return listarHorarios(model, request);
		}finally{
			context.close();
		}
		return listarHorarios(model, request);
	}

}
