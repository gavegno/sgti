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
	
	private static final String TEST_TELEFONO2 = "12345679";
	private static final String TEST_EMAIL2 = "test2@email.com";
	private static final String TEST_CONTRASENA2 = "testContrasena2";
	private static final String TEST_APELLIDO2 = "testApellido2";
	private static final String TEST_NOMBRE2 = "testNombre2";
	private static final String TIPO_SOCIO = "SOCIO";
	private static final String TEST_TELEFONO = "12345678";
	private static final String TEST_EMAIL = "test@email.com";
	private static final String TEST_CONTRASENA = "testContrasena";
	private static final String TEST_APELLIDO = "testApellido";
	private static final String TEST_NOMBRE = "testNombre";
	private static final String ID = "id";
	private static final String ID_USUARIO10 = "10";
	private static final String ID_USUARIO11 = "11";

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
	public void borrarDatos() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		if(isHabilitarTest()){
			borrarTiposHoraUsuario();
			consultasUsuario.borrarUsuario(ID_USUARIO11);
			consultasUsuario.borrarUsuario(ID_USUARIO10);
			consultasTipoHora.borrarTipoHora(TIPO_TEST);
			consultasTipoHora.borrarTipoHora(TIPO_TEST2);
		}
		
	}

	private void borrarTiposHoraUsuario() throws FileNotFoundException,	IOException, SQLException, ClassNotFoundException {
		ResultSet tipoHoraTest = consultasTipoHora.verTipoHora(TIPO_TEST);
		ResultSet tipoHoraTest2 = consultasTipoHora.verTipoHora(TIPO_TEST2);
		if(tipoHoraTest.next()){
			consultasUsuario.borrarTipoHoraDeUsuario(ID_USUARIO10, tipoHoraTest.getInt(ID));
		}
		if(tipoHoraTest2.next()){
			consultasUsuario.borrarTipoHoraDeUsuario(ID_USUARIO10, tipoHoraTest2.getInt(ID));
		}
	}
	
	@Test
	public void testInsertarSeleccionarUsuario() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		if(isHabilitarTest()){
			usuarioDAO.agregar(ID_USUARIO10,TEST_NOMBRE,TEST_APELLIDO,TEST_CONTRASENA,TEST_EMAIL, TEST_TELEFONO, TIPO_SOCIO,true);
			assertTrue(usuarioDAO.seleccionarUsuarioPorId(ID_USUARIO10).getApellido().equalsIgnoreCase(TEST_APELLIDO));
			assertTrue(usuarioDAO.seleccionarUsuarioPorId(ID_USUARIO10).getNombre().equalsIgnoreCase(TEST_NOMBRE));
			assertTrue(usuarioDAO.seleccionarUsuarioPorId(ID_USUARIO10).getEmail().equalsIgnoreCase(TEST_EMAIL));
			assertTrue(usuarioDAO.seleccionarUsuarioPorId(ID_USUARIO10).getTelefono().equalsIgnoreCase(TEST_TELEFONO));
			assertTrue(usuarioDAO.seleccionarUsuarioPorId(ID_USUARIO10).getTipo().equalsIgnoreCase(TIPO_SOCIO));
			assertTrue(usuarioDAO.seleccionarUsuarioPorId(ID_USUARIO10).isActivo());
		}
	}
	
	@Test
	public void testSeleccionarUsuarioPorIdContrasena() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		if(isHabilitarTest()){
			usuarioDAO.agregar(ID_USUARIO10,TEST_NOMBRE,TEST_APELLIDO,TEST_CONTRASENA,TEST_EMAIL, TEST_TELEFONO, TIPO_SOCIO,true);
			assertTrue(usuarioDAO.seleccionarUsuarioPorIdContrasena(ID_USUARIO10,TEST_CONTRASENA).getApellido().equalsIgnoreCase(TEST_APELLIDO));
			assertTrue(usuarioDAO.seleccionarUsuarioPorIdContrasena(ID_USUARIO10,TEST_CONTRASENA).getNombre().equalsIgnoreCase(TEST_NOMBRE));
			assertTrue(usuarioDAO.seleccionarUsuarioPorIdContrasena(ID_USUARIO10,TEST_CONTRASENA).getEmail().equalsIgnoreCase(TEST_EMAIL));
			assertTrue(usuarioDAO.seleccionarUsuarioPorIdContrasena(ID_USUARIO10,TEST_CONTRASENA).getTelefono().equalsIgnoreCase(TEST_TELEFONO));
			assertTrue(usuarioDAO.seleccionarUsuarioPorIdContrasena(ID_USUARIO10,TEST_CONTRASENA).getTipo().equalsIgnoreCase(TIPO_SOCIO));
			assertTrue(usuarioDAO.seleccionarUsuarioPorIdContrasena(ID_USUARIO10,TEST_CONTRASENA).isActivo());
		}
	}
	
	@Test
	public void testInsertarSeleccionarTiposHoraAUsuario() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		if(isHabilitarTest()){
			usuarioDAO.agregar(ID_USUARIO10,TEST_NOMBRE,TEST_APELLIDO,TEST_CONTRASENA,TEST_EMAIL, TEST_TELEFONO, TIPO_SOCIO,true);
			assertTrue(usuarioDAO.seleccionarUsuarioPorId(ID_USUARIO10).getApellido().equalsIgnoreCase(TEST_APELLIDO));
			tipoHoraDAO.agregar(TIPO_TEST);
			tipoHoraDAO.agregar(TIPO_TEST2);
			List<Integer> tiposHora = new ArrayList<Integer>();
			tiposHora.add(tipoHoraDAO.seleccionarPorTipo(TIPO_TEST).getId());
			tiposHora.add(tipoHoraDAO.seleccionarPorTipo(TIPO_TEST2).getId());
			usuarioDAO.agregarTipoHoraUsuario(tiposHora, ID_USUARIO10);
			assertTrue(usuarioDAO.verTiposHoraPorUsuario(ID_USUARIO10).size()==2);
			
		}
	}
	
	@Test
	public void testInsertarSeleccionarUsuarios() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		if(isHabilitarTest()){
			usuarioDAO.agregar(ID_USUARIO10,TEST_NOMBRE,TEST_APELLIDO,TEST_CONTRASENA,TEST_EMAIL, TEST_TELEFONO, TIPO_SOCIO,true);
			usuarioDAO.agregar(ID_USUARIO11,TEST_NOMBRE2,TEST_APELLIDO2,TEST_CONTRASENA2,TEST_EMAIL2, TEST_TELEFONO2, "TECNICO",true);
			assertTrue(usuarioDAO.seleccionarUsuarioPorId(ID_USUARIO10).getApellido().equalsIgnoreCase(TEST_APELLIDO));
			assertTrue(usuarioDAO.seleccionarUsuarioPorId(ID_USUARIO11).getApellido().equalsIgnoreCase(TEST_APELLIDO2));
			assertTrue(usuarioDAO.seleccionarUsuarios().size()==2);
		}
	}
	
	@Test
	public void testEditarUsuario() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		if(isHabilitarTest()){
			usuarioDAO.agregar(ID_USUARIO10,TEST_NOMBRE,TEST_APELLIDO,TEST_CONTRASENA,TEST_EMAIL, TEST_TELEFONO, TIPO_SOCIO,true);
			usuarioDAO.editarUsuario(ID_USUARIO10,TEST_NOMBRE2,TEST_APELLIDO2,TEST_EMAIL2, TEST_TELEFONO2);
			assertTrue(usuarioDAO.seleccionarUsuarioPorId(ID_USUARIO10).getApellido().equalsIgnoreCase(TEST_APELLIDO2));
			assertTrue(usuarioDAO.seleccionarUsuarioPorId(ID_USUARIO10).getNombre().equalsIgnoreCase(TEST_NOMBRE2));
			assertTrue(usuarioDAO.seleccionarUsuarioPorId(ID_USUARIO10).getEmail().equalsIgnoreCase(TEST_EMAIL2));
			assertTrue(usuarioDAO.seleccionarUsuarioPorId(ID_USUARIO10).getTelefono().equalsIgnoreCase(TEST_TELEFONO2));
			assertTrue(usuarioDAO.seleccionarUsuarioPorId(ID_USUARIO10).getTipo().equalsIgnoreCase(TIPO_SOCIO));
			assertTrue(usuarioDAO.seleccionarUsuarioPorId(ID_USUARIO10).isActivo());
		}
	}
	
	@Test
	public void testCambiarContrasena() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		if(isHabilitarTest()){
			usuarioDAO.agregar(ID_USUARIO10,TEST_NOMBRE,TEST_APELLIDO,TEST_CONTRASENA,TEST_EMAIL, TEST_TELEFONO, TIPO_SOCIO,true);
			usuarioDAO.cambiarContrasena(ID_USUARIO10, "contrasenaModificada");
			assertTrue(usuarioDAO.seleccionarUsuarioPorId(ID_USUARIO10).getApellido().equalsIgnoreCase(TEST_APELLIDO));
			assertTrue(usuarioDAO.seleccionarUsuarioPorId(ID_USUARIO10).getNombre().equalsIgnoreCase(TEST_NOMBRE));
			assertTrue(usuarioDAO.seleccionarUsuarioPorId(ID_USUARIO10).getEmail().equalsIgnoreCase(TEST_EMAIL));
			assertTrue(usuarioDAO.seleccionarUsuarioPorId(ID_USUARIO10).getTelefono().equalsIgnoreCase(TEST_TELEFONO));
			assertTrue(usuarioDAO.seleccionarUsuarioPorId(ID_USUARIO10).getTipo().equalsIgnoreCase(TIPO_SOCIO));
			assertTrue(usuarioDAO.seleccionarUsuarioPorId(ID_USUARIO10).isActivo());
		}
	}

}
