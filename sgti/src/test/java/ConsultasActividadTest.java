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
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.proyectodegrado.sgti.consultas.ConsultasActividad;
import com.proyectodegrado.sgti.daos.ActividadDAO;
import com.proyectodegrado.sgti.modelo.Actividad;

public class ConsultasActividadTest extends ConfigurarTest{
	
	private static ActividadDAO actividadDao;
	private static ConsultasActividad consultasActividad;
	
	@BeforeClass
	public static void prepararContexto(){
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		consultasActividad = (ConsultasActividad) context.getBean("consultasActividad");
		actividadDao = (ActividadDAO) context.getBean("actividadDao");
		prepararContextoDeConsultaContrato();
	}
	
	@AfterClass
	public static void cerrarContexto(){
		context.close();
	}
	
	@After
	public void borrarDatos() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		if(isHabilitarTest()){
			consultasActividad.borrarActividades();
			borrarRelacionadoConContrato();
		}
	}
	
	@Test
	public void testInsertar() throws FileNotFoundException, ClassNotFoundException, SQLException, IOException{
		if(isHabilitarTest()){
			agregarRelacionadoConConsultasContrato();
			SimpleDateFormat dateFormat = new SimpleDateFormat(FORMATO_FECHA);
			Date fechaCreacion = new Date();
			Calendar fechaActividad = Calendar.getInstance();
			fechaActividad.set(Calendar.MONTH, 12);
			fechaActividad.set(Calendar.DAY_OF_MONTH, 31);
			Actividad actividad = new Actividad(ACTIVIDAD_TEST, TIPO_ACTIVIDAD_TEST, CONTRATO_TEST, USUARIO_TEST, fechaCreacion,fechaActividad.getTime(),3," ");
			actividadDao.insertarActividad(actividad);
			
			assertEquals(1, actividadDao.verActividades().size());
			assertEquals(3, actividadDao.verActividades().get(0).getPeriodo());
			assertEquals(TIPO_ACTIVIDAD_TEST, actividadDao.verActividadesPorContrato(CONTRATO_TEST).get(0).getTipo());
			assertEquals(ACTIVIDAD_TEST, actividadDao.verActividadesPorContrato(CONTRATO_TEST).get(0).getId());
			assertEquals(dateFormat.format(fechaCreacion), actividadDao.verActividadesPorUsuario(USUARIO_TEST).get(0).getFechaCreacion().toString());
			assertEquals(dateFormat.format(fechaActividad.getTime()), actividadDao.verActividadesPorUsuario(USUARIO_TEST).get(0).getFechaActividad().toString());
		}
	}
	
	@Test
	public void testEditar() throws FileNotFoundException, ClassNotFoundException, SQLException, IOException{
		if(isHabilitarTest()){
			agregarRelacionadoConConsultasContrato();
			SimpleDateFormat dateFormat = new SimpleDateFormat(FORMATO_FECHA);
			Date fechaCreacion = new Date();
			Calendar fechaActividad = Calendar.getInstance();
			fechaActividad.set(Calendar.MONTH, 12);
			fechaActividad.set(Calendar.DAY_OF_MONTH, 31);
			Actividad actividad = new Actividad(ACTIVIDAD_TEST, TIPO_ACTIVIDAD_TEST, CONTRATO_TEST, USUARIO_TEST, fechaCreacion,fechaCreacion,3," ");
			actividadDao.insertarActividad(actividad);
			
			assertEquals(1, actividadDao.verActividades().size());
			assertEquals(3, actividadDao.verActividades().get(0).getPeriodo());
			assertEquals(TIPO_ACTIVIDAD_TEST, actividadDao.verActividadesPorContrato(CONTRATO_TEST).get(0).getTipo());
			assertEquals(ACTIVIDAD_TEST, actividadDao.verActividadesPorContrato(CONTRATO_TEST).get(0).getId());
			assertEquals(dateFormat.format(fechaCreacion), actividadDao.verActividadesPorUsuario(USUARIO_TEST).get(0).getFechaCreacion().toString());
			assertEquals(dateFormat.format(fechaCreacion), actividadDao.verActividadesPorUsuario(USUARIO_TEST).get(0).getFechaActividad().toString());
			
			actividad.setPeriodo(1);
			actividad.setFechaActividad(fechaActividad.getTime());
			actividadDao.editarActividad(actividad);
			
			assertEquals(1, actividadDao.verActividades().size());
			assertEquals(1, actividadDao.verActividades().get(0).getPeriodo());
			assertEquals(TIPO_ACTIVIDAD_TEST, actividadDao.verActividadesPorContrato(CONTRATO_TEST).get(0).getTipo());
			assertEquals(ACTIVIDAD_TEST, actividadDao.verActividadesPorContrato(CONTRATO_TEST).get(0).getId());
			assertEquals(dateFormat.format(fechaCreacion), actividadDao.verActividadesPorUsuario(USUARIO_TEST).get(0).getFechaCreacion().toString());
			assertEquals(dateFormat.format(fechaActividad.getTime()), actividadDao.verActividadesPorUsuario(USUARIO_TEST).get(0).getFechaActividad().toString());
		}
	}
	
	

}
