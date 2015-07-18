package test.java;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.proyectodegrado.sgti.consultas.ConsultasPrecio;
import com.proyectodegrado.sgti.daos.PrecioDAO;

public class ConsultasPrecioTest extends ConfigurarTest{
	
	private static ConsultasPrecio consultasPrecio;
	
	private static PrecioDAO precioDao;
	
	@BeforeClass
	public static void prepararContexto(){
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		consultasPrecio = (ConsultasPrecio) context.getBean("consultasPrecio");
		precioDao = (PrecioDAO) context.getBean("precioDao");
		prepararContextoDeConsultaContrato();
	}

	@AfterClass
	public static void cerrarContexto(){
		context.close();
	}
	
	@After
	public void borrarDatos() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		consultasPrecio.borrarPrecios();
		borrarRelacionadoConContrato();
	}

	@Test
	public void testInsertar() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		agregarRelacionadoConConsultasContrato();
		Calendar fechaHasta = Calendar.getInstance();
		fechaHasta.setTime(new Date());
		fechaHasta.set(Calendar.YEAR, 2020);
		precioDao.insertarPrecio(5.0, new Date(), fechaHasta.getTime(), CONTRATO_TEST);
		
		assertEquals(1, precioDao.verPrecios(CONTRATO_TEST).size());
		assertTrue(5.0 == precioDao.verPrecioActual(CONTRATO_TEST).getPrecio());
	}
}
