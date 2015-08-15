package test.java;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.proyectodegrado.sgti.consultas.ConsultasConfiguracion;
import com.proyectodegrado.sgti.daos.ConfiguracionDAO;
import com.proyectodegrado.sgti.exceptions.SgtiException;
import com.proyectodegrado.sgti.modelo.Configuracion;
import com.proyectodegrado.sgti.modelo.Dia;
import com.proyectodegrado.sgti.modelo.HorarioLaboral;
import com.proyectodegrado.sgti.servicios.ServicioConfiguracion;

public class ServicioConfiguracionTest extends ConfigurarTest{
	
	private static final String RENOVACION_TEST_EDITADA = "RENOVACION_TEST_EDITADA";
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	private static ConsultasConfiguracion consultasConfiguracion;
	private static ServicioConfiguracion servicioConfiguracion;
	private static ConfiguracionDAO configuracionDAO;
	
	@BeforeClass
	public static void prepararContexto(){
		
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		consultasConfiguracion = (ConsultasConfiguracion) context.getBean("consultasConfiguracion");
		servicioConfiguracion = (ServicioConfiguracion) context.getBean("servicioConfiguracion");
		configuracionDAO = (ConfiguracionDAO) context.getBean("configuracionDao");
		prepararContextoDeServicioContrato();
	}
	
	@AfterClass
	public static void cerrarContexto(){
		context.close();
	}
	
	@After
	public void borrarDatos() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		if(isHabilitarTest()){
			consultasConfiguracion.borrarConfiguraciones();
			borrarRelacionadoConContrato();
		}
	}
	
	@Test
	public void testInsertar() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException, SgtiException{
		if(isHabilitarTest()){
			agregarRelacionadoConServicioContrato();
			Configuracion configuracion = new Configuracion();
			HorarioLaboral horarioLaboral = new HorarioLaboral(HORARIO_LABORAL_TEST);
			horarioLaboral.setDias(new ArrayList<Dia>());
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
			
			servicioConfiguracion.insertar(configuracion, CONTRATO_TEST);
			configuracion.setId(configuracionDAO.verConfiguracionesPorContrato(CONTRATO_TEST).get(0).getId());
			
			assertTrue(servicioConfiguracion.seleccionarConfiguracion(configuracion.getId()).getRenovacion().equalsIgnoreCase(RENOVACION_TEST));
			assertTrue(servicioConfiguracion.seleccionarConfiguracion(configuracion.getId()).getTipoContrato().equalsIgnoreCase(TIPO_CONTRATO_TEST));
			assertTrue(servicioConfiguracion.seleccionarConfiguracion(configuracion.getId()).getTiempoRespuesta().equalsIgnoreCase(TIEMPO_RESPUESTA_TEST));
		}
	}
	
	@Test
	public void testEditar() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException, SgtiException{
		if(isHabilitarTest()){
			agregarRelacionadoConServicioContrato();
			Configuracion configuracion = new Configuracion();
			HorarioLaboral horarioLaboral = new HorarioLaboral(HORARIO_LABORAL_TEST);
			horarioLaboral.setId(HORARIO_LABORAL_TEST);
			horarioLaboral.setDias(new ArrayList<Dia>());
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
			
			servicioConfiguracion.insertar(configuracion, CONTRATO_TEST);
			configuracion.setRenovacion(RENOVACION_TEST_EDITADA);
			configuracion.setId(configuracionDAO.verConfiguracionesPorContrato(CONTRATO_TEST).get(0).getId());
			servicioConfiguracion.editarConfiguracion(configuracion);
			
			assertTrue(servicioConfiguracion.seleccionarConfiguracion(configuracion.getId()).getRenovacion().equalsIgnoreCase(RENOVACION_TEST_EDITADA));
		}
	}
	
	@Test
	public void testConfiguracionSuperpuesta() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException, SgtiException{
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
			Configuracion configuracion = new Configuracion();
			HorarioLaboral horarioLaboral = new HorarioLaboral(HORARIO_LABORAL_TEST);
			horarioLaboral.setId(HORARIO_LABORAL_TEST);
			horarioLaboral.setDias(new ArrayList<Dia>());
			configuracion.setFechaInicio(fechaDesdeVieja.getTime());
			configuracion.setFechaFin(fechaHastaVieja.getTime());
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
			servicioConfiguracion.insertar(configuracion, CONTRATO_TEST);
			configuracion.setFechaInicio(fechaDesde.getTime());
			configuracion.setFechaFin(fechaHasta.getTime());
			thrown.expect(SQLException.class);
			thrown.expectMessage("La configuración ingresada se superpone con otro precio");
			
			servicioConfiguracion.insertar(configuracion, CONTRATO_TEST);
		}
		
	}
	
	@Test
	public void testConfiguracionActual() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException, SgtiException{
		if(isHabilitarTest()){
			agregarRelacionadoConServicioContrato();
			Calendar fechaDesdeVieja = Calendar.getInstance();
			Calendar fechaHastaVieja = Calendar.getInstance();
			Calendar fechaDesde = Calendar.getInstance();
			Calendar fechaHasta = Calendar.getInstance();
			fechaDesde.set(Calendar.YEAR, 2010);
			fechaHasta.set(Calendar.YEAR, 2020);
			fechaDesdeVieja.set(Calendar.YEAR, 1990);
			fechaHastaVieja.set(Calendar.YEAR, 2008);
			Configuracion configuracion = new Configuracion();
			HorarioLaboral horarioLaboral = new HorarioLaboral(HORARIO_LABORAL_TEST);
			horarioLaboral.setId(HORARIO_LABORAL_TEST);
			horarioLaboral.setDias(new ArrayList<Dia>());
			configuracion.setFechaInicio(fechaDesdeVieja.getTime());
			configuracion.setFechaFin(fechaHastaVieja.getTime());
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
			servicioConfiguracion.insertar(configuracion, CONTRATO_TEST);
			configuracion.setFechaInicio(fechaDesde.getTime());
			configuracion.setFechaFin(fechaHasta.getTime());
			servicioConfiguracion.insertar(configuracion, CONTRATO_TEST);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			assertEquals(dateFormat.format(fechaDesde.getTime()),servicioConfiguracion.seleccionarConfiguracionActual(CONTRATO_TEST).getFechaInicio().toString());
			assertEquals(dateFormat.format(fechaHasta.getTime()),servicioConfiguracion.seleccionarConfiguracionActual(CONTRATO_TEST).getFechaFin().toString());
		}
	}

}
