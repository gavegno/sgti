package test.java;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.proyectodegrado.sgti.consultas.ConsultasConfiguracion;
import com.proyectodegrado.sgti.daos.ConfiguracionDAO;

public class ConsultasConfiguracionTest extends ConfigurarTest{
	
	private static ConsultasConfiguracion consultasConfiguracion;
	
	private static ConfiguracionDAO configuracionDAO;
	
	@BeforeClass
	public static void prepararContexto(){
		
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		consultasConfiguracion = (ConsultasConfiguracion) context.getBean("consultasConfiguracion");
		configuracionDAO = (ConfiguracionDAO) context.getBean("configuracionDao");
	}
	
	@AfterClass
	public static void cerrarContexto(){
		context.close();
	}
	
	@After
	public void borrarDatos() throws FileNotFoundException, IOException, SQLException{
		consultasConfiguracion.borrarConfiguraciones();
	}
	
	@Test
	public void testInsertarConfiguracion() throws FileNotFoundException, IOException, SQLException{
		Date fechaInicio = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar fechaFin = Calendar.getInstance();
		fechaFin.set(Calendar.YEAR, 2017);
		fechaFin.set(Calendar.MONTH, 12);
		fechaFin.set(Calendar.DAY_OF_MONTH, 31);
		configuracionDAO.insertarConfiguracion(fechaInicio, fechaFin.getTime(), "RENOVACION_TEST", 3, "TIPO_TEST", 12, 0, 7, true, 1, 1, 3, 3, 
				"TIEMPO_ESPUESTA_TEST", "HORARIO_TEST", "CONTRATO_TEST");
		
		assertTrue(configuracionDAO.verConfiguracionesPorContrato("CONTRATO_TEST").size() == 1);
		assertTrue(configuracionDAO.verConfiguracionesPorContrato("CONTRATO_TEST").get(0).isAcumulacion());
		assertTrue(configuracionDAO.verConfiguracionesPorContrato("CONTRATO_TEST").get(0).getRenovacion().equalsIgnoreCase("RENOVACION_TEST"));
		assertTrue(configuracionDAO.verConfiguracionesPorContrato("CONTRATO_TEST").get(0).getTipoContrato().equalsIgnoreCase("TIPO_TEST"));
		assertTrue(configuracionDAO.verConfiguracionesPorContrato("CONTRATO_TEST").get(0).getTiempoRespuesta().equalsIgnoreCase("TIEMPO_ESPUESTA_TEST"));
		assertTrue(configuracionDAO.verConfiguracionesPorContrato("CONTRATO_TEST").get(0).getFechaInicio().toString().equalsIgnoreCase(dateFormat.format(fechaInicio)));
		assertTrue(configuracionDAO.verConfiguracionesPorContrato("CONTRATO_TEST").get(0).getFechaFin().toString().equalsIgnoreCase(dateFormat.format(fechaFin.getTime())));
	}
	
	@Test
	public void testVerConfiguracionesEntreFechas() throws FileNotFoundException, IOException, SQLException{
		Date fechaInicio = new Date();
		Calendar fechaFin = Calendar.getInstance();
		fechaFin.set(Calendar.YEAR, 2017);
		fechaFin.set(Calendar.MONTH, 12);
		fechaFin.set(Calendar.DAY_OF_MONTH, 31);
		Calendar fechaFinHasta = Calendar.getInstance();
		fechaFinHasta.set(Calendar.YEAR, 2019);
		fechaFinHasta.set(Calendar.MONTH, 12);
		fechaFinHasta.set(Calendar.DAY_OF_MONTH, 31);
		configuracionDAO.insertarConfiguracion(fechaInicio, fechaFin.getTime(), "RENOVACION_TEST", 3, "TIPO_TEST", 12, 0, 7, true, 1, 1, 3, 3, 
				"TIEMPO_ESPUESTA_TEST", "HORARIO_TEST", "CONTRATO_TEST");
		
		assertTrue(configuracionDAO.verConfiguracionesEntreFechasFin(fechaInicio, fechaFinHasta.getTime(), "CONTRATO_TEST").get(0).getTipoContrato().equalsIgnoreCase("TIPO_TEST"));
	}
	
	@Test
	public void testVerConfiguracionesEntreFechasCasoBorde() throws FileNotFoundException, IOException, SQLException{
		Date fechaInicio = new Date();
		Calendar fechaFin = Calendar.getInstance();
		fechaFin.set(Calendar.YEAR, 2017);
		fechaFin.set(Calendar.MONTH, 12);
		fechaFin.set(Calendar.DAY_OF_MONTH, 31);
		configuracionDAO.insertarConfiguracion(fechaInicio, fechaFin.getTime(), "RENOVACION_TEST", 3, "TIPO_TEST", 12, 0, 7, true, 1, 1, 3, 3, 
				"TIEMPO_ESPUESTA_TEST", "HORARIO_TEST", "CONTRATO_TEST");
		
		assertTrue(configuracionDAO.verConfiguracionesEntreFechasFin(fechaFin.getTime(), fechaFin.getTime(), "CONTRATO_TEST").get(0).getTipoContrato().equalsIgnoreCase("TIPO_TEST"));
		assertTrue(configuracionDAO.verConfiguracionesEntreFechasFin(fechaInicio, fechaInicio, "CONTRATO_TEST").size() == 0);
	}
	
	@Test
	public void testEditarConfiguracion() throws FileNotFoundException, IOException, SQLException{
		Date fechaInicio = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar fechaFin = Calendar.getInstance();
		fechaFin.set(Calendar.YEAR, 2017);
		fechaFin.set(Calendar.MONTH, 12);
		fechaFin.set(Calendar.DAY_OF_MONTH, 31);
		configuracionDAO.insertarConfiguracion(fechaInicio, fechaFin.getTime(), "RENOVACION_TEST", 3, "TIPO_TEST", 12, 0, 7, true, 1, 1, 3, 3, 
				"TIEMPO_ESPUESTA_TEST", "HORARIO_TEST", "CONTRATO_TEST");
		
		assertTrue(configuracionDAO.verConfiguracionesPorContrato("CONTRATO_TEST").size() == 1);
		assertTrue(configuracionDAO.verConfiguracionesPorContrato("CONTRATO_TEST").get(0).isAcumulacion());
		assertTrue(configuracionDAO.verConfiguracionesPorContrato("CONTRATO_TEST").get(0).getRenovacion().equalsIgnoreCase("RENOVACION_TEST"));
		assertTrue(configuracionDAO.verConfiguracionesPorContrato("CONTRATO_TEST").get(0).getTipoContrato().equalsIgnoreCase("TIPO_TEST"));
		assertTrue(configuracionDAO.verConfiguracionesPorContrato("CONTRATO_TEST").get(0).getTiempoRespuesta().equalsIgnoreCase("TIEMPO_ESPUESTA_TEST"));
		assertTrue(configuracionDAO.verConfiguracionesPorContrato("CONTRATO_TEST").get(0).getFechaInicio().toString().equalsIgnoreCase(dateFormat.format(fechaInicio)));
		assertTrue(configuracionDAO.verConfiguracionesPorContrato("CONTRATO_TEST").get(0).getFechaFin().toString().equalsIgnoreCase(dateFormat.format(fechaFin.getTime())));
	
		
		configuracionDAO.editarConfiguracion(configuracionDAO.verConfiguracionesPorContrato("CONTRATO_TEST").get(0).getId(), fechaInicio, fechaFin.getTime(), 
				"RENOVACION_TEST2", 1, "TIPO_TEST2", 10, 2, 0, false, 2, 2, 2, 2, "TIEMPO_RESPUESTA_TEST2", "HORARIO_TEST2");
		
		assertTrue(configuracionDAO.verConfiguracionesPorContrato("CONTRATO_TEST").size() == 1);
		assertTrue(!configuracionDAO.verConfiguracionesPorContrato("CONTRATO_TEST").get(0).isAcumulacion());
		assertTrue(configuracionDAO.verConfiguracionesPorContrato("CONTRATO_TEST").get(0).getRenovacion().equalsIgnoreCase("RENOVACION_TEST2"));
		assertTrue(configuracionDAO.verConfiguracionesPorContrato("CONTRATO_TEST").get(0).getTipoContrato().equalsIgnoreCase("TIPO_TEST2"));
		assertTrue(configuracionDAO.verConfiguracionesPorContrato("CONTRATO_TEST").get(0).getTiempoRespuesta().equalsIgnoreCase("TIEMPO_RESPUESTA_TEST2"));
		assertTrue(configuracionDAO.verConfiguracionesPorContrato("CONTRATO_TEST").get(0).getFechaInicio().toString().equalsIgnoreCase(dateFormat.format(fechaInicio)));
		assertTrue(configuracionDAO.verConfiguracionesPorContrato("CONTRATO_TEST").get(0).getFechaFin().toString().equalsIgnoreCase(dateFormat.format(fechaFin.getTime())));
	}
}
