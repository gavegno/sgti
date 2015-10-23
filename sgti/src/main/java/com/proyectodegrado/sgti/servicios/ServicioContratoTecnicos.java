package com.proyectodegrado.sgti.servicios;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.proyectodegrado.sgti.modelo.Contrato;
import com.proyectodegrado.sgti.modelo.Usuario;

public interface ServicioContratoTecnicos {

	void asignarTecnicoAContrato(String idUsuario, String idContrato)
			throws FileNotFoundException, ClassNotFoundException, IOException,
			SQLException;

	List<Usuario> listarTecnicosPorContratoTodos(String idContrato)
			throws FileNotFoundException, ClassNotFoundException, IOException,
			SQLException;


	List<Contrato> listarContratosPorTecnicoTodos(String idUsuario)
			throws FileNotFoundException, ClassNotFoundException, IOException,
			SQLException;


	void eliminarTecnicoDeContrato(String idContrato, String idUsuario)
			throws FileNotFoundException, ClassNotFoundException, IOException,
			SQLException;

	List<Usuario> listarTecnicosCandidatosPorContrato(String idContrato)
			throws FileNotFoundException, ClassNotFoundException, IOException,
			SQLException;

	

}