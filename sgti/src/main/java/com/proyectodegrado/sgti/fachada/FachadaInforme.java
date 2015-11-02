package com.proyectodegrado.sgti.fachada;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hwpf.HWPFDocument;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyectodegrado.sgti.servicios.ServicioInforme;

public class FachadaInforme {

	private ServicioInforme servicioInforme;
	
	public HWPFDocument informarHoras(Model model, HttpServletRequest request, @RequestParam("id") final String idContrato) throws IOException,
			ClassNotFoundException, SQLException, ParseException {
	
		return servicioInforme.informarHoras(request, idContrato);
	}
	
	public void validarInforme(String idContrato) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException, ParseException{
		servicioInforme.validarInforme(idContrato);
	}

	public ServicioInforme getServicioInforme() {
		return servicioInforme;
	}

	public void setServicioInforme(ServicioInforme servicioInforme) {
		this.servicioInforme = servicioInforme;
	}
	
}
