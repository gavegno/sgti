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
import com.proyectodegrado.sgti.modelo.Cliente;
import com.proyectodegrado.sgti.servicios.impl.ServicioCliente;

public class ServicioClienteTest extends ConfigurarTest{
	
	private static ServicioCliente servicioCliente;
	private static ConsultasCliente consultasCliente;
	
	@BeforeClass
	public static void prepararContexto(){
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		consultasCliente = (ConsultasCliente) context.getBean("consultasCliente");
		servicioCliente = (ServicioCliente) context.getBean("servicioCliente");
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
		Cliente cliente = new Cliente();
		cliente.setNombre("CLIENTE_TEST");
		cliente.setDireccion("DIRECCION_TEST");
		cliente.setTelefono("12345678");
		servicioCliente.agregar(cliente);
		
		assertTrue(servicioCliente.verClientes().size() == 1);
		assertTrue(servicioCliente.verPorNombre(cliente).getNombre().equalsIgnoreCase("CLIENTE_TEST"));
		assertTrue(servicioCliente.verPorNombre(cliente).getDireccion().equalsIgnoreCase("DIRECCION_TEST"));
		assertTrue(servicioCliente.verPorNombre(cliente).getTelefono().equalsIgnoreCase("12345678"));
		assertTrue(servicioCliente.verPorNombre(cliente).isActivo());
	}
	
	@Test
	public void testAgregarClientes() throws FileNotFoundException, IOException, SQLException{
		Cliente cliente = new Cliente();
		cliente.setNombre("CLIENTE_TEST");
		cliente.setDireccion("DIRECCION_TEST");
		cliente.setTelefono("12345678");
		
		Cliente clienteNuevo = new Cliente();
		clienteNuevo.setNombre("CLIENTE_TEST2");
		clienteNuevo.setDireccion("DIRECCION_TEST2");
		clienteNuevo.setTelefono("12345679");
		
		servicioCliente.agregar(cliente);
		servicioCliente.agregar(clienteNuevo);
		
		assertTrue(servicioCliente.verClientes().size() == 2);
		
		assertTrue(servicioCliente.verPorNombre(cliente).getNombre().equalsIgnoreCase("CLIENTE_TEST"));
		assertTrue(servicioCliente.verPorNombre(cliente).getDireccion().equalsIgnoreCase("DIRECCION_TEST"));
		assertTrue(servicioCliente.verPorNombre(cliente).getTelefono().equalsIgnoreCase("12345678"));
		assertTrue(servicioCliente.verPorNombre(cliente).isActivo());
		
		assertTrue(servicioCliente.verPorNombre(clienteNuevo).getNombre().equalsIgnoreCase("CLIENTE_TEST2"));
		assertTrue(servicioCliente.verPorNombre(clienteNuevo).getDireccion().equalsIgnoreCase("DIRECCION_TEST2"));
		assertTrue(servicioCliente.verPorNombre(clienteNuevo).getTelefono().equalsIgnoreCase("12345679"));
		assertTrue(servicioCliente.verPorNombre(clienteNuevo).isActivo());
	}
	
	@Test
	public void testEditarCliente() throws FileNotFoundException, IOException, SQLException{
		Cliente cliente = new Cliente();
		cliente.setNombre("CLIENTE_TEST");
		cliente.setDireccion("DIRECCION_TEST");
		cliente.setTelefono("12345678");
		servicioCliente.agregar(cliente);
		
		assertTrue(servicioCliente.verClientes().size() == 1);
		assertTrue(servicioCliente.verPorNombre(cliente).getNombre().equalsIgnoreCase("CLIENTE_TEST"));
		assertTrue(servicioCliente.verPorNombre(cliente).getDireccion().equalsIgnoreCase("DIRECCION_TEST"));
		assertTrue(servicioCliente.verPorNombre(cliente).getTelefono().equalsIgnoreCase("12345678"));
		assertTrue(servicioCliente.verPorNombre(cliente).isActivo());
		
		cliente.setId(servicioCliente.verPorNombre(cliente).getId());
		cliente.setNombre("CLIENTE_TEST_EDITADO");
		cliente.setDireccion("DIRECCION_TEST_CAMBIADA");
		cliente.setTelefono("00000000");
		servicioCliente.editarCliente(cliente);
		
		assertTrue(servicioCliente.verClientes().size() == 1);
		assertTrue(servicioCliente.verPorNombre(cliente).getNombre().equalsIgnoreCase("CLIENTE_TEST_EDITADO"));
		assertTrue(servicioCliente.verPorNombre(cliente).getDireccion().equalsIgnoreCase("DIRECCION_TEST_CAMBIADA"));
		assertTrue(servicioCliente.verPorNombre(cliente).getTelefono().equalsIgnoreCase("00000000"));
		assertTrue(servicioCliente.verPorNombre(cliente).isActivo());
	}
	
	@Test
	public void testEliminarCliente() throws FileNotFoundException, IOException, SQLException{
		Cliente cliente = new Cliente();
		cliente.setNombre("CLIENTE_TEST");
		cliente.setDireccion("DIRECCION_TEST");
		cliente.setTelefono("12345678");
		servicioCliente.agregar(cliente);
		
		assertTrue(servicioCliente.verClientes().size() == 1);
		assertTrue(servicioCliente.verPorNombre(cliente).getNombre().equalsIgnoreCase("CLIENTE_TEST"));
		assertTrue(servicioCliente.verPorNombre(cliente).getDireccion().equalsIgnoreCase("DIRECCION_TEST"));
		assertTrue(servicioCliente.verPorNombre(cliente).getTelefono().equalsIgnoreCase("12345678"));
		assertTrue(servicioCliente.verPorNombre(cliente).isActivo());
		
		cliente.setId(servicioCliente.verPorNombre(cliente).getId());
		servicioCliente.eliminarCliente(cliente);
		assertTrue(!servicioCliente.verPorNombre(cliente).isActivo());
		
	}

}
