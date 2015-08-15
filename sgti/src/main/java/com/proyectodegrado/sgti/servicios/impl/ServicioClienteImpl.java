package com.proyectodegrado.sgti.servicios.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.proyectodegrado.sgti.daos.ClienteDAO;
import com.proyectodegrado.sgti.exceptions.SgtiException;
import com.proyectodegrado.sgti.modelo.Cliente;
import com.proyectodegrado.sgti.servicios.ServicioCliente;

public class ServicioClienteImpl implements ServicioCliente {
	
	private ClienteDAO clienteDao;
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioCliente#agregar(com.proyectodegrado.sgti.modelo.Cliente)
	 */
	@Override
	public void agregar(Cliente cliente) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException, SgtiException{
		if(clienteDao.seleccionarPorNombre(cliente.getNombre()).getNombre() == null){
			clienteDao.agregar(cliente.getNombre(), cliente.getDireccion(), cliente.getTelefono(), true);
		}else{
			throw new SgtiException("El cliente ya existe en el sistema");
		}
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioCliente#verPorNombre(com.proyectodegrado.sgti.modelo.Cliente)
	 */
	@Override
	public Cliente verPorNombre(Cliente cliente) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		return clienteDao.seleccionarPorNombre(cliente.getNombre());
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioCliente#verClientes()
	 */
	@Override
	public List<Cliente> verClientes() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		return clienteDao.seleccionarClientes();
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioCliente#editarCliente(com.proyectodegrado.sgti.modelo.Cliente)
	 */
	@Override
	public void editarCliente(Cliente cliente) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		clienteDao.editarUsuario(cliente.getId(), cliente.getNombre(), cliente.getDireccion(), cliente.getTelefono());
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioCliente#eliminarCliente(com.proyectodegrado.sgti.modelo.Cliente)
	 */
	@Override
	public void eliminarCliente(Cliente cliente) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		clienteDao.cambiarActivo(cliente.getId(), false);
	}

	public ClienteDAO getClienteDao() {
		return clienteDao;
	}

	public void setClienteDao(ClienteDAO clienteDao) {
		this.clienteDao = clienteDao;
	}
}
