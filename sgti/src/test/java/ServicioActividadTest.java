package test.java;

import static org.junit.Assert.assertEquals;

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
import com.proyectodegrado.sgti.exceptions.SgtiException;
import com.proyectodegrado.sgti.modelo.Actividad;
import com.proyectodegrado.sgti.servicios.ServicioActividad;

public class ServicioActividadTest extends ConfigurarTest{

	private static ServicioActividad servicioActividad;
	private static ConsultasActividad consultasActividad;
	
	@BeforeClass
	public static void prepararContexto(){
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		consultasActividad = (ConsultasActividad) context.getBean("consultasActividad");
		servicioActividad = (ServicioActividad) context.getBean("servicioActividad");
		prepararContextoDeServicioContrato();
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
	public void testAgregar() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, SgtiException{
		if(isHabilitarTest()){
			agregarRelacionadoConServicioContrato();
			SimpleDateFormat dateFormat = new SimpleDateFormat(FORMATO_FECHA);
			Date fechaCreacion = new Date();
			Calendar fechaActividad = Calendar.getInstance();
			fechaActividad.set(Calendar.MONTH, 12);
			fechaActividad.set(Calendar.DAY_OF_MONTH, 31);
			Actividad actividad = new Actividad(ACTIVIDAD_TEST, TIPO_ACTIVIDAD_TEST, CONTRATO_TEST, USUARIO_TEST, fechaCreacion,fechaActividad.getTime(),3," ");
			
			servicioActividad.agregar(actividad);
			
			assertEquals(1, servicioActividad.seleccionarActividades().size());
			assertEquals(3, servicioActividad.seleccionarActividades().get(0).getPeriodo());
			assertEquals(TIPO_ACTIVIDAD_TEST, servicioActividad.seleccionarActividadesPorContrato(CONTRATO_TEST).get(0).getTipo());
			assertEquals(ACTIVIDAD_TEST, servicioActividad.seleccionarActividadesPorContrato(CONTRATO_TEST).get(0).getId());
			assertEquals(dateFormat.format(fechaCreacion), servicioActividad.seleccionarActividadesPorUsuario(USUARIO_TEST).get(0).getFechaCreacion().toString());
			assertEquals(dateFormat.format(fechaActividad.getTime()), servicioActividad.seleccionarActividadesPorUsuario(USUARIO_TEST).get(0).getFechaActividad().toString());
		}
	}
	
	@Test
	public void testEditar() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, SgtiException{
		if(isHabilitarTest()){
			agregarRelacionadoConServicioContrato();
			SimpleDateFormat dateFormat = new SimpleDateFormat(FORMATO_FECHA);
			Date fechaCreacion = new Date();
			Calendar fechaActividad = Calendar.getInstance();
			fechaActividad.set(Calendar.MONTH, 12);
			fechaActividad.set(Calendar.DAY_OF_MONTH, 31);
			Actividad actividad = new Actividad(ACTIVIDAD_TEST, TIPO_ACTIVIDAD_TEST, CONTRATO_TEST, USUARIO_TEST, fechaCreacion,fechaCreacion,3," ");
			
			servicioActividad.agregar(actividad);
			
			assertEquals(1, servicioActividad.seleccionarActividades().size());
			assertEquals(3, servicioActividad.seleccionarActividades().get(0).getPeriodo());
			assertEquals(TIPO_ACTIVIDAD_TEST, servicioActividad.seleccionarActividadesPorContrato(CONTRATO_TEST).get(0).getTipo());
			assertEquals(ACTIVIDAD_TEST, servicioActividad.seleccionarActividadesPorContrato(CONTRATO_TEST).get(0).getId());
			assertEquals(dateFormat.format(fechaCreacion), servicioActividad.seleccionarActividadesPorUsuario(USUARIO_TEST).get(0).getFechaCreacion().toString());
			assertEquals(dateFormat.format(fechaCreacion), servicioActividad.seleccionarActividadesPorUsuario(USUARIO_TEST).get(0).getFechaActividad().toString());
			
			actividad.setFechaActividad(fechaActividad.getTime());
			actividad.setPeriodo(1);
			servicioActividad.editar(actividad);
			
			assertEquals(1, servicioActividad.seleccionarActividades().size());
			assertEquals(1, servicioActividad.seleccionarActividades().get(0).getPeriodo());
			assertEquals(TIPO_ACTIVIDAD_TEST, servicioActividad.seleccionarActividadesPorContrato(CONTRATO_TEST).get(0).getTipo());
			assertEquals(ACTIVIDAD_TEST, servicioActividad.seleccionarActividadesPorContrato(CONTRATO_TEST).get(0).getId());
			assertEquals(dateFormat.format(fechaCreacion), servicioActividad.seleccionarActividadesPorUsuario(USUARIO_TEST).get(0).getFechaCreacion().toString());
			assertEquals(dateFormat.format(fechaActividad.getTime()), servicioActividad.seleccionarActividadesPorUsuario(USUARIO_TEST).get(0).getFechaActividad().toString());
		}
	}
	
}
