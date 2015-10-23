package com.proyectodegrado.sgti.fachada;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.proyectodegrado.sgti.modelo.Contrato;
import com.proyectodegrado.sgti.modelo.Usuario;
import com.proyectodegrado.sgti.servicios.ServicioContratoTecnicos;

public class FachadaContratoTecnicos {
	
	private ServicioContratoTecnicos servicioContratoTecnicos;

	public void asignarTecnicoAContrato (String idUsuario, String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		servicioContratoTecnicos.asignarTecnicoAContrato(idUsuario, idContrato);
	}
	
	public List<Usuario> listarTecnicosPorContratoTodos(String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		return servicioContratoTecnicos.listarTecnicosPorContratoTodos(idContrato);
	}
	
	public List<Usuario> listarTecnicosCandidatosPorContrato(String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		return servicioContratoTecnicos.listarTecnicosCandidatosPorContrato(idContrato);
	}
	
	
	public List<Contrato> listarContratosPorTecnicoTodos(String idUsuario) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		return servicioContratoTecnicos.listarContratosPorTecnicoTodos(idUsuario);
	}
	
	public void eliminarTecnicoDeContrato (String idContrato, String idUsuario) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		servicioContratoTecnicos.eliminarTecnicoDeContrato(idUsuario, idContrato);
	}
	
	
	
	
	
	public ServicioContratoTecnicos getServicioContratoTecnicos() {
		return servicioContratoTecnicos;
	}

	public void setServicioContratoTecnicos(
			ServicioContratoTecnicos servicioContratoTecnicos) {
		this.servicioContratoTecnicos = servicioContratoTecnicos;
	}
	
	
	
}
