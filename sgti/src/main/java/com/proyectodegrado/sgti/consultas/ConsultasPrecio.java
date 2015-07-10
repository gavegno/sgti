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

public class ConsultasPrecio {
	
	private Conexion conexionBD;
	
	public void insertarPrecio (double precio, Date fechadesde, Date fechahasta, String idContrato) throws FileNotFoundException, IOException, SQLException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO precio(precio,fechadesde,fechahasta,idcontrato) VALUES (?,?,?,?)");
		preparedStatement.setDouble(1, precio);
		preparedStatement.setDate(2, fechadesde);
		preparedStatement.setDate(3, fechahasta);
		preparedStatement.setString(4, idContrato);
		preparedStatement.executeUpdate();
		conexionBD.cerrar(con);
	}
	
	public ResultSet verPrecios (String idContrato) throws FileNotFoundException, IOException, SQLException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM precio AS p WHERE p.idcontrato=?");
		preparedStatement.setString(1, idContrato);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(con);
		return resultSet;
	}
	
	public ResultSet verPrecioActual (String idContrato) throws FileNotFoundException, IOException, SQLException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM precio AS p WHERE p.idcontrato=? AND ? BETWEEN p.fechadesde AND p.fechahasta ");
		preparedStatement.setString(1, idContrato);
		preparedStatement.setDate(2, new Date(new java.util.Date().getTime()));
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(con);
		return resultSet;
	}
	
	public ResultSet verPreciosPorFecha (String idContrato, Date fecha) throws FileNotFoundException, IOException, SQLException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM precio AS p WHERE p.idcontrato=? AND ? BETWEEN p.fechadesde AND p.fechahasta ");
		preparedStatement.setString(1, idContrato);
		preparedStatement.setDate(2, fecha);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(con);
		return resultSet;
	}
	
	public void borrarPrecios() throws FileNotFoundException, IOException, SQLException{
		Connection con = conexionBD.conectar();
		Statement statement = con.createStatement();
		statement.executeUpdate("DELETE FROM precio");
		conexionBD.cerrar(con);
	}

	public Conexion getConexionBD() {
		return conexionBD;
	}

	public void setConexionBD(Conexion conexionBD) {
		this.conexionBD = conexionBD;
	}
}
