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

import com.proyectodegrado.sgti.daos.HorarioLaboralDAO;

public class ConsultasHorarioLaboralTest extends ConfigurarTest{
	
	private static HorarioLaboralDAO horarioLaboralDao;
	
	@BeforeClass
	public static void prepararContexto(){
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		horarioLaboralDao = (HorarioLaboralDAO) context.getBean("horarioLaboralDao");
	}
	
	@AfterClass
	public static void cerrarContexto(){
		context.close();
	}
	
	@After
	public void borrarDatos() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		if(isHabilitarTest()){
			horarioLaboralDao.borrarDiaDeHorarioLaboral(HORARIO_LABORAL_TEST, LUNES);
			horarioLaboralDao.borrarDiaDeHorarioLaboral(HORARIO_LABORAL_TEST, MARTES);
		}
	}
	
	@Test
	public void testInsertarHorarioLaboral() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		if(isHabilitarTest()){
			horarioLaboralDao.agregar(HORARIO_LABORAL_TEST, LUNES, HORARIO_ENTRADA1, HORARIO_SALIDA1);
			horarioLaboralDao.agregar(HORARIO_LABORAL_TEST, MARTES, HORARIO_ENTRADA1, HORARIO_SALIDA1);
			
			assertTrue(horarioLaboralDao.seleccionarHorarioLaboral(HORARIO_LABORAL_TEST).getDias().size() == 2);
		}
	}
	
	@Test
	public void testEditarDia() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		if(isHabilitarTest()){
			horarioLaboralDao.agregar(HORARIO_LABORAL_TEST, LUNES, HORARIO_ENTRADA1, HORARIO_SALIDA1);
			
			assertEquals(horarioLaboralDao.seleccionarHorarioLaboral(HORARIO_LABORAL_TEST).getDias().size(), 1);
			assertEquals(horarioLaboralDao.seleccionarHorarioLaboral(HORARIO_LABORAL_TEST).getDias().get(0).getNombre(),LUNES);
			assertEquals(horarioLaboralDao.seleccionarHorarioLaboral(HORARIO_LABORAL_TEST).getDias().get(0).getHoraDesde(),HORARIO_ENTRADA1);
			assertEquals(horarioLaboralDao.seleccionarHorarioLaboral(HORARIO_LABORAL_TEST).getDias().get(0).getHoraHasta(),HORARIO_SALIDA1);
			
			horarioLaboralDao.editarDiaDeHorarioLaboral(HORARIO_LABORAL_TEST, LUNES, HORARIO_ENTRADA2, HORARIO_SALIDA2);
			assertEquals(horarioLaboralDao.seleccionarHorarioLaboral(HORARIO_LABORAL_TEST).getDias().get(0).getNombre(),LUNES);
			assertEquals(horarioLaboralDao.seleccionarHorarioLaboral(HORARIO_LABORAL_TEST).getDias().get(0).getHoraDesde(),HORARIO_ENTRADA2);
			assertEquals(horarioLaboralDao.seleccionarHorarioLaboral(HORARIO_LABORAL_TEST).getDias().get(0).getHoraHasta(),HORARIO_SALIDA2);
		}
	}

}
