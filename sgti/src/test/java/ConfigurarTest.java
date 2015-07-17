package test.java;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.springframework.context.support.ClassPathXmlApplicationContext;    

import com.proyectodegrado.sgti.consultas.ConsultasCliente;
import com.proyectodegrado.sgti.consultas.ConsultasConfiguracion;
import com.proyectodegrado.sgti.consultas.ConsultasContrato;
import com.proyectodegrado.sgti.consultas.ConsultasContratoTipoHora;
import com.proyectodegrado.sgti.consultas.ConsultasPrecio;
import com.proyectodegrado.sgti.consultas.ConsultasTipoHora;
import com.proyectodegrado.sgti.consultas.ConsultasUsuario;
import com.proyectodegrado.sgti.daos.ClienteDAO;
import com.proyectodegrado.sgti.daos.ContratoDAO;
import com.proyectodegrado.sgti.daos.HorarioLaboralDAO;
import com.proyectodegrado.sgti.daos.UsuarioDAO;
import com.proyectodegrado.sgti.modelo.Cliente;
import com.proyectodegrado.sgti.modelo.Configuracion;
import com.proyectodegrado.sgti.modelo.Contrato;
import com.proyectodegrado.sgti.modelo.Dia;
import com.proyectodegrado.sgti.modelo.HorarioLaboral;
import com.proyectodegrado.sgti.modelo.Precio;
import com.proyectodegrado.sgti.modelo.TipoHora;
import com.proyectodegrado.sgti.modelo.TipoHoraComputo;
import com.proyectodegrado.sgti.modelo.Usuario;
import com.proyectodegrado.sgti.servicios.ServicioCliente;
import com.proyectodegrado.sgti.servicios.ServicioContrato;
import com.proyectodegrado.sgti.servicios.ServicioTipoHora;
import com.proyectodegrado.sgti.servicios.impl.ServicioUsuarioContraparteImpl;

public class ConfigurarTest {   
	
	private static final int COMPUTOS = 3; 

	private static final String TIPO_TEST = "TIPO_TEST";
	
	private static final String FORMATO_FECHA = "yyyy-MM-dd";
	
	private static final String TIEMPO_RESPUESTA_TEST = "TIEMPO_RESPUESTA_TEST";

	private static final String TIPO_CONTRATO_TEST = "TIPO_CONTRATO_TEST";

	private static final String RENOVACION_TEST = "RENOVACION_TEST";

	private static final String HORARIO_LABORAL_TEST = "HORARIO_LABORAL_TEST";
	   
	private static final String EMAIL_TEST = "EMAIL_TEST";

	private static final String CONTASEÑA_TEST = "CONTASEÑA_TEST";

	private static final String APELLIDO_TEST = "APELLIDO_TEST";

	private static final String NOMBRE_TEST = "NOMBRE_TEST";

	private static final String DIRECCION_TEST = "DIRECCION_TEST";

	protected static final String USUARIO_TEST = "USUARIO_TEST";

	protected static final String TELEFONO = "123456789";

	protected static final String CLIENTE_TEST = "CLIENTE_TEST";

	protected static final String CONTRATO_TEST = "CONTRATO_TEST";
	
	protected boolean habilitarTest;
	
	protected static ContratoDAO contratoDao;
	
	protected static ClienteDAO clienteDao;
	
	protected static UsuarioDAO usuarioDao;
	
	protected static ConsultasContrato consultasContrato;
	
	protected static ConsultasCliente consultasCliente;
	
	protected static ConsultasUsuario consultasUsuario;
	
	protected static ServicioUsuarioContraparteImpl servicioUsuario;
	
	protected static ServicioCliente servicioCliente;
	
	protected static ServicioContrato servicioContrato;
	
	private static ServicioTipoHora servicioTipoHora;
	
	private static ConsultasConfiguracion consultasConfiguracion;
	
	private static ConsultasPrecio consultasPrecio;
	
	private static ConsultasContratoTipoHora consultasContratoTipoHora;
	
	private static ConsultasTipoHora consultasTipoHora;
	
	private static HorarioLaboralDAO horarioLaboralDao;
	
	protected static ClassPathXmlApplicationContext context;  
	
	public boolean isHabilitarTest() {
		
		Properties localProperties = new Properties();
		boolean testHabilitado = false;
		try {
			localProperties.load(new FileInputStream("src/main/resources/local.properties"));
			testHabilitado = Boolean.valueOf(localProperties.getProperty("habilitarTest"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return testHabilitado;
		
	}
	
	protected void borrarRelacionadoConContrato() throws FileNotFoundException, IOException, SQLException {
		consultasConfiguracion.borrarConfiguraciones();
		horarioLaboralDao.borrarDiaDeHorarioLaboral(HORARIO_LABORAL_TEST, "LUNES");
		horarioLaboralDao.borrarDiaDeHorarioLaboral(HORARIO_LABORAL_TEST, "MARTES");
		consultasPrecio.borrarPrecios();
		consultasContratoTipoHora.borrarContratoTiposHora();
		consultasTipoHora.borrarTipoHora(TIPO_TEST);
		consultasContrato.borrarContratos();
		consultasCliente.borrarCliente(CLIENTE_TEST);
		consultasUsuario.borrarUsuario(USUARIO_TEST);
		
	}
	
	protected static void prepararContextoDeConsultaContrato() {
		consultasContrato = (ConsultasContrato) context.getBean("consultasContrato");
		contratoDao = (ContratoDAO) context.getBean("contratoDao");
		consultasCliente = (ConsultasCliente) context.getBean("consultasCliente");
		clienteDao = (ClienteDAO) context.getBean("clienteDao");
		consultasUsuario = (ConsultasUsuario) context.getBean("consultasUsuario"); 
		usuarioDao = (UsuarioDAO) context.getBean("usuarioDao");
		consultasContrato = (ConsultasContrato) context.getBean("consultasContrato");
		servicioContrato = (ServicioContrato) context.getBean("servicioContrato");
		consultasCliente = (ConsultasCliente) context.getBean("consultasCliente");
		servicioCliente = (ServicioCliente) context.getBean("servicioCliente");
		consultasUsuario = (ConsultasUsuario) context.getBean("consultasUsuario");
		servicioUsuario = (ServicioUsuarioContraparteImpl) context.getBean("servicioUsuarioContraparte");
		servicioTipoHora = (ServicioTipoHora) context.getBean("servicioTipoHora");
		consultasConfiguracion = (ConsultasConfiguracion) context.getBean("consultasConfiguracion");
		consultasPrecio = (ConsultasPrecio) context.getBean("consultasPrecio");
		consultasContratoTipoHora = (ConsultasContratoTipoHora) context.getBean("consultasContratoTipoHora");
		consultasTipoHora = (ConsultasTipoHora) context.getBean("consultasTipoHora");
		horarioLaboralDao = (HorarioLaboralDAO) context.getBean("horarioLaboralDao");
	}
	
	protected static void prepararContextoDeServicioContrato() {
		consultasContrato = (ConsultasContrato) context.getBean("consultasContrato");
		servicioContrato = (ServicioContrato) context.getBean("servicioContrato");
		consultasCliente = (ConsultasCliente) context.getBean("consultasCliente");
		servicioCliente = (ServicioCliente) context.getBean("servicioCliente");
		consultasUsuario = (ConsultasUsuario) context.getBean("consultasUsuario");
		servicioUsuario = (ServicioUsuarioContraparteImpl) context.getBean("servicioUsuarioContraparte");
		servicioTipoHora = (ServicioTipoHora) context.getBean("servicioTipoHora");
		consultasConfiguracion = (ConsultasConfiguracion) context.getBean("consultasConfiguracion");
		consultasPrecio = (ConsultasPrecio) context.getBean("consultasPrecio");
		consultasContratoTipoHora = (ConsultasContratoTipoHora) context.getBean("consultasContratoTipoHora");
		consultasTipoHora = (ConsultasTipoHora) context.getBean("consultasTipoHora");
		horarioLaboralDao = (HorarioLaboralDAO) context.getBean("horarioLaboralDao");
	}
	
	protected void agregarRelacionadoConConsultasContrato() throws FileNotFoundException, IOException, SQLException {
		clienteDao.agregar(CLIENTE_TEST, DIRECCION_TEST, TELEFONO, true);
		usuarioDao.agregar(USUARIO_TEST, NOMBRE_TEST, APELLIDO_TEST, CONTASEÑA_TEST, EMAIL_TEST, TELEFONO, "", true);
		contratoDao.insertarContrato(CONTRATO_TEST, clienteDao.seleccionarPorNombre(CLIENTE_TEST).getId(), USUARIO_TEST);
	}
	
	protected void agregarRelacionadoConServicioContrato() throws FileNotFoundException, IOException, SQLException {
		Cliente cliente = crearCliente();
		servicioCliente.agregar(cliente);
		cliente.setId(servicioCliente.verPorNombre(cliente).getId());
		Usuario contraparte = crearUsuarioContraparte();
		servicioUsuario.agregar(contraparte);
		Contrato contrato = new Contrato();
		contrato.setId(CONTRATO_TEST);
		
		contrato.setCliente(cliente);
		contrato.setContraparte(contraparte);
		servicioContrato.insertar(contrato);
		
	}
	
	protected void agregarContrato() throws FileNotFoundException, IOException, SQLException {
		Cliente cliente = crearCliente();
		Usuario contraparte = crearUsuarioContraparte();
		List<Configuracion> configuraciones = new ArrayList<Configuracion>();
		configuraciones.add(crearConfiguracion());
		List<Precio> precios = new ArrayList<Precio>();
		precios.add(crearPrecio());
		List<TipoHoraComputo> tiposHoraComputo = new ArrayList<TipoHoraComputo>();
		tiposHoraComputo.add(crearTipoHoraComputo());
		Contrato contrato = new Contrato();
		contrato.setId(CONTRATO_TEST);
		
		contrato.setCliente(cliente);
		contrato.setContraparte(contraparte);
		contrato.setConfiguraciones(configuraciones);
		contrato.setTipoHoraComputo(tiposHoraComputo);
		contrato.setPrecio(precios);
		servicioContrato.insertarCompleto(contrato);
		
	}

	private Usuario crearUsuarioContraparte() {
		return new Usuario(USUARIO_TEST, NOMBRE_TEST, APELLIDO_TEST, CONTASEÑA_TEST, EMAIL_TEST, TELEFONO, "", true, null);
	}

	private Cliente crearCliente() {
		return new Cliente(0, CLIENTE_TEST, DIRECCION_TEST, TELEFONO, true);
	}

	private HorarioLaboral crearHorarioLaboral() {
		HorarioLaboral horarioLaboral = new HorarioLaboral(HORARIO_LABORAL_TEST);
		Dia diaLunes = new Dia("LUNES", "09:00", "18:00");
		Dia diaMartes = new Dia("MARTES", "09:00", "18:00");
		List<Dia> dias = new ArrayList<Dia>();
		dias.add(diaLunes);
		dias.add(diaMartes);
		horarioLaboral.setDias(dias);
		return horarioLaboral;
	}
	
	private Configuracion crearConfiguracion(){
		Configuracion configuracion = new Configuracion();
		HorarioLaboral horarioLaboral = crearHorarioLaboral();
		horarioLaboral.setId(HORARIO_LABORAL_TEST);
		configuracion.setFechaInicio(new Date());
		configuracion.setFechaFin(new Date());
		configuracion.setRenovacion(RENOVACION_TEST);
		configuracion.setPeriodoRenovacion(12);
		configuracion.setTipoContrato(TIPO_CONTRATO_TEST);
		configuracion.setComputosPaquete(15);
		configuracion.setPeriodoValidezMes(1);
		configuracion.setPeriodoValidezDia(0);
		configuracion.setAcumulacion(false);
		configuracion.setPeriodoAcumulacion(0);
		configuracion.setFrecuenciaInforme(1);
		configuracion.setFrecuenciaFacturacion(1);
		configuracion.setFrecuenciaComputosExtra(3);
		configuracion.setTiempoRespuesta(TIEMPO_RESPUESTA_TEST);
		configuracion.setHorarioLaboral(horarioLaboral);
		
		return configuracion;
	}
	
	private Precio crearPrecio(){
		Calendar fechaHasta = Calendar.getInstance();
		fechaHasta.set(Calendar.YEAR, 2020);
		return new Precio(5.5, new Date(), fechaHasta.getTime());
	}
	
	private TipoHoraComputo crearTipoHoraComputo() throws FileNotFoundException, IOException, SQLException{
		TipoHora tipoHora = new TipoHora();
		tipoHora.setTipo(TIPO_TEST);
		servicioTipoHora.agregar(tipoHora);
		tipoHora.setId(servicioTipoHora.seleccionarPorTipo(TIPO_TEST).getId());
		TipoHoraComputo tipoHoraComputo = new TipoHoraComputo();
		tipoHoraComputo.setTipoHora(tipoHora);
		tipoHoraComputo.setComputo(COMPUTOS);
		return tipoHoraComputo;
	}
}
