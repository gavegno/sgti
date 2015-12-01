package com.proyectodegrado.sgti.servicios;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.proyectodegrado.sgti.exceptions.SgtiException;
import com.proyectodegrado.sgti.modelo.TipoHora;

public interface ServicioTipoHora {

	public abstract void agregar(TipoHora dataTipoHora) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException, SgtiException;

	public abstract TipoHora seleccionarPorTipo(String tipo) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException;
	
	public abstract TipoHora seleccionarPorId(int id) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException;

	public abstract List<TipoHora> seleccionarTipos() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException;

	void editarTipoHora(int id, String tipo) throws FileNotFoundException,
			ClassNotFoundException, IOException, SQLException;

	void borrarTipoHora(int id) throws FileNotFoundException,
			ClassNotFoundException, IOException, SQLException;

	List<TipoHora> seleccionarTiposQueNoEsteUsandoElContrato(String idContrato)
			throws FileNotFoundException, IOException, SQLException,
			ClassNotFoundException, SgtiException;

	List<TipoHora> seleccionarTiposQueNoTengaElTecnico(String idUsuario)
			throws FileNotFoundException, IOException, SQLException,
			ClassNotFoundException, SgtiException;

	List<TipoHora> seleccionarTiposAsignadosAlTecnico(String idUsuario)
			throws FileNotFoundException, IOException, SQLException,
			ClassNotFoundException, SgtiException;

	void sacarTipoHoraATecnico(String idUsuario, int idTipoHora)
			throws FileNotFoundException, ClassNotFoundException, IOException,
			SQLException;

	List<TipoHora> verTiposHoraPorContrato(String idContrato)
			throws FileNotFoundException, ClassNotFoundException, IOException,
			SQLException;

}