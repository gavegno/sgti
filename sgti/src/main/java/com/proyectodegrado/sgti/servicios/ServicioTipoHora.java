package com.proyectodegrado.sgti.servicios;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.proyectodegrado.sgti.Data.DataTipoHora;

public interface ServicioTipoHora {

	public abstract void agregar(DataTipoHora dataTipoHora) throws FileNotFoundException, IOException, SQLException;

	public abstract DataTipoHora seleccionarPorTipo(String tipo) throws FileNotFoundException, IOException, SQLException;

	public abstract List<DataTipoHora> seleccionarTipos() throws FileNotFoundException, IOException, SQLException;

}