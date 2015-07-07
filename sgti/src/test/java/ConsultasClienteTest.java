package test.java;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.proyectodegrado.sgti.consultas.ConsultasCliente;
import com.proyectodegrado.sgti.daos.ClienteDAO;
import com.proyectodegrado.sgti.modelo.Cliente;

public class ConsultasClienteTest extends ConfigurarTest{
	
	private static ConsultasCliente consultasCliente;
	
	private static ClienteDAO clienteDAO;
	
	@BeforeClass
	public static void prepararContexto(){
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		consultasCliente = (ConsultasCliente) context.getBean("consultasCliente");
		clienteDAO = (ClienteDAO) context.getBean("clienteDao");
	}
	
	@AfterClass
	public static void cerrarContexto(){
		context.close();
	}
	
	@After
	public void borrarDatos() throws FileNotFoundException, IOException, SQLException{
		consultasCliente.borrarCliente("CLIENTE_TEST");
		consultasCliente.borrarCliente("CLIENTE_TEST2");
		consultasCliente.borrarCliente("CLIENTE_TEST_EDITADO");
	}
	
	@Test
	public void testAgregarCliente() throws FileNotFoundException, IOException, SQLException{
		clienteDAO.agregar("CLIENTE_TEST", "DIRECCION_TEST", "12345678", true);
		
		assertTrue(clienteDAO.seleccionarClientes().size()==1);
		assertTrue(clienteDAO.seleccionarPorNombre("CLIENTE_TEST").getNombre().equalsIgnoreCase("CLIENTE_TEST"));
		assertTrue(clienteDAO.seleccionarPorNombre("CLIENTE_TEST").getDireccion().equalsIgnoreCase("DIRECCION_TEST"));
		assertTrue(clienteDAO.seleccionarPorNombre("CLIENTE_TEST").getTelefono().equalsIgnoreCase("12345678"));
		assertTrue(clienteDAO.seleccionarPorNombre("CLIENTE_TEST").isActivo());
	}
	
	@Test
	public void testAgregarClientes() throws FileNotFoundException, IOException, SQLException{
		clienteDAO.agregar("CLIENTE_TEST", "DIRECCION_TEST", "12345678", true);
		clienteDAO.agregar("CLIENTE_TEST2", "DIRECCION_TEST2", "12345679", false);
		assertTrue(clienteDAO.seleccionarClientes().size()==2);
		assertTrue(clienteDAO.seleccionarPorNombre("CLIENTE_TEST").getNombre().equalsIgnoreCase("CLIENTE_TEST"));
		assertTrue(clienteDAO.seleccionarPorNombre("CLIENTE_TEST").getDireccion().equalsIgnoreCase("DIRECCION_TEST"));
		assertTrue(clienteDAO.seleccionarPorNombre("CLIENTE_TEST").getTelefono().equalsIgnoreCase("12345678"));
		assertTrue(clienteDAO.seleccionarPorNombre("CLIENTE_TEST").isActivo());
		
		assertTrue(clienteDAO.seleccionarPorNombre("CLIENTE_TEST2").getNombre().equalsIgnoreCase("CLIENTE_TEST2"));
		assertTrue(clienteDAO.seleccionarPorNombre("CLIENTE_TEST2").getDireccion().equalsIgnoreCase("DIRECCION_TEST2"));
		assertTrue(clienteDAO.seleccionarPorNombre("CLIENTE_TEST2").getTelefono().equalsIgnoreCase("12345679"));
		assertTrue(!clienteDAO.seleccionarPorNombre("CLIENTE_TEST2").isActivo());
	}
	
	@Test
	public void testEditarCliente() throws FileNotFoundException, IOException, SQLException{
		clienteDAO.agregar("CLIENTE_TEST", "DIRECCION_TEST", "12345678", true);
		assertTrue(clienteDAO.seleccionarClientes().size()==1);
		assertTrue(clienteDAO.seleccionarPorNombre("CLIENTE_TEST").getNombre().equalsIgnoreCase("CLIENTE_TEST"));
		assertTrue(clienteDAO.seleccionarPorNombre("CLIENTE_TEST").getDireccion().equalsIgnoreCase("DIRECCION_TEST"));
		assertTrue(clienteDAO.seleccionarPorNombre("CLIENTE_TEST").getTelefono().equalsIgnoreCase("12345678"));
		assertTrue(clienteDAO.seleccionarPorNombre("CLIENTE_TEST").isActivo());
		
		Cliente cliente = clienteDAO.seleccionarPorNombre("CLIENTE_TEST");
		clienteDAO.editarUsuario(cliente.getId(), "CLIENTE_TEST_EDITADO", "DIRECCION_TEST_CAMBIADA", "00000000");
		assertTrue(clienteDAO.seleccionarPorNombre("CLIENTE_TEST_EDITADO").getNombre().equalsIgnoreCase("CLIENTE_TEST_EDITADO"));
		assertTrue(clienteDAO.seleccionarPorNombre("CLIENTE_TEST_EDITADO").getDireccion().equalsIgnoreCase("DIRECCION_TEST_CAMBIADA"));
		assertTrue(clienteDAO.seleccionarPorNombre("CLIENTE_TEST_EDITADO").getTelefono().equalsIgnoreCase("00000000"));
		assertTrue(clienteDAO.seleccionarPorNombre("CLIENTE_TEST_EDITADO").getId() == cliente.getId());
		assertTrue(clienteDAO.seleccionarPorNombre("CLIENTE_TEST_EDITADO").isActivo());
	}
	
	@Test
	public void testCambiarActivo() throws FileNotFoundException, IOException, SQLException{
		clienteDAO.agregar("CLIENTE_TEST", "DIRECCION_TEST", "12345678", true);
		
		assertTrue(clienteDAO.seleccionarClientes().size()==1);
		assertTrue(clienteDAO.seleccionarPorNombre("CLIENTE_TEST").getNombre().equalsIgnoreCase("CLIENTE_TEST"));
		assertTrue(clienteDAO.seleccionarPorNombre("CLIENTE_TEST").getDireccion().equalsIgnoreCase("DIRECCION_TEST"));
		assertTrue(clienteDAO.seleccionarPorNombre("CLIENTE_TEST").getTelefono().equalsIgnoreCase("12345678"));
		assertTrue(clienteDAO.seleccionarPorNombre("CLIENTE_TEST").isActivo());
		
		clienteDAO.cambiarActivo(clienteDAO.seleccionarPorNombre("CLIENTE_TEST").getId(), false);
		assertTrue(!clienteDAO.seleccionarPorNombre("CLIENTE_TEST").isActivo());
	}

}
