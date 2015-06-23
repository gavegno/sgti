package com.proyectodegrado.sgti.conexionbd;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexion {
	
	public Connection conectar() throws FileNotFoundException, IOException, SQLException{
		Properties localProperties = new Properties();
		Connection conn = null;
			localProperties.load(new FileInputStream("src/main/resources/local.properties"));
			String connectString = localProperties.getProperty("conexionBD.connectString");
			String user = localProperties.getProperty("conexionBD.username");
			String password = localProperties.getProperty("conexionBD.password");
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
}
