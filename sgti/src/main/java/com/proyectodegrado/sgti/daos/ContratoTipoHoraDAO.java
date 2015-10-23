package com.proyectodegrado.sgti.daos;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.proyectodegrado.sgti.consultas.ConsultasContratoTipoHora;
import com.proyectodegrado.sgti.modelo.ContratoTipoHora;
import com.proyectodegrado.sgti.modelo.TipoHora;
import com.proyectodegrado.sgti.modelo.TipoHoraComputo;

public class ContratoTipoHoraDAO {
	
	private ConsultasContratoTipoHora consultasContratoTipoHora;
	
	private TipoHoraDAO tipoHoraDao;
	
	public void insertarContratoTipoHora (String idContrato, int idTipoHora, double computos) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		consultasContratoTipoHora.insertarContratoTipoHora(idContrato, idTipoHora, computos);
	}
	
	public List<TipoHoraComputo> verContratoTipoHora (String idContrato) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		List<TipoHoraComputo> tiposHoraComputo = new ArrayList<TipoHoraComputo>();
		ResultSet resultSet = consultasContratoTipoHora.verContratoTiposHora(idContrato);
		while (resultSet.next()){
			TipoHora tipoHora = tipoHoraDao.seleccionarPorId(resultSet.getInt("idtipohora"));
			TipoHoraComputo tipoHoraComputo = new TipoHoraComputo();
			tipoHoraComputo.setTipoHora(tipoHora);
			tipoHoraComputo.setComputo(resultSet.getDouble("computos"));
			tiposHoraComputo.add(tipoHoraComputo);
		}
		return tiposHoraComputo;
	}
	
	public List<ContratoTipoHora> verContratoTiposHoraParaGestionarHoras() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		List<ContratoTipoHora> lista = new ArrayList<ContratoTipoHora>();
		ResultSet resultSet = consultasContratoTipoHora.verContratoTiposHoraParaGestionarHoras();
		while (resultSet.next()){
			ContratoTipoHora item = new ContratoTipoHora(resultSet.getInt("idtipohora"), 
					resultSet.getString("tipo"), resultSet.getString("idcontrato"));
			lista.add(item);
		}
		
		return lista;
		
	}
	
	public List<ContratoTipoHora> verContratoTiposHoraParaGestionarHorasPorTecnico(String idUsuario) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		List<ContratoTipoHora> lista = new ArrayList<ContratoTipoHora>();
		ResultSet resultSet = consultasContratoTipoHora.verContratoTiposHoraParaGestionarHorasPorTecnico(idUsuario);
		while (resultSet.next()){
			ContratoTipoHora item = new ContratoTipoHora(resultSet.getInt("idtipohora"), 
					resultSet.getString("tipo"), resultSet.getString("idcontrato"));
			lista.add(item);
		}
		
		return lista;
		
	}
		
	public void borrarContratoTiposHora (String idContrato, int idTipoHora) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		consultasContratoTipoHora.borrarContratoTiposHora(idContrato, idTipoHora);
	}
	
	public void editarContratoTipoHora (String idContrato, int idTipoHora, double computos) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		consultasContratoTipoHora.editarContratoTipoHora(idContrato, idTipoHora, computos);
	}

	public ConsultasContratoTipoHora getConsultasContratoTipoHora() {
		return consultasContratoTipoHora;
	}

	public void setConsultasContratoTipoHora(
			ConsultasContratoTipoHora consultasContratoTipoHora) {
		this.consultasContratoTipoHora = consultasContratoTipoHora;
	}

	public TipoHoraDAO getTipoHoraDao() {
		return tipoHoraDao;
	}

	public void setTipoHoraDao(TipoHoraDAO tipoHoraDao) {
		this.tipoHoraDao = tipoHoraDao;
	}
}
