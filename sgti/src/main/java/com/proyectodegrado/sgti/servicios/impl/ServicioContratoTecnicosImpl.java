package com.proyectodegrado.sgti.servicios.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.proyectodegrado.sgti.daos.ContratoTecnicosDAO;
import com.proyectodegrado.sgti.modelo.Contrato;
import com.proyectodegrado.sgti.modelo.Usuario;
import com.proyectodegrado.sgti.servicios.ServicioConfiguracion;
import com.proyectodegrado.sgti.servicios.ServicioContratoTecnicos;

public class ServicioContratoTecnicosImpl implements ServicioContratoTecnicos {
	
	private ContratoTecnicosDAO contratoTecnicosDao;
	private ServicioConfiguracion servicioConfiguracion;
	
	public ServicioConfiguracion getServicioConfiguracion() {
		return servicioConfiguracion;
	}

	public void setServicioConfiguracion(ServicioConfiguracion servicioConfiguracion) {
		this.servicioConfiguracion = servicioConfiguracion;
	}

	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioContratoTipoHora#insertar(java.lang.String, com.proyectodegrado.sgti.modelo.TipoHoraComputo)
	 */
	/*
	@Override
	public void insertar(String idContrato, TipoHoraComputo tipoHoraComputo) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		contratoTipoHoraDao.insertarContratoTipoHora(idContrato, tipoHoraComputo.getTipoHora().getId(), tipoHoraComputo.getComputo());
	}
	*/
	@Override
	public void asignarTecnicoAContrato (String idUsuario, String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		contratoTecnicosDao.asignarTecnicoAContrato(idUsuario, idContrato);
	}
	
	@Override
	public List<Usuario> listarTecnicosPorContratoTodos(String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		return contratoTecnicosDao.listarTecnicosPorContratoTodos(idContrato);
	}
	
	
	@Override
	public List<Usuario> listarTecnicosCandidatosPorContrato(String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		return contratoTecnicosDao.listarTecnicosCandidatosPorContrato(idContrato);
	}
	
	/*
	@Override
	public List<Usuario> listarTecnicosPorContratoActivos(String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		return contratoTecnicosDao.listarTecnicosPorContratoActivos(idContrato);
	}
	
	@Override
	public List<Contrato> listarContratosPorTecnicoActivo(String idUsuario) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		return contratoTecnicosDao.listarContratosPorTecnicoActivo(idUsuario);
	}
	*/
	@Override
	public List<Contrato> listarContratosPorTecnicoTodos(String idUsuario) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		List<Contrato> contratos = new ArrayList<Contrato>();
		for(Contrato contrato : contratoTecnicosDao.listarContratosPorTecnicoTodos(idUsuario)){
			if(servicioConfiguracion.seleccionarConfiguracionActual(contrato.getId()).getFechaInicio() != null){
				contratos.add(contrato);
			}
		}
		return contratos;
	}
	
	@Override
	public void eliminarTecnicoDeContrato (String idContrato, String idUsuario) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		contratoTecnicosDao.eliminarTecnicoDeContrato(idUsuario, idContrato);
	}
	
	
	
	
	
	
	
	
	
	
	
	



	public ContratoTecnicosDAO getContratoTecnicosDao() {
		return contratoTecnicosDao;
	}



	public void setContratoTecnicosDao(ContratoTecnicosDAO contratoTecnicosDao) {
		this.contratoTecnicosDao = contratoTecnicosDao;
	}



}
