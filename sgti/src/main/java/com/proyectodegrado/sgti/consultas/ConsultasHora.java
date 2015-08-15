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

public class ConsultasHora {
	
	private Conexion conexionBD;
	
	public void insertarHora(Date fechaDesde, Date fechaHasta, String horaDesde, String horaHasta, boolean remoto, String idUsuario, String idContrato, String idActividad, 
			Date fechaInformar, Date fechaFacturar, Date fechaComputar, int idTipoHora, String descripcion, boolean validada, boolean informada, boolean facturada) throws SQLException, FileNotFoundException, ClassNotFoundException, IOException{
		
		Connection connection = conexionBD.conectar();
		PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO hora(fechadesde,fechahasta,horadesde,horahasta,remoto,usuario,contrato,actividad,"
				+ "fechainformar,fechafacturar,fechacomputar,tipohora,descripcion,validada,informada,facturada) "
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		preparedStatement.setDate(1, fechaDesde);
		preparedStatement.setDate(2,fechaHasta);
		preparedStatement.setString(3,horaDesde);
		preparedStatement.setString(4,horaHasta);
		preparedStatement.setBoolean(5,remoto);
		preparedStatement.setString(6,idUsuario);
		preparedStatement.setString(7,idContrato);
		preparedStatement.setString(8,idActividad);
		preparedStatement.setDate(9,fechaInformar);
		preparedStatement.setDate(10,fechaFacturar);
		preparedStatement.setDate(11,fechaComputar);
		preparedStatement.setInt(12,idTipoHora);
		preparedStatement.setString(13,descripcion);
		preparedStatement.setBoolean(14,validada);
		preparedStatement.setBoolean(15,informada);
		preparedStatement.setBoolean(16,facturada);
		preparedStatement.executeUpdate();
		conexionBD.cerrar(connection);
		
	}
	
	public ResultSet verHorasRegistradas() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		Connection connection = conexionBD.conectar();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM hora");
		conexionBD.cerrar(connection);
		return resultSet;
	}
	
	public ResultSet verHora(int id) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		Connection connection = conexionBD.conectar();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM hora AS h WHERE h.id=?");
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(connection);
		return resultSet;
	}
	
	public ResultSet verHorasRegistradasPorUsuario(String idUsuario) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		Connection connection = conexionBD.conectar();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM hora AS h WHERE h.usuario=?");
		preparedStatement.setString(1, idUsuario);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(connection);
		return resultSet;
	}
	
	public ResultSet verHorasRegistradasPorContrato(String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		Connection connection = conexionBD.conectar();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM hora AS h WHERE h.contrato=?");
		preparedStatement.setString(1, idContrato);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(connection);
		return resultSet;
	}
	
	public ResultSet verHorasRegistradasNoFacturadas(String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		Connection connection = conexionBD.conectar();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM hora AS h WHERE h.contrato=? AND h.facturada='false'");
		preparedStatement.setString(1, idContrato);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(connection);
		return resultSet;
	}
	
	public ResultSet verHorasRegistradasNoInformadas(String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		Connection connection = conexionBD.conectar();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM hora AS h WHERE h.contrato=? AND h.informada='false'");
		preparedStatement.setString(1, idContrato);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(connection);
		return resultSet;
	}
	
	public void editarHora(int id, Date fechaDesde, Date fechaHasta, String horaDesde, String horaHasta, boolean remoto, String idActividad, 
			Date fechaInformar, Date fechaFacturar, Date fechaComputar, int idTipoHora, String descripcion, boolean validada, boolean informada, boolean facturada) throws SQLException, FileNotFoundException, ClassNotFoundException, IOException{
		
		Connection connection = conexionBD.conectar();
		PreparedStatement preparedStatement = connection.prepareStatement("UPDATE hora AS h SET fechadesde=?, fechahasta=?, horadesde=?, horahasta=?, remoto=?, actividad=?,"
				+ "fechainformar=?, fechafacturar=?, fechacomputar=?, tipohora=?, descripcion=?, validada=?, informada=?, facturada=? WHERE h.id=?");
		preparedStatement.setDate(1, fechaDesde);
		preparedStatement.setDate(2,fechaHasta);
		preparedStatement.setString(3,horaDesde);
		preparedStatement.setString(4,horaHasta);
		preparedStatement.setBoolean(5,remoto);
		preparedStatement.setString(6,idActividad);
		preparedStatement.setDate(7,fechaInformar);
		preparedStatement.setDate(8,fechaFacturar);
		preparedStatement.setDate(9,fechaComputar);
		preparedStatement.setInt(10,idTipoHora);
		preparedStatement.setString(11,descripcion);
		preparedStatement.setBoolean(12,validada);
		preparedStatement.setBoolean(13,informada);
		preparedStatement.setBoolean(14,facturada);
		preparedStatement.setInt(15, id);
		preparedStatement.executeUpdate();
		conexionBD.cerrar(connection);
	}
	
	public void borrarHora (int id) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM hora AS h WHERE h.id =?");
		preparedStatement.setInt(1, id);
		preparedStatement.executeUpdate();
		conexionBD.cerrar(con);
	}
	
	public void borrarHoras() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		Statement statement = con.createStatement();
		statement.executeUpdate("DELETE FROM hora");
		conexionBD.cerrar(con);
	}

	public Conexion getConexionBD() {
		return conexionBD;
	}

	public void setConexionBD(Conexion conexionBD) {
		this.conexionBD = conexionBD;
	}
	
}
