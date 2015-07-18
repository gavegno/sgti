package com.proyectodegrado.sgti.servicios.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.proyectodegrado.sgti.daos.ContratoDAO;
import com.proyectodegrado.sgti.modelo.Cliente;
import com.proyectodegrado.sgti.modelo.Contrato;
import com.proyectodegrado.sgti.servicios.ServicioCliente;
import com.proyectodegrado.sgti.servicios.ServicioConfiguracion;
import com.proyectodegrado.sgti.servicios.ServicioContrato;
import com.proyectodegrado.sgti.servicios.ServicioContratoTipoHora;
import com.proyectodegrado.sgti.servicios.ServicioPrecio;

public class ServicioContratoImpl implements ServicioContrato {
	
	private ContratoDAO contratoDao;
	
	private ServicioConfiguracion servicioConfiguracion;
	
	private ServicioPrecio servicioPrecio;
	
	private ServicioContratoTipoHora servicioContratoTipoHora;
	
	private ServicioHorarioLaboral servicioHorarioLaboral;
	
	private ServicioCliente servicioCliente;
	
	private ServicioUsuarioContraparteImpl servicioUsuarioContraparte;
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioContrato#insertar(com.proyectodegrado.sgti.modelo.Contrato)
	 */
	@Override
	public void insertar (Contrato contrato) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		contratoDao.insertarContrato(contrato.getId(), contrato.getCliente().getId(), contrato.getContraparte().getId());
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioContrato#insertarCompleto(com.proyectodegrado.sgti.modelo.Contrato)
	 */
	@Override
	public void insertarCompleto (Contrato contrato) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		servicioUsuarioContraparte.agregar(contrato.getContraparte());
		servicioCliente.agregar(contrato.getCliente());
		Cliente cliente = prepararCliente(contrato);
		contratoDao.insertarContrato(contrato.getId(), cliente.getId(), contrato.getContraparte().getId());
		servicioConfiguracion.insertar(contrato.getConfiguraciones().get(0), contrato.getId());
		servicioPrecio.insertar(contrato.getPrecio().get(0), contrato.getId());
		servicioContratoTipoHora.insertar(contrato.getId(), contrato.getTipoHoraComputo().get(0));
		
	}

	private Cliente prepararCliente(Contrato contrato)
			throws FileNotFoundException, IOException, SQLException, ClassNotFoundException {
		Cliente cliente = contrato.getCliente();
		cliente.setId(servicioCliente.verPorNombre(cliente).getId());
		return cliente;
	}
	
	public List<Contrato> verContratos () throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		return contratoDao.verContratos();
	}

	public ContratoDAO getContratoDao() {
		return contratoDao;
	}

	public void setContratoDao(ContratoDAO contratoDao) {
		this.contratoDao = contratoDao;
	}

	public ServicioConfiguracion getServicioConfiguracion() {
		return servicioConfiguracion;
	}

	public void setServicioConfiguracion(ServicioConfiguracion servicioConfiguracion) {
		this.servicioConfiguracion = servicioConfiguracion;
	}

	public ServicioPrecio getServicioPrecio() {
		return servicioPrecio;
	}

	public void setServicioPrecio(ServicioPrecio servicioPrecio) {
		this.servicioPrecio = servicioPrecio;
	}

	public ServicioContratoTipoHora getServicioContratoTipoHora() {
		return servicioContratoTipoHora;
	}

	public void setServicioContratoTipoHora(
			ServicioContratoTipoHora servicioContratoTipoHora) {
		this.servicioContratoTipoHora = servicioContratoTipoHora;
	}

	public ServicioHorarioLaboral getServicioHorarioLaboral() {
		return servicioHorarioLaboral;
	}

	public void setServicioHorarioLaboral(
			ServicioHorarioLaboral servicioHorarioLaboral) {
		this.servicioHorarioLaboral = servicioHorarioLaboral;
	}

	public ServicioCliente getServicioCliente() {
		return servicioCliente;
	}

	public void setServicioCliente(ServicioCliente servicioCliente) {
		this.servicioCliente = servicioCliente;
	}

	public ServicioUsuarioContraparteImpl getServicioUsuarioContraparte() {
		return servicioUsuarioContraparte;
	}

	public void setServicioUsuarioContraparte(
			ServicioUsuarioContraparteImpl servicioUsuarioContraparte) {
		this.servicioUsuarioContraparte = servicioUsuarioContraparte;
	}
}
