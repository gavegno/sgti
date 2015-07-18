package test.java;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.proyectodegrado.sgti.conexionbd.Conexion;

public class ConexionDBTest extends ConfigurarTest{
	
	@Resource
	private Conexion conexionBD;
	
	@Test
	public void testearConexionBD() throws SQLException, FileNotFoundException, IOException, ClassNotFoundException{
		if(isHabilitarTest()){
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
			Conexion conexion = (Conexion) context.getBean("conexionBD");
			context.close();
			Connection con = conexion.conectar();
			assertTrue(con != null && !con.isClosed());
			conexion.cerrar(con);
			assertTrue(con.isClosed());
		}
	}

}
