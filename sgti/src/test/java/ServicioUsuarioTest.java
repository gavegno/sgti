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
	public void borrarDatos() throws FileNotFoundException, IOException, SQLException{
		borrarTiposHoraUsuario();
		consultasUsuario.borrarUsuario("10");
		consultasUsuario.borrarUsuario("11");
		consultasUsuario.borrarUsuario("12");
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
	public void testAgregarUsuario() throws FileNotFoundException, IOException, SQLException{
		Usuario usuario = new Usuario("10","testNombre","testApellido","testContrasena","test@email.com", "12345678", null,false, null);
		Usuario usuarioSegundo = new Usuario("11","testNombre2","testApellido2","testContrasena2","test2@email.com", "12345679", null,false, null);
		Usuario usuarioTercero = new Usuario("12","testNombre3","testApellido3","testContrasena3","test3@email.com", "12345680", null,false, null);
		servicioUsuarioSocio.agregar(usuario);
		servicioUsuarioTecnico.agregar(usuarioSegundo);
		servicioUsuarioContraparte.agregar(usuarioTercero);
		
		assertTrue(servicioUsuario.seleccionarUsuarios().size() == 3);
		assertTrue(servicioUsuario.selecionarUsuario("10").getTipo().equalsIgnoreCase("SOCIO"));
		assertTrue(servicioUsuario.selecionarUsuario("11").getTipo().equalsIgnoreCase("TECNICO"));
		assertTrue(servicioUsuario.selecionarUsuario("12").getTipo().equalsIgnoreCase("CONTRAPARTE"));
		
	}
	
	@Test
	public void testAgregarUsuarioTipoHora() throws FileNotFoundException, IOException, SQLException{
		Usuario usuario = new Usuario("10","testNombre","testApellido","testContrasena","test@email.com", "12345678", null,false, null);
		TipoHora tipoHora = new TipoHora();
		tipoHora.setTipo("TIPO_TEST");
		List<TipoHora> tiposHora = new ArrayList<TipoHora>();
		servicioUsuarioSocio.agregar(usuario);
		servicioTipoHora.agregar(tipoHora);
		tipoHora = servicioTipoHora.seleccionarPorTipo("TIPO_TEST");
		tiposHora.add(tipoHora);
		usuario.setUsuarioTipoHora(tiposHora);
		servicioUsuario.agregarTipoHoraUsuario(usuario);
		
		assertTrue(servicioUsuario.selecionarUsuario("10").getTipo().equalsIgnoreCase("SOCIO"));
		assertTrue(servicioUsuario.verTiposHoraPorUsuario(usuario).size() == 1);
		assertTrue(servicioUsuario.verTiposHoraPorUsuario(usuario).get(0).getTipo().equalsIgnoreCase("TIPO_TEST"));
		
	}
	
	@Test
	public void testEditarUsuario() throws FileNotFoundException, IOException, SQLException{
		Usuario usuario = new Usuario("10","testNombre","testApellido","testContrasena","test@email.com", "12345678", null,false, null);
		servicioUsuarioSocio.agregar(usuario);
		usuario.setApellido("testApellido2");
		usuario.setNombre("testNombre2");
		usuario.setEmail("test2@email.com");
		servicioUsuario.editarUsuario(usuario);
		
		assertTrue(servicioUsuario.seleccionarUsuarios().size() == 1);
		assertTrue(servicioUsuario.selecionarUsuario("10").getApellido().equalsIgnoreCase("testApellido2"));
		assertTrue(servicioUsuario.selecionarUsuario("10").getNombre().equalsIgnoreCase("testNombre2"));
		assertTrue(servicioUsuario.selecionarUsuario("10").getEmail().equalsIgnoreCase("test2@email.com"));
		
	}
	
	@Test
	public void testCambiarContrasena() throws FileNotFoundException, IOException, SQLException{
		Usuario usuario = new Usuario("10","testNombre","testApellido","testContrasena","test@email.com", "12345678", null,false, null);
		servicioUsuarioSocio.agregar(usuario);
		assertTrue(usuarioDAO.verContraseñaUsuario("10").equalsIgnoreCase("testContrasena"));
		
		usuario.setContrasena("testContrasena2");
		servicioUsuario.cambiarContrasena(usuario);
		assertTrue(servicioUsuario.seleccionarUsuarios().size() == 1);
		assertTrue(usuarioDAO.verContraseñaUsuario("10").equalsIgnoreCase("testContrasena2"));
		
	}
	
	@Test
	public void testEliminarUsuario() throws FileNotFoundException, IOException, SQLException{
		Usuario usuario = new Usuario("10","testNombre","testApellido","testContrasena","test@email.com", "12345678", null,false, null);
		servicioUsuarioSocio.agregar(usuario);
		assertTrue(servicioUsuario.seleccionarUsuarios().size() == 1);
		assertTrue(servicioUsuario.selecionarUsuario("10").isActivo());
		
		servicioUsuario.eliminarUsuario(usuario);
		assertTrue(!servicioUsuario.selecionarUsuario("10").isActivo());
		
	}

}
