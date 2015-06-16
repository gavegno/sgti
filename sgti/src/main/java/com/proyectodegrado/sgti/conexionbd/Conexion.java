package com.proyectodegrado.sgti.conexionbd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	
	String driver = "org.postgresql.Driver";
	String connectString = "jdbc:postgresql://localhost:5432/sgti";
	String user = "root";
	String password = "admin";

	public Connection conectar(){
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(connectString, user, password);
		}catch (SQLException e){
			e.printStackTrace();
		}finally{
			return conn;
		}
	}
	
	public void cerrar(Connection con){
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
