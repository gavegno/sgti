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
	
	@Override
	public List<TipoHora> seleccionarTiposQueNoEsteUsandoElContrato(String idContrato) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException, SgtiException{
		List<TipoHora> lista = tipoHoraDao.verTiposHoraQueContratoNoTengaEnUso(idContrato);
		if (lista.size() > 0)
			return lista;
		else
			throw new SgtiException("Ya no existen más tipos de hora para asignar a este contrato");
	}
	
	@Override
	public List<TipoHora> seleccionarTiposQueNoTengaElTecnico(String idUsuario) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException, SgtiException{
		List<TipoHora> lista = tipoHoraDao.verTiposHoraQueTecnicoNoTengaAsignados(idUsuario);
		return lista;
	}
	
	@Override
	public List<TipoHora> seleccionarTiposAsignadosAlTecnico(String idUsuario) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException, SgtiException{
		List<TipoHora> lista = tipoHoraDao.verTiposHoraDeTecnico(idUsuario);
		return lista;
	}
	
	@Override
	public void sacarTipoHoraATecnico (String idUsuario, int idTipoHora) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		tipoHoraDao.sacarTipoHoraATecnico(idUsuario, idTipoHora);
	}
	
	@Override
	public void editarTipoHora (int id, String tipo) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		tipoHoraDao.editarTipoHora(id, tipo);
	}
	
	@Override
	public void borrarTipoHora (int id) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		tipoHoraDao.borrarTipoHora(id);
	}

	public TipoHoraDAO getTipoHoraDao() {
		return tipoHoraDao;
	}

	public void setTipoHoraDao(TipoHoraDAO tipoHoraDao) {
		this.tipoHoraDao = tipoHoraDao;
	}
	
	

}
