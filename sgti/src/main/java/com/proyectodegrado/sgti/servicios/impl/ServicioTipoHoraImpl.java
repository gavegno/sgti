package com.proyectodegrado.sgti.servicios.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.proyectodegrado.sgti.daos.TipoHoraDAO;
import com.proyectodegrado.sgti.modelo.TipoHora;
import com.proyectodegrado.sgti.servicios.ServicioTipoHora;

public class ServicioTipoHoraImpl implements ServicioTipoHora {
	
	private TipoHoraDAO tipoHoraDao;
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.ServicioTipoHora#agregar(com.proyectodegrado.sgti.Data.DataTipoHora)
	 */
	@Override
	public void agregar(TipoHora dataTipoHora) throws FileNotFoundException, IOException, SQLException{
		tipoHoraDao.agregar(dataTipoHora.getTipo());
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.ServicioTipoHora#seleccionarPorTipo(java.lang.String)
	 */
	@Override
	public TipoHora seleccionarPorTipo(String tipo) throws FileNotFoundException, IOException, SQLException{
		return tipoHoraDao.seleccionarPorTipo(tipo);
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.ServicioTipoHora#seleccionarTipos()
	 */
	@Override
	public List<TipoHora> seleccionarTipos() throws FileNotFoundException, IOException, SQLException{
		return tipoHoraDao.seleccionarTipos();
	}

	public TipoHoraDAO getTipoHoraDao() {
		return tipoHoraDao;
	}

	public void setTipoHoraDao(TipoHoraDAO tipoHoraDao) {
		this.tipoHoraDao = tipoHoraDao;
	}
	
	

}
