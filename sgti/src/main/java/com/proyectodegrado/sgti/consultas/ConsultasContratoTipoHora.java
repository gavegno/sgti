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
	
	public void insertarContratoTipoHora (String idContrato, int idTipoHora, double computos) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO contrato_tipohora(idcontrato, idtipohora, computos) VALUES (?,?,?)");
		preparedStatement.setString(1, idContrato);
		preparedStatement.setInt(2, idTipoHora);
		preparedStatement.setDouble(3, computos);
		preparedStatement.executeUpdate();
		conexionBD.cerrar(con);
	}
	
	public ResultSet verContratoTiposHora (String idContrato) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM contrato_tipohora AS c WHERE c.idcontrato=?");
		preparedStatement.setString(1, idContrato);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(con);
		return resultSet;
	}
	//
	
	public ResultSet verContratoTiposHoraParaGestionarHoras() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("select c.idcontrato, c.idtipohora, t.tipo from contrato_tipohora AS c "
				+ "INNER JOIN tipohora AS t ON c.idtipohora = t.id order by idcontrato");
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(con);
		return resultSet;
	}
	
	public ResultSet verContratoTiposHoraParaGestionarHorasPorTecnico(String idUsuario) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("SELECT c.idcontrato, c.idtipohora, t.tipo, ut.id_usuario FROM "
				+ "contrato_tipohora AS c INNER JOIN tipohora AS t ON c.idtipohora = t.id "
				+ "INNER JOIN usuario_tipohora AS ut ON ut.id_tipohora = c.idtipohora WHERE ut.id_usuario=?");
		preparedStatement.setString(1, idUsuario);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(con);
		return resultSet;
	}
	
	public ResultSet verTiposHoraPorTecnicoYContrato(String idUsuario, String idContrato) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("SELECT c.idcontrato, c.idtipohora, t.tipo, ut.id_usuario FROM "
				+ "contrato_tipohora AS c INNER JOIN tipohora AS t ON c.idtipohora = t.id "
				+ "INNER JOIN usuario_tipohora AS ut ON ut.id_tipohora = c.idtipohora WHERE ut.id_usuario=? AND c.idcontrato=?");
		preparedStatement.setString(1, idUsuario);
		preparedStatement.setString(2, idContrato);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(con);
		return resultSet;
	}
	
	public void editarContratoTipoHora (String idContrato, int idTipoHora, double computos) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("UPDATE contrato_tipohora SET computos=? WHERE idcontrato=? AND idtipohora=?");
		preparedStatement.setDouble(1, computos);
		preparedStatement.setString(2, idContrato);
		preparedStatement.setInt(3, idTipoHora);
		preparedStatement.executeUpdate();
		conexionBD.cerrar(con);
	}
	
	public void borrarContratoTiposHora () throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		Statement statement = con.createStatement();
		statement.executeUpdate("DELETE FROM contrato_tipohora");
		conexionBD.cerrar(con);
	}
	
	public void borrarContratoTiposHora (String idContrato, int tipoHoraId) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM contrato_tipohora AS c WHERE c.idcontrato=? AND c.idtipohora=?");
		preparedStatement.setString(1, idContrato);
		preparedStatement.setInt(2, tipoHoraId);
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
