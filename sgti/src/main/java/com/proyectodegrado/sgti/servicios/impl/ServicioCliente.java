package com.proyectodegrado.sgti.servicios.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.proyectodegrado.sgti.modelo.Cliente;

public interface ServicioCliente {

	public abstract void agregar(Cliente cliente) throws FileNotFoundException,
			IOException, SQLException;

	public abstract Cliente verPorNombre(Cliente cliente)
			throws FileNotFoundException, IOException, SQLException;

	public abstract List<Cliente> verClientes() throws FileNotFoundException,
			IOException, SQLException;

	public abstract void editarCliente(Cliente cliente)
			throws FileNotFoundException, IOException, SQLException;

	public abstract void eliminarCliente(Cliente cliente)
			throws FileNotFoundException, IOException, SQLException;

}