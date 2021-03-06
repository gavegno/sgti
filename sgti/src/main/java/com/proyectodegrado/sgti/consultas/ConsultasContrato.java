package com.proyectodegrado.sgti.consultas;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.proyectodegrado.sgti.conexionbd.Conexion;

public class ConsultasContrato {
	
	private Conexion conexionBD;
	
	public void insertarContrato (String id, int idCliente, String idContraparte) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO contrato(id,cliente,contraparte) VALUES (?,?,?)");
		preparedStatement.setString(1, id);
		preparedStatement.setInt(2, idCliente);
		preparedStatement.setString(3, idContraparte);
		preparedStatement.executeUpdate();
		conexionBD.cerrar(con);
	}
	
	public ResultSet verContratos () throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		Statement statement = con.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM contrato");
		conexionBD.cerrar(con);
		return resultSet;
	}
	
	public ResultSet verContrato (String id) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM contrato AS c WHERE c.id=?");
		preparedStatement.setString(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(con);
		return resultSet;
	}
	
	public ResultSet verContratosPorCliente (int idCliente) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM contrato AS c WHERE c.cliente=?");
		preparedStatement.setInt(1, idCliente);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(con);
		return resultSet;
	}
	
	public ResultSet verContratosPorContraparte (String idContraparte) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM contrato AS c WHERE c.contraparte=?");
		preparedStatement.setString(1, idContraparte);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(con);
		return resultSet;
	}
	
	public void editar (String id, Date ultimafechaInforme, Date ultimaFechaFacturacion, Date ultimaFechaComputacion) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("UPDATE contrato AS c set ultimafechainforme=?, ultimafechafacturacion=?, ultimafechacomputacion=?"
				+ "WHERE c.id=?");
		preparedStatement.setDate(1, ultimafechaInforme);
		preparedStatement.setDate(2, ultimaFechaFacturacion);
		preparedStatement.setDate(3, ultimaFechaComputacion);
		preparedStatement.setString(4, id);
		preparedStatement.executeUpdate();
		conexionBD.cerrar(con);
	}
	
	public void borrarContratos() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
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
