package com.proyectodegrado.sgti.servicios.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.proyectodegrado.sgti.daos.ContratoTipoHoraDAO;
import com.proyectodegrado.sgti.modelo.ContratoTipoHora;
import com.proyectodegrado.sgti.modelo.TipoHoraComputo;
import com.proyectodegrado.sgti.servicios.ServicioContratoTipoHora;

public class ServicioContratoTipoHoraImpl implements ServicioContratoTipoHora {
	
	private ContratoTipoHoraDAO contratoTipoHoraDao;
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioContratoTipoHora#insertar(java.lang.String, com.proyectodegrado.sgti.modelo.TipoHoraComputo)
	 */
	@Override
	public void insertar(String idContrato, TipoHoraComputo tipoHoraComputo) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		contratoTipoHoraDao.insertarContratoTipoHora(idContrato, tipoHoraComputo.getTipoHora().getId(), tipoHoraComputo.getComputo());
	}
	
	@Override
	public void borrarContratoTiposHora (String idContrato, int idTipoHora) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		contratoTipoHoraDao.borrarContratoTiposHora(idContrato, idTipoHora);
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioContratoTipoHora#verContratoTipoHora(java.lang.String)
	 */
	@Override
	public List<TipoHoraComputo> verContratoTipoHora (String idContrato) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		return contratoTipoHoraDao.verContratoTipoHora(idContrato);
	}
	
	@Override
	public List<ContratoTipoHora> verContratoTiposHoraParaGestionarHoras() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		return contratoTipoHoraDao.verContratoTiposHoraParaGestionarHoras();
	}
	
	@Override
	public List<ContratoTipoHora> verContratoTiposHoraParaGestionarHorasPorTecnico(String idUsuario) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		return contratoTipoHoraDao.verContratoTiposHoraParaGestionarHorasPorTecnico(idUsuario);
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioContratoTipoHora#editarContratoTipoHora(java.lang.String, com.proyectodegrado.sgti.modelo.TipoHoraComputo)
	 */
	@Override
	public void editarContratoTipoHora (String idContrato, TipoHoraComputo tipoHoraComputo) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		contratoTipoHoraDao.editarContratoTipoHora(idContrato, tipoHoraComputo.getTipoHora().getId(), tipoHoraComputo.getComputo());
	}

	public ContratoTipoHoraDAO getContratoTipoHoraDao() {
		return contratoTipoHoraDao;
	}

	public void setContratoTipoHoraDao(ContratoTipoHoraDAO contratoTipoHoraDao) {
		this.contratoTipoHoraDao = contratoTipoHoraDao;
	}
}
