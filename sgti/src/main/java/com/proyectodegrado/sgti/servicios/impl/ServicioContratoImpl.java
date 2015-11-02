package com.proyectodegrado.sgti.servicios.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.proyectodegrado.sgti.daos.ContratoDAO;
import com.proyectodegrado.sgti.exceptions.SgtiException;
import com.proyectodegrado.sgti.modelo.Cliente;
import com.proyectodegrado.sgti.modelo.Configuracion;
import com.proyectodegrado.sgti.modelo.Contrato;
import com.proyectodegrado.sgti.servicios.ServicioCliente;
import com.proyectodegrado.sgti.servicios.ServicioConfiguracion;
import com.proyectodegrado.sgti.servicios.ServicioContrato;
import com.proyectodegrado.sgti.servicios.ServicioContratoTipoHora;
import com.proyectodegrado.sgti.servicios.ServicioHorarioLaboral;
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
	public void insertar (Contrato contrato) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException, SgtiException{
		if(contratoDao.verContrato(contrato.getId()).getId() == null){
		contratoDao.insertarContrato(contrato.getId(), contrato.getCliente().getId(), contrato.getContraparte().getId());
		}else{
			throw new SgtiException("Ya existe un contrato con el mismo id");
		}
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioContrato#insertarCompleto(com.proyectodegrado.sgti.modelo.Contrato)
	 */
	@Override
	public void insertarCompleto (Contrato contrato) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException, SgtiException{
		servicioUsuarioContraparte.agregar(contrato.getContraparte());
		servicioCliente.agregar(contrato.getCliente());
		Cliente cliente = prepararCliente(contrato);
		contratoDao.insertarContrato(contrato.getId(), cliente.getId(), contrato.getContraparte().getId());
		servicioConfiguracion.insertar(contrato.getConfiguraciones().get(0), contrato.getId());
		servicioPrecio.insertar(contrato.getPrecio().get(0), contrato.getId());
		servicioContratoTipoHora.insertar(contrato.getId(), contrato.getTipoHoraComputo().get(0));
		
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioContrato#seleccionarContratos()
	 */
	@Override
	public List<Contrato> seleccionarContratos() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		return contratoDao.verContratos();
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioContrato#seleccionarContratosVigentes()
	 */
	@Override
	public List<Contrato> seleccionarContratosVigentes() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		List<Contrato> contratos = new ArrayList<Contrato>();
		for(Contrato contrato : contratoDao.verContratos()){
			if(servicioConfiguracion.seleccionarConfiguracionActual(contrato.getId()).getFechaInicio() != null){
				contratos.add(contrato);
			}
		}
		return contratos;
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioContrato#proximaFechaInforme(java.lang.String)
	 */
	@Override
	public Date proximaFechaInforme(String id) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		Contrato contrato = contratoDao.verContrato(id);
		Configuracion configuracion = servicioConfiguracion.seleccionarConfiguracionActual(id);
		int frecuenciaInforme = configuracion.getFrecuenciaInforme();
		Calendar calendar = Calendar.getInstance();
		if(contrato.getUltimaFechaInforme() != null){
			calendar.setTime(contrato.getUltimaFechaInforme());
		}else{
			calendar.setTime(configuracion.getFechaInicio());
		}
		calendar.add(Calendar.MONTH, frecuenciaInforme);
		return calendar.getTime();
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioContrato#proximaFechaFacturacion(java.lang.String)
	 */
	@Override
	public Date proximaFechaFacturacion(String id) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		Contrato contrato = contratoDao.verContrato(id);
		Configuracion configuracion = servicioConfiguracion.seleccionarConfiguracionActual(id);
		int frecuenciaFacturacion = configuracion.getFrecuenciaFacturacion();
		Calendar calendar = Calendar.getInstance();
		if(contrato.getUltimaFechaFacturacion() != null){
			calendar.setTime(contrato.getUltimaFechaFacturacion());
		}else{
			calendar.setTime(configuracion.getFechaInicio());
		}
		calendar.add(Calendar.MONTH, frecuenciaFacturacion);
		return calendar.getTime();
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioContrato#editar(com.proyectodegrado.sgti.modelo.Contrato)
	 */
	@Override
	public void editar(Contrato contrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		contratoDao.editarContrato(contrato.getId(), new java.sql.Date(contrato.getUltimaFechaInforme().getTime()), new java.sql.Date(contrato.getUltimaFechaFacturacion().getTime()), new java.sql.Date(contrato.getUltimaFechaComputacion().getTime()));
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
	
	public List<Contrato> verContratosPorCliente (int idCliente) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		return contratoDao.verContratosPorCliente(idCliente);
	}
	
	public List<Contrato> verContratosPorContraparte (String idContraparte) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		return contratoDao.verContratosPorContraparte(idContraparte);
	}
	
	@Override
	public Contrato verContrato (String id) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		return contratoDao.verContrato(id);
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
