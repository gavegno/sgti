package com.proyectodegrado.sgti.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
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

@Controller
@RequestMapping("/desktop/informe")
public class InformeController extends AbstractController{
	
	private FachadaInforme fachadaInforme;
	
	private FachadaContrato fachadaContrato;
	private FachadaUsuario fachadaUsuario;
	private FachadaConfiguracion fachadaConfiguracion;
	private FachadaPrecio fachadaPrecio;
	
	private static final String MENSAJE_ERROR = "Ha ocurrido un error";

	@RequestMapping(value="/informarHoras", method = RequestMethod.POST)
	public String informarHoras(Model model,HttpServletRequest request, @RequestParam("id") final String idContrato) throws IOException{
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMYYYY");
			fachadaInforme = (FachadaInforme) context.getBean("fachadaInforme");
			HWPFDocument word = fachadaInforme.informarHoras(model, request, idContrato);
			JFileChooser js = new JFileChooser();
			js.setSelectedFile(new File(idContrato + simpleDateFormat.format(new Date()) + ".doc"));
			if(js.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
				OutputStream output = new FileOutputStream(js.getSelectedFile().getAbsolutePath());
				output.flush();
				word.write(output);
				output.close();
			}
		} catch (ClassNotFoundException | SQLException | ParseException e) {
			model.addAttribute("errorMessage", MENSAJE_ERROR);
			cargarTablaContratosInformarFacturar(model, request);
			e.printStackTrace();
		}finally{
			context.close();
		}
		return cargarTablaContratosInformarFacturar(model, request);
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
		System.out.println("INFORMAR FACTURACION");
		return cargarTablaContratosInformarFacturar(model, request);
	}

}
