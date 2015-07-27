package com.proyectodegrado.sgti.servicios.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.proyectodegrado.sgti.modelo.Usuario;

public class ServicioUsuarioContraparteImpl extends ServicioUsuarioImpl{
	
	@Override
	public void agregar(Usuario dataUsuario) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException {
		dataUsuario.setTipo("CONTRAPARTE");
		super.agregar(dataUsuario);
	}
	
	@Override
	public List<Usuario> seleccionarUsuarios() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		return usuarioDao.seleccionarUsuariosPorTipo("CONTRAPARTE");
	} 

}
