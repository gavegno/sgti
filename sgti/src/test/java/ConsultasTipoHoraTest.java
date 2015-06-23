package test.java;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.proyectodegrado.sgti.consultas.ConsultasTipoHora;
import com.proyectodegrado.sgti.daos.TipoHoraDAO;

public class ConsultasTipoHoraTest extends ConfigurarTest{
	
	
	@Test
	public void testSeleccionarTipoHora() throws SQLException, FileNotFoundException, IOException{
		if(isHabilitarTest()){
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
			ConsultasTipoHora consultasTipoHora = (ConsultasTipoHora) context.getBean("consultasTipoHora");
			TipoHoraDAO tipoHoraDao = (TipoHoraDAO) context.getBean("tipoHoraDao");
			context.close();
			consultasTipoHora.insertarTipoHora("TIPO_TEST");
			assertTrue(tipoHoraDao.seleccionarPorTipo("TIPO_TEST").getTipo().equalsIgnoreCase("TIPO_TEST"));
			consultasTipoHora.borrarTipoHora("TIPO_TEST");
		}
	}
	
	@Test
	public void testSeleccionarTiposHora() throws SQLException, FileNotFoundException, IOException{
		if(isHabilitarTest()){
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
			ConsultasTipoHora consultasTipoHora = (ConsultasTipoHora) context.getBean("consultasTipoHora");
			TipoHoraDAO tipoHoraDao = (TipoHoraDAO) context.getBean("tipoHoraDao");
			context.close();
			consultasTipoHora.insertarTipoHora("TIPO_TEST");
			consultasTipoHora.insertarTipoHora("TIPO_TEST2");
			assertTrue(tipoHoraDao.seleccionarTipos().size()==2);
			consultasTipoHora.borrarTipoHora("TIPO_TEST");
			consultasTipoHora.borrarTipoHora("TIPO_TEST2");
		}
	}

}
