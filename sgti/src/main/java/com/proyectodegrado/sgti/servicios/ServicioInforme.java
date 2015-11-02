package com.proyectodegrado.sgti.servicios;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hwpf.HWPFDocument;

public interface ServicioInforme {

	public abstract HWPFDocument informarHoras(HttpServletRequest request,
			String idContrato) throws IOException, ClassNotFoundException,
			SQLException, ParseException;

	void validarInforme(String idContrato) throws FileNotFoundException,
			ClassNotFoundException, SQLException, IOException, ParseException;

}