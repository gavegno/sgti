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

import com.proyectodegrado.sgti.consultas.ConsultasTipoHora;
import com.proyectodegrado.sgti.daos.TipoHoraDAO;

public class ConsultasTipoHoraTest extends ConfigurarTest{
	
	private static ConsultasTipoHora consultasTipoHora;
	private static TipoHoraDAO tipoHoraDao;
	
	@BeforeClass
	public static void prepararContexto(){
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		consultasTipoHora = (ConsultasTipoHora) context.getBean("consultasTipoHora");
		tipoHoraDao = (TipoHoraDAO) context.getBean("tipoHoraDao");
	}
	
	@AfterClass
	public static void cerrarContexto(){
		context.close();
	}
	
	@After
	public void borrarDatos() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		if(isHabilitarTest()){
			consultasTipoHora.borrarTipoHora(TIPO_TEST);
			consultasTipoHora.borrarTipoHora(TIPO_TEST2);
		}
	}
	
	
	@Test
	public void testInsertarSeleccionarTipoHora() throws SQLException, FileNotFoundException, IOException, ClassNotFoundException{
		if(isHabilitarTest()){
			tipoHoraDao = (TipoHoraDAO) context.getBean("tipoHoraDao");
			consultasTipoHora.insertarTipoHora(TIPO_TEST);
			assertTrue(tipoHoraDao.seleccionarPorTipo(TIPO_TEST).getTipo().equalsIgnoreCase(TIPO_TEST));
		}
	}
	
	@Test
	public void testSeleccionarTiposHora() throws SQLException, FileNotFoundException, IOException, ClassNotFoundException{
		if(isHabilitarTest()){
			consultasTipoHora.insertarTipoHora(TIPO_TEST);
			consultasTipoHora.insertarTipoHora(TIPO_TEST2);
			assertTrue(tipoHoraDao.seleccionarTipos().size()==2);
		}
	}

}
