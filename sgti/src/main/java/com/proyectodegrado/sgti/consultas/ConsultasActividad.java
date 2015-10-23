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

public class ConsultasActividad {
	
	private Conexion conexionBD;
	
	public void insertarActividad(String id, String tipo, Date fechaCreacion, Date fechaActividad, String idUsuario, String idContrato, int periodo, String descripcion) throws SQLException, FileNotFoundException, ClassNotFoundException, IOException{
		Connection connection = conexionBD.conectar();
		PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO actividad(id,tipo,fechacreacion,fechaactividad,usuario,contrato,periodo,descripcion,estado) VALUES (?,?,?,?,?,?,?,?,?)");
		preparedStatement.setString(1, id);
		preparedStatement.setString(2,tipo);
		preparedStatement.setDate(3,fechaCreacion);
		preparedStatement.setDate(4,fechaActividad);
		preparedStatement.setString(5,idUsuario);
		preparedStatement.setString(6,idContrato);
		preparedStatement.setInt(7,periodo);
		preparedStatement.setString(8,descripcion);
		preparedStatement.setString(9,"PENDIENTE");
		preparedStatement.executeUpdate();
		conexionBD.cerrar(connection);
	}
	
	public ResultSet verActividad(String id) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		Connection connection = conexionBD.conectar();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM actividad AS a WHERE a.id=? order by a.fechaactividad DESC");
		preparedStatement.setString(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(connection);
		return resultSet;
	}
	
	public ResultSet verActividades() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		Connection connection = conexionBD.conectar();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM actividad");
		conexionBD.cerrar(connection);
		return resultSet;
	}
	
	public ResultSet verActividadesConFechaDesde(Date fechaDesde) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		Connection connection = conexionBD.conectar();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM actividad AS a where a.fechaactividad >= ?  order by a.fechaactividad DESC");
		preparedStatement.setDate(1, fechaDesde);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(connection);
		return resultSet;
	}
	
	public ResultSet verActividadesPorUsuario(String idUsuario) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		Connection connection = conexionBD.conectar();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM actividad AS a WHERE a.usuario=?  order by a.fechaactividad DESC");
		preparedStatement.setString(1, idUsuario);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(connection);
		return resultSet;
	}
	
	public ResultSet verActividadesDeTecnicoYSusContratos(String idUsuario, Date fechaDesde) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		Connection connection = conexionBD.conectar();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM actividad AS a "
				+ "WHERE ((a.usuario=?) OR "
				+ "(a.usuario='' AND a.contrato IN "
					+ "(SELECT c.idcontrato FROM contrato_tecnico AS c "
					+ "WHERE c.idusuario=?))) "
			+ "AND a.fechaactividad>=?  order by a.fechaactividad DESC");
		preparedStatement.setString(1, idUsuario);
		preparedStatement.setString(2, idUsuario);
		preparedStatement.setDate(3,fechaDesde);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(connection);
		return resultSet;
	}
	
	public ResultSet verActividadesPorContrato(String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		Connection connection = conexionBD.conectar();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM actividad AS a WHERE a.contrato=? order by a.fechaactividad DESC");
		preparedStatement.setString(1, idContrato);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(connection);
		return resultSet;
	}
	
	public ResultSet actividadAsignadaAHora(String id) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		Connection connection = conexionBD.conectar();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM hora AS h WHERE h.actividad=?");
		preparedStatement.setString(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		conexionBD.cerrar(connection);
		return resultSet;
	}
	
	public void editarActividad(String id, Date fechaActividad, String idUsuario, int periodo, String descripcion) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		Connection connection = conexionBD.conectar();
		PreparedStatement preparedStatement = connection.prepareStatement("UPDATE actividad AS a SET a.fechaactividad=?, a.usuario=?, a.periodo=?, a.descripcion=? WHERE a.id=?");
		preparedStatement.setDate(1,fechaActividad);
		preparedStatement.setString(2,idUsuario);
		preparedStatement.setInt(3,periodo);
		preparedStatement.setString(4,descripcion);
		preparedStatement.setString(5,id);
		
		preparedStatement.executeUpdate();
		conexionBD.cerrar(connection);
		
	}
	
	public void cambiarEstadoActividad(String id, String estado) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		Connection connection = conexionBD.conectar();
		PreparedStatement preparedStatement = connection.prepareStatement("UPDATE actividad SET estado=? WHERE id=?");
		preparedStatement.setString(1,estado);
		preparedStatement.setString(2,id);
		
		preparedStatement.executeUpdate();
		conexionBD.cerrar(connection);
		
	}
	
	public void asignarActividad(String id, String idUsuario) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		
		Connection connection = conexionBD.conectar();
		PreparedStatement preparedStatement = connection.prepareStatement("UPDATE actividad SET usuario=? WHERE id=?");
		preparedStatement.setString(1,idUsuario);
		preparedStatement.setString(2,id);
		
		preparedStatement.executeUpdate();
		conexionBD.cerrar(connection);
		
	}
	
	public void borrarActividad(String id) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM actividad AS a WHERE a.id = ?");
		preparedStatement.setString(1, id);
		preparedStatement.executeUpdate();
		conexionBD.cerrar(con);
	}
	
	public void borrarActividades() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection con = conexionBD.conectar();
		Statement statement = con.createStatement();
		statement.executeUpdate("DELETE FROM actividad");
		conexionBD.cerrar(con);
	}

	public Conexion getConexionBD() {
		return conexionBD;
	}

	public void setConexionBD(Conexion conexionBD) {
		this.conexionBD = conexionBD;
	}
	
}
