package com.proyectodegrado.sgti.servicios.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import com.proyectodegrado.sgti.modelo.Usuario;

public class ServicioUsuarioTecnicoImpl extends ServicioUsuarioImpl{
	
	@Override
	public void agregar(Usuario dataUsuario) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException {
		dataUsuario.setTipo("TECNICO");
		super.agregar(dataUsuario);
	}

}
