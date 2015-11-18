package com.proyectodegrado.sgti.fachada;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.proyectodegrado.sgti.exceptions.SgtiException;
import com.proyectodegrado.sgti.modelo.Cliente;
import com.proyectodegrado.sgti.modelo.Contrato;
import com.proyectodegrado.sgti.servicios.ServicioCliente;
import com.proyectodegrado.sgti.servicios.ServicioContrato;
import com.proyectodegrado.sgti.servicios.ServicioUsuario;

public class FachadaContrato {
	
	private ServicioContrato servicioContrato;
	
	private ServicioUsuario servicioUsuario;
	
	private ServicioCliente servicioCliente;
	
	public void ingresarContrato(String id, String idContraparte, String nombreCliente) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, SgtiException{
		Contrato contrato = new Contrato();
		contrato.setId(id);
		contrato.setContraparte(servicioUsuario.selecionarUsuario(idContraparte));
		Cliente cliente = new Cliente();
		cliente.setNombre(nombreCliente);
		contrato.setCliente(servicioCliente.verPorNombre(cliente));
		servicioContrato.insertar(contrato);
	}
	
	public List<Contrato> verContratosPorCliente(int idCliente) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		return servicioContrato.verContratosPorCliente(idCliente);
	}
	
	public List<Contrato> verContratosPorContraparte(String idContraparte) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		return servicioContrato.verContratosPorContraparte(idContraparte);
	}
	
	public Contrato verContrato(String id) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		return servicioContrato.verContrato(id);
	}
	
	public List<Contrato> seleccionarContratos() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		return servicioContrato.seleccionarContratos();
	}
	
	public List<Contrato> seleccionarContratosVigentes() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		return servicioContrato.seleccionarContratosVigentes();
	}
	
	public Map<String, String> verProximaFechaInforme() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Map<String, String> contratosFechaInforme = new HashMap<String, String>();
		for(Contrato contrato : servicioContrato.seleccionarContratosVigentes()){
			contratosFechaInforme.put(contrato.getId(), dateFormat.format(servicioContrato.proximaFechaInforme(contrato.getId())));
		}
		return contratosFechaInforme;
	}
	
	public ServicioContrato getServicioContrato() {
		return servicioContrato;
	}

	public void setServicioContrato(ServicioContrato servicioContrato) {
		this.servicioContrato = servicioContrato;
	}

	public ServicioUsuario getServicioUsuario() {
		return servicioUsuario;
	}

	public void setServicioUsuario(ServicioUsuario servicioUsuario) {
		this.servicioUsuario = servicioUsuario;
	}

	public ServicioCliente getServicioCliente() {
		return servicioCliente;
	}

	public void setServicioCliente(ServicioCliente servicioCliente) {
		this.servicioCliente = servicioCliente;
	}
	
}
