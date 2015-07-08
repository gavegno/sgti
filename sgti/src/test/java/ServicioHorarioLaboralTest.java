package test.java;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.proyectodegrado.sgti.modelo.Dia;
import com.proyectodegrado.sgti.modelo.HorarioLaboral;
import com.proyectodegrado.sgti.servicios.impl.ServicioHorarioLaboral;

public class ServicioHorarioLaboralTest extends ConfigurarTest{
	
	private static final String HORARIO_SALIDA2 = "18:30";
	private static final String HORARIO_ENTRADA2 = "09:30";
	private static final String HORARIO_SALIDA1 = "18:00";
	private static final String HORARIO_ENTRADA1 = "09:00";
	private static final String MARTES = "Martes";
	private static final String LUNES = "Lunes";
	private static final String HORARIO_LABORAL_TEST = "HORARIO_LABORAL_TEST";
	private static ServicioHorarioLaboral servicioHorarioLaboral;
	
	@BeforeClass
	public static void prepararContexto(){
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		servicioHorarioLaboral = (ServicioHorarioLaboral) context.getBean("servicioHorarioLaboral");
	}
	
	@AfterClass
	public static void cerrarContexto(){
		context.close();
	}
	
	@After
	public void borrarDatos() throws FileNotFoundException, IOException, SQLException{
		Dia diaLunes = new Dia(LUNES, HORARIO_ENTRADA1, HORARIO_SALIDA1);
		Dia diaMartes = new Dia(MARTES, HORARIO_ENTRADA1, HORARIO_SALIDA1);
		servicioHorarioLaboral.borrarDiaDeHorarioLaboral(HORARIO_LABORAL_TEST, diaLunes);
		servicioHorarioLaboral.borrarDiaDeHorarioLaboral(HORARIO_LABORAL_TEST, diaMartes);
	}
	
	@Test
	public void testInsertarHorarioLaboral() throws FileNotFoundException, IOException, SQLException{
		HorarioLaboral horarioLaboral = new HorarioLaboral(HORARIO_LABORAL_TEST);
		Dia diaLunes = new Dia(LUNES, HORARIO_ENTRADA1, HORARIO_SALIDA1);
		Dia diaMartes = new Dia(MARTES, HORARIO_ENTRADA1, HORARIO_SALIDA1);
		List<Dia> dias = new ArrayList<Dia>();
		dias.add(diaLunes);
		dias.add(diaMartes);
		horarioLaboral.setDias(dias);
		servicioHorarioLaboral.insertar(horarioLaboral);
		
		assertTrue(servicioHorarioLaboral.seleccionarHorarioLaboral(horarioLaboral.getId()).getDias().size() == 2);
	}
	
	@Test
	public void testEditarDia() throws FileNotFoundException, IOException, SQLException{
		HorarioLaboral horarioLaboral = new HorarioLaboral(HORARIO_LABORAL_TEST);
		Dia diaLunes = new Dia(LUNES, HORARIO_ENTRADA1, HORARIO_SALIDA1);
		List<Dia> dias = new ArrayList<Dia>();
		dias.add(diaLunes);
		horarioLaboral.setDias(dias);
		servicioHorarioLaboral.insertar(horarioLaboral);
		
		assertEquals(servicioHorarioLaboral.seleccionarHorarioLaboral(HORARIO_LABORAL_TEST).getDias().size(), 1);
		assertEquals(servicioHorarioLaboral.seleccionarHorarioLaboral(HORARIO_LABORAL_TEST).getDias().get(0).getNombre(),LUNES);
		assertEquals(servicioHorarioLaboral.seleccionarHorarioLaboral(HORARIO_LABORAL_TEST).getDias().get(0).getHoraDesde(),HORARIO_ENTRADA1);
		assertEquals(servicioHorarioLaboral.seleccionarHorarioLaboral(HORARIO_LABORAL_TEST).getDias().get(0).getHoraHasta(),HORARIO_SALIDA1);
		
		diaLunes.setHoraDesde(HORARIO_ENTRADA2);
		diaLunes.setHoraHasta(HORARIO_SALIDA2);
		
		servicioHorarioLaboral.editarDiaDeHorarioLaboral(horarioLaboral.getId(), diaLunes);
		assertEquals(servicioHorarioLaboral.seleccionarHorarioLaboral(HORARIO_LABORAL_TEST).getDias().get(0).getNombre(),LUNES);
		assertEquals(servicioHorarioLaboral.seleccionarHorarioLaboral(HORARIO_LABORAL_TEST).getDias().get(0).getHoraDesde(),HORARIO_ENTRADA2);
		assertEquals(servicioHorarioLaboral.seleccionarHorarioLaboral(HORARIO_LABORAL_TEST).getDias().get(0).getHoraHasta(),HORARIO_SALIDA2);
	}

}
