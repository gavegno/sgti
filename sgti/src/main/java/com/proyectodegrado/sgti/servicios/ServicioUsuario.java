package com.proyectodegrado.sgti.servicios;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.proyectodegrado.sgti.modelo.TipoHora;
import com.proyectodegrado.sgti.modelo.Usuario;

public interface ServicioUsuario {

	public abstract void agregar(Usuario dataUsuario)
			throws FileNotFoundException, IOException, SQLException, ClassNotFoundException;
	
	public void agregarTipoHoraUsuario(Usuario usuario) 
			throws FileNotFoundException, IOException, SQLException, ClassNotFoundException;
	
	public List<TipoHora> verTiposHoraPorUsuario(Usuario usuario) 
			throws FileNotFoundException, IOException, SQLException, ClassNotFoundException;
	
	public boolean existeUsuario(Usuario usuario) 
			throws FileNotFoundException, IOException, SQLException, ClassNotFoundException;

	public abstract Usuario selecionarUsuario(String id)
			throws FileNotFoundException, IOException, SQLException, ClassNotFoundException;

	public abstract List<Usuario> seleccionarUsuarios()
			throws FileNotFoundException, IOException, SQLException, ClassNotFoundException;

	public abstract void editarUsuario(Usuario dataUsuario)
			throws FileNotFoundException, IOException, SQLException, ClassNotFoundException;

	public abstract void cambiarContrasena(Usuario dataUsuario)
			throws FileNotFoundException, IOException, SQLException, ClassNotFoundException;

	public abstract void eliminarUsuario(Usuario dataUsuario)
			throws FileNotFoundException, IOException, SQLException, ClassNotFoundException;

}