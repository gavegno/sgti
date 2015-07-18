package com.proyectodegrado.sgti.servicios;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

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

}