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
import com.proyectodegrado.sgti.modelo.TipoHora;
import com.proyectodegrado.sgti.servicios.ServicioTipoHora;

public class ServicioTipoHoraTest extends ConfigurarTest{
	
	private static final String TIPO_TEST2 = "TIPO_TEST2";
	private static final String TIPO_TEST = "TIPO_TEST";
	private static ServicioTipoHora servicioTipoHora;
	private static ConsultasTipoHora consultasTipoHora;
	
	@BeforeClass
	public static void prepararContexto(){
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		consultasTipoHora = (ConsultasTipoHora) context.getBean("consultasTipoHora");
		servicioTipoHora = (ServicioTipoHora) context.getBean("servicioTipoHora");
	}
	
	@AfterClass
	public static void cerrarContexto(){
		context.close();
	}
	
	@After
	public void borrarDatos() throws FileNotFoundException, IOException, SQLException{
		consultasTipoHora.borrarTipoHora(TIPO_TEST);
		consultasTipoHora.borrarTipoHora(TIPO_TEST2);
	}
	
	@Test
	public void testearAgregar() throws FileNotFoundException, IOException, SQLException{
		if(isHabilitarTest()){
			TipoHora tipoHora = new TipoHora();
			tipoHora.setTipo(TIPO_TEST);
			servicioTipoHora.agregar(tipoHora);
			assertTrue(servicioTipoHora.seleccionarPorTipo(TIPO_TEST).getTipo().equalsIgnoreCase(TIPO_TEST));
		}
	}
	
	@Test
	public void testearSeleccionar() throws FileNotFoundException, IOException, SQLException{
		if(isHabilitarTest()){
			TipoHora tipoHora = new TipoHora();
			tipoHora.setTipo(TIPO_TEST);
			TipoHora segundoDataTipoHora = new TipoHora();
			segundoDataTipoHora.setTipo(TIPO_TEST2);
			servicioTipoHora.agregar(tipoHora);
			servicioTipoHora.agregar(segundoDataTipoHora);
			assertTrue(servicioTipoHora.seleccionarTipos().size()==2);

		}
	}

}
