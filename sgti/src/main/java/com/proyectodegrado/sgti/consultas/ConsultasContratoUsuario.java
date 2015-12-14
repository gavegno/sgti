package com.proyectodegrado.sgti.consultas;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.proyectodegrado.sgti.conexionbd.Conexion;

public class ConsultasContratoUsuario {
	
	private Conexion conexionBD;
	
	public void asignarTecnicoAContrato(String idUsuario, String idContrato) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection connection = conexionBD.conectar();
		PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO contrato_tecnico(idcontrato,idusuario) VALUES (?,?)");
		preparedStatement.setString(1, idContrato);
		preparedStatement.setString(2, idUsuario);
		preparedStatement.executeUpdate();
		conexionBD.cerrar(connection);
		
	}
	
	public ResultSet listarTecnicosPorContratoTodos(String idContrato) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection connection = conexionBD.conectar();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM contrato_tecnico AS c WHERE c.idcontrato=? AND idusuario IN (Select id from usuario where activo = true)");
		preparedStatement.setString(1, idContrato);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(connection);
		return resultSet;
	}
	
	public ResultSet listarTecnicosCandidatosPorContrato(String idContrato) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection connection = conexionBD.conectar();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM usuario AS u WHERE u.tipo!='CONTRAPARTE' AND u.id NOT IN (Select idusuario from contrato_tecnico AS c where c.idcontrato=?)");
		preparedStatement.setString(1, idContrato);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(connection);
		return resultSet;
	}
	
	
	public ResultSet listarContratosPorTecnicoTodos(String idUsuario) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection connection = conexionBD.conectar();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM contrato_tecnico AS c WHERE c.idusuario=? AND idusuario IN (Select id from usuario where activo = true) order by c.idcontrato");
		preparedStatement.setString(1, idUsuario);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(connection);
		return resultSet;
	}
	
	public void eliminarTecnicoDeContrato (String idContrato, String idUsuario) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM contrato_tecnico AS c WHERE c.idcontrato=? AND c.idusuario=?");
		preparedStatement.setString(1, idContrato);
		preparedStatement.setString(2, idUsuario);
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
