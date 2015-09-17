package com.proyectodegrado.sgti.servicios.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.proyectodegrado.sgti.daos.UsuarioDAO;
import com.proyectodegrado.sgti.exceptions.SgtiException;
import com.proyectodegrado.sgti.modelo.TipoHora;
import com.proyectodegrado.sgti.modelo.Usuario;
import com.proyectodegrado.sgti.servicios.ServicioUsuario;

public class ServicioUsuarioImpl implements ServicioUsuario {
	
	protected UsuarioDAO usuarioDao;
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioUsuario#agregar(com.proyectodegrado.sgti.Data.DataUsuario)
	 */
	@Override
	public void agregar(Usuario usuario) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException, SgtiException{
		if(usuarioDao.seleccionarUsuarioPorId(usuario.getId()).getId() == null){
			usuarioDao.agregar(usuario.getId(), usuario.getNombre(), usuario.getApellido(), usuario.getContrasena(), usuario.getEmail(), usuario.getTelefono(), usuario.getTipo(), true);
		}else{
			throw new SgtiException("El usuario ingresado ya existe");
		}
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioUsuario#agregarTipoHoraUsuario(com.proyectodegrado.sgti.Data.DataUsuario)
	 */
	@Override
	public void agregarTipoHoraUsuario(Usuario usuario) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
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
	public List<TipoHora> verTiposHoraPorUsuario(Usuario usuario) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		return usuarioDao.verTiposHoraPorUsuario(usuario.getId());
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioUsuario#existeUsuario(com.proyectodegrado.sgti.Data.DataUsuario)
	 */
	@Override
	public boolean existeUsuario(Usuario usuario) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		return usuarioDao.seleccionarUsuarioPorIdContrasena(usuario.getId(), usuario.getContrasena()) != null;
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioUsuario#selecionarUsuario(java.lang.String)
	 */
	@Override
	public Usuario selecionarUsuario(String id) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		return usuarioDao.seleccionarUsuarioPorId(id);
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioUsuario#seleccionarUsuarios()
	 */
	@Override
	public List<Usuario> seleccionarUsuarios() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		return usuarioDao.seleccionarUsuarios();
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioUsuario#editarUsuario(com.proyectodegrado.sgti.Data.DataUsuario)
	 */
	@Override
	public void editarUsuario(Usuario usuario) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		usuarioDao.editarUsuario(usuario.getId(), usuario.getNombre(), usuario.getApellido(), usuario.getEmail(), usuario.getTelefono());
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioUsuario#cambiarContrasena(com.proyectodegrado.sgti.Data.DataUsuario)
	 */
	@Override
	public void cambiarContrasena(Usuario usuario) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		usuarioDao.cambiarContrasena(usuario.getId(), usuario.getContrasena());
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioUsuario#eliminarUsuario(com.proyectodegrado.sgti.Data.DataUsuario)
	 */
	@Override
	public void eliminarUsuario(Usuario usuario) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		usuarioDao.cambiarActivo(usuario.getId(), false);
	}
	
	@Override
	public String get_MD5_SecurePassword(String passwordToHash) throws NoSuchAlgorithmException
    {
		String generatedPassword = null;
		// Create MessageDigest instance for MD5
        MessageDigest md = MessageDigest.getInstance("MD5");
        //Add password bytes to digest
        md.update(passwordToHash.getBytes());
        //Get the hash's bytes
        byte[] bytes = md.digest();
        //This bytes[] has bytes in decimal format;
        //Convert it to hexadecimal format
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< bytes.length ;i++)
        {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        //Get complete hashed password in hex format
        generatedPassword = sb.toString();
        
        return generatedPassword;
    }
	
	

	public UsuarioDAO getUsuarioDao() {
		return usuarioDao;
	}

	public void setUsuarioDao(UsuarioDAO usuarioDao) {
		this.usuarioDao = usuarioDao;
	}
	
	

}
