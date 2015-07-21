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
import com.proyectodegrado.sgti.daos.UsuarioDAO;
import com.proyectodegrado.sgti.modelo.TipoHora;
import com.proyectodegrado.sgti.modelo.Usuario;
import com.proyectodegrado.sgti.servicios.ServicioTipoHora;
import com.proyectodegrado.sgti.servicios.ServicioUsuario;
import com.proyectodegrado.sgti.servicios.impl.ServicioUsuarioContraparteImpl;
import com.proyectodegrado.sgti.servicios.impl.ServicioUsuarioSocioImpl;
import com.proyectodegrado.sgti.servicios.impl.ServicioUsuarioTecnicoImpl;

public class ServicioUsuarioTest extends ConfigurarTest{
	
	private static final String TEST_CONTRASENA2 = "testContrasena2";
	private static final String TEST_EMAIL2 = "test2@email.com";
	private static final String TEST_NOMBRE2 = "testNombre2";
	private static final String TEST_APELLIDO2 = "testApellido2";
	private static final String TEST_TELEFONO = "12345678";
	private static final String TEST_EMAIL = "test@email.com";
	private static final String TEST_CONTRASENA = "testContrasena";
	private static final String TEST_APELLIDO = "testApellido";
	private static final String TEST_NOMBRE = "testNombre";
	private static final String TIPO_CONTRAPARTE = "CONTRAPARTE";
	private static final String TIPO_TECNICO = "TECNICO";
	private static final String TIPO_SOCIO = "SOCIO";
	private static final String ID = "id";
	private static final String TIPO_TEST2 = "TIPO_TEST2";
	private static final String TIPO_TEST = "TIPO_TEST";
	private static final String ID_USUARIO12 = "12";
	private static final String ID_USUARIO11 = "11";
	private static final String ID_USUARIO10 = "10";
	private static ServicioUsuario servicioUsuario;
	private static ServicioTipoHora servicioTipoHora;
	private static ServicioUsuarioSocioImpl servicioUsuarioSocio;
	private static ServicioUsuarioTecnicoImpl servicioUsuarioTecnico;
	private static ServicioUsuarioContraparteImpl servicioUsuarioContraparte;
	private static ConsultasUsuario consultasUsuario;
	private static UsuarioDAO usuarioDAO;
	private static ConsultasTipoHora consultasTipoHora;
	
	@BeforeClass
	public static void prepararContexto(){
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		consultasUsuario = (ConsultasUsuario) context.getBean("consultasUsuario");
		servicioUsuario = (ServicioUsuario) context.getBean("servicioUsuario");
		servicioUsuarioSocio = (ServicioUsuarioSocioImpl) context.getBean("servicioUsuarioSocio");
		servicioUsuarioTecnico = (ServicioUsuarioTecnicoImpl) context.getBean("servicioUsuarioTecnico");
		servicioUsuarioContraparte = (ServicioUsuarioContraparteImpl) context.getBean("servicioUsuarioContraparte");
		usuarioDAO = (UsuarioDAO) context.getBean("usuarioDao");
		consultasTipoHora = (ConsultasTipoHora) context.getBean("consultasTipoHora");
		servicioTipoHora = (ServicioTipoHora) context.getBean("servicioTipoHora");
	}
	
	@AfterClass
	public static void cerrarContexto(){
		context.close();
	}
	
	@After
	public void borrarDatos() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		borrarTiposHoraUsuario();
		consultasUsuario.borrarUsuario(ID_USUARIO10);
		consultasUsuario.borrarUsuario(ID_USUARIO11);
		consultasUsuario.borrarUsuario(ID_USUARIO12);
		consultasTipoHora.borrarTipoHora(TIPO_TEST);
		consultasTipoHora.borrarTipoHora(TIPO_TEST2);
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
	public void testAgregarUsuario() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		if(isHabilitarTest()){
		Usuario usuario = new Usuario(ID_USUARIO10,TEST_NOMBRE,TEST_APELLIDO,TEST_CONTRASENA,TEST_EMAIL, TEST_TELEFONO, null,false, null);
		Usuario usuarioSegundo = new Usuario(ID_USUARIO11,TEST_NOMBRE2,TEST_APELLIDO2,TEST_CONTRASENA2,TEST_EMAIL2, "12345679", null,false, null);
		Usuario usuarioTercero = new Usuario(ID_USUARIO12,"testNombre3","testApellido3","testContrasena3","test3@email.com", "12345680", null,false, null);
		servicioUsuarioSocio.agregar(usuario);
		servicioUsuarioTecnico.agregar(usuarioSegundo);
		servicioUsuarioContraparte.agregar(usuarioTercero);
		
		assertTrue(servicioUsuario.seleccionarUsuarios().size() == 3);
		assertTrue(servicioUsuario.selecionarUsuario(ID_USUARIO10).getTipo().equalsIgnoreCase(TIPO_SOCIO));
		assertTrue(servicioUsuario.selecionarUsuario(ID_USUARIO11).getTipo().equalsIgnoreCase(TIPO_TECNICO));
		assertTrue(servicioUsuario.selecionarUsuario(ID_USUARIO12).getTipo().equalsIgnoreCase(TIPO_CONTRAPARTE));
		}
	}
	
	@Test
	public void testExisteUsuario() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		if(isHabilitarTest()){
		Usuario usuario = new Usuario(ID_USUARIO10,TEST_NOMBRE,TEST_APELLIDO,TEST_CONTRASENA,TEST_EMAIL, TEST_TELEFONO, null,false, null);
		servicioUsuarioSocio.agregar(usuario);
		Usuario usuarioLoguear = new Usuario();
		usuarioLoguear.setId(ID_USUARIO10);
		usuarioLoguear.setContrasena(TEST_CONTRASENA);
		
		assertTrue(servicioUsuarioSocio.existeUsuario(usuario));
		}
	}
	
	@Test
	public void testAgregarUsuarioTipoHora() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		if(isHabilitarTest()){
		Usuario usuario = new Usuario(ID_USUARIO10,TEST_NOMBRE,TEST_APELLIDO,TEST_CONTRASENA,TEST_EMAIL, TEST_TELEFONO, null,false, null);
		TipoHora tipoHora = new TipoHora();
		tipoHora.setTipo(TIPO_TEST);
		List<TipoHora> tiposHora = new ArrayList<TipoHora>();
		servicioUsuarioSocio.agregar(usuario);
		servicioTipoHora.agregar(tipoHora);
		tipoHora = servicioTipoHora.seleccionarPorTipo(TIPO_TEST);
		tiposHora.add(tipoHora);
		usuario.setUsuarioTipoHora(tiposHora);
		servicioUsuario.agregarTipoHoraUsuario(usuario);
		
		assertTrue(servicioUsuario.selecionarUsuario(ID_USUARIO10).getTipo().equalsIgnoreCase(TIPO_SOCIO));
		assertTrue(servicioUsuario.verTiposHoraPorUsuario(usuario).size() == 1);
		assertTrue(servicioUsuario.verTiposHoraPorUsuario(usuario).get(0).getTipo().equalsIgnoreCase(TIPO_TEST));
		}
		
	}
	
	@Test
	public void testEditarUsuario() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		if(isHabilitarTest()){
		Usuario usuario = new Usuario(ID_USUARIO10,TEST_NOMBRE,TEST_APELLIDO,TEST_CONTRASENA,TEST_EMAIL, TEST_TELEFONO, null,false, null);
		servicioUsuarioSocio.agregar(usuario);
		usuario.setApellido(TEST_APELLIDO2);
		usuario.setNombre(TEST_NOMBRE2);
		usuario.setEmail(TEST_EMAIL2);
		servicioUsuario.editarUsuario(usuario);
		
		assertTrue(servicioUsuario.seleccionarUsuarios().size() == 1);
		assertTrue(servicioUsuario.selecionarUsuario(ID_USUARIO10).getApellido().equalsIgnoreCase(TEST_APELLIDO2));
		assertTrue(servicioUsuario.selecionarUsuario(ID_USUARIO10).getNombre().equalsIgnoreCase(TEST_NOMBRE2));
		assertTrue(servicioUsuario.selecionarUsuario(ID_USUARIO10).getEmail().equalsIgnoreCase(TEST_EMAIL2));
		}
		
	}
	
	@Test
	public void testCambiarContrasena() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		if(isHabilitarTest()){
		Usuario usuario = new Usuario(ID_USUARIO10,TEST_NOMBRE,TEST_APELLIDO,TEST_CONTRASENA,TEST_EMAIL, TEST_TELEFONO, null,false, null);
		servicioUsuarioSocio.agregar(usuario);
		assertTrue(usuarioDAO.verContrasenaUsuario(ID_USUARIO10).equalsIgnoreCase(TEST_CONTRASENA));
		
		usuario.setContrasena(TEST_CONTRASENA2);
		servicioUsuario.cambiarContrasena(usuario);
		assertTrue(servicioUsuario.seleccionarUsuarios().size() == 1);
		assertTrue(usuarioDAO.verContrasenaUsuario(ID_USUARIO10).equalsIgnoreCase(TEST_CONTRASENA2));
		}
		
	}
	
	@Test
	public void testEliminarUsuario() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		if(isHabilitarTest()){
		Usuario usuario = new Usuario(ID_USUARIO10,TEST_NOMBRE,TEST_APELLIDO,TEST_CONTRASENA,TEST_EMAIL, TEST_TELEFONO, null,false, null);
		servicioUsuarioSocio.agregar(usuario);
		assertTrue(servicioUsuario.seleccionarUsuarios().size() == 1);
		assertTrue(servicioUsuario.selecionarUsuario(ID_USUARIO10).isActivo());
		
		servicioUsuario.eliminarUsuario(usuario);
		assertTrue(!servicioUsuario.selecionarUsuario(ID_USUARIO10).isActivo());
		}
	}

}
