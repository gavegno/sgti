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

	
	public void agregar(String tipo) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		
		consultasTipoHora.insertarTipoHora(tipo);
	}
	
	public TipoHora seleccionarPorTipo(String tipo) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		TipoHora tipoHora= new TipoHora();
		ResultSet resultSet = consultasTipoHora.verTipoHora(tipo);
		if(resultSet.next()){
			tipoHora.setId(resultSet.getInt("id"));
			tipoHora.setTipo(resultSet.getString("tipo"));
		}
		return tipoHora;
	}
	
	public TipoHora seleccionarPorId(int id) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		TipoHora tipoHora= new TipoHora();
		ResultSet resultSet = consultasTipoHora.verTipoHoraPorId(id);
		if(resultSet.next()){
			tipoHora.setId(resultSet.getInt("id"));
			tipoHora.setTipo(resultSet.getString("tipo"));
		}
		return tipoHora;
	}
	
	public List<TipoHora> seleccionarTipos() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
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
	
	public List<TipoHora> verTiposHoraQueContratoNoTengaEnUso (String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		List<TipoHora> tiposHora= new ArrayList<TipoHora>();
		ResultSet resultSet = consultasTipoHora.verTiposHoraQueContratoNoTengaEnUso(idContrato);
		while(resultSet.next()){
			TipoHora dataTipoHora = new TipoHora();
			dataTipoHora.setId(resultSet.getInt("id"));
			dataTipoHora.setTipo(resultSet.getString("tipo"));
			tiposHora.add(dataTipoHora);
		}
		return tiposHora;
	}
	
	public List<TipoHora> verTiposHoraQueTecnicoNoTengaAsignados (String idUsuario) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		List<TipoHora> tiposHora= new ArrayList<TipoHora>();
		ResultSet resultSet = consultasTipoHora.verTiposHoraQueTecnicoNoTengaAsignado(idUsuario);
		while(resultSet.next()){
			TipoHora dataTipoHora = new TipoHora();
			dataTipoHora.setId(resultSet.getInt("id"));
			dataTipoHora.setTipo(resultSet.getString("tipo"));
			tiposHora.add(dataTipoHora);
		}
		return tiposHora;
	}
	
	public List<TipoHora> verTiposHoraDeTecnico (String idUsuario) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		List<TipoHora> tiposHora= new ArrayList<TipoHora>();
		ResultSet resultSet = consultasTipoHora.verTiposHoraAsignadosATecnico(idUsuario);
		while(resultSet.next()){
			TipoHora dataTipoHora = new TipoHora();
			dataTipoHora.setId(resultSet.getInt("id"));
			dataTipoHora.setTipo(resultSet.getString("tipo"));
			tiposHora.add(dataTipoHora);
		}
		return tiposHora;
	}
	
	
	public List<TipoHora> verTiposHoraPorContrato(String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		List<TipoHora> tiposHora= new ArrayList<TipoHora>();
		ResultSet resultSet = consultasTipoHora.verTiposHoraPorContrato(idContrato);
		while(resultSet.next()){
			TipoHora dataTipoHora = new TipoHora();
			dataTipoHora.setId(resultSet.getInt("id"));
			dataTipoHora.setTipo(resultSet.getString("tipo"));
			tiposHora.add(dataTipoHora);
		}
		return tiposHora;
	}
	
	public void editarTipoHora (int id, String tipo) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		consultasTipoHora.editarTipoHora(id, tipo);
	}
	
	public void borrarTipoHora (int id) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		consultasTipoHora.borrarTipoHora(id);
	}
	
	
	public void sacarTipoHoraATecnico (String idUsuario, int idTipoHora) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		consultasTipoHora.sacarTipoHoraATecnico(idUsuario, idTipoHora);
	}

	public ConsultasTipoHora getConsultasTipoHora() {
		return consultasTipoHora;
	}

	public void setConsultasTipoHora(ConsultasTipoHora consultasTipoHora) {
		this.consultasTipoHora = consultasTipoHora;
	}
	
	
}
