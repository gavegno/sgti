package com.proyectodegrado.sgti.servicios.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.proyectodegrado.sgti.daos.TipoHoraDAO;
import com.proyectodegrado.sgti.exceptions.SgtiException;
import com.proyectodegrado.sgti.modelo.TipoHora;
import com.proyectodegrado.sgti.servicios.ServicioTipoHora;

public class ServicioTipoHoraImpl implements ServicioTipoHora {
	
	private TipoHoraDAO tipoHoraDao;
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.ServicioTipoHora#agregar(com.proyectodegrado.sgti.Data.DataTipoHora)
	 */
	@Override
	public void agregar(TipoHora dataTipoHora) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException, SgtiException{
		if(tipoHoraDao.seleccionarPorTipo(dataTipoHora.getTipo()).getTipo() == null){
			tipoHoraDao.agregar(dataTipoHora.getTipo());
		}else{
			throw new SgtiException("El tipo de hora ingresado ya existe");
		}
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.ServicioTipoHora#seleccionarPorTipo(java.lang.String)
	 */
	@Override
	public TipoHora seleccionarPorTipo(String tipo) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		return tipoHoraDao.seleccionarPorTipo(tipo);
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.ServicioTipoHora#seleccionarPorId(int)
	 */
	@Override
	public TipoHora seleccionarPorId(int id) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		return tipoHoraDao.seleccionarPorId(id);
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.ServicioTipoHora#seleccionarTipos()
	 */
	@Override
	public List<TipoHora> seleccionarTipos() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		return tipoHoraDao.seleccionarTipos();
	}

	public TipoHoraDAO getTipoHoraDao() {
		return tipoHoraDao;
	}

	public void setTipoHoraDao(TipoHoraDAO tipoHoraDao) {
		this.tipoHoraDao = tipoHoraDao;
	}
	
	

}
