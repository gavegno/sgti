package com.proyectodegrado.sgti.servicios;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.proyectodegrado.sgti.modelo.Cliente;

public interface ServicioCliente {

	public abstract void agregar(Cliente cliente) throws FileNotFoundException,
			IOException, SQLException, ClassNotFoundException;

	public abstract Cliente verPorNombre(Cliente cliente)
			throws FileNotFoundException, IOException, SQLException, ClassNotFoundException;

	public abstract List<Cliente> verClientes() throws FileNotFoundException,
			IOException, SQLException, ClassNotFoundException;

	public abstract void editarCliente(Cliente cliente)
			throws FileNotFoundException, IOException, SQLException, ClassNotFoundException;

	public abstract void eliminarCliente(Cliente cliente)
			throws FileNotFoundException, IOException, SQLException, ClassNotFoundException;

}