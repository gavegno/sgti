package com.proyectodegrado.sgti.conexionbd;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexion {
	
	public Connection conectar() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		//Properties localProperties = new Properties();
		Connection conn = null;
			//localProperties.load(new FileInputStream("src/main/resources/local.properties"));
			String connectString = "jdbc:postgresql://localhost:5432/sgti";//localProperties.getProperty("conexionBD.connectString");
			if(usarJunit()){
				connectString = "jdbc:postgresql://localhost:5432/junit_sgti";
			}
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
	
	private boolean usarJunit() {
		Properties localProperties = new Properties();
		boolean testHabilitado = false;
		/*try {
			localProperties.load(new FileInputStream("src/main/resources/local.properties"));
			testHabilitado = Boolean.valueOf(localProperties.getProperty("habilitarTest"));
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		return testHabilitado;
	}
}
