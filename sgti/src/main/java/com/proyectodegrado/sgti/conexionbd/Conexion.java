package com.proyectodegrado.sgti.conexionbd;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;

public class Conexion {
	
	@Value("${conexionBD.connectString}")
	private String urlConexion;
	
	@Value("${conexionBD.connectStringJUnit}")
	private String urlConexionJUnit;
	
	@Value("${conexionBD.username}")
	private String user;
	
	@Value("${conexionBD.password}")
	private String password;
	
	@Value("${habilitarTest}")
	private String usarJunit;
	
	public Connection conectar() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		Connection conn = null;
			String connectString = urlConexion;
			if(usarJunit()){
				connectString = urlConexionJUnit;
			}
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(connectString, user, password);
			return conn;
	}
	
	public void cerrar(Connection con){
		try {
			con.close();
		} catch (SQLException e) {
			System.out.println("Error al cerrar sesión de base de datos: ");
			e.printStackTrace();
		}
		
	}
	
	private boolean usarJunit() {
		boolean testHabilitado = false;
		testHabilitado = Boolean.valueOf(usarJunit);
		return testHabilitado;
	}
}
