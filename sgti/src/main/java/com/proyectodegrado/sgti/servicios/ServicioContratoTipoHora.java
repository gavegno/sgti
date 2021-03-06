package com.proyectodegrado.sgti.servicios;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.proyectodegrado.sgti.modelo.ContratoTipoHora;
import com.proyectodegrado.sgti.modelo.TipoHora;
import com.proyectodegrado.sgti.modelo.TipoHoraComputo;

public interface ServicioContratoTipoHora {

	public abstract void insertar(String idContrato,
			TipoHoraComputo tipoHoraComputo) throws FileNotFoundException,
			IOException, SQLException, ClassNotFoundException;

	public abstract List<TipoHoraComputo> verContratoTipoHora(String idContrato)
			throws FileNotFoundException, IOException, SQLException, ClassNotFoundException;

	public abstract void editarContratoTipoHora(String idContrato,
			TipoHoraComputo tipoHoraComputo) throws FileNotFoundException,
			IOException, SQLException, ClassNotFoundException;

	void borrarContratoTiposHora(String idContrato, int idTipoHora)
			throws FileNotFoundException, ClassNotFoundException, IOException,
			SQLException;

	List<ContratoTipoHora> verContratoTiposHoraParaGestionarHoras()
			throws FileNotFoundException, ClassNotFoundException, IOException,
			SQLException;

	List<ContratoTipoHora> verContratoTiposHoraParaGestionarHorasPorTecnico(
			String idUsuario) throws FileNotFoundException,
			ClassNotFoundException, IOException, SQLException;

	List<TipoHora> verTiposHoraPorTecnicoYContrato(String idUsuario,
			String idContrato) throws FileNotFoundException,
			ClassNotFoundException, IOException, SQLException;

}