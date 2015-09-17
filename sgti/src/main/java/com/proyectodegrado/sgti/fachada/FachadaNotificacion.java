package com.proyectodegrado.sgti.fachada;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import com.proyectodegrado.sgti.dto.DataNotificacion;
import com.proyectodegrado.sgti.modelo.Actividad;
import com.proyectodegrado.sgti.modelo.Contrato;
import com.proyectodegrado.sgti.servicios.ServicioNotificacion;

public class FachadaNotificacion {

	private ServicioNotificacion servicioNotificacion;
	
	public List<DataNotificacion> notificar(String tipoUsuario, String idUsuario, int dias) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException{
		return servicioNotificacion.notificar(tipoUsuario, idUsuario, dias);
	}
	
	public List<Actividad> actividadesARealizar(String tipoUsuario, String idUsuario, int dias) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException{
		return servicioNotificacion.verActividadesARealizar(idUsuario, dias);
	}
	
	public List<Contrato> contratosConConfiguracionesAVencer(int dias) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		return servicioNotificacion.verContratosConConfiguracionesAVencer(dias);
	}
	
	public List<Contrato> contratosConPreciosAVencer(int dias) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		return servicioNotificacion.verContratosConPreciosAVencer(dias);
	}
	
	public List<Contrato> contratosConHorasAInformar(int dias) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException{
		return servicioNotificacion.verContratosConHorasAInformar(dias);
	}
	
	public List<Contrato> contratosConHorasAFacturar(int dias) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException{
		return servicioNotificacion.verContratosConHorasAFacturar(dias);
	}
	
	public ServicioNotificacion getServicioNotificacion() {
		return servicioNotificacion;
	}

	public void setServicioNotificacion(ServicioNotificacion servicioNotificacion) {
		this.servicioNotificacion = servicioNotificacion;
	}
	
}
