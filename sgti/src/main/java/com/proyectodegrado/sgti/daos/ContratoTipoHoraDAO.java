package com.proyectodegrado.sgti.daos;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.proyectodegrado.sgti.consultas.ConsultasContratoTipoHora;
import com.proyectodegrado.sgti.modelo.TipoHora;
import com.proyectodegrado.sgti.modelo.TipoHoraComputo;

public class ContratoTipoHoraDAO {
	
	private ConsultasContratoTipoHora consultasContratoTipoHora;
	
	private TipoHoraDAO tipoHoraDao;
	
	public void insertarContratoTipoHora (String idContrato, int idTipoHora, int computos) throws FileNotFoundException, IOException, SQLException{
		consultasContratoTipoHora.insertarContratoTipoHora(idContrato, idTipoHora, computos);
	}
	
	public List<TipoHoraComputo> verContratoTipoHora (String idContrato) throws FileNotFoundException, IOException, SQLException{
		List<TipoHoraComputo> tiposHoraComputo = new ArrayList<TipoHoraComputo>();
		ResultSet resultSet = consultasContratoTipoHora.verContratoTiposHora(idContrato);
		while (resultSet.next()){
			TipoHora tipoHora = tipoHoraDao.seleccionarPorId(resultSet.getInt("idtipohora"));
			TipoHoraComputo tipoHoraComputo = new TipoHoraComputo();
			tipoHoraComputo.setTipoHora(tipoHora);
			tipoHoraComputo.setComputo(resultSet.getInt("computos"));
			tiposHoraComputo.add(tipoHoraComputo);
		}
		return tiposHoraComputo;
	}
	
	public void editarContratoTipoHora (String idContrato, int idTipoHora, int computos) throws FileNotFoundException, IOException, SQLException{
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
