package com.proyectodegrado.sgti.fachada;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import com.proyectodegrado.sgti.modelo.Cliente;
import com.proyectodegrado.sgti.modelo.Contrato;
import com.proyectodegrado.sgti.servicios.ServicioCliente;
import com.proyectodegrado.sgti.servicios.ServicioContrato;
import com.proyectodegrado.sgti.servicios.ServicioUsuario;

public class FachadaContrato {
	
	private ServicioContrato servicioContrato;
	
	private ServicioUsuario servicioUsuario;
	
	private ServicioCliente servicioCliente;
	
	public void ingresarContrato(String id, String idContraparte, String nombreCliente) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		Contrato contrato = new Contrato();
		contrato.setId(id);
		contrato.setContraparte(servicioUsuario.selecionarUsuario(idContraparte));
		Cliente cliente = new Cliente();
		cliente.setNombre(nombreCliente);
		contrato.setCliente(servicioCliente.verPorNombre(cliente));
		servicioContrato.insertar(contrato);
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
