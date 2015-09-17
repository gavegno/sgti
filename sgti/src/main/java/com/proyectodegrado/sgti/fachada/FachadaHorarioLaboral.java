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
	
	public int insertarHorarioLaboralCompleto(String idHorarioLaboral, 
			String lunes, String lunesDesde, String lunesHasta,
			String martes, String martesDesde, String martesHasta,
			String miercoles, String miercolesDesde, String miercolesHasta,
			String jueves, String juevesDesde, String juevesHasta,
			String viernes, String viernesDesde, String viernesHasta,
			String sabado, String sabadoDesde, String sabadoHasta,
			String domingo, String domingoDesde, String domingoHasta
			) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		int cantidad = 0;
		
		if (chequearFormatoHora(lunesDesde, lunesHasta))
		{
			HorarioLaboral horarioLaboral = new HorarioLaboral(idHorarioLaboral);
			Dia dia = new Dia(lunes, lunesDesde, lunesHasta);
			servicioHorarioLaboral.insertarDíaAHorarioLaboral(horarioLaboral, dia);
			cantidad++;
		}
		
		if (chequearFormatoHora(martesDesde,martesHasta))
		{
			HorarioLaboral horarioLaboral = new HorarioLaboral(idHorarioLaboral);
			Dia dia = new Dia(martes, martesDesde, martesHasta);
			servicioHorarioLaboral.insertarDíaAHorarioLaboral(horarioLaboral, dia);
			cantidad++;
		}
		
		if (chequearFormatoHora(miercolesDesde,miercolesHasta))
		{
			HorarioLaboral horarioLaboral = new HorarioLaboral(idHorarioLaboral);
			Dia dia = new Dia(miercoles, miercolesDesde, miercolesHasta);
			servicioHorarioLaboral.insertarDíaAHorarioLaboral(horarioLaboral, dia);
			cantidad++;
		}
		
		if (chequearFormatoHora(juevesDesde, juevesHasta))
		{
			HorarioLaboral horarioLaboral = new HorarioLaboral(idHorarioLaboral);
			Dia dia = new Dia(jueves, juevesDesde, juevesHasta);
			servicioHorarioLaboral.insertarDíaAHorarioLaboral(horarioLaboral, dia);
			cantidad++;
		}
		
		if (chequearFormatoHora(viernesDesde,viernesHasta))
		{
			HorarioLaboral horarioLaboral = new HorarioLaboral(idHorarioLaboral);
			Dia dia = new Dia(viernes, viernesDesde, viernesHasta);
			servicioHorarioLaboral.insertarDíaAHorarioLaboral(horarioLaboral, dia);
			cantidad++;
		}
		
		if (chequearFormatoHora(sabadoDesde,sabadoHasta))
		{
			HorarioLaboral horarioLaboral = new HorarioLaboral(idHorarioLaboral);
			Dia dia = new Dia(sabado, sabadoDesde, sabadoHasta);
			servicioHorarioLaboral.insertarDíaAHorarioLaboral(horarioLaboral, dia);
			cantidad++;
		}
		
		if (chequearFormatoHora(domingoDesde,domingoHasta))
		{
			HorarioLaboral horarioLaboral = new HorarioLaboral(idHorarioLaboral);
			Dia dia = new Dia(domingo, domingoDesde, domingoHasta);
			servicioHorarioLaboral.insertarDíaAHorarioLaboral(horarioLaboral, dia);
			cantidad++;
		}
		
		return cantidad;
	}
	
	private boolean chequearFormatoHora(String horaDesde, String horaHasta)
	{
		boolean salida = false;
		int h1 = -1;
		int h2 = -1;
		int m1 = -1;
		int m2 = -1;
		if ((horaDesde.length() == 5) && (horaHasta.length() == 5))
		{
			h1 = Integer.valueOf(horaDesde.substring(0,2));
			m1 = Integer.valueOf(horaDesde.substring(3));
			h2 = Integer.valueOf(horaHasta.substring(0,2));
			m2 = Integer.valueOf(horaHasta.substring(3));
			
			if (
					(h1>=0 && h1<=23) && 
					(m1>=0 && m1<=59) && 
					(horaDesde.substring(2,3).matches(":")) &&
					(h2>=0 && h2<=23) && 
					(m2>=0 && m2<=59) &&
					(horaHasta.substring(2,3).matches(":")) &&
					(((h1<h2) && (m1<=m2)) || ((h1==h2) && (m1<m2)))
				)
				salida = true;
		}
		return salida;
	}
	
	public List<HorarioLaboral> verHorariosLaborales() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		return servicioHorarioLaboral.seleccionarHorariosLaborales();
	}
	
	public HorarioLaboral verDiasDeHorarioLaboral(String idHorarioLaboral) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		return servicioHorarioLaboral.seleccionarHorarioLaboral(idHorarioLaboral);
	}
	
	public void borrarHorarioLaboral(String idHorarioLaboral) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		servicioHorarioLaboral.borrarHorarioLaboral(idHorarioLaboral);
	}
	
	public boolean horarioLaboralEnUso(String idHorarioLaboral) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		return servicioHorarioLaboral.horarioLaboralEnUso(idHorarioLaboral);
	}

	public ServicioHorarioLaboral getServicioHorarioLaboral() {
		return servicioHorarioLaboral;
	}

	public void setServicioHorarioLaboral(
			ServicioHorarioLaboral servicioHorarioLaboral) {
		this.servicioHorarioLaboral = servicioHorarioLaboral;
	}
}
