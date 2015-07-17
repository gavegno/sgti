package com.proyectodegrado.sgti.consultas;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.proyectodegrado.sgti.conexionbd.Conexion;

public class ConsultasContrato {
	
	private Conexion conexionBD;
	
	public void insertarContrato (String id, int idCliente, String idContraparte) throws FileNotFoundException, IOException, SQLException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO contrato(id,cliente,contraparte) VALUES (?,?,?)");
		preparedStatement.setString(1, id);
		preparedStatement.setInt(2, idCliente);
		preparedStatement.setString(3, idContraparte);
		preparedStatement.executeUpdate();
		conexionBD.cerrar(con);
	}
	
	public ResultSet verContratos () throws FileNotFoundException, IOException, SQLException{
		Connection con = conexionBD.conectar();
		Statement statement = con.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM contrato");
		conexionBD.cerrar(con);
		return resultSet;
	}
	
	public ResultSet verContratosPorCliente (int idCliente) throws FileNotFoundException, IOException, SQLException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM contrato AS c WHERE c.cliente=?");
		preparedStatement.setInt(1, idCliente);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(con);
		return resultSet;
	}
	
	public void borrarContratos() throws FileNotFoundException, IOException, SQLException{
		Connection con = conexionBD.conectar();
		Statement statement = con.createStatement();
		statement.executeUpdate("DELETE FROM contrato");
		conexionBD.cerrar(con);
	}

	public Conexion getConexionBD() {
		return conexionBD;
	}

	public void setConexionBD(Conexion conexionBD) {
		this.conexionBD = conexionBD;
	}
}
