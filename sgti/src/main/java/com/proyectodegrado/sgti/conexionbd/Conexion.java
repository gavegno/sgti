package com.proyectodegrado.sgti.conexionbd;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	
	public Connection conectar() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		//Properties localProperties = new Properties();
		Connection conn = null;
			//localProperties.load(new FileInputStream("src/main/resources/local.properties"));
			String connectString = "jdbc:postgresql://localhost:5432/sgti";//localProperties.getProperty("conexionBD.connectString");
			String user = "root";//localProperties.getProperty("conexionBD.username");
			String password = "admin";//localProperties.getProperty("conexionBD.password");
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
}
