package com.proyectodegrado.sgti.daos;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.proyectodegrado.sgti.consultas.ConsultasContrato;
import com.proyectodegrado.sgti.modelo.Contrato;

public class ContratoDAO {
	
	private ConsultasContrato consultasContrato;
	
	private ConfiguracionDAO configuracionDao;
	
	private HorarioLaboralDAO horarioLaboralDao;
	
	private PrecioDAO precioDao;
	
	private ContratoTipoHoraDAO contratoTipoHoraDao;
	
	private ClienteDAO clienteDao;
	
	private UsuarioDAO usuarioDao;
	
	public void insertarContrato (String id, int idCliente, String idContraparte) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		consultasContrato.insertarContrato(id, idCliente, idContraparte);
	}
	
	public List<Contrato> verContratos () throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		List<Contrato> contratos = new ArrayList<Contrato>();
		ResultSet resultSet = consultasContrato.verContratos();
		while(resultSet.next()){
			Contrato contrato = new Contrato();
			contrato.setId(resultSet.getString("id"));
			contrato.setConfiguraciones(configuracionDao.verConfiguracionesPorContrato(contrato.getId()));
			contrato.setPrecio(precioDao.verPrecios(contrato.getId()));
			contrato.setTipoHoraComputo(contratoTipoHoraDao.verContratoTipoHora(contrato.getId()));
			contrato.setCliente(clienteDao.seleccionarPorId(resultSet.getInt("cliente")));
			contrato.setContraparte(usuarioDao.seleccionarUsuarioPorId(resultSet.getString("contraparte")));
			contratos.add(contrato);
		}
		return contratos;
	}
	
	public List<Contrato> verContratosPorCliente (int idCliente) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		List<Contrato> contratos = new ArrayList<Contrato>();
		ResultSet resultSet = consultasContrato.verContratosPorCliente(idCliente);
		while(resultSet.next()){
			Contrato contrato = new Contrato();
			contrato.setId(resultSet.getString("id"));
			contrato.setConfiguraciones(configuracionDao.verConfiguracionesPorContrato(contrato.getId()));
			contrato.setPrecio(precioDao.verPrecios(contrato.getId()));
			contrato.setTipoHoraComputo(contratoTipoHoraDao.verContratoTipoHora(contrato.getId()));
			contrato.setCliente(clienteDao.seleccionarPorId(resultSet.getInt("cliente")));
			contrato.setContraparte(usuarioDao.seleccionarUsuarioPorId(resultSet.getString("contraparte")));
			contratos.add(contrato);
		}
		return contratos;
	}
	public Contrato verContrato (String id) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Contrato contrato = new Contrato();
		ResultSet resultSet = consultasContrato.verContrato(id);
		if(resultSet.next()){
			contrato.setId(resultSet.getString("id"));
			contrato.setConfiguraciones(configuracionDao.verConfiguracionesPorContrato(contrato.getId()));
			contrato.setPrecio(precioDao.verPrecios(contrato.getId()));
			contrato.setTipoHoraComputo(contratoTipoHoraDao.verContratoTipoHora(contrato.getId()));
			contrato.setCliente(clienteDao.seleccionarPorId(resultSet.getInt("cliente")));
			contrato.setContraparte(usuarioDao.seleccionarUsuarioPorId(resultSet.getString("contraparte")));
			contrato.setUltimaFechaInforme(resultSet.getDate("ultimafechainforme"));
			contrato.setUltimaFechaFacturacion(resultSet.getDate("ultimafechaFacturacion"));
			contrato.setUltimaFechaComputacion(resultSet.getDate("ultimafechaComputacion"));
		}
		return contrato;
	}
	
	public void editarContrato (String id, Date ultimafechaInforme, Date ultimaFechaFacturacion, Date ultimaFechaComputacion) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		consultasContrato.editar(id, ultimafechaInforme, ultimaFechaFacturacion, ultimaFechaComputacion);
	}
	
	public List<Contrato> verContratosPorContraparte (String idContraparte) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		List<Contrato> contratos = new ArrayList<Contrato>();
		ResultSet resultSet = consultasContrato.verContratosPorContraparte(idContraparte);
		while(resultSet.next()){
			Contrato contrato = new Contrato();
			contrato.setId(resultSet.getString("id"));
			contrato.setConfiguraciones(configuracionDao.verConfiguracionesPorContrato(contrato.getId()));
			contrato.setPrecio(precioDao.verPrecios(contrato.getId()));
			contrato.setTipoHoraComputo(contratoTipoHoraDao.verContratoTipoHora(contrato.getId()));
			contrato.setCliente(clienteDao.seleccionarPorId(resultSet.getInt("cliente")));
			contrato.setContraparte(usuarioDao.seleccionarUsuarioPorId(resultSet.getString("contraparte")));
			contratos.add(contrato);
		}
		return contratos;
	}

	public ConsultasContrato getConsultasContrato() {
		return consultasContrato;
	}

	public void setConsultasContrato(ConsultasContrato consultasContrato) {
		this.consultasContrato = consultasContrato;
	}

	public ConfiguracionDAO getConfiguracionDao() {
		return configuracionDao;
	}

	public void setConfiguracionDao(ConfiguracionDAO configuracionDao) {
		this.configuracionDao = configuracionDao;
	}

	public HorarioLaboralDAO getHorarioLaboralDao() {
		return horarioLaboralDao;
	}

	public void setHorarioLaboralDao(HorarioLaboralDAO horarioLaboralDao) {
		this.horarioLaboralDao = horarioLaboralDao;
	}

	public PrecioDAO getPrecioDao() {
		return precioDao;
	}

	public void setPrecioDao(PrecioDAO precioDao) {
		this.precioDao = precioDao;
	}

	public ContratoTipoHoraDAO getContratoTipoHoraDao() {
		return contratoTipoHoraDao;
	}

	public void setContratoTipoHoraDao(ContratoTipoHoraDAO contratoTipoHoraDao) {
		this.contratoTipoHoraDao = contratoTipoHoraDao;
	}

	public ClienteDAO getClienteDao() {
		return clienteDao;
	}

	public void setClienteDao(ClienteDAO clienteDao) {
		this.clienteDao = clienteDao;
	}

	public UsuarioDAO getUsuarioDao() {
		return usuarioDao;
	}

	public void setUsuarioDao(UsuarioDAO usuarioDao) {
		this.usuarioDao = usuarioDao;
	}
}
