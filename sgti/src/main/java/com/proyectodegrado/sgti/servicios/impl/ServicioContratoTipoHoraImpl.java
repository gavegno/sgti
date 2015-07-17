package com.proyectodegrado.sgti.servicios.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.proyectodegrado.sgti.daos.ContratoTipoHoraDAO;
import com.proyectodegrado.sgti.modelo.TipoHoraComputo;
import com.proyectodegrado.sgti.servicios.ServicioContratoTipoHora;
import com.proyectodegrado.sgti.servicios.ServicioTipoHora;

public class ServicioContratoTipoHoraImpl implements ServicioContratoTipoHora {
	
	private ContratoTipoHoraDAO contratoTipoHoraDao;
	
	private ServicioTipoHora servicioTipoHora;
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioContratoTipoHora#insertar(java.lang.String, com.proyectodegrado.sgti.modelo.TipoHoraComputo)
	 */
	@Override
	public void insertar(String idContrato, TipoHoraComputo tipoHoraComputo) throws FileNotFoundException, IOException, SQLException{
		contratoTipoHoraDao.insertarContratoTipoHora(idContrato, tipoHoraComputo.getTipoHora().getId(), tipoHoraComputo.getComputo());
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioContratoTipoHora#verContratoTipoHora(java.lang.String)
	 */
	@Override
	public List<TipoHoraComputo> verContratoTipoHora (String idContrato) throws FileNotFoundException, IOException, SQLException{
		return contratoTipoHoraDao.verContratoTipoHora(idContrato);
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioContratoTipoHora#editarContratoTipoHora(java.lang.String, com.proyectodegrado.sgti.modelo.TipoHoraComputo)
	 */
	@Override
	public void editarContratoTipoHora (String idContrato, TipoHoraComputo tipoHoraComputo) throws FileNotFoundException, IOException, SQLException{
		contratoTipoHoraDao.editarContratoTipoHora(idContrato, tipoHoraComputo.getTipoHora().getId(), tipoHoraComputo.getComputo());
	}

	public ContratoTipoHoraDAO getContratoTipoHoraDao() {
		return contratoTipoHoraDao;
	}

	public void setContratoTipoHoraDao(ContratoTipoHoraDAO contratoTipoHoraDao) {
		this.contratoTipoHoraDao = contratoTipoHoraDao;
	}
}
