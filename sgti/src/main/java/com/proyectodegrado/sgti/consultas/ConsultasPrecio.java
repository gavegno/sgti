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
	
	public void insertarPrecio (double precio, double precioExtra, Date fechadesde, Date fechahasta, String idContrato) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO precio(precio,precioExtra,fechadesde,fechahasta,idcontrato) VALUES (?,?,?,?,?)");
		preparedStatement.setDouble(1, precio);
		preparedStatement.setDouble(2, precioExtra);
		preparedStatement.setDate(3, fechadesde);
		preparedStatement.setDate(4, fechahasta);
		preparedStatement.setString(5, idContrato);
		preparedStatement.executeUpdate();
		conexionBD.cerrar(con);
	}
	
	public ResultSet verPrecios (String idContrato) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM precio AS p WHERE p.idcontrato=? ORDER BY p.fechadesde DESC");
		preparedStatement.setString(1, idContrato);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(con);
		return resultSet;
	}
	
	public ResultSet saberSiPuedoInsertarNuevoPrecio (String idContrato, double precio, Date fechaFuturaInicio, Date fechaFuturaFin) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM precio AS p "+
					"WHERE p.idcontrato=? AND (" +
											"(? BETWEEN p.fechadesde AND p.fechahasta) OR "+ 
											"(? BETWEEN p.fechadesde AND p.fechahasta) OR "+
											"((p.fechadesde > ?) AND p.fechahasta < ?)); "); 
							
		preparedStatement.setString(1, idContrato);
		preparedStatement.setDate(2, fechaFuturaInicio);
		preparedStatement.setDate(3, fechaFuturaFin);
		preparedStatement.setDate(4, fechaFuturaInicio);
		preparedStatement.setDate(5, fechaFuturaFin);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(con);
		return resultSet;
	}
	
	public ResultSet saberSiPuedoEditarPrecio (String idContrato, double precio, Date fechadesdeActual, Date fechahastaActual, Date fechaFuturaInicio, Date fechaFuturaFin) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM precio AS p "+
					"WHERE p.idcontrato=? AND (" +
											"(? BETWEEN p.fechadesde AND p.fechahasta) OR "+ 
											"(? BETWEEN p.fechadesde AND p.fechahasta) OR "+
											"((p.fechadesde > ?) AND p.fechahasta < ?) "+ 
							") AND NOT (p.fechadesde= ? AND p.fechahasta= ? AND p.precio=?);");
		preparedStatement.setString(1, idContrato);
		preparedStatement.setDate(2, fechaFuturaInicio);
		preparedStatement.setDate(3, fechaFuturaFin);
		preparedStatement.setDate(4, fechaFuturaInicio);
		preparedStatement.setDate(5, fechaFuturaFin);
		preparedStatement.setDate(6, fechadesdeActual);
		preparedStatement.setDate(7, fechahastaActual);
		preparedStatement.setDouble(8, precio);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(con);
		return resultSet;
	}
	
	public ResultSet verPrecioActual (String idContrato) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM precio AS p WHERE p.idcontrato=? AND ? BETWEEN p.fechadesde AND p.fechahasta ");
		preparedStatement.setString(1, idContrato);
		preparedStatement.setDate(2, new Date(new java.util.Date().getTime()));
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(con);
		return resultSet;
	}
	
	public ResultSet verPrecioExacto (String idContrato, double precio, String fechaDesde, String fechaHasta) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM precio AS p WHERE p.idcontrato=? AND p.fechadesde=? AND p.fechahasta=? AND p.precio=?");
		preparedStatement.setString(1, idContrato);
		preparedStatement.setDate(2, Date.valueOf(fechaDesde));
		preparedStatement.setDate(3, Date.valueOf(fechaHasta));
		preparedStatement.setDouble(4, precio);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(con);
		return resultSet;
	}
	
	public ResultSet verPrecioActualTodos() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM precio AS p WHERE ? BETWEEN p.fechadesde AND p.fechahasta ");
		preparedStatement.setDate(1, new Date(new java.util.Date().getTime()));
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(con);
		return resultSet;
	}
	
	public ResultSet verPreciosPorFecha (String idContrato, Date fecha) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM precio AS p WHERE p.idcontrato=? AND ? BETWEEN p.fechadesde AND p.fechahasta ");
		preparedStatement.setString(1, idContrato);
		preparedStatement.setDate(2, fecha);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(con);
		return resultSet;
	}
	
	public void borrarPrecio(String idContrato, double precio, Date fechaDesde, Date fechaHasta) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM precio AS p WHERE p.idcontrato=? AND p.fechadesde=? AND p.fechahasta=? AND p.precio=?");
		preparedStatement.setString(1, idContrato);
		preparedStatement.setDate(2, fechaDesde);
		preparedStatement.setDate(3, fechaHasta);
		preparedStatement.setDouble(4, precio);
		preparedStatement.executeUpdate();
		conexionBD.cerrar(con);
	}
	
	
	public void borrarPrecios() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
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
