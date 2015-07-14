package com.proyectodegrado.sgti.daos;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.proyectodegrado.sgti.consultas.ConsultasTipoHora;
import com.proyectodegrado.sgti.modelo.TipoHora;

public class TipoHoraDAO {
	
	private ConsultasTipoHora consultasTipoHora;

	
	public void agregar(String tipo) throws FileNotFoundException, IOException, SQLException{
		
		consultasTipoHora.insertarTipoHora(tipo);
	}
	
	public TipoHora seleccionarPorTipo(String tipo) throws FileNotFoundException, IOException, SQLException{
		TipoHora tipoHora= new TipoHora();
		ResultSet resultSet = consultasTipoHora.verTipoHora(tipo);
		if(resultSet.next()){
			tipoHora.setId(resultSet.getInt("id"));
			tipoHora.setTipo(resultSet.getString("tipo"));
		}
		return tipoHora;
	}
	
	public TipoHora seleccionarPorId(int id) throws FileNotFoundException, IOException, SQLException{
		TipoHora tipoHora= new TipoHora();
		ResultSet resultSet = consultasTipoHora.verTipoHoraPorId(id);
		if(resultSet.next()){
			tipoHora.setId(resultSet.getInt("id"));
			tipoHora.setTipo(resultSet.getString("tipo"));
		}
		return tipoHora;
	}
	
	public List<TipoHora> seleccionarTipos() throws FileNotFoundException, IOException, SQLException{
		List<TipoHora> tiposHora= new ArrayList<TipoHora>();
		ResultSet resultSet = consultasTipoHora.verTiposHora();
		while(resultSet.next()){
			TipoHora dataTipoHora = new TipoHora();
			dataTipoHora.setId(resultSet.getInt("id"));
			dataTipoHora.setTipo(resultSet.getString("tipo"));
			tiposHora.add(dataTipoHora);
		}
		return tiposHora;
	}

	public ConsultasTipoHora getConsultasTipoHora() {
		return consultasTipoHora;
	}

	public void setConsultasTipoHora(ConsultasTipoHora consultasTipoHora) {
		this.consultasTipoHora = consultasTipoHora;
	}
	
	
}
