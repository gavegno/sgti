package com.proyectodegrado.sgti.consultas;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.proyectodegrado.sgti.conexionbd.Conexion;

public class ConsultasComputoAcumulado {
	
private Conexion conexionBD;
	
	public void insertarComputosAcumulados (Date fecha, String idContrato, double computos) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO computo_acumulado(idContrato,fecha,computos) VALUES (?,?,?)");
		preparedStatement.setString(1, idContrato);
		preparedStatement.setDate(2, fecha);
		preparedStatement.setDouble(3, computos);
		preparedStatement.executeUpdate();
		conexionBD.cerrar(con);
	}
	
	public ResultSet verComputosAcumulados (String idContrato) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM computo_acumulado AS c WHERE c.idContrato=? ORDER BY c.fecha ASC");
		preparedStatement.setString(1, idContrato);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(con);
		return resultSet;
	}
	
	public void borrarComputosAcumulados (int id) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM computo_acumulado AS c WHERE c.id =?");
		preparedStatement.setInt(1, id);
		preparedStatement.executeUpdate();
		conexionBD.cerrar(con);
	}
	
	public void editarComputosAcumulados (int id, double computos) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("UPDATE computo_acumulado AS c SET computos=? WHERE c.id=?");
		preparedStatement.setDouble(1, computos);
		preparedStatement.setInt(2, id);
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
