package test.java;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.proyectodegrado.sgti.consultas.ConsultasPrecio;
import com.proyectodegrado.sgti.exceptions.SgtiException;
import com.proyectodegrado.sgti.modelo.Precio;
import com.proyectodegrado.sgti.servicios.ServicioPrecio;

public class ServicioPrecioTest extends ConfigurarTest{
	
	private static final String FORMATO_FECHA = "yyyy-MM-dd";

	private static ServicioPrecio servicioPrecio;
	private static ConsultasPrecio consultasPrecio;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@BeforeClass
	public static void prepararContexto(){
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		consultasPrecio = (ConsultasPrecio) context.getBean("consultasPrecio");
		servicioPrecio = (ServicioPrecio) context.getBean("servicioPrecio");
		prepararContextoDeServicioContrato();
	}
	
	@AfterClass
	public static void cerrarContexto(){
		context.close();
	}
	
	@After
	public void borrarDatos() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		if(isHabilitarTest()){
			consultasPrecio.borrarPrecios();
			borrarRelacionadoConContrato();
		}
	}
	
	@Test
	public void testInsertar() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException, SgtiException{
		if(isHabilitarTest()){
			agregarRelacionadoConServicioContrato();
			SimpleDateFormat dateFormat = new SimpleDateFormat(FORMATO_FECHA);
			Calendar fechaHasta = Calendar.getInstance();
			fechaHasta.set(Calendar.YEAR, 2020);
			Precio precio = new Precio(5.5, 6.5, new Date(), fechaHasta.getTime());
			servicioPrecio.insertar(precio, CONTRATO_TEST);
			
			assertEquals(1, servicioPrecio.seleccionarPrecios(CONTRATO_TEST).size());
			assertTrue(5.5 == servicioPrecio.seleccionarPrecios(CONTRATO_TEST).get(0).getPrecio());
			assertEquals(dateFormat.format(new Date()), servicioPrecio.seleccionarPrecios(CONTRATO_TEST).get(0).getFechaDesde().toString());
			assertEquals(dateFormat.format(fechaHasta.getTime()), servicioPrecio.seleccionarPrecios(CONTRATO_TEST).get(0).getFechaHasta().toString());
		}
	}
	
	@Test
	public void testPrecioVigente() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException, SgtiException{
		if(isHabilitarTest()){
			agregarRelacionadoConServicioContrato();
			SimpleDateFormat dateFormat = new SimpleDateFormat(FORMATO_FECHA);
			Calendar fechaDesdeVieja = Calendar.getInstance();
			Calendar fechaHastaVieja = Calendar.getInstance();
			Calendar fechaHasta = Calendar.getInstance();
			fechaHasta.set(Calendar.YEAR, 2020);
			fechaDesdeVieja.set(Calendar.YEAR, 1990);
			fechaHastaVieja.set(Calendar.YEAR, 2008);
			Precio precio = new Precio(5.5, 6.5, new Date(), fechaHasta.getTime());
			Precio precioViejo = new Precio(5.5, 6.5, fechaDesdeVieja.getTime(), fechaHastaVieja.getTime());
			servicioPrecio.insertar(precio, CONTRATO_TEST);
			servicioPrecio.insertar(precioViejo, CONTRATO_TEST);
			
			assertEquals(2, servicioPrecio.seleccionarPrecios(CONTRATO_TEST).size());
			assertTrue(5.5 == servicioPrecio.seleccionarPrecioActual(CONTRATO_TEST).getPrecio());
			assertEquals(dateFormat.format(new Date()), servicioPrecio.seleccionarPrecioActual(CONTRATO_TEST).getFechaDesde().toString());
			assertEquals(dateFormat.format(fechaHasta.getTime()), servicioPrecio.seleccionarPrecioActual(CONTRATO_TEST).getFechaHasta().toString());
		}
	}
	
	@Test
	public void testPreciosSuperpuestos() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException, SgtiException{
		if(isHabilitarTest()){
			agregarRelacionadoConServicioContrato();
			Calendar fechaDesdeVieja = Calendar.getInstance();
			Calendar fechaHastaVieja = Calendar.getInstance();
			Calendar fechaDesde = Calendar.getInstance();
			Calendar fechaHasta = Calendar.getInstance();
			fechaDesde.set(Calendar.YEAR, 1991);
			fechaHasta.set(Calendar.YEAR, 2020);
			fechaDesdeVieja.set(Calendar.YEAR, 1990);
			fechaHastaVieja.set(Calendar.YEAR, 2008);
			Precio precio = new Precio(5.5, 6.5, fechaDesde.getTime(), fechaHasta.getTime());
			Precio precioViejo = new Precio(5.5, 6.5, fechaDesdeVieja.getTime(), fechaHastaVieja.getTime());
			servicioPrecio.insertar(precioViejo, CONTRATO_TEST);
			thrown.expect(SQLException.class);
			thrown.expectMessage("El precio ingresado se superpone con otro precio");
			servicioPrecio.insertar(precio, CONTRATO_TEST);
			
			assertEquals(1, servicioPrecio.seleccionarPrecios(CONTRATO_TEST).size());
		}
	}

}
