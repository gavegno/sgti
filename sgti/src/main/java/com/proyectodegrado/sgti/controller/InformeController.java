package com.proyectodegrado.sgti.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JFileChooser;

import org.apache.poi.hwpf.HWPFDocument;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyectodegrado.sgti.fachada.FachadaConfiguracion;
import com.proyectodegrado.sgti.fachada.FachadaContrato;
import com.proyectodegrado.sgti.fachada.FachadaInforme;
import com.proyectodegrado.sgti.fachada.FachadaPrecio;
import com.proyectodegrado.sgti.fachada.FachadaUsuario;
import com.proyectodegrado.sgti.modelo.Usuario;

@Controller
@RequestMapping("/desktop/informe")
public class InformeController extends AbstractController{
	
	private FachadaInforme fachadaInforme;
	
	private FachadaContrato fachadaContrato;
	private FachadaUsuario fachadaUsuario;
	private FachadaConfiguracion fachadaConfiguracion;
	private FachadaPrecio fachadaPrecio;
	
	private static final String PLANTILLA_INFORME = "/WEB-INF/resources/Informes/";
	
	private static final String MENSAJE_ERROR = "Ha ocurrido un error";

	@RequestMapping(value="/informarHoras", method = RequestMethod.POST)
	public String informarHoras(Model model,HttpServletRequest request, HttpServletResponse response, @RequestParam("id") final String idContrato) throws IOException{
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		try {
			fachadaInforme = (FachadaInforme) context.getBean("fachadaInforme");
			HWPFDocument word = fachadaInforme.informarHoras(model, request, idContrato);
			response.setContentType("application/msword");
			response.setHeader("Content-Disposition", "filename=" + idContrato + new Date().getTime() + ".doc");
			OutputStream output = response.getOutputStream();
			output.flush();
			word.write(output);
			output.close();
		} catch (ClassNotFoundException | SQLException | ParseException e) {
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			cargarTablaContratosInformarFacturar(model, request);
			e.printStackTrace();
		}finally{
			context.close();
		}
		return cargarTablaContratosInformarFacturar(model, request);
	}
	
	@RequestMapping(value="/tecnicos", method = RequestMethod.GET)
	public String cargarPaginaInformeTecnicos(Model model){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		try {
			Calendar fecha = Calendar.getInstance();
			fecha.setTime(new Date());
			List<String> fechas = new ArrayList<String>();
			SimpleDateFormat formato = new SimpleDateFormat("MMMM");
			fechas.add(formato.format(fecha.getTime()));
			for(int i = 0; i < 2; i++){
				fecha.add(Calendar.MONTH, -1);
				fechas.add(formato.format(fecha.getTime()));
			}
			List<Usuario> usuarios = fachadaUsuario.seleccionarUsuariosSocio();
			usuarios.addAll(fachadaUsuario.seleccionarUsuariosTecnico());
			model.addAttribute("usuarios", usuarios);
			model.addAttribute("fechas", fechas);
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return "/desktop/informeHorasTecnico";
		}finally{
			context.close();
		}
		
		return "/desktop/informeHorasTecnico";
	}
	
	@RequestMapping(value="/informarHorasTecnico", method = RequestMethod.POST)
	public String informarHorasTecnico(Model model, HttpServletRequest request, HttpServletResponse response, @RequestParam("usuario") final String idUsuario, @RequestParam("fecha") final String mesFecha){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaInforme = (FachadaInforme) context.getBean("fachadaInforme");
		try {
			HWPFDocument word = fachadaInforme.informarHorasTecnico(model, request, idUsuario, mesFecha);
			response.setContentType("application/msword");
			response.setHeader("Content-Disposition", "filename=" + idUsuario + new Date().getTime() + ".doc");
			OutputStream output = response.getOutputStream();
			output.flush();
			word.write(output);
			output.close();

		} catch (ClassNotFoundException | IOException | SQLException | ParseException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			return cargarPaginaInformeTecnicos(model);
		}finally{
			context.close();
		}
		return cargarPaginaInformeTecnicos(model);
	}
	
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
				model.addAttribute("contratosFechaInforme", fachadaContrato.verProximaFechaInforme());
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

	@RequestMapping(value="/validarInforme", method = RequestMethod.POST)
	public String informarFacturacion(Model model, HttpServletRequest request, @RequestParam("id") final String idContrato){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		try {
			fachadaInforme = (FachadaInforme) context.getBean("fachadaInforme");
			fachadaInforme.validarInforme(idContrato);
		} catch (ClassNotFoundException | SQLException | IOException
				| ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			context.close();
		}
		return cargarTablaContratosInformarFacturar(model, request);
	}

}
