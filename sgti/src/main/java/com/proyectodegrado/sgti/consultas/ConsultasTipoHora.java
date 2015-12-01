package com.proyectodegrado.sgti.consultas;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.proyectodegrado.sgti.conexionbd.Conexion;

public class ConsultasTipoHora {
	
	private Conexion conexionBD;
	
	public void insertarTipoHora (String tipo) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO tipohora(tipo) VALUES (?)");
		preparedStatement.setString(1, tipo);
		preparedStatement.executeUpdate();
		conexionBD.cerrar(con);
	}
	
	public ResultSet verTipoHora (String tipo) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM tipohora AS t WHERE t.tipo=?");
		preparedStatement.setString(1, tipo);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(con);
		return resultSet;
	}
	
	public ResultSet verTipoHoraPorId (int id) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM tipohora AS t WHERE t.id=?");
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(con);
		return resultSet;
	}
	
	public ResultSet verTiposHora () throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		Statement stmt = con.createStatement();
		ResultSet resultSet = stmt.executeQuery("SELECT * FROM tipohora");
		conexionBD.cerrar(con);
		return resultSet;
	}
	
	public ResultSet verTiposHoraAsignadosATecnico(String idTecnico) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM tipohora AS t where t.id IN (SELECT id_tipohora FROM usuario_tipohora WHERE id_usuario=?)");
		preparedStatement.setString(1, idTecnico);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(con);
		return resultSet;
		
	}
	
	public ResultSet verTiposHoraPorContrato(String idContrato) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM tipohora AS t where t.id IN (SELECT idtipohora FROM contrato_tipohora AS c WHERE c.idContrato = ?)");
		preparedStatement.setString(1, idContrato);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(con);
		return resultSet;
		
	}
	
	public ResultSet verTiposHoraQueTecnicoNoTengaAsignado(String idTecnico) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM tipohora AS t where t.id NOT IN (SELECT id_tipohora FROM usuario_tipohora WHERE id_usuario=?)");
		preparedStatement.setString(1, idTecnico);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(con);
		return resultSet;
	}
	
	public ResultSet verTiposHoraQueContratoNoTengaEnUso (String idContrato) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM tipohora AS t where t.id NOT IN (SELECT idtipohora FROM contrato_tipohora AS c WHERE c.idContrato = ?)");
		preparedStatement.setString(1, idContrato);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(con);
		return resultSet;
	}
	
	public void sacarTipoHoraATecnico (String idUsuario, int idTipoHora) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM usuario_tipohora AS t WHERE t.id_usuario=? AND t.id_tipohora=?");
		preparedStatement.setString(1, idUsuario);
		preparedStatement.setInt(2, idTipoHora);
		preparedStatement.executeUpdate();
		conexionBD.cerrar(con);
	}
	
	public void borrarTipoHora (int id) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM tipohora AS t WHERE t.id =?");
		preparedStatement.setInt(1, id);
		preparedStatement.executeUpdate();
		conexionBD.cerrar(con);
	}
	
	public void editarTipoHora(int id, String tipo) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("UPDATE tipohora AS t SET tipo=? WHERE t.id=?");
		preparedStatement.setString(1, tipo);
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
