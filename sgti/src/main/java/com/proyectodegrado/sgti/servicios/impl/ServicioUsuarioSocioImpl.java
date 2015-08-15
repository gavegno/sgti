package com.proyectodegrado.sgti.servicios.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.proyectodegrado.sgti.exceptions.SgtiException;
import com.proyectodegrado.sgti.modelo.Usuario;

public class ServicioUsuarioSocioImpl extends ServicioUsuarioImpl{
	
	@Override
	public void agregar(Usuario dataUsuario) throws FileNotFoundException,IOException, SQLException, ClassNotFoundException, SgtiException {
		dataUsuario.setTipo("SOCIO");
		super.agregar(dataUsuario);
	}
	
	@Override
	public List<Usuario> seleccionarUsuarios() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		return usuarioDao.seleccionarUsuariosPorTipo("SOCIO");
	}

}
