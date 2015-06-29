package test.java;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.proyectodegrado.sgti.consultas.ConsultasTipoHora;
import com.proyectodegrado.sgti.consultas.ConsultasUsuario;
import com.proyectodegrado.sgti.daos.TipoHoraDAO;
import com.proyectodegrado.sgti.daos.UsuarioDAO;

public class ConsultasUsuarioTest extends ConfigurarTest{
	
	private static ConsultasUsuario consultasUsuario;
	
	private static UsuarioDAO usuarioDAO;
	
	private static TipoHoraDAO tipoHoraDAO;
	
	private static ConsultasTipoHora consultasTipoHora;
	
	@BeforeClass
	public static void prepararContexto(){
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		consultasUsuario = (ConsultasUsuario) context.getBean("consultasUsuario");
		consultasTipoHora = (ConsultasTipoHora) context.getBean("consultasTipoHora");
		usuarioDAO = (UsuarioDAO) context.getBean("usuarioDao");
		tipoHoraDAO = (TipoHoraDAO) context.getBean("tipoHoraDao");
	}
	
	@AfterClass
	public static void cerrarContexto(){
		context.close();
	}
	
	@After
	public void borrarDatos() throws FileNotFoundException, IOException, SQLException{
		borrarTiposHoraUsuario();
		consultasUsuario.borrarUsuario("11");
		consultasUsuario.borrarUsuario("10");
		consultasTipoHora.borrarTipoHora("TIPO_TEST");
		consultasTipoHora.borrarTipoHora("TIPO_TEST2");
		
	}

	private void borrarTiposHoraUsuario() throws FileNotFoundException,	IOException, SQLException {
		ResultSet tipoHoraTest = consultasTipoHora.verTipoHora("TIPO_TEST");
		ResultSet tipoHoraTest2 = consultasTipoHora.verTipoHora("TIPO_TEST2");
		if(tipoHoraTest.next()){
			consultasUsuario.borrarTipoHoraDeUsuario("10", tipoHoraTest.getInt("id"));
		}
		if(tipoHoraTest2.next()){
			consultasUsuario.borrarTipoHoraDeUsuario("10", tipoHoraTest2.getInt("id"));
		}
	}
	
	@Test
	public void testInsertarSeleccionarUsuario() throws FileNotFoundException, IOException, SQLException{
		if(isHabilitarTest()){
			usuarioDAO.agregar("10","testNombre","testApellido","testContrasena","test@email.com", "12345678", "SOCIO",true);
			assertTrue(usuarioDAO.seleccionarUsuarioPorId("10").getApellido().equalsIgnoreCase("testApellido"));
			assertTrue(usuarioDAO.seleccionarUsuarioPorId("10").getNombre().equalsIgnoreCase("testNombre"));
			assertTrue(usuarioDAO.seleccionarUsuarioPorId("10").getEmail().equalsIgnoreCase("test@email.com"));
			assertTrue(usuarioDAO.seleccionarUsuarioPorId("10").getTelefono().equalsIgnoreCase("12345678"));
			assertTrue(usuarioDAO.seleccionarUsuarioPorId("10").getTipo().equalsIgnoreCase("SOCIO"));
			assertTrue(usuarioDAO.seleccionarUsuarioPorId("10").isActivo());
		}
	}
	
	@Test
	public void testInsertarSeleccionarTiposHoraAUsuario() throws FileNotFoundException, IOException, SQLException{
		if(isHabilitarTest()){
			usuarioDAO.agregar("10","testNombre","testApellido","testContrasena","test@email.com", "12345678", "SOCIO",true);
			assertTrue(usuarioDAO.seleccionarUsuarioPorId("10").getApellido().equalsIgnoreCase("testApellido"));
			tipoHoraDAO.agregar("TIPO_TEST");
			tipoHoraDAO.agregar("TIPO_TEST2");
			List<Integer> tiposHora = new ArrayList<Integer>();
			tiposHora.add(tipoHoraDAO.seleccionarPorTipo("TIPO_TEST").getId());
			tiposHora.add(tipoHoraDAO.seleccionarPorTipo("TIPO_TEST2").getId());
			usuarioDAO.agregarTipoHoraUsuario(tiposHora, "10");
			assertTrue(usuarioDAO.verTiposHoraPorUsuario("10").size()==2);
			
		}
	}
	
	@Test
	public void testInsertarSeleccionarUsuarios() throws FileNotFoundException, IOException, SQLException{
		if(isHabilitarTest()){
			usuarioDAO.agregar("10","testNombre","testApellido","testContrasena","test@email.com", "12345678", "SOCIO",true);
			usuarioDAO.agregar("11","testNombre2","testApellido2","testContrasena2","test2@email.com", "12345679", "TECNICO",true);
			assertTrue(usuarioDAO.seleccionarUsuarioPorId("10").getApellido().equalsIgnoreCase("testApellido"));
			assertTrue(usuarioDAO.seleccionarUsuarioPorId("11").getApellido().equalsIgnoreCase("testApellido2"));
			assertTrue(usuarioDAO.seleccionarUsuarios().size()==2);
		}
	}
	
	@Test
	public void testEditarUsuario() throws FileNotFoundException, IOException, SQLException{
		if(isHabilitarTest()){
			usuarioDAO.agregar("10","testNombre","testApellido","testContrasena","test@email.com", "12345678", "SOCIO",true);
			usuarioDAO.editarUsuario("10","testNombre2","testApellido2","test2@email.com", "12345679");
			assertTrue(usuarioDAO.seleccionarUsuarioPorId("10").getApellido().equalsIgnoreCase("testApellido2"));
			assertTrue(usuarioDAO.seleccionarUsuarioPorId("10").getNombre().equalsIgnoreCase("testNombre2"));
			assertTrue(usuarioDAO.seleccionarUsuarioPorId("10").getEmail().equalsIgnoreCase("test2@email.com"));
			assertTrue(usuarioDAO.seleccionarUsuarioPorId("10").getTelefono().equalsIgnoreCase("12345679"));
			assertTrue(usuarioDAO.seleccionarUsuarioPorId("10").getTipo().equalsIgnoreCase("SOCIO"));
			assertTrue(usuarioDAO.seleccionarUsuarioPorId("10").isActivo());
		}
	}
	
	@Test
	public void testCambiarContrasena() throws FileNotFoundException, IOException, SQLException{
		if(isHabilitarTest()){
			usuarioDAO.agregar("10","testNombre","testApellido","testContrasena","test@email.com", "12345678", "SOCIO",true);
			usuarioDAO.cambiarContraseña("10", "contrasenaModificada");
			assertTrue(usuarioDAO.seleccionarUsuarioPorId("10").getApellido().equalsIgnoreCase("testApellido"));
			assertTrue(usuarioDAO.seleccionarUsuarioPorId("10").getNombre().equalsIgnoreCase("testNombre"));
			assertTrue(usuarioDAO.seleccionarUsuarioPorId("10").getEmail().equalsIgnoreCase("test@email.com"));
			assertTrue(usuarioDAO.seleccionarUsuarioPorId("10").getTelefono().equalsIgnoreCase("12345678"));
			assertTrue(usuarioDAO.seleccionarUsuarioPorId("10").getTipo().equalsIgnoreCase("SOCIO"));
			assertTrue(usuarioDAO.seleccionarUsuarioPorId("10").isActivo());
		}
	}

}
