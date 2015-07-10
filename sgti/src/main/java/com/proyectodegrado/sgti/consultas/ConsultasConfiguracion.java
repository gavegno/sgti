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

public class ConsultasConfiguracion {
	
	private Conexion conexionBD;
	
	public void insertarConfiguracion(Date fechaInicio, Date fechaFin, String renovacion, int periodoRenovacion,
			 String tipoContrato, int computosPaquete, int periodoValidezMes, int periodoValidezDia, boolean acumulacion,
			 int periodoAcumulacion, int frecuenciaInforme, int frecuenciaFacturacion, int frecuenciaComputosExtra,
			 String tiempoRespuesta, String idHorarioLaboral, String idContrato) throws FileNotFoundException, IOException, SQLException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO configuracion(fechainicio,fechafin,renovacion,periodorenovacion,tipocontrato,"
				+ "computospaquete,periodovalidezmes,"
				+ "periodovalidezdia,acumulacion,periodoacumulacion,frecuenciainforme,frecuenciafacturacion,frecuenciacomputosextra,tiemporespuesta,id_horariolaboral,"
				+ "id_contrato) "
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		preparedStatement.setDate(1, fechaInicio);
		preparedStatement.setDate(2, fechaFin);
		preparedStatement.setString(3, renovacion);
		preparedStatement.setInt(4, periodoRenovacion);
		preparedStatement.setString(5, tipoContrato);
		preparedStatement.setInt(6, computosPaquete);
		preparedStatement.setInt(7, periodoValidezMes);
		preparedStatement.setInt(8, periodoValidezDia);
		preparedStatement.setBoolean(9, acumulacion);
		preparedStatement.setInt(10, periodoAcumulacion);
		preparedStatement.setInt(11, frecuenciaInforme);
		preparedStatement.setInt(12, frecuenciaFacturacion);
		preparedStatement.setInt(13, frecuenciaComputosExtra);
		preparedStatement.setString(14, tiempoRespuesta);
		preparedStatement.setString(15, idHorarioLaboral);
		preparedStatement.setString(16, idContrato);
		preparedStatement.executeUpdate();
		conexionBD.cerrar(con);
	}
	
	public ResultSet verConfiguracion(int id) throws SQLException, FileNotFoundException, IOException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM configuracion AS c WHERE c.id=?");
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(con);
		return resultSet;
	}
	
	public ResultSet verConfiguracionesEntreFechasFin(Date fechaMayorQue, Date fechaMenorQue) throws SQLException, FileNotFoundException, IOException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM configuracion AS c WHERE c.fechafin BETWEEN ? AND ?");
		preparedStatement.setDate(1, fechaMayorQue);
		preparedStatement.setDate(2, fechaMenorQue);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(con);
		return resultSet;
	}
	
	public ResultSet verConfiguracionActual(String idContrato) throws SQLException, FileNotFoundException, IOException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM configuracion AS c WHERE ? BETWEEN c.fechainicio AND c.fechafin AND c.id_contrato=?");
		preparedStatement.setDate(1, new Date(new java.util.Date().getTime()));
		preparedStatement.setString(2, idContrato);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(con);
		return resultSet;
	}
	
	public ResultSet verConfiguracionPorFecha(Date fecha, String idContrato) throws SQLException, FileNotFoundException, IOException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM configuracion AS c WHERE ? BETWEEN c.fechainicio AND c.fechafin AND c.id_contrato=?");
		preparedStatement.setDate(1, fecha);
		preparedStatement.setString(2, idContrato);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(con);
		return resultSet;
	}
	
	public ResultSet verConfiguracionesPorContrato(String idContrato) throws SQLException, FileNotFoundException, IOException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM configuracion AS c WHERE c.id_contrato=?");
		preparedStatement.setString(1, idContrato);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(con);
		return resultSet;
	}
	
	public void editarConfiguracion(int id, Date fechaInicio, Date fechaFin, String renovacion, int periodoRenovacion,
			 String tipoContrato, int computosPaquete, int periodoValidezMes, int periodoValidezDia, boolean acumulacion,
			 int periodoAcumulacion, int frecuenciaInforme, int frecuenciaFacturacion, int frecuenciaComputosExtra,
			 String tiempoRespuesta, String idHorarioLaboral) throws SQLException, FileNotFoundException, IOException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("UPDATE configuracion AS c SET fechainicio=?,fechafin=?,renovacion=?,periodorenovacion=?,tipocontrato=?,"
				+ "computospaquete=?,periodovalidezmes=?,periodovalidezdia=?,acumulacion=?,periodoacumulacion=?,frecuenciainforme=?,frecuenciafacturacion=?,"
				+ "frecuenciacomputosextra=?,tiemporespuesta=?,id_horariolaboral=? WHERE c.id=?");
		preparedStatement.setDate(1, fechaInicio);
		preparedStatement.setDate(2, fechaFin);
		preparedStatement.setString(3, renovacion);
		preparedStatement.setInt(4, periodoRenovacion);
		preparedStatement.setString(5, tipoContrato);
		preparedStatement.setInt(6, computosPaquete);
		preparedStatement.setInt(7, periodoValidezMes);
		preparedStatement.setInt(8, periodoValidezDia);
		preparedStatement.setBoolean(9, acumulacion);
		preparedStatement.setInt(10, periodoAcumulacion);
		preparedStatement.setInt(11, frecuenciaInforme);
		preparedStatement.setInt(12, frecuenciaFacturacion);
		preparedStatement.setInt(13, frecuenciaComputosExtra);
		preparedStatement.setString(14, tiempoRespuesta);
		preparedStatement.setString(15, idHorarioLaboral);
		preparedStatement.setInt(16, id);
		preparedStatement.executeUpdate();
		conexionBD.cerrar(con);
	}
	
	public void borrarConfiguraciones () throws FileNotFoundException, IOException, SQLException{
		Connection con = conexionBD.conectar();
		Statement statement = con.createStatement();
		statement.executeUpdate("DELETE FROM configuracion");
		conexionBD.cerrar(con);
	}

	public Conexion getConexionBD() {
		return conexionBD;
	}

	public void setConexionBD(Conexion conexionBD) {
		this.conexionBD = conexionBD;
	}
}
