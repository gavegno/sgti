package com.proyectodegrado.sgti.fachada;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.proyectodegrado.sgti.exceptions.SgtiException;
import com.proyectodegrado.sgti.modelo.Cliente;
import com.proyectodegrado.sgti.servicios.ServicioCliente;

public class FachadaCliente {
	
	private ServicioCliente servicioCliente;
	
	public void insertarCliente(String nombre, String direccion, String telefono) 
			throws FileNotFoundException, IOException, SQLException, ClassNotFoundException, SgtiException{
		Cliente cliente = new Cliente();
		cliente.setNombre(nombre);
		cliente.setDireccion(direccion);
		cliente.setTelefono(telefono);
		servicioCliente.agregar(cliente);
	}
	
	public Cliente verClientePorNombre(String nombre) 
			throws FileNotFoundException, IOException, SQLException, ClassNotFoundException	
	{
		Cliente cliente = new Cliente();
		cliente.setNombre(nombre);
		return servicioCliente.verPorNombre(cliente);
	}
	
	public List<Cliente> verClientes() 
			throws FileNotFoundException, IOException, SQLException, ClassNotFoundException
	
	{
		return servicioCliente.verClientes();
		
	}
	
	public void editarCliente(String nombre, String direccion, String telefono) 
			throws FileNotFoundException, IOException, SQLException, ClassNotFoundException
	{
		Cliente cliente = verClientePorNombre(nombre);
		cliente.setNombre(nombre);
		cliente.setDireccion(direccion);
		cliente.setTelefono(telefono);
		servicioCliente.editarCliente(cliente);
	}
	
	
	public void eliminarCliente(String nombre) 
			throws FileNotFoundException, IOException, SQLException, ClassNotFoundException
	
	{
		Cliente clienteABorrar = verClientePorNombre(nombre);
		servicioCliente.eliminarCliente(clienteABorrar);
	}
	
	
	
	
	
	
	public ServicioCliente getServicioCliente()
	{
		return servicioCliente;
	}
	
	public void setServicioCliente(ServicioCliente servicioCliente)
	{
		this.servicioCliente = servicioCliente;
	}

}
