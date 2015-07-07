package com.proyectodegrado.sgti.servicios.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.proyectodegrado.sgti.daos.ClienteDAO;
import com.proyectodegrado.sgti.modelo.Cliente;

public class ServicioClienteImpl implements ServicioCliente {
	
	private ClienteDAO clienteDao;
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioCliente#agregar(com.proyectodegrado.sgti.modelo.Cliente)
	 */
	@Override
	public void agregar(Cliente cliente) throws FileNotFoundException, IOException, SQLException{
		clienteDao.agregar(cliente.getNombre(), cliente.getDireccion(), cliente.getTelefono(), true);
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioCliente#verPorNombre(com.proyectodegrado.sgti.modelo.Cliente)
	 */
	@Override
	public Cliente verPorNombre(Cliente cliente) throws FileNotFoundException, IOException, SQLException{
		return clienteDao.seleccionarPorNombre(cliente.getNombre());
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioCliente#verClientes()
	 */
	@Override
	public List<Cliente> verClientes() throws FileNotFoundException, IOException, SQLException{
		return clienteDao.seleccionarClientes();
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioCliente#editarCliente(com.proyectodegrado.sgti.modelo.Cliente)
	 */
	@Override
	public void editarCliente(Cliente cliente) throws FileNotFoundException, IOException, SQLException{
		clienteDao.editarUsuario(cliente.getId(), cliente.getNombre(), cliente.getDireccion(), cliente.getTelefono());
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioCliente#eliminarCliente(com.proyectodegrado.sgti.modelo.Cliente)
	 */
	@Override
	public void eliminarCliente(Cliente cliente) throws FileNotFoundException, IOException, SQLException{
		clienteDao.cambiarActivo(cliente.getId(), false);
	}

	public ClienteDAO getClienteDao() {
		return clienteDao;
	}

	public void setClienteDao(ClienteDAO clienteDao) {
		this.clienteDao = clienteDao;
	}
}
