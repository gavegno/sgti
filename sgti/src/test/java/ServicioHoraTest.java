package test.java;

import static org.junit.Assert.assertEquals;

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
import com.proyectodegrado.sgti.exceptions.SgtiException;
import com.proyectodegrado.sgti.modelo.Hora;
import com.proyectodegrado.sgti.modelo.TipoHora;
import com.proyectodegrado.sgti.servicios.ServicioHora;
import com.proyectodegrado.sgti.servicios.ServicioTipoHora;

public class ServicioHoraTest extends ConfigurarTest{
	
	private static ServicioHora servicioHora;
	private static ServicioTipoHora servicioTipoHora;
	private static ConsultasHora consultasHora;
	
	@BeforeClass
	public static void prepararContexto(){
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		consultasHora = (ConsultasHora) context.getBean("consultasHora");
		servicioHora = (ServicioHora) context.getBean("servicioHora");
		servicioTipoHora = (ServicioTipoHora) context.getBean("servicioTipoHora");
		prepararContextoDeServicioContrato();
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
	public void testInsertar() throws FileNotFoundException, ClassNotFoundException, SQLException, IOException, ParseException, SgtiException{
			if(isHabilitarTest()){
			agregarRelacionadoConConsultasContrato();
			TipoHora tipoHora = new TipoHora();
			tipoHora.setTipo(TIPO_TEST);
			servicioTipoHora.agregar(tipoHora);
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
			
			servicioHora.agregar(hora);
			assertEquals(1, servicioHora.seleccionarHorasRegistradas().size());
			assertEquals(stringFechaDesde, simpleDateFormatConHora.format(servicioHora.seleccionarHorasRegistradas().get(0).getFechaDesde()));
			assertEquals(stringFechaHasta, simpleDateFormatConHora.format(servicioHora.seleccionarHorasRegistradasPorUsuario(USUARIO_TEST).get(0).getFechaHasta()));
			assertEquals(simpleDateFormat.format(calendar.getTime()), simpleDateFormat.format(servicioHora.seleccionarHorasRegistradasNoInformadas(CONTRATO_TEST).get(0).getFechaInformar()));
			assertEquals(simpleDateFormat.format(calendar.getTime()), simpleDateFormat.format(servicioHora.seleccionarHorasRegistradasNoFacturadas(CONTRATO_TEST).get(0).getFechaFacturar()));
			assertEquals(simpleDateFormat.format(calendar.getTime()), simpleDateFormat.format(servicioHora.seleccionarHorasRegistradas().get(0).getFechaComputar()));
			assertEquals(ACTIVIDAD_TEST, servicioHora.seleccionarHorasRegistradas().get(0).getIdActividad());
			assertEquals(TIPO_TEST, servicioHora.seleccionarHorasRegistradas().get(0).getNombreTipoHora());
			assertEquals("DESCRIPCION_TEST", servicioHora.seleccionarHorasRegistradas().get(0).getDescripcion());
			assertEquals(false, servicioHora.seleccionarHorasRegistradas().get(0).isValidada());
			assertEquals(false, servicioHora.seleccionarHorasRegistradas().get(0).isInformada());
			assertEquals(false, servicioHora.seleccionarHorasRegistradas().get(0).isFacturada());
		}
	}
	
	@Test
	public void testEditar() throws FileNotFoundException, ClassNotFoundException, SQLException, IOException, ParseException, SgtiException{
			if(isHabilitarTest()){
			agregarRelacionadoConConsultasContrato();
			TipoHora tipoHora = new TipoHora();
			tipoHora.setTipo(TIPO_TEST);
			servicioTipoHora.agregar(tipoHora);
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
			
			servicioHora.agregar(hora);
			stringFechaDesde = simpleDateFormat.format(fechaHoy) + " 10:00";
			stringFechaHasta = simpleDateFormat.format(fechaHoy) + " 10:40";
			fechaDesde = simpleDateFormatConHora.parse(stringFechaDesde);
			fechaHasta = simpleDateFormatConHora.parse(stringFechaHasta);
			calendar.set(Calendar.MONTH, 5);
			hora.setId(servicioHora.seleccionarHorasRegistradas().get(0).getId());
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
			servicioHora.editar(hora);
			assertEquals(1, servicioHora.seleccionarHorasRegistradas().size());
			assertEquals(stringFechaDesde, simpleDateFormatConHora.format(servicioHora.seleccionarHorasRegistradas().get(0).getFechaDesde()));
			assertEquals(stringFechaHasta, simpleDateFormatConHora.format(servicioHora.seleccionarHorasRegistradasPorUsuario(USUARIO_TEST).get(0).getFechaHasta()));
			assertEquals(0, servicioHora.seleccionarHorasRegistradasNoInformadas(CONTRATO_TEST).size());
			assertEquals(0, servicioHora.seleccionarHorasRegistradasNoFacturadas(CONTRATO_TEST).size());
			assertEquals(simpleDateFormat.format(calendar.getTime()), simpleDateFormat.format(servicioHora.seleccionarHorasRegistradas().get(0).getFechaComputar()));
			assertEquals(ACTIVIDAD_TEST2, servicioHora.seleccionarHorasRegistradas().get(0).getIdActividad());
			assertEquals(TIPO_TEST, servicioHora.seleccionarHorasRegistradas().get(0).getNombreTipoHora());
			assertEquals("DESCRIPCION_TEST2", servicioHora.seleccionarHorasRegistradas().get(0).getDescripcion());
			assertEquals(true, servicioHora.seleccionarHorasRegistradas().get(0).isValidada());
			assertEquals(true, servicioHora.seleccionarHorasRegistradas().get(0).isInformada());
			assertEquals(true, servicioHora.seleccionarHorasRegistradas().get(0).isFacturada());
		}
	}

}
