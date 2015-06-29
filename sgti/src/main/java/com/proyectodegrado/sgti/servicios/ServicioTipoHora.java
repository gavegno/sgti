package com.proyectodegrado.sgti.servicios;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.proyectodegrado.sgti.modelo.TipoHora;

public interface ServicioTipoHora {

	public abstract void agregar(TipoHora dataTipoHora) throws FileNotFoundException, IOException, SQLException;

	public abstract TipoHora seleccionarPorTipo(String tipo) throws FileNotFoundException, IOException, SQLException;

	public abstract List<TipoHora> seleccionarTipos() throws FileNotFoundException, IOException, SQLException;

}