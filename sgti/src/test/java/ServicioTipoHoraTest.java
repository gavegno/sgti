package test.java;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.proyectodegrado.sgti.Data.DataTipoHora;
import com.proyectodegrado.sgti.consultas.ConsultasTipoHora;
import com.proyectodegrado.sgti.servicios.ServicioTipoHora;

public class ServicioTipoHoraTest extends ConfigurarTest{
	
	@Test
	public void testearAgregar() throws FileNotFoundException, IOException, SQLException{
		if(isHabilitarTest()){
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
			ServicioTipoHora servicioTipoHora = (ServicioTipoHora) context.getBean("servicioTipoHora");
			ConsultasTipoHora consultasTipoHora = (ConsultasTipoHora) context.getBean("consultasTipoHora");
			context.close();
			DataTipoHora dataTipoHora = new DataTipoHora();
			dataTipoHora.setTipo("TIPO_TEST");
			servicioTipoHora.agregar(dataTipoHora);
			assertTrue(servicioTipoHora.seleccionarPorTipo("TIPO_TEST").getTipo().equalsIgnoreCase("TIPO_TEST"));
			consultasTipoHora.borrarTipoHora("TIPO_TEST");
		}
	}
	
	@Test
	public void testearSeleccionar() throws FileNotFoundException, IOException, SQLException{
		if(isHabilitarTest()){
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
			ServicioTipoHora servicioTipoHora = (ServicioTipoHora) context.getBean("servicioTipoHora");
			ConsultasTipoHora consultasTipoHora = (ConsultasTipoHora) context.getBean("consultasTipoHora");
			context.close();
			DataTipoHora dataTipoHora = new DataTipoHora();
			dataTipoHora.setTipo("TIPO_TEST");
			DataTipoHora segundoDataTipoHora = new DataTipoHora();
			segundoDataTipoHora.setTipo("TIPO_TEST2");
			servicioTipoHora.agregar(dataTipoHora);
			servicioTipoHora.agregar(segundoDataTipoHora);
			assertTrue(servicioTipoHora.seleccionarTipos().size()==2);
			consultasTipoHora.borrarTipoHora("TIPO_TEST");
			consultasTipoHora.borrarTipoHora("TIPO_TEST2");
		}
	}

}
