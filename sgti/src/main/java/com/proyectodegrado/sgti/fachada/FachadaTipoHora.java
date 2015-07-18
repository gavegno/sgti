package com.proyectodegrado.sgti.fachada;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import com.proyectodegrado.sgti.modelo.TipoHora;
import com.proyectodegrado.sgti.servicios.ServicioTipoHora;

public class FachadaTipoHora {
	
	private ServicioTipoHora servicioTipoHora;
	
	public void insertarTipoHora(String tipo) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		TipoHora tipoHora = new TipoHora();
		tipoHora.setTipo(tipo);
		servicioTipoHora.agregar(tipoHora);
	}

	public ServicioTipoHora getServicioTipoHora() {
		return servicioTipoHora;
	}

	public void setServicioTipoHora(ServicioTipoHora servicioTipoHora) {
		this.servicioTipoHora = servicioTipoHora;
	}
}
