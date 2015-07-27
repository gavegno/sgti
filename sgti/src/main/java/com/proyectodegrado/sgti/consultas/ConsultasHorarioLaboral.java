package com.proyectodegrado.sgti.consultas;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.proyectodegrado.sgti.conexionbd.Conexion;

public class ConsultasHorarioLaboral {
	
	private Conexion conexionBD;
	
	public void insertarDiaAHorarioLaboral (String idHorarioLaboral, String nombreDia, String horaDesde, String horaHasta) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO horario(id,nombredia,horadesde,horahasta) VALUES (?,?,?,?)");
		preparedStatement.setString(1, idHorarioLaboral);
		preparedStatement.setString(2, nombreDia);
		preparedStatement.setString(3, horaDesde);
		preparedStatement.setString(4, horaHasta);
		preparedStatement.executeUpdate();
		conexionBD.cerrar(con);
	}
	
	public ResultSet verHorarioLaboral (String idHorarioLaboral) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM horario AS h WHERE h.id=?");
		preparedStatement.setString(1, idHorarioLaboral);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(con);
		return resultSet;
	}
	
	public ResultSet verHorariosLaborales () throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		Statement statement = con.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM horario");
		conexionBD.cerrar(con);
		return resultSet;
	}
	
	public void editarDiaDeHorarioLaboral(String idHorarioLaboral, String nombreDia, String horaDesde, String horaHasta) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection connection = conexionBD.conectar();
		PreparedStatement preparedStatement = connection.prepareStatement("UPDATE horario AS h SET horadesde=?, horahasta=? WHERE h.id=? AND h.nombredia=?");
		preparedStatement.setString(1,horaDesde);
		preparedStatement.setString(2,horaHasta);
		preparedStatement.setString(3,idHorarioLaboral);
		preparedStatement.setString(4,nombreDia);
		preparedStatement.executeUpdate();
		conexionBD.cerrar(connection);
	}
	
	public void borrarDiaDeHorarioLaboral (String idHorarioLaboral, String nombreDia) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM horario AS h WHERE h.id =? AND h.nombredia=?");
		preparedStatement.setString(1, idHorarioLaboral);
		preparedStatement.setString(2, nombreDia);
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
