package com.proyectodegrado.sgti.consultas;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.proyectodegrado.sgti.conexionbd.Conexion;

public class ConsultasUsuario {
	
	private Conexion conexionBD;
	
	public void insetarUsuario(String id, String nombre, String apellido, String contrasena, String email, String telefono, String tipo, boolean activo) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection connection = conexionBD.conectar();
		PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO usuario(id,nombre,apellido,contrasena,email,telefono,tipo,activo) VALUES (?,?,?,?,?,?,?,?)");
		preparedStatement.setString(1, id);
		preparedStatement.setString(2,nombre);
		preparedStatement.setString(3,apellido);
		preparedStatement.setString(4,contrasena);
		preparedStatement.setString(5,email);
		preparedStatement.setString(6,telefono);
		preparedStatement.setString(7,tipo);
		preparedStatement.setBoolean(8,activo);
		preparedStatement.executeUpdate();
		conexionBD.cerrar(connection);
		
	}
	
	public void insetarTipoHoraEnUsuario(int idTipoHora, String idUsuario) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection connection = conexionBD.conectar();
		PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO usuario_tipohora(id_tipohora,id_usuario) VALUES (?,?)");
		preparedStatement.setInt(1, idTipoHora);
		preparedStatement.setString(2,idUsuario);
		preparedStatement.executeUpdate();
		conexionBD.cerrar(connection);
		
	}
	
	public ResultSet verTiposHoraPorUsuario(String idUsuario) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection connection = conexionBD.conectar();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM usuario_tipohora AS u WHERE u.id_usuario=?");
		preparedStatement.setString(1, idUsuario);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(connection);
		return resultSet;
	}
	
	public ResultSet verUsuarios() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection connection = conexionBD.conectar();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM usuario AS u WHERE u.activo=true");
		conexionBD.cerrar(connection);
		return resultSet;
	}
	
	public ResultSet verUsuariosTodos() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection connection = conexionBD.conectar();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM usuario");
		conexionBD.cerrar(connection);
		return resultSet;
	}
	
	public ResultSet verUsuarioPorIdContrasena(String id, String contrasena) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection connection = conexionBD.conectar();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM usuario AS u WHERE u.id=? AND u.contrasena=? AND u.activo=true");
		preparedStatement.setString(1, id);
		preparedStatement.setString(2, contrasena);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(connection);
		return resultSet;
	}
	
	public ResultSet verUsuarioPorId(String id) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection connection = conexionBD.conectar();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM usuario AS u WHERE u.id=? AND u.activo=true");
		preparedStatement.setString(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(connection);
		return resultSet;
	}
	
	public ResultSet verUsuariosPorTipo(String tipo) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection connection = conexionBD.conectar();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM usuario AS u WHERE u.tipo=? AND u.activo=true");
		preparedStatement.setString(1, tipo);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(connection);
		return resultSet;
	}
	
	public void editarUsuario(String id, String nombre, String apellido, String email, String telefono) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection connection = conexionBD.conectar();
		PreparedStatement preparedStatement = connection.prepareStatement("UPDATE usuario AS u SET nombre=?, apellido=?, email=?, telefono=? WHERE u.id=?");
		preparedStatement.setString(1,nombre);
		preparedStatement.setString(2,apellido);
		preparedStatement.setString(3,email);
		preparedStatement.setString(4,telefono);
		preparedStatement.setString(5, id);
		preparedStatement.executeUpdate();
		conexionBD.cerrar(connection);
	}
	
	public void cambiarContrasena(String id, String contrasena) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection connection = conexionBD.conectar();
		PreparedStatement preparedStatement = connection.prepareStatement("UPDATE usuario AS u SET contrasena=? WHERE u.id=?");
		preparedStatement.setString(1, contrasena);
		preparedStatement.setString(2, id);
		preparedStatement.executeUpdate();
		conexionBD.cerrar(connection);
	}
	
	public void cambiarActivo(String id) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection connection = conexionBD.conectar();
		PreparedStatement preparedStatement = connection.prepareStatement("UPDATE usuario AS u SET activo = not activo WHERE u.id=?");
		//preparedStatement.setBoolean(1, activo);
		preparedStatement.setString(1, id);
		preparedStatement.executeUpdate();
		conexionBD.cerrar(connection);
	}
	
	public void borrarUsuario (String id) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM usuario AS u WHERE u.id =?");
		preparedStatement.setString(1, id);
		preparedStatement.executeUpdate();
		conexionBD.cerrar(con);
	}
	
	public void borrarTipoHoraDeUsuario (String idUsuario, int idTipoHora) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM usuario_tipohora AS u WHERE u.id_usuario =? AND u.id_tipohora =?");
		preparedStatement.setString(1, idUsuario);
		preparedStatement.setInt(2, idTipoHora);
		preparedStatement.executeUpdate();
		conexionBD.cerrar(con);
	}

	public Conexion getConexionBD() {
		return conexionBD;
	}

	public void setConexionBD(Conexion conexionBD) {
		this.conexionBD = conexionBD;
	}
	
	

}
