package test.java;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.proyectodegrado.sgti.consultas.ConsultasConfiguracion;
import com.proyectodegrado.sgti.daos.ConfiguracionDAO;
import com.proyectodegrado.sgti.modelo.Configuracion;
import com.proyectodegrado.sgti.modelo.HorarioLaboral;
import com.proyectodegrado.sgti.servicios.ServicioConfiguracion;

public class ServicioConfiguracionTest extends ConfigurarTest{
	
	private static final String RENOVACION_TEST_EDITADA = "RENOVACION_TEST_EDITADA";

	private static final String TIEMPO_RESPUESTA_TEST = "TIEMPO_RESPUESTA_TEST";

	private static final String TIPO_CONTRATO_TEST = "TIPO_CONTRATO_TEST";

	private static final String RENOVACION_TEST = "RENOVACION_TEST";

	private static final String HORARIO_LABORAL_TEST = "HORARIO_LABORAL_TEST";

	private static ConsultasConfiguracion consultasConfiguracion;
	
	private static ServicioConfiguracion servicioConfiguracion;
	
	private static ConfiguracionDAO configuracionDAO;
	
	@BeforeClass
	public static void prepararContexto(){
		
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		consultasConfiguracion = (ConsultasConfiguracion) context.getBean("consultasConfiguracion");
		servicioConfiguracion = (ServicioConfiguracion) context.getBean("servicioConfiguracion");
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
	public void testInsertar() throws FileNotFoundException, IOException, SQLException{
		Configuracion configuracion = new Configuracion();
		HorarioLaboral horarioLaboral = new HorarioLaboral();
		horarioLaboral.setId(HORARIO_LABORAL_TEST);
		configuracion.setFechaInicio(new Date());
		configuracion.setFechaFin(new Date());
		configuracion.setRenovacion(RENOVACION_TEST);
		configuracion.setPeriodoRenovacion(12);
		configuracion.setTipoContrato(TIPO_CONTRATO_TEST);
		configuracion.setComputosPaquete(15);
		configuracion.setPeriodoValidezMes(1);
		configuracion.setPeriodoValidezDia(0);
		configuracion.setAcumulacion(false);
		configuracion.setPeriodoAcumulacion(0);
		configuracion.setFrecuenciaInforme(1);
		configuracion.setFrecuenciaFacturacion(1);
		configuracion.setFrecuenciaComputosExtra(3);
		configuracion.setTiempoRespuesta(TIEMPO_RESPUESTA_TEST);
		configuracion.setHorarioLaboral(horarioLaboral);
		
		servicioConfiguracion.insertar(configuracion);
		configuracion.setId(configuracionDAO.verConfiguracionesPorContrato("").get(0).getId());
		
		assertTrue(servicioConfiguracion.seleccionarConfiguracion(configuracion.getId()).getRenovacion().equalsIgnoreCase(RENOVACION_TEST));
		assertTrue(servicioConfiguracion.seleccionarConfiguracion(configuracion.getId()).getTipoContrato().equalsIgnoreCase(TIPO_CONTRATO_TEST));
		assertTrue(servicioConfiguracion.seleccionarConfiguracion(configuracion.getId()).getTiempoRespuesta().equalsIgnoreCase(TIEMPO_RESPUESTA_TEST));
	}
	
	@Test
	public void testEditar() throws FileNotFoundException, IOException, SQLException{
		Configuracion configuracion = new Configuracion();
		HorarioLaboral horarioLaboral = new HorarioLaboral();
		horarioLaboral.setId(HORARIO_LABORAL_TEST);
		configuracion.setFechaInicio(new Date());
		configuracion.setFechaFin(new Date());
		configuracion.setRenovacion(RENOVACION_TEST);
		configuracion.setPeriodoRenovacion(12);
		configuracion.setTipoContrato(TIPO_CONTRATO_TEST);
		configuracion.setComputosPaquete(15);
		configuracion.setPeriodoValidezMes(1);
		configuracion.setPeriodoValidezDia(0);
		configuracion.setAcumulacion(false);
		configuracion.setPeriodoAcumulacion(0);
		configuracion.setFrecuenciaInforme(1);
		configuracion.setFrecuenciaFacturacion(1);
		configuracion.setFrecuenciaComputosExtra(3);
		configuracion.setTiempoRespuesta(TIEMPO_RESPUESTA_TEST);
		configuracion.setHorarioLaboral(horarioLaboral);
		
		servicioConfiguracion.insertar(configuracion);
		configuracion.setRenovacion(RENOVACION_TEST_EDITADA);
		configuracion.setId(configuracionDAO.verConfiguracionesPorContrato("").get(0).getId());
		servicioConfiguracion.editarConfiguracion(configuracion);
		
		assertTrue(servicioConfiguracion.seleccionarConfiguracion(configuracion.getId()).getRenovacion().equalsIgnoreCase(RENOVACION_TEST_EDITADA));
	}

}
