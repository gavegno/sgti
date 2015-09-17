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
			Date fechaInformar, Date fechaFacturar, Date fechaComputar, int idTipoHora, String descripcion, String comentario, boolean validada, boolean informada, boolean facturada, int duracion) throws SQLException, FileNotFoundException, ClassNotFoundException, IOException{
		
		Connection connection = conexionBD.conectar();
		PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO hora(fechadesde,fechahasta,horadesde,horahasta,remoto,usuario,contrato,actividad,"
				+ "fechainformar,fechafacturar,fechacomputar,tipohora,descripcion,comentario,validada,informada,facturada,duracion) "
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
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
		preparedStatement.setString(14,comentario);
		preparedStatement.setBoolean(15,validada);
		preparedStatement.setBoolean(16,informada);
		preparedStatement.setBoolean(17,facturada);
		preparedStatement.setInt(18,duracion);
		preparedStatement.executeUpdate();
		conexionBD.cerrar(connection);
		
	}
	
	public void editarHora(int id, Date fechaDesde, Date fechaHasta, String horaDesde, String horaHasta, boolean remoto, String idActividad, 
			Date fechaInformar, Date fechaFacturar, Date fechaComputar, int idTipoHora, String descripcion, String comentario, boolean validada, boolean informada, boolean facturada, int duracion) throws SQLException, FileNotFoundException, ClassNotFoundException, IOException{

		
		Connection connection = conexionBD.conectar();
		PreparedStatement preparedStatement = connection.prepareStatement("UPDATE hora AS h SET fechadesde=?, fechahasta=?, horadesde=?, horahasta=?, remoto=?, actividad=?,"
				+ "fechainformar=?, fechafacturar=?, fechacomputar=?, tipohora=?, descripcion=?, comentario=?, validada=?, informada=?, facturada=?, duracion=? WHERE h.id=?");
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
		preparedStatement.setString(12,comentario);
		preparedStatement.setBoolean(13,validada);
		preparedStatement.setBoolean(14,informada);
		preparedStatement.setBoolean(15,facturada);
		preparedStatement.setInt(16,duracion);
		preparedStatement.setInt(17, id);
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
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM hora AS h WHERE h.usuario=? ORDER BY h.fechadesde DESC");
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
	
	
	public void editarHoraFechas(int id, Date fechaInformar, Date fechaFacturar, Date fechaComputar) throws SQLException, FileNotFoundException, ClassNotFoundException, IOException{
		
		Date fechaNula = Date.valueOf("1900-01-01");
		
		
		Connection connection = conexionBD.conectar();
		PreparedStatement preparedStatement = connection.prepareStatement("UPDATE hora AS h SET fechainformar=?, fechafacturar=?, fechacomputar=? WHERE h.id=?");
		
		if (fechaInformar.compareTo(fechaNula) == 0)
			preparedStatement.setNull(1, java.sql.Types.DATE);
		else	
			preparedStatement.setDate(1,fechaInformar);
		
		if (fechaFacturar.compareTo(fechaNula) == 0)
			preparedStatement.setNull(2, java.sql.Types.DATE);
		else	
			preparedStatement.setDate(2,fechaFacturar);
		
		if (fechaComputar.compareTo(fechaNula) == 0)
			preparedStatement.setNull(3, java.sql.Types.DATE);
		else	
			preparedStatement.setDate(3,fechaComputar);
		
		preparedStatement.setInt(4, id);
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
	
	public void cambiarValidacionHora (int id) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("UPDATE hora AS h SET validada = NOT validada WHERE h.id=?");
		preparedStatement.setInt(1, id);
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
