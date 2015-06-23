package com.proyectodegrado.sgti.consultas;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.proyectodegrado.sgti.conexionbd.Conexion;

public class ConsultasTipoHora {
	
	private Conexion conexionBD;
	
	public void insertarTipoHora (String tipo) throws FileNotFoundException, IOException, SQLException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO tipohora(tipo) VALUES (?)");
		preparedStatement.setString(1, tipo);
		preparedStatement.executeUpdate();
		conexionBD.cerrar(con);
	}
	
	public ResultSet verTipoHora (String tipo) throws FileNotFoundException, IOException, SQLException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM tipohora AS t WHERE t.tipo=?");
		preparedStatement.setString(1, tipo);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(con);
		return resultSet;
	}
	
	public ResultSet verTiposHora () throws FileNotFoundException, IOException, SQLException{
		Connection con = conexionBD.conectar();
		Statement stmt = con.createStatement();
		ResultSet resultSet = stmt.executeQuery("SELECT * FROM tipohora");
		conexionBD.cerrar(con);
		return resultSet;
	}
	
	public void borrarTipoHora (String tipo) throws FileNotFoundException, IOException, SQLException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM tipohora AS t WHERE t.tipo =?");
		preparedStatement.setString(1, tipo);
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
