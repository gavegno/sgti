package com.proyectodegrado.sgti.consultas;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.proyectodegrado.sgti.conexionbd.Conexion;

public class ConsultasCliente {
	
	private Conexion conexionBD;
	
	public void insertarCliente(String nombre, String direccion, String telefono, boolean activo) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO cliente(nombre,direccion,telefono, activo) VALUES (?,?,?,?)");
		preparedStatement.setString(1, nombre);
		preparedStatement.setString(2, direccion);
		preparedStatement.setString(3, telefono);
		preparedStatement.setBoolean(4, activo);
		preparedStatement.executeUpdate();
		conexionBD.cerrar(con);
	}
	
	public ResultSet verClientePorNombre (String nombre) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM cliente AS c WHERE c.nombre=?");
		preparedStatement.setString(1, nombre);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(con);
		return resultSet;
	}
	
	public ResultSet verClientePorId (int id) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM cliente AS c WHERE c.id=?");
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(con);
		return resultSet;
	}
	
	public ResultSet verClientes () throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		Statement stmt = con.createStatement();
		ResultSet resultSet = stmt.executeQuery("SELECT * FROM cliente");
		conexionBD.cerrar(con);
		return resultSet;
	}
	
	public void cambiarActivo(int id, boolean activo) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("UPDATE cliente AS c SET activo=? WHERE c.id=?");
		preparedStatement.setBoolean(1, activo);
		preparedStatement.setInt(2, id);
		preparedStatement.executeUpdate();
		conexionBD.cerrar(con);
	}
	
	public void editarCliente(int id, String nombre, String direccion, String telefono) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection connection = conexionBD.conectar();
		PreparedStatement preparedStatement = connection.prepareStatement("UPDATE cliente AS c SET nombre=?, direccion=?, telefono=? WHERE c.id=?");
		preparedStatement.setString(1,nombre);
		preparedStatement.setString(2,direccion);
		preparedStatement.setString(3,telefono);
		preparedStatement.setInt(4,id);
		preparedStatement.executeUpdate();
		conexionBD.cerrar(connection);
	}
	
	public void borrarCliente (String nombre) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM cliente AS c WHERE c.nombre =?");
		preparedStatement.setString(1, nombre);
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
