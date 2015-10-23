package com.proyectodegrado.sgti.daos;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.proyectodegrado.sgti.consultas.ConsultasContratoUsuario;
import com.proyectodegrado.sgti.modelo.Contrato;
import com.proyectodegrado.sgti.modelo.Usuario;

public class ContratoTecnicosDAO {
	
	private ConsultasContratoUsuario consultasContratoUsuario;
	private ConfiguracionDAO configuracionDao;
	
	private HorarioLaboralDAO horarioLaboralDao;
	
	private PrecioDAO precioDao;
	
	private ContratoTipoHoraDAO contratoTipoHoraDao;
	
	private ClienteDAO clienteDao;
	
	private UsuarioDAO usuarioDao;
	
	private ContratoDAO contratoDao;
	
	public void asignarTecnicoAContrato (String idUsuario, String idContrato) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		consultasContratoUsuario.asignarTecnicoAContrato(idUsuario, idContrato);
	}
	
	
	public List<Usuario> listarTecnicosPorContratoTodos(String idContrato) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		List<Usuario> usuarios = new ArrayList<Usuario>();
		ResultSet resultSet = consultasContratoUsuario.listarTecnicosPorContratoTodos(idContrato);

		while(resultSet.next()){
			Usuario usuario = new Usuario();
			usuario = usuarioDao.seleccionarUsuarioPorId(resultSet.getString("idusuario"));
			usuarios.add(usuario);
		}
		return usuarios;
	}
	
	
	public List<Usuario> listarTecnicosCandidatosPorContrato(String idContrato) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		List<Usuario> usuarios = new ArrayList<Usuario>();
		ResultSet resultSet = consultasContratoUsuario.listarTecnicosCandidatosPorContrato(idContrato);

		while(resultSet.next()){
			Usuario usuario = new Usuario();
			usuario = usuarioDao.seleccionarUsuarioPorId(resultSet.getString("id"));
			usuarios.add(usuario);
		}
		return usuarios;
	}
	
	public List<Contrato> listarContratosPorTecnicoTodos(String idUsuario) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		List<Contrato> contratos = new ArrayList<Contrato>();
		ResultSet resultSet = consultasContratoUsuario.listarContratosPorTecnicoTodos(idUsuario);
		while(resultSet.next()){
			Contrato contrato = new Contrato();
			contrato = contratoDao.verContrato(resultSet.getString("idcontrato"));
			contratos.add(contrato);
		}
		return contratos;
	}
	
	
	public void eliminarTecnicoDeContrato (String idContrato, String idUsuario) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		consultasContratoUsuario.eliminarTecnicoDeContrato(idContrato, idUsuario);
	}
	
	

	public ConsultasContratoUsuario getConsultasContratoUsuario() {
		return consultasContratoUsuario;
	}


	public void setConsultasContratoUsuario(
			ConsultasContratoUsuario consultasContratoUsuario) {
		this.consultasContratoUsuario = consultasContratoUsuario;
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


	public ContratoDAO getContratoDao() {
		return contratoDao;
	}


	public void setContratoDao(ContratoDAO contratoDao) {
		this.contratoDao = contratoDao;
	}
	
	
	
}
