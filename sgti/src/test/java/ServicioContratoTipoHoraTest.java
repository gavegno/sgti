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

import com.proyectodegrado.sgti.consultas.ConsultasContratoTipoHora;
import com.proyectodegrado.sgti.consultas.ConsultasTipoHora;
import com.proyectodegrado.sgti.modelo.TipoHora;
import com.proyectodegrado.sgti.modelo.TipoHoraComputo;
import com.proyectodegrado.sgti.servicios.ServicioContratoTipoHora;
import com.proyectodegrado.sgti.servicios.ServicioTipoHora;

public class ServicioContratoTipoHoraTest extends ConfigurarTest{
	
	private static final int COMPUTOS_EDITADO = 5;

	private static final int COMPUTOS = 3;

	private static final int TAMAÑO_LISTA = 1;
	
	private static final int TAMAÑO_LISTA2 = 2;

	private static final String CONTRATO_TEST = "CONTRATO_TEST";

	private static final String TIPO_TEST = "TIPO_TEST";
	
	private static final String TIPO_TEST2 = "TIPO_TEST2";
	
	private static ServicioContratoTipoHora servicioContratoTipoHora;
	
	private static ConsultasContratoTipoHora consultasContratoTipoHora;
	
	private static ServicioTipoHora servicioTipoHora;
	
	private static ConsultasTipoHora consultasTipoHora;
	
	@BeforeClass
	public static void prepararContexto(){
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		consultasContratoTipoHora = (ConsultasContratoTipoHora) context.getBean("consultasContratoTipoHora");
		servicioContratoTipoHora = (ServicioContratoTipoHora) context.getBean("servicioContratoTipoHora");
		consultasTipoHora = (ConsultasTipoHora) context.getBean("consultasTipoHora");
		servicioTipoHora = (ServicioTipoHora) context.getBean("servicioTipoHora");
		prepararContextoDeServicioContrato();
	}
	
	@AfterClass
	public static void cerrarContexto(){
		context.close();
	}
	
	@After
	public void borrarDatos() throws FileNotFoundException, IOException, SQLException{
		consultasContratoTipoHora.borrarContratoTiposHora();
		consultasTipoHora.borrarTipoHora(TIPO_TEST);
		consultasTipoHora.borrarTipoHora(TIPO_TEST2);
		borrarRelacionadoConContrato();
	}
	
	@Test
	public void testInsertar() throws FileNotFoundException, IOException, SQLException{
		agregarRelacionadoConServicioContrato();
		TipoHora tipoHora = new TipoHora();
		tipoHora.setTipo(TIPO_TEST);
		servicioTipoHora.agregar(tipoHora);
		tipoHora.setId(servicioTipoHora.seleccionarPorTipo(TIPO_TEST).getId());
		TipoHoraComputo tipoHoraComputo = new TipoHoraComputo();
		tipoHoraComputo.setTipoHora(tipoHora);
		tipoHoraComputo.setComputo(COMPUTOS);
		servicioContratoTipoHora.insertar(CONTRATO_TEST, tipoHoraComputo);
		
		assertEquals(TAMAÑO_LISTA, servicioContratoTipoHora.verContratoTipoHora(CONTRATO_TEST).size());
		assertEquals(TIPO_TEST, servicioContratoTipoHora.verContratoTipoHora(CONTRATO_TEST).get(0).getTipoHora().getTipo());
		assertEquals(servicioTipoHora.seleccionarPorTipo(TIPO_TEST).getId(), servicioContratoTipoHora.verContratoTipoHora(CONTRATO_TEST).get(0).getTipoHora().getId());
		assertEquals(COMPUTOS, servicioContratoTipoHora.verContratoTipoHora(CONTRATO_TEST).get(0).getComputo());
	}
	
	@Test
	public void testInsertarDosTipos() throws FileNotFoundException, IOException, SQLException{
		agregarRelacionadoConServicioContrato();
		TipoHora tipoHora = new TipoHora();
		tipoHora.setTipo(TIPO_TEST);
		servicioTipoHora.agregar(tipoHora);
		tipoHora.setId(servicioTipoHora.seleccionarPorTipo(TIPO_TEST).getId());
		TipoHoraComputo tipoHoraComputo = new TipoHoraComputo();
		tipoHoraComputo.setTipoHora(tipoHora);
		tipoHoraComputo.setComputo(COMPUTOS);
		servicioContratoTipoHora.insertar(CONTRATO_TEST, tipoHoraComputo);
		tipoHora.setTipo(TIPO_TEST2);
		servicioTipoHora.agregar(tipoHora);
		tipoHora.setId(servicioTipoHora.seleccionarPorTipo(TIPO_TEST2).getId());
		servicioContratoTipoHora.insertar(CONTRATO_TEST, tipoHoraComputo);
		
		assertEquals(TAMAÑO_LISTA2, servicioContratoTipoHora.verContratoTipoHora(CONTRATO_TEST).size());
		assertEquals(TIPO_TEST, servicioContratoTipoHora.verContratoTipoHora(CONTRATO_TEST).get(0).getTipoHora().getTipo());
		assertEquals(servicioTipoHora.seleccionarPorTipo(TIPO_TEST).getId(), servicioContratoTipoHora.verContratoTipoHora(CONTRATO_TEST).get(0).getTipoHora().getId());
		assertEquals(COMPUTOS, servicioContratoTipoHora.verContratoTipoHora(CONTRATO_TEST).get(0).getComputo());
	}
	
	@Test
	public void testEditar() throws FileNotFoundException, IOException, SQLException{
		agregarRelacionadoConServicioContrato();
		TipoHora tipoHora = new TipoHora();
		tipoHora.setTipo(TIPO_TEST);
		servicioTipoHora.agregar(tipoHora);
		tipoHora.setId(servicioTipoHora.seleccionarPorTipo(TIPO_TEST).getId());
		tipoHora.setId(servicioTipoHora.seleccionarPorTipo(TIPO_TEST).getId());
		TipoHoraComputo tipoHoraComputo = new TipoHoraComputo();
		tipoHoraComputo.setTipoHora(tipoHora);
		tipoHoraComputo.setComputo(COMPUTOS);
		servicioContratoTipoHora.insertar(CONTRATO_TEST, tipoHoraComputo);
		
		assertEquals(TAMAÑO_LISTA, servicioContratoTipoHora.verContratoTipoHora(CONTRATO_TEST).size());
		assertEquals(TIPO_TEST, servicioContratoTipoHora.verContratoTipoHora(CONTRATO_TEST).get(0).getTipoHora().getTipo());
		assertEquals(servicioTipoHora.seleccionarPorTipo(TIPO_TEST).getId(), servicioContratoTipoHora.verContratoTipoHora(CONTRATO_TEST).get(0).getTipoHora().getId());
		assertEquals(COMPUTOS, servicioContratoTipoHora.verContratoTipoHora(CONTRATO_TEST).get(0).getComputo());
		
		tipoHoraComputo.setComputo(COMPUTOS_EDITADO);
		servicioContratoTipoHora.editarContratoTipoHora(CONTRATO_TEST, tipoHoraComputo);
		
		assertEquals(TAMAÑO_LISTA, servicioContratoTipoHora.verContratoTipoHora(CONTRATO_TEST).size());
		assertEquals(TIPO_TEST, servicioContratoTipoHora.verContratoTipoHora(CONTRATO_TEST).get(0).getTipoHora().getTipo());
		assertEquals(servicioTipoHora.seleccionarPorTipo(TIPO_TEST).getId(), servicioContratoTipoHora.verContratoTipoHora(CONTRATO_TEST).get(0).getTipoHora().getId());
		assertEquals(COMPUTOS_EDITADO, servicioContratoTipoHora.verContratoTipoHora(CONTRATO_TEST).get(0).getComputo());
		
	}

}
