package com.proyectodegrado.sgti.fachada;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.proyectodegrado.sgti.modelo.Dia;
import com.proyectodegrado.sgti.modelo.HorarioLaboral;
import com.proyectodegrado.sgti.servicios.ServicioHorarioLaboral;

public class FachadaHorarioLaboral {
	
	private ServicioHorarioLaboral servicioHorarioLaboral;
	
	public void insertarDiaHorarioLaboral(String id, String nombreDia, String horaDesde, String horaHasta) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		HorarioLaboral horarioLaboral = new HorarioLaboral(id);
		Dia dia = new Dia(nombreDia, horaDesde, horaHasta);
		servicioHorarioLaboral.insertarDíaAHorarioLaboral(horarioLaboral, dia);
	}
	
	public List<HorarioLaboral> verHorariosLaborales() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		return servicioHorarioLaboral.seleccionarHorariosLaborales();
	}

	public ServicioHorarioLaboral getServicioHorarioLaboral() {
		return servicioHorarioLaboral;
	}

	public void setServicioHorarioLaboral(
			ServicioHorarioLaboral servicioHorarioLaboral) {
		this.servicioHorarioLaboral = servicioHorarioLaboral;
	}
}
