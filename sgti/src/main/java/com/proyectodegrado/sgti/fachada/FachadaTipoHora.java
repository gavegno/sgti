package com.proyectodegrado.sgti.fachada;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.proyectodegrado.sgti.modelo.TipoHora;
import com.proyectodegrado.sgti.modelo.TipoHoraComputo;
import com.proyectodegrado.sgti.servicios.ServicioContratoTipoHora;
import com.proyectodegrado.sgti.servicios.ServicioTipoHora;

public class FachadaTipoHora {
	
	private ServicioTipoHora servicioTipoHora;
	
	private ServicioContratoTipoHora servicioContratoTipoHora;
	
	public void insertarTipoHora(String tipo) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		TipoHora tipoHora = new TipoHora();
		tipoHora.setTipo(tipo);
		servicioTipoHora.agregar(tipoHora);
	}
	
	public void insertarContratoTipoHora(String idContrato, String nombreTipoHora, int computo) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		TipoHora tipoHora = servicioTipoHora.seleccionarPorTipo(nombreTipoHora);
		TipoHoraComputo tipoHoraComputo = new TipoHoraComputo(tipoHora, computo);
		servicioContratoTipoHora.insertar(idContrato, tipoHoraComputo);
	}
	
	public List<String> verTiposDeHora() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		List<String> nombreTipos = new ArrayList<String>();
		for(TipoHora tipoHora : servicioTipoHora.seleccionarTipos()){
			nombreTipos.add(tipoHora.getTipo());
		}
		return nombreTipos;
	}

	public ServicioTipoHora getServicioTipoHora() {
		return servicioTipoHora;
	}

	public void setServicioTipoHora(ServicioTipoHora servicioTipoHora) {
		this.servicioTipoHora = servicioTipoHora;
	}

	public ServicioContratoTipoHora getServicioContratoTipoHora() {
		return servicioContratoTipoHora;
	}

	public void setServicioContratoTipoHora(
			ServicioContratoTipoHora servicioContratoTipoHora) {
		this.servicioContratoTipoHora = servicioContratoTipoHora;
	}
}
