package com.proyectodegrado.sgti.daos;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.proyectodegrado.sgti.consultas.ConsultasCliente;
import com.proyectodegrado.sgti.modelo.Cliente;

public class ClienteDAO {
	
	private ConsultasCliente consultasCliente;
	
	public void agregar(String nombre, String direccion, String telefono, boolean activo) throws FileNotFoundException, IOException, SQLException{
		consultasCliente.insertarCliente(nombre, direccion, telefono, activo);
	}
	
	public Cliente seleccionarPorNombre(String nombre) throws FileNotFoundException, IOException, SQLException{
		Cliente cliente = new Cliente();
		ResultSet resultSet = consultasCliente.verClientePorNombre(nombre);
		if(resultSet.next()){
			cliente.setId(resultSet.getInt("id"));
			cliente.setNombre(resultSet.getString("nombre"));
			cliente.setDireccion(resultSet.getString("direccion"));
			cliente.setTelefono(resultSet.getString("telefono"));
			cliente.setActivo(resultSet.getBoolean("activo"));
		}
		return cliente;
	}
	
	public Cliente seleccionarPorId(int id) throws FileNotFoundException, IOException, SQLException{
		Cliente cliente = new Cliente();
		ResultSet resultSet = consultasCliente.verClientePorId(id);
		if(resultSet.next()){
			cliente.setId(resultSet.getInt("id"));
			cliente.setNombre(resultSet.getString("nombre"));
			cliente.setDireccion(resultSet.getString("direccion"));
			cliente.setTelefono(resultSet.getString("telefono"));
			cliente.setActivo(resultSet.getBoolean("activo"));
		}
		return cliente;
	}
	
	public List<Cliente> seleccionarClientes() throws FileNotFoundException, IOException, SQLException{
		List<Cliente> clientes = new ArrayList<Cliente>();
		ResultSet resultSet = consultasCliente.verClientes();
		while(resultSet.next()){
			Cliente cliente = new Cliente();
			cliente.setId(resultSet.getInt("id"));
			cliente.setNombre(resultSet.getString("nombre"));
			cliente.setDireccion(resultSet.getString("direccion"));
			cliente.setTelefono(resultSet.getString("telefono"));
			cliente.setActivo(resultSet.getBoolean("activo"));
			clientes.add(cliente);
		}
		return clientes;
	}
	
	public void cambiarActivo(int id, boolean activo) throws FileNotFoundException, IOException, SQLException{
		consultasCliente.cambiarActivo(id, activo);
	}
	
	public void editarUsuario(int id, String nombre, String direccion, String telefono) throws FileNotFoundException, IOException, SQLException{
		consultasCliente.editarCliente(id, nombre, direccion, telefono);
	}

	public ConsultasCliente getConsultasCliente() {
		return consultasCliente;
	}

	public void setConsultasCliente(ConsultasCliente consultasCliente) {
		this.consultasCliente = consultasCliente;
	}
}
