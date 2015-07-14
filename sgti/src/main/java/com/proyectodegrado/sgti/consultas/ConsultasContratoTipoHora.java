package com.proyectodegrado.sgti.consultas;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.proyectodegrado.sgti.conexionbd.Conexion;

public class ConsultasContratoTipoHora {
	
	private Conexion conexionBD;
	
	public void insertarContratoTipoHora (String idContrato, int idTipoHora, int computos) throws FileNotFoundException, IOException, SQLException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO contrato_tipohora(idcontrato, idtipohora, computos) VALUES (?,?,?)");
		preparedStatement.setString(1, idContrato);
		preparedStatement.setInt(2, idTipoHora);
		preparedStatement.setInt(3, computos);
		preparedStatement.executeUpdate();
		conexionBD.cerrar(con);
	}
	
	public ResultSet verContratoTiposHora (String idContrato) throws FileNotFoundException, IOException, SQLException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM contrato_tipohora AS c WHERE c.idcontrato=?");
		preparedStatement.setString(1, idContrato);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(con);
		return resultSet;
	}
	
	public void editarContratoTipoHora (String idContrato, int idTipoHora, int computos) throws FileNotFoundException, IOException, SQLException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("UPDATE contrato_tipohora SET computos=? WHERE idcontrato=? AND idtipohora=?");
		preparedStatement.setInt(1, computos);
		preparedStatement.setString(2, idContrato);
		preparedStatement.setInt(3, idTipoHora);
		preparedStatement.executeUpdate();
		conexionBD.cerrar(con);
	}
	
	public void borrarContratoTiposHora () throws FileNotFoundException, IOException, SQLException{
		Connection con = conexionBD.conectar();
		Statement statement = con.createStatement();
		statement.executeUpdate("DELETE FROM contrato_tipohora");
		conexionBD.cerrar(con);
	}

	public Conexion getConexionBD() {
		return conexionBD;
	}

	public void setConexionBD(Conexion conexionBD) {
		this.conexionBD = conexionBD;
	}
}
