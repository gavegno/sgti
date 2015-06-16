package test.java;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.proyectodegrado.sgti.conexionbd.Conexion;

public class ConexionDBTest {
	
	@Test
	public void testearConexionBD() throws SQLException{
		
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		BeanFactory factory = context;
		Conexion conexion = (Conexion) factory.getBean("conexionBD");
		Connection con = conexion.conectar();
		assertTrue(con != null && !con.isClosed());
		conexion.cerrar(con);
		assertTrue(con.isClosed());
		
	}

}
