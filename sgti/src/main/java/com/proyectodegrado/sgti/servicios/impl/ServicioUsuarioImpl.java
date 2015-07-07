package com.proyectodegrado.sgti.servicios.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.proyectodegrado.sgti.daos.UsuarioDAO;
import com.proyectodegrado.sgti.modelo.TipoHora;
import com.proyectodegrado.sgti.modelo.Usuario;
import com.proyectodegrado.sgti.servicios.ServicioUsuario;

public class ServicioUsuarioImpl implements ServicioUsuario {
	
	private UsuarioDAO usuarioDao;
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioUsuario#agregar(com.proyectodegrado.sgti.Data.DataUsuario)
	 */
	@Override
	public void agregar(Usuario usuario) throws FileNotFoundException, IOException, SQLException{
		usuarioDao.agregar(usuario.getId(), usuario.getNombre(), usuario.getApellido(), usuario.getContrasena(), usuario.getEmail(), usuario.getTelefono(), usuario.getTipo(), true);
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioUsuario#agregarTipoHoraUsuario(com.proyectodegrado.sgti.Data.DataUsuario)
	 */
	@Override
	public void agregarTipoHoraUsuario(Usuario usuario) throws FileNotFoundException, IOException, SQLException{
		List<Integer> tipoHoraids = new ArrayList<Integer>();
		for(TipoHora tipoHora: usuario.getUsuarioTipoHora()){
			tipoHoraids.add(tipoHora.getId());
		}
		usuarioDao.agregarTipoHoraUsuario(tipoHoraids, usuario.getId());
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioUsuario#verTiposHoraPorUsuario(com.proyectodegrado.sgti.Data.DataUsuario)
	 */
	@Override
	public List<TipoHora> verTiposHoraPorUsuario(Usuario usuario) throws FileNotFoundException, IOException, SQLException{
		return usuarioDao.verTiposHoraPorUsuario(usuario.getId());
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioUsuario#selecionarUsuario(java.lang.String)
	 */
	@Override
	public Usuario selecionarUsuario(String id) throws FileNotFoundException, IOException, SQLException{
		return usuarioDao.seleccionarUsuarioPorId(id);
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioUsuario#seleccionarUsuarios()
	 */
	@Override
	public List<Usuario> seleccionarUsuarios() throws FileNotFoundException, IOException, SQLException{
		return usuarioDao.seleccionarUsuarios();
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioUsuario#editarUsuario(com.proyectodegrado.sgti.Data.DataUsuario)
	 */
	@Override
	public void editarUsuario(Usuario usuario) throws FileNotFoundException, IOException, SQLException{
		usuarioDao.editarUsuario(usuario.getId(), usuario.getNombre(), usuario.getApellido(), usuario.getEmail(), usuario.getTelefono());
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioUsuario#cambiarContrasena(com.proyectodegrado.sgti.Data.DataUsuario)
	 */
	@Override
	public void cambiarContrasena(Usuario usuario) throws FileNotFoundException, IOException, SQLException{
		usuarioDao.cambiarContraseña(usuario.getId(), usuario.getContrasena());
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioUsuario#eliminarUsuario(com.proyectodegrado.sgti.Data.DataUsuario)
	 */
	@Override
	public void eliminarUsuario(Usuario usuario) throws FileNotFoundException, IOException, SQLException{
		usuarioDao.cambiarActivo(usuario.getId(), false);
	}

	public UsuarioDAO getUsuarioDao() {
		return usuarioDao;
	}

	public void setUsuarioDao(UsuarioDAO usuarioDao) {
		this.usuarioDao = usuarioDao;
	}
	
	

}
