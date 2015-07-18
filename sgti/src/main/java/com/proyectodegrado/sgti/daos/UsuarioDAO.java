package com.proyectodegrado.sgti.daos;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.proyectodegrado.sgti.consultas.ConsultasTipoHora;
import com.proyectodegrado.sgti.consultas.ConsultasUsuario;
import com.proyectodegrado.sgti.modelo.TipoHora;
import com.proyectodegrado.sgti.modelo.Usuario;

public class UsuarioDAO {
	
	private ConsultasUsuario consultasUsuario;
	
	private ConsultasTipoHora consultasTipoHora;
	
	public void agregar(String id, String nombre, String apellido, String contrasena, String email, String telefono, String tipo, boolean activo) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		consultasUsuario.insetarUsuario(id, nombre, apellido, contrasena, email, telefono, tipo, activo);
	}
	
	public void agregarTipoHoraUsuario(List<Integer> idTiposHora, String idUsuario ) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		for(int idTipoHora : idTiposHora){
			consultasUsuario.insetarTipoHoraEnUsuario(idTipoHora, idUsuario);
		}
	}
	
	public List<TipoHora> verTiposHoraPorUsuario(String idUsuario) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		List<TipoHora> tiposHora = new ArrayList<TipoHora>();
		ResultSet resultSet = consultasUsuario.verTiposHoraPorUsuario(idUsuario);
		while(resultSet.next()){
			TipoHora dataTipoHora = new TipoHora();
			dataTipoHora.setId(resultSet.getInt("id_tipohora"));
			ResultSet tipoHoraResultSet = consultasTipoHora.verTipoHoraPorId(resultSet.getInt("id_tipohora"));
			if(tipoHoraResultSet.next()){
				dataTipoHora.setTipo(tipoHoraResultSet.getString("tipo"));
			}
			tiposHora.add(dataTipoHora);
		}
		return tiposHora;
	}
	
	public Usuario seleccionarUsuarioPorIdContrasena(String id, String contrasena) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Usuario usuario= new Usuario();
		ResultSet resultSet = consultasUsuario.verUsuarioPorIdContrasena(id, contrasena);
		if(resultSet.next()){
			usuario.setId(resultSet.getString("id"));
			usuario.setNombre(resultSet.getString("nombre"));
			usuario.setApellido(resultSet.getString("apellido"));
			usuario.setEmail(resultSet.getString("email"));
			usuario.setTelefono(resultSet.getString("telefono"));
			usuario.setTipo(resultSet.getString("tipo"));
			usuario.setActivo(resultSet.getBoolean("activo"));
		}
		return usuario;
	}
	
	public Usuario seleccionarUsuarioPorId(String id) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Usuario usuario= new Usuario();
		ResultSet resultSet = consultasUsuario.verUsuarioPorId(id);
		if(resultSet.next()){
			usuario.setId(resultSet.getString("id"));
			usuario.setNombre(resultSet.getString("nombre"));
			usuario.setApellido(resultSet.getString("apellido"));
			usuario.setEmail(resultSet.getString("email"));
			usuario.setTelefono(resultSet.getString("telefono"));
			usuario.setTipo(resultSet.getString("tipo"));
			usuario.setActivo(resultSet.getBoolean("activo"));
		}
		return usuario;
	}
	
	public List<Usuario> seleccionarUsuarios() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		List<Usuario> usuarios = new ArrayList<Usuario>();
		ResultSet resultSet = consultasUsuario.verUsuarios();
		while(resultSet.next()){
			Usuario dataUsuario= new Usuario();
			dataUsuario.setId(resultSet.getString("id"));
			dataUsuario.setNombre(resultSet.getString("nombre"));
			dataUsuario.setApellido(resultSet.getString("apellido"));
			dataUsuario.setEmail(resultSet.getString("email"));
			dataUsuario.setTelefono(resultSet.getString("telefono"));
			dataUsuario.setTipo(resultSet.getString("tipo"));
			dataUsuario.setActivo(resultSet.getBoolean("activo"));
			usuarios.add(dataUsuario);
		}
		return usuarios;
	}
	
	public void editarUsuario(String id, String nombre, String apellido, String email, String telefono) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		consultasUsuario.editarUsuario(id, nombre, apellido, email, telefono);
	}
	
	public void cambiarContrasena(String id, String contrasena) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		consultasUsuario.cambiarContrasena(id, contrasena);
	}
	
	public void cambiarActivo(String id, boolean activo) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		consultasUsuario.cambiarActivo(id, activo);
	}
	
	public String verContrasenaUsuario(String id) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		ResultSet rs = consultasUsuario.verUsuarioPorId(id);
		if(rs.next()){
			return rs.getString("contrasena");
		}
		return "";
	}

	public ConsultasUsuario getConsultasUsuario() {
		return consultasUsuario;
	}

	public void setConsultasUsuario(ConsultasUsuario consultasUsuario) {
		this.consultasUsuario = consultasUsuario;
	}

	public ConsultasTipoHora getConsultasTipoHora() {
		return consultasTipoHora;
	}

	public void setConsultasTipoHora(ConsultasTipoHora consultasTipoHora) {
		this.consultasTipoHora = consultasTipoHora;
	}
	
}
