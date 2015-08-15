package test.java;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.proyectodegrado.sgti.consultas.ConsultasHora;
import com.proyectodegrado.sgti.daos.HoraDAO;
import com.proyectodegrado.sgti.daos.TipoHoraDAO;
import com.proyectodegrado.sgti.modelo.Hora;

public class ConsultasHoraTest extends ConfigurarTest{
	
	private static HoraDAO horaDao;
	private static ConsultasHora consultasHora;
	private static TipoHoraDAO tipoHoraDao;
	
	@BeforeClass
	public static void prepararContexto(){
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		consultasHora = (ConsultasHora) context.getBean("consultasHora");
		horaDao = (HoraDAO) context.getBean("horaDao");
		tipoHoraDao = (TipoHoraDAO) context.getBean("tipoHoraDao");
		prepararContextoDeConsultaContrato();
	}
	
	@AfterClass
	public static void cerrarContexto(){
		context.close();
	}
	
	@After
	public void borrarDatos() throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		if(isHabilitarTest()){
			consultasHora.borrarHoras();
			borrarRelacionadoConContrato();
		}
	}
	
	@Test
	public void testInsertar() throws FileNotFoundException, ClassNotFoundException, SQLException, IOException, ParseException{
		if(isHabilitarTest()){
			agregarRelacionadoConConsultasContrato();
			tipoHoraDao.agregar(TIPO_TEST);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat simpleDateFormatConHora = new SimpleDateFormat("dd/MM/yyyy hh:mm");
			Date fechaHoy = new Date();
			String stringFechaDesde = simpleDateFormat.format(fechaHoy) + " 09:00";
			String stringFechaHasta = simpleDateFormat.format(fechaHoy) + " 09:40";
			Date fechaDesde = simpleDateFormatConHora.parse(stringFechaDesde);
			Date fechaHasta = simpleDateFormatConHora.parse(stringFechaHasta);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fechaHoy);
			calendar.set(Calendar.MONTH, 3);
			Hora hora = new Hora();
			hora.setFechaDesde(fechaDesde);
			hora.setFechaHasta(fechaHasta);
			hora.setRemoto(false);
			hora.setIdUsuario(USUARIO_TEST);
			hora.setIdContrato(CONTRATO_TEST);
			hora.setIdActividad(ACTIVIDAD_TEST);
			hora.setFechaInformar(calendar.getTime());
			hora.setFechaFacturar(calendar.getTime());
			hora.setFechaComputar(calendar.getTime());
			hora.setNombreTipoHora(TIPO_TEST);
			hora.setDescripcion("DESCRIPCION_TEST");
			hora.setValidada(false);
			hora.setInformada(false);
			hora.setFacturada(false);
			
			horaDao.insertarHora(hora);
			assertEquals(1, horaDao.verHorasRegistradas().size());
			assertEquals(stringFechaDesde, simpleDateFormatConHora.format(horaDao.verHorasRegistradas().get(0).getFechaDesde()));
			assertEquals(stringFechaHasta, simpleDateFormatConHora.format(horaDao.verHorasRegistradasPorUsuario(USUARIO_TEST).get(0).getFechaHasta()));
			assertEquals(simpleDateFormat.format(calendar.getTime()), simpleDateFormat.format(horaDao.verHorasRegistradasNoInformadas(CONTRATO_TEST).get(0).getFechaInformar()));
			assertEquals(simpleDateFormat.format(calendar.getTime()), simpleDateFormat.format(horaDao.verHorasRegistradasNoFacturadas(CONTRATO_TEST).get(0).getFechaFacturar()));
			assertEquals(simpleDateFormat.format(calendar.getTime()), simpleDateFormat.format(horaDao.verHorasRegistradas().get(0).getFechaComputar()));
			assertEquals(ACTIVIDAD_TEST, horaDao.verHorasRegistradas().get(0).getIdActividad());
			assertEquals(TIPO_TEST, horaDao.verHorasRegistradas().get(0).getNombreTipoHora());
			assertEquals("DESCRIPCION_TEST", horaDao.verHorasRegistradas().get(0).getDescripcion());
			assertEquals(false, horaDao.verHorasRegistradas().get(0).isValidada());
			assertEquals(false, horaDao.verHorasRegistradas().get(0).isInformada());
			assertEquals(false, horaDao.verHorasRegistradas().get(0).isFacturada());
		}
	}
	
	@Test
	public void testEditar() throws FileNotFoundException, ClassNotFoundException, SQLException, IOException, ParseException{
		if(isHabilitarTest()){
			agregarRelacionadoConConsultasContrato();
			tipoHoraDao.agregar(TIPO_TEST);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat simpleDateFormatConHora = new SimpleDateFormat("dd/MM/yyyy hh:mm");
			Date fechaHoy = new Date();
			String stringFechaDesde = simpleDateFormat.format(fechaHoy) + " 09:00";
			String stringFechaHasta = simpleDateFormat.format(fechaHoy) + " 09:40";
			Date fechaDesde = simpleDateFormatConHora.parse(stringFechaDesde);
			Date fechaHasta = simpleDateFormatConHora.parse(stringFechaHasta);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fechaHoy);
			calendar.set(Calendar.MONTH, 3);
			Hora hora = new Hora();
			hora.setFechaDesde(fechaDesde);
			hora.setFechaHasta(fechaHasta);
			hora.setRemoto(false);
			hora.setIdUsuario(USUARIO_TEST);
			hora.setIdContrato(CONTRATO_TEST);
			hora.setIdActividad(ACTIVIDAD_TEST);
			hora.setFechaInformar(calendar.getTime());
			hora.setFechaFacturar(calendar.getTime());
			hora.setFechaComputar(calendar.getTime());
			hora.setNombreTipoHora(TIPO_TEST);
			hora.setDescripcion("DESCRIPCION_TEST");
			hora.setValidada(false);
			hora.setInformada(false);
			hora.setFacturada(false);
			
			horaDao.insertarHora(hora);
			stringFechaDesde = simpleDateFormat.format(fechaHoy) + " 10:00";
			stringFechaHasta = simpleDateFormat.format(fechaHoy) + " 10:40";
			fechaDesde = simpleDateFormatConHora.parse(stringFechaDesde);
			fechaHasta = simpleDateFormatConHora.parse(stringFechaHasta);
			calendar.set(Calendar.MONTH, 5);
			hora.setId(horaDao.verHorasRegistradas().get(0).getId());
			hora.setFechaDesde(fechaDesde);
			hora.setFechaHasta(fechaHasta);
			hora.setRemoto(true);
			hora.setIdUsuario(USUARIO_TEST2);
			hora.setIdContrato(CONTRATO_TEST);
			hora.setIdActividad(ACTIVIDAD_TEST2);
			hora.setFechaInformar(calendar.getTime());
			hora.setFechaFacturar(calendar.getTime());
			hora.setFechaComputar(calendar.getTime());
			hora.setNombreTipoHora(TIPO_TEST);
			hora.setDescripcion("DESCRIPCION_TEST2");
			hora.setValidada(true);
			hora.setInformada(true);
			hora.setFacturada(true);
			horaDao.editarHora(hora);
			assertEquals(1, horaDao.verHorasRegistradas().size());
			assertEquals(stringFechaDesde, simpleDateFormatConHora.format(horaDao.verHorasRegistradas().get(0).getFechaDesde()));
			assertEquals(stringFechaHasta, simpleDateFormatConHora.format(horaDao.verHorasRegistradasPorUsuario(USUARIO_TEST).get(0).getFechaHasta()));
			assertEquals(0, horaDao.verHorasRegistradasNoInformadas(CONTRATO_TEST).size());
			assertEquals(0, horaDao.verHorasRegistradasNoFacturadas(CONTRATO_TEST).size());
			assertEquals(simpleDateFormat.format(calendar.getTime()), simpleDateFormat.format(horaDao.verHorasRegistradas().get(0).getFechaComputar()));
			assertEquals(ACTIVIDAD_TEST2, horaDao.verHorasRegistradas().get(0).getIdActividad());
			assertEquals(TIPO_TEST, horaDao.verHorasRegistradas().get(0).getNombreTipoHora());
			assertEquals("DESCRIPCION_TEST2", horaDao.verHorasRegistradas().get(0).getDescripcion());
			assertEquals(true, horaDao.verHorasRegistradas().get(0).isValidada());
			assertEquals(true, horaDao.verHorasRegistradas().get(0).isInformada());
			assertEquals(true, horaDao.verHorasRegistradas().get(0).isFacturada());
		}
	}

}
