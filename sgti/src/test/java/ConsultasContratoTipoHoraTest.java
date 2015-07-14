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
import com.proyectodegrado.sgti.daos.ContratoTipoHoraDAO;
import com.proyectodegrado.sgti.daos.TipoHoraDAO;

public class ConsultasContratoTipoHoraTest extends ConfigurarTest{
	
	private static final int COMPUTOS_EDITADOS = 5;

	private static final int COMPUTOS = 3;

	private static final int TAMAÑO_LISTA = 1;

	private static final String CONTRATO_TEST = "CONTRATO_TEST";

	private static final String TIPO_TEST = "TIPO_TEST";
	
	private static ContratoTipoHoraDAO contratoTipoHoraDao;
	
	private static ConsultasContratoTipoHora consultasContratoTipoHora;
	
	private static ConsultasTipoHora consultasTipoHora;
	
	private static TipoHoraDAO tipoHoraDao;
	
	@BeforeClass
	public static void prepararContexto(){
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		consultasContratoTipoHora = (ConsultasContratoTipoHora) context.getBean("consultasContratoTipoHora");
		contratoTipoHoraDao = (ContratoTipoHoraDAO) context.getBean("contratoTipoHoraDao");
		consultasTipoHora = (ConsultasTipoHora) context.getBean("consultasTipoHora");
		tipoHoraDao = (TipoHoraDAO) context.getBean("tipoHoraDao");
	}
	
	@AfterClass
	public static void cerrarContexto(){
		context.close();
	}
	
	@After
	public void borrarDatos() throws FileNotFoundException, IOException, SQLException{
		consultasContratoTipoHora.borrarContratoTiposHora();
		consultasTipoHora.borrarTipoHora(TIPO_TEST);
	}
	
	@Test
	public void testInsertarContratoTipoHora() throws FileNotFoundException, IOException, SQLException{
		tipoHoraDao.agregar(TIPO_TEST);
		contratoTipoHoraDao.insertarContratoTipoHora(CONTRATO_TEST, tipoHoraDao.seleccionarPorTipo(TIPO_TEST).getId(), COMPUTOS);
		
		assertEquals(TAMAÑO_LISTA, contratoTipoHoraDao.verContratoTipoHora(CONTRATO_TEST).size());		
		assertEquals(TIPO_TEST,contratoTipoHoraDao.verContratoTipoHora(CONTRATO_TEST).get(0).getTipoHora().getTipo());
		assertEquals(tipoHoraDao.seleccionarPorTipo(TIPO_TEST).getId(),contratoTipoHoraDao.verContratoTipoHora(CONTRATO_TEST).get(0).getTipoHora().getId());
		assertEquals(COMPUTOS,contratoTipoHoraDao.verContratoTipoHora(CONTRATO_TEST).get(0).getComputo());
		
	}
	
	@Test
	public void testEditarContratoTipoHora() throws FileNotFoundException, IOException, SQLException{
		tipoHoraDao.agregar(TIPO_TEST);
		contratoTipoHoraDao.insertarContratoTipoHora(CONTRATO_TEST, tipoHoraDao.seleccionarPorTipo(TIPO_TEST).getId(), COMPUTOS);
		
		assertEquals(TAMAÑO_LISTA, contratoTipoHoraDao.verContratoTipoHora(CONTRATO_TEST).size());		
		assertEquals(TIPO_TEST,contratoTipoHoraDao.verContratoTipoHora(CONTRATO_TEST).get(0).getTipoHora().getTipo());
		assertEquals(tipoHoraDao.seleccionarPorTipo(TIPO_TEST).getId(),contratoTipoHoraDao.verContratoTipoHora(CONTRATO_TEST).get(0).getTipoHora().getId());
		assertEquals(COMPUTOS,contratoTipoHoraDao.verContratoTipoHora(CONTRATO_TEST).get(0).getComputo());
		
		contratoTipoHoraDao.editarContratoTipoHora(CONTRATO_TEST, tipoHoraDao.seleccionarPorTipo(TIPO_TEST).getId(), COMPUTOS_EDITADOS);
		
		assertEquals(TAMAÑO_LISTA, contratoTipoHoraDao.verContratoTipoHora(CONTRATO_TEST).size());		
		assertEquals(TIPO_TEST,contratoTipoHoraDao.verContratoTipoHora(CONTRATO_TEST).get(0).getTipoHora().getTipo());
		assertEquals(tipoHoraDao.seleccionarPorTipo(TIPO_TEST).getId(),contratoTipoHoraDao.verContratoTipoHora(CONTRATO_TEST).get(0).getTipoHora().getId());
		assertEquals(COMPUTOS_EDITADOS,contratoTipoHoraDao.verContratoTipoHora(CONTRATO_TEST).get(0).getComputo());
		
	}

}
