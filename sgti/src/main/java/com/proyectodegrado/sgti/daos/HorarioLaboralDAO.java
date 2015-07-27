package com.proyectodegrado.sgti.daos;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.proyectodegrado.sgti.consultas.ConsultasHorarioLaboral;
import com.proyectodegrado.sgti.modelo.Dia;
import com.proyectodegrado.sgti.modelo.HorarioLaboral;

public class HorarioLaboralDAO {
	
	private ConsultasHorarioLaboral consultasHorarioLaboral;
	
	public void agregar(String idHorarioLaboral, String nombreDia, String horaDesde, String horaHasta) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{

	consultasHorarioLaboral.insertarDiaAHorarioLaboral(idHorarioLaboral, nombreDia, horaDesde, horaHasta);
	
	}
	
	public HorarioLaboral seleccionarHorarioLaboral(String idHorarioLaboral) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		HorarioLaboral horarioLaboral= new HorarioLaboral(idHorarioLaboral);
		List<Dia> dias = new ArrayList<Dia>();
		ResultSet resultSet = consultasHorarioLaboral.verHorarioLaboral(idHorarioLaboral);
		while(resultSet.next()){
			Dia dia = new Dia();
			dia.setNombre(resultSet.getString("nombredia"));
			dia.setHoraDesde(resultSet.getString("horadesde"));
			dia.setHoraHasta(resultSet.getString("horahasta"));
			dias.add(dia);
		}
		horarioLaboral.setDias(dias);
		return horarioLaboral;
	}
	
	public List<HorarioLaboral> seleccionarHorariosLaborales() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		List<HorarioLaboral> horariosLaborales = new ArrayList<HorarioLaboral>();
		List<Dia> dias = new ArrayList<Dia>();
		List<String> HorariosAgregados = new ArrayList<String>();
		ResultSet resultSet = consultasHorarioLaboral.verHorariosLaborales();
		while(resultSet.next()){
			if(!HorariosAgregados.contains(resultSet.getString("id"))){
				HorarioLaboral horarioLaboral= new HorarioLaboral(resultSet.getString("id"));
				Dia dia = new Dia();
				dia.setNombre(resultSet.getString("nombredia"));
				dia.setHoraDesde(resultSet.getString("horadesde"));
				dia.setHoraHasta(resultSet.getString("horahasta"));
				dias.add(dia);
				horarioLaboral.setDias(dias);
				horariosLaborales.add(horarioLaboral);
				HorariosAgregados.add(resultSet.getString("id"));
			}
		}
		
		return horariosLaborales;
	}
	
	public void editarDiaDeHorarioLaboral(String idHorarioLaboral, String nombreDia, String horaDesde, String horaHasta) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{

		consultasHorarioLaboral.editarDiaDeHorarioLaboral(idHorarioLaboral, nombreDia, horaDesde, horaHasta);
		
	}
	
	public void borrarDiaDeHorarioLaboral(String idHorarioLaboral, String nombreDia) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{

		consultasHorarioLaboral.borrarDiaDeHorarioLaboral(idHorarioLaboral, nombreDia);
		
	}

	public ConsultasHorarioLaboral getConsultasHorarioLaboral() {
		return consultasHorarioLaboral;
	}

	public void setConsultasHorarioLaboral(
			ConsultasHorarioLaboral consltasHorarioLaboral) {
		this.consultasHorarioLaboral = consltasHorarioLaboral;
	}
}
