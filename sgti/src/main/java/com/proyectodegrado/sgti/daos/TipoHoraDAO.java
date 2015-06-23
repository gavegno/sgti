package com.proyectodegrado.sgti.daos;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.proyectodegrado.sgti.Data.DataTipoHora;
import com.proyectodegrado.sgti.consultas.ConsultasTipoHora;

public class TipoHoraDAO {
	
	private ConsultasTipoHora consultasTipoHora;

	
	public void agregar(DataTipoHora dataTipoHora) throws FileNotFoundException, IOException, SQLException{
		
		consultasTipoHora.insertarTipoHora(dataTipoHora.getTipo());
	}
	
	public DataTipoHora seleccionarPorTipo(String tipo) throws FileNotFoundException, IOException, SQLException{
		DataTipoHora dataTipoHora= new DataTipoHora();
		ResultSet rs = consultasTipoHora.verTipoHora(tipo);
		if(rs.next()){
			dataTipoHora.setId(rs.getInt("id"));
			dataTipoHora.setTipo(rs.getString("tipo"));
		}
		return dataTipoHora;
	}
	
	public List<DataTipoHora> seleccionarTipos() throws FileNotFoundException, IOException, SQLException{
		List<DataTipoHora> dataTiposHora= new ArrayList<DataTipoHora>();
		ResultSet rs = consultasTipoHora.verTiposHora();
		while(rs.next()){
			DataTipoHora dataTipoHora = new DataTipoHora();
			dataTipoHora.setId(rs.getInt("id"));
			dataTipoHora.setTipo(rs.getString("tipo"));
			dataTiposHora.add(dataTipoHora);
		}
		return dataTiposHora;
	}

	public ConsultasTipoHora getConsultasTipoHora() {
		return consultasTipoHora;
	}

	public void setConsultasTipoHora(ConsultasTipoHora consultasTipoHora) {
		this.consultasTipoHora = consultasTipoHora;
	}
	
	
}
