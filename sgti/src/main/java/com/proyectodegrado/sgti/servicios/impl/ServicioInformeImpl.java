package com.proyectodegrado.sgti.servicios.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.util.CollectionUtils;

import com.proyectodegrado.sgti.modelo.ComputoAcumulado;
import com.proyectodegrado.sgti.modelo.Configuracion;
import com.proyectodegrado.sgti.modelo.Contrato;
import com.proyectodegrado.sgti.modelo.Hora;
import com.proyectodegrado.sgti.modelo.Informe;
import com.proyectodegrado.sgti.modelo.TipoHoraComputo;
import com.proyectodegrado.sgti.modelo.Usuario;
import com.proyectodegrado.sgti.servicios.ServicioComputosAcumulados;
import com.proyectodegrado.sgti.servicios.ServicioConfiguracion;
import com.proyectodegrado.sgti.servicios.ServicioContrato;
import com.proyectodegrado.sgti.servicios.ServicioContratoTipoHora;
import com.proyectodegrado.sgti.servicios.ServicioHora;
import com.proyectodegrado.sgti.servicios.ServicioInforme;
import com.proyectodegrado.sgti.servicios.ServicioPrecio;
import com.proyectodegrado.sgti.servicios.ServicioTipoHora;
import com.proyectodegrado.sgti.servicios.ServicioUsuario;

public class ServicioInformeImpl implements ServicioInforme {
	
	private static final String PRECIO_FIJO = "PRECIO FIJO";
	private static final String HORAS_LIBRES = "HORAS LIBRES";
	private static final String BOLSA_DE_COMPUTOS = "BOLSA DE COMPUTOS";
	private static final String PLANTILLA_INFORME = "/WEB-INF/resources/Informes/informe.doc";
	private static final String PROYECTO = "<@proyecto>";
	private static final String FECHA_NOMBRE = "<@fechaNombre>";
	private static final String FECHA = "<@fecha>";
	private static final String CLIENTE = "<@cliente>";
	private static final String SOCIO = "<@socio>";
	private static final String HORAS = "<@horas>";
	private static final String TABLA_INFORME = "<@tablaInforme>";
	private static final String SALTO_LINEA = "\r\n";
	
	private ServicioContrato servicioContrato;

	private ServicioUsuario servicioUsuario;

	private ServicioConfiguracion servicioConfiguracion;

	private ServicioHora servicioHora;
	
	private ServicioTipoHora servicioTipoHora;
	
	private ServicioContratoTipoHora servicioContratoTipoHora;
	
	private ServicioPrecio servicioPrecio;
	
	private ServicioComputosAcumulados servicioComputosAcumulados;
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioInforme#informarHoras(javax.servlet.http.HttpServletRequest, java.lang.String)
	 */
	@Override
	public HWPFDocument informarHoras(HttpServletRequest request, final String idContrato) throws IOException,
	ClassNotFoundException, SQLException, ParseException {
		SimpleDateFormat formatoFechaNombre = new SimpleDateFormat("MMMM yyyy",	new Locale("es"));
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat formatoFechaHora = new SimpleDateFormat("EEEE dd/MM/yyyy", new Locale("es"));
		Contrato contrato = servicioContrato.verContrato(idContrato);
		String idUsuario = (String) request.getSession().getAttribute("usuario");
		Usuario socio = servicioUsuario.selecionarUsuario(idUsuario);
		File doc = new File(request.getServletContext().getRealPath("/"), PLANTILLA_INFORME);
		FileInputStream fileInputStream = new FileInputStream(doc);
		BufferedInputStream buffInputStream = new BufferedInputStream(fileInputStream);
		HWPFDocument word = new HWPFDocument(new POIFSFileSystem(buffInputStream));
		
		Range range = word.getRange();
		range.replaceText(PROYECTO, contrato.getId());
		range.replaceText(CLIENTE, contrato.getCliente().getNombre());
		range.replaceText(FECHA_NOMBRE, formatoFechaNombre.format(new Date()));
		range.replaceText(FECHA, formatoFecha.format(new Date()));
		range.replaceText(SOCIO, socio.getApellido() + " " + socio.getApellido());
		
		String textoHoras = "";
		Map<String, Informe> tablaCantidadPorTipoHora = new HashMap<String, Informe>();
		Configuracion configuracion = servicioConfiguracion.seleccionarConfiguracionActual(idContrato);
		if(configuracion.getTipoContrato().equalsIgnoreCase(BOLSA_DE_COMPUTOS)){
			textoHoras = informarContratoBolsaDeComputos(idContrato,	formatoFecha, formatoFechaHora, contrato, range, textoHoras, tablaCantidadPorTipoHora, configuracion);
		}else if(configuracion.getTipoContrato().equalsIgnoreCase(HORAS_LIBRES)){
			textoHoras = informarContratoHorasLibres(idContrato, formatoFechaHora,	textoHoras, formatoFecha, tablaCantidadPorTipoHora);
			range.replaceText(TABLA_INFORME, informarTabla(tablaCantidadPorTipoHora));
		}else if(configuracion.getTipoContrato().equalsIgnoreCase(PRECIO_FIJO)){
			textoHoras = informarContratoHorasLibres(idContrato, formatoFechaHora,	textoHoras, formatoFecha, tablaCantidadPorTipoHora);
			range.replaceText(TABLA_INFORME, "");
		}
		range.replaceText(HORAS, textoHoras);
		return word;
	}
	
	@Override
	public void validarInforme(String idContrato) throws FileNotFoundException, ClassNotFoundException, SQLException, IOException, ParseException{
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
		Configuracion configuracion = servicioConfiguracion.seleccionarConfiguracionActual(idContrato);
		Contrato contrato = servicioContrato.verContrato(idContrato);
		List<Hora> horas = verHorasEnElInforme(idContrato);
		if(configuracion.getTipoContrato().equalsIgnoreCase(BOLSA_DE_COMPUTOS) && configuracion.getFrecuenciaInforme() == 0){
			double computosAcumulados = servicioComputosAcumulados.verComputosAcumuladosUsables(idContrato, configuracion.getPeriodoAcumulacion());
			acumularComputosIndefinido(idContrato, computosAcumulados, configuracion.getComputosPaquete());
			validarInformeContratoBolsaComputos(idContrato, horas, formatoFecha,configuracion);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(contrato.getUltimaFechaInforme());
			calendar.add(Calendar.MONTH, configuracion.getFrecuenciaInforme());
			contrato.setUltimaFechaInforme(calendar.getTime());
			calendar.setTime(contrato.getUltimaFechaFacturacion());
			calendar.add(Calendar.MONTH, configuracion.getFrecuenciaFacturacion());
			contrato.setUltimaFechaFacturacion(calendar.getTime());
			servicioContrato.editar(contrato);
		}else{
			if(configuracion.getTipoContrato().equalsIgnoreCase(BOLSA_DE_COMPUTOS)){
				double computosAcumulados = servicioComputosAcumulados.verComputosAcumuladosUsables(idContrato, configuracion.getPeriodoAcumulacion());
				acumularComputos(idContrato, formatoFecha, computosAcumulados, configuracion.getComputosPaquete());
			}
			validarInformeNoBolsaComputos(idContrato, formatoFecha, horas);
			modificarUltimaFechaInforme(horas, configuracion, contrato, formatoFecha);
			modificarUltimaFechaFacturacion(horas, configuracion, contrato, formatoFecha);
			
		}
		
	}
	
	private void modificarUltimaFechaInforme(List<Hora> horas, Configuracion configuracion, Contrato contrato, SimpleDateFormat formatoFecha) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		Calendar calendar = Calendar.getInstance();
		for(Hora hora : horas){
			if(formatoFecha.format(hora.getFechaInformar()).equalsIgnoreCase(formatoFecha.format(servicioContrato.proximaFechaInforme(contrato.getId())))){
				calendar.setTime(contrato.getUltimaFechaInforme());
				calendar.add(Calendar.MONTH, configuracion.getFrecuenciaInforme());
				contrato.setUltimaFechaInforme(calendar.getTime());
				break;
			}
		}
		servicioContrato.editar(contrato);
	}
	
	private void modificarUltimaFechaFacturacion(List<Hora> horas, Configuracion configuracion, Contrato contrato, SimpleDateFormat formatoFecha) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		Calendar calendar = Calendar.getInstance();
		for(Hora hora : horas){
			if(formatoFecha.format(hora.getFechaFacturar()).equalsIgnoreCase(formatoFecha.format(servicioContrato.proximaFechaFacturacion(contrato.getId())))){
				calendar.setTime(contrato.getUltimaFechaFacturacion());
				calendar.add(Calendar.MONTH, configuracion.getFrecuenciaFacturacion());
				contrato.setUltimaFechaFacturacion(calendar.getTime());
				break;
			}
		}
		servicioContrato.editar(contrato);
	}

	private void validarInformeNoBolsaComputos(String idContrato, SimpleDateFormat formatoFecha, List<Hora> horas) throws FileNotFoundException, 
	ClassNotFoundException, IOException, SQLException {
		for(Hora hora : horas){
			if(formatoFecha.format(hora.getFechaInformar()).equalsIgnoreCase(formatoFecha.format(servicioContrato.proximaFechaInforme(idContrato)))){
				hora.setInformada(true);
			}
			if(formatoFecha.format(hora.getFechaFacturar()).equalsIgnoreCase(formatoFecha.format(servicioContrato.proximaFechaFacturacion(idContrato)))){
				hora.setFacturada(true);
			}
			servicioHora.editar(hora);
		}
	}

	private void validarInformeContratoBolsaComputos(String idContrato, List<Hora> horas, SimpleDateFormat formatoFecha, Configuracion configuracion) throws FileNotFoundException, 
	ClassNotFoundException, IOException, SQLException, ParseException {
		for(Hora hora : horas){
			hora.setInformada(true);
			hora.setFacturada(true);
			servicioHora.editar(hora);
		}
	}

	private String informarContratoBolsaDeComputos(final String idContrato, SimpleDateFormat formatoFecha, SimpleDateFormat formatoFechaHora, Contrato contrato, Range range,
			String textoHoras, Map<String, Informe> tablaCantidadPorTipoHora, Configuracion configuracion) throws FileNotFoundException, ClassNotFoundException, 
			IOException, SQLException, ParseException {
		double computosAcumulados = servicioComputosAcumulados.verComputosAcumuladosUsables(idContrato, configuracion.getPeriodoAcumulacion());
		if (configuracion.getFrecuenciaInforme() == 0) {
			textoHoras = informarContratoNoDefinido(idContrato, formatoFechaHora,	textoHoras, tablaCantidadPorTipoHora, computosAcumulados);
			range.replaceText(TABLA_INFORME, informarTabla(tablaCantidadPorTipoHora));
		}else if(configuracion.getFrecuenciaInforme() == configuracion.getPeriodoValidezMes()){
			textoHoras = informarContrato(idContrato, formatoFechaHora,	textoHoras, formatoFecha, tablaCantidadPorTipoHora, computosAcumulados);
			range.replaceText(TABLA_INFORME, informarTabla(tablaCantidadPorTipoHora));
		}else{
			textoHoras = informarContrato(idContrato, formatoFechaHora,	textoHoras, formatoFecha, tablaCantidadPorTipoHora, computosAcumulados);
			if(configuracion.getPeriodoValidezMes() > 0){
				List<Map<String, Informe>> listaCantidadPorTipoHoras = calcularFacturacion(Calendar.MONTH, idContrato, formatoFecha, contrato, configuracion, computosAcumulados);
				range.replaceText(TABLA_INFORME, informarTablaSeparada(listaCantidadPorTipoHoras));
			}else if(configuracion.getPeriodoValidezDia() > 0){
				List<Map<String, Informe>> listaCantidadPorTipoHoras = calcularFacturacion(Calendar.DATE, idContrato, formatoFecha, contrato, configuracion, computosAcumulados);
				range.replaceText(TABLA_INFORME, informarTablaSeparada(listaCantidadPorTipoHoras));
			}
		}
		return textoHoras;
	}

	private List<Map<String, Informe>> calcularFacturacion(int unidad, final String idContrato, SimpleDateFormat formatoFecha, Contrato contrato, 
			Configuracion configuracion, double computosAcumulados)
			throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException {
		List<Hora> horasParaInformar = obtenerHorasParaInformar(verHorasEnElInforme(idContrato), servicioContrato.proximaFechaFacturacion(idContrato), idContrato, formatoFecha);
		List<List<Hora>> horasSeparadasAux = new ArrayList<List<Hora>>();
		Calendar fechaHasta = Calendar.getInstance();
		fechaHasta.setTime(contrato.getUltimaFechaInforme());
		fechaHasta.add(unidad, configuracion.getPeriodoValidezMes());
		List<List<Hora>> horasSeparadas = separarPorPeriodoValidezMes(unidad, contrato.getUltimaFechaInforme(), fechaHasta.getTime(), configuracion, horasParaInformar, configuracion.getPeriodoValidezMes(), horasSeparadasAux);
		List<Map<String, Informe>> listaCantidadPorTipoHoras = new ArrayList<Map<String, Informe>>();
		for(List<Hora> horas : horasSeparadas){
			Map<String, Informe> tablaCantidadPorTipoHoraSeparadas = new HashMap<String, Informe>();
			for(Hora hora : horas){
				double computos = (hora.getDuracion()/15) * obtenerComputoDeTipoDeHora(hora.getIdContrato(), hora.getNombreTipoHora());
				if(tablaCantidadPorTipoHoraSeparadas.containsKey(hora.getNombreTipoHora())){
					actualizarTabla(hora, tablaCantidadPorTipoHoraSeparadas, computos, servicioConfiguracion.seleccionarConfiguracionActual(hora.getIdContrato()).getComputosPaquete(), computosAcumulados);
				}else{
					generarNuevoInforme(hora, tablaCantidadPorTipoHoraSeparadas, computos, servicioConfiguracion.seleccionarConfiguracionActual(hora.getIdContrato()).getComputosPaquete(), computosAcumulados);
				}
			}
			listaCantidadPorTipoHoras.add(tablaCantidadPorTipoHoraSeparadas);
		}
		return listaCantidadPorTipoHoras;
	}
	
	private String informarContratoNoDefinido(final String idContrato, SimpleDateFormat formatoFechaHora, String textoHoras, Map<String, Informe> tablaCantidadPorTipoHora
			, double computosAcumulados) throws FileNotFoundException, ClassNotFoundException, IOException,
	SQLException, ParseException {
		List<Hora> horas = verHorasEnElInforme(idContrato);
		for (Hora hora : horas) {
			String textoHora = "";
			textoHora = formatoFechaHora.format(hora.getFechaDesde()) + "-" + hora.getDuracion() / 60 + ":" + hora.getDuracion() % 60 + "-" + hora.getNombreTipoHora();
			textoHora += SALTO_LINEA + hora.getDescripcion() + SALTO_LINEA;
			textoHoras += SALTO_LINEA + textoHora;
			generarTabla(hora, tablaCantidadPorTipoHora, computosAcumulados);
		}
		return textoHoras;
	}
	
	private String informarContrato(final String idContrato, SimpleDateFormat formatoFechaHora, String textoHoras, SimpleDateFormat formatoFecha, 
			Map<String, Informe> tablaCantidadPorTipoHora, double computosAcumulados) throws FileNotFoundException, ClassNotFoundException, IOException,
	SQLException, ParseException {
		List<Hora> horas = verHorasEnElInforme(idContrato);
		for (Hora hora : horas) {
			if(formatoFecha.format(hora.getFechaInformar()).equalsIgnoreCase(formatoFecha.format(servicioContrato.proximaFechaInforme(idContrato)))){
				String textoHora = formatoFechaHora.format(hora.getFechaDesde()) + "-" + hora.getDuracion() / 60 + ":" + hora.getDuracion() % 60 + "-" + hora.getNombreTipoHora();
				textoHora += SALTO_LINEA + hora.getDescripcion() + SALTO_LINEA;
				textoHoras += SALTO_LINEA + textoHora;
			}
			if(formatoFecha.format(hora.getFechaFacturar()).equalsIgnoreCase(formatoFecha.format(servicioContrato.proximaFechaFacturacion(idContrato)))){
				generarTabla(hora, tablaCantidadPorTipoHora, computosAcumulados);
			}
		}
		return textoHoras;
	}
	
	private void acumularComputosIndefinido(final String idContrato, double computosAcumulados, int computos) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException{
		Map<String, Informe> tablaCantidadPorTipoHora = new HashMap<String, Informe>();
		List<Hora> horas = verHorasEnElInforme(idContrato);
		double computosUsados = 0.0;
		Date fechaAcumulacion = horas.get(0).getFechaFacturar();
		for (Hora hora : horas) {
			generarTabla(hora, tablaCantidadPorTipoHora, computosAcumulados);
		}
		for(Map.Entry<String, Informe> entry: tablaCantidadPorTipoHora.entrySet()){
			computosUsados += entry.getValue().getComputosConsumidos();
		}
		acumularComputo(idContrato, computosAcumulados, computos, fechaAcumulacion, computosUsados);
	}
	
	private void acumularComputos(final String idContrato, SimpleDateFormat formatoFecha, double computosAcumulados, int computos) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException{
		Map<String, Informe> tablaCantidadPorTipoHora = new HashMap<String, Informe>();
		List<Hora> horas = verHorasEnElInforme(idContrato);
		double computosUsados = 0.0;
		Date fechaAcumulacion = horas.get(0).getFechaFacturar();
		for (Hora hora : horas) {
			if(formatoFecha.format(hora.getFechaFacturar()).equalsIgnoreCase(formatoFecha.format(servicioContrato.proximaFechaFacturacion(idContrato)))){
				generarTabla(hora, tablaCantidadPorTipoHora, computosAcumulados);
			}
		}
		for(Map.Entry<String, Informe> entry: tablaCantidadPorTipoHora.entrySet()){
			computosUsados += entry.getValue().getComputosConsumidos();
		}
		acumularComputo(idContrato, computosAcumulados, computos, fechaAcumulacion, computosUsados);
	}

	private void acumularComputo(final String idContrato, double computosAcumulados, int computos, Date fechaAcumulacion,
			double computosUsados) throws FileNotFoundException,
			ClassNotFoundException, IOException, SQLException {
		if(computosUsados - computosAcumulados < computos){
			ComputoAcumulado computoAcumulado = new ComputoAcumulado();
			computoAcumulado.setIdContrato(idContrato);
			computoAcumulado.setFecha(fechaAcumulacion);
			computoAcumulado.setComputos(computos - (computosUsados - computosAcumulados));
			servicioComputosAcumulados.insertar(computoAcumulado);
		}
	}
	
	private String informarContratoHorasLibres(final String idContrato, SimpleDateFormat formatoFechaHora, String textoHoras, SimpleDateFormat formatoFecha, 
			Map<String, Informe> tablaCantidadPorTipoHora) throws FileNotFoundException, ClassNotFoundException, IOException,
	SQLException, ParseException {
		List<Hora> horas = verHorasEnElInforme(idContrato);
		for (Hora hora : horas) {
			if(formatoFecha.format(hora.getFechaInformar()).equalsIgnoreCase(formatoFecha.format(servicioContrato.proximaFechaInforme(idContrato)))){
				String textoHora = formatoFechaHora.format(hora.getFechaDesde()) + "-" + hora.getDuracion() / 60 + ":" + hora.getDuracion() % 60 + "-" + hora.getNombreTipoHora();
				textoHora += SALTO_LINEA + hora.getDescripcion() + SALTO_LINEA;
				textoHoras += SALTO_LINEA + textoHora;
			}
			if(formatoFecha.format(hora.getFechaFacturar()).equalsIgnoreCase(formatoFecha.format(servicioContrato.proximaFechaFacturacion(idContrato)))){
				generarTablaHorasLibres(hora, tablaCantidadPorTipoHora);
			}
		}
		return textoHoras;
	}
	
	private Double obtenerComputoDeTipoDeHora(String idContrato, String nombreTipoHora) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		for(TipoHoraComputo tipoHoraComputo : servicioContratoTipoHora.verContratoTipoHora(idContrato)){
			if(nombreTipoHora.equalsIgnoreCase(tipoHoraComputo.getTipoHora().getTipo())){
				return tipoHoraComputo.getComputo();
			}
		}
		return 0.0;
	}
	
	private String informarTablaSeparada(List<Map<String, Informe>> listaCantidadPorTipoHora){
		String textosTabla = "";
		Map<String, Informe> mapaAgregado = new HashMap<String, Informe>();
		for(Map<String, Informe> tablaCantidadPorTipoHora : listaCantidadPorTipoHora){
			for(Map.Entry<String, Informe> entry: tablaCantidadPorTipoHora.entrySet()){
				if(mapaAgregado.containsKey(entry.getKey())){
					Informe informeNuevo = new Informe();
					informeNuevo.setPrecioTotal((mapaAgregado.get(entry.getKey()).getPrecioTotal() != null ? mapaAgregado.get(entry.getKey()).getPrecioTotal() : 0.0) + (entry.getValue().getPrecioTotal() != null ? entry.getValue().getPrecioTotal() : 0.0));
					informeNuevo.setMinutosConsumidos(mapaAgregado.get(entry.getKey()).getMinutosConsumidos() + entry.getValue().getMinutosConsumidos());
					informeNuevo.setComputosConsumidos(mapaAgregado.get(entry.getKey()).getComputosConsumidos() + entry.getValue().getComputosConsumidos());
					informeNuevo.setPrecioExtraTotal((mapaAgregado.get(entry.getKey()).getPrecioExtraTotal() != null ? mapaAgregado.get(entry.getKey()).getPrecioExtraTotal() : 0.0) + (entry.getValue().getPrecioExtraTotal() != null ? entry.getValue().getPrecioExtraTotal() : 0.0));
					mapaAgregado.put(entry.getKey(), informeNuevo);
				}else{
					mapaAgregado.put(entry.getKey(), entry.getValue());
				}
			}
		}
		textosTabla += informarTabla(mapaAgregado);
		return textosTabla;
	}
	
	private String informarTabla(Map<String, Informe> tablaCantidadPorTipoHora){
		String textosTabla = "";
		double total = 0.0;
		for(Map.Entry<String, Informe> entry: tablaCantidadPorTipoHora.entrySet()){
			double totalPorHora = 0.0;
			String textoTabla = entry.getKey() + " -> " + entry.getValue().getMinutosConsumidos()/60 + ":" + entry.getValue().getMinutosConsumidos()%60;
			if(entry.getValue().getPrecioTotal() != null && entry.getValue().getPrecioTotal() != 0.0){
				textoTabla += ", precio: $" + entry.getValue().getPrecioTotal();
				total += entry.getValue().getPrecioTotal();
				totalPorHora += entry.getValue().getPrecioTotal();
			}
			if(entry.getValue().getPrecioExtraTotal() != null && entry.getValue().getPrecioExtraTotal() != 0.0){
				textoTabla += ", precio extra: $" + entry.getValue().getPrecioExtraTotal();
				total += entry.getValue().getPrecioExtraTotal();
				totalPorHora += entry.getValue().getPrecioExtraTotal();
			}
			textoTabla += ", Total: $" + totalPorHora + SALTO_LINEA;
			textosTabla += textoTabla;
		}
		textosTabla += SALTO_LINEA + "Total = " + total;
		return textosTabla;
	}
	
	private double esExtra(double computos, Map<String, Informe> tablaCantidadPorTipoHora, int totalComputosConsumir){
		double totalComputos = 0.0;
		for(Map.Entry<String, Informe> entry: tablaCantidadPorTipoHora.entrySet()){
			totalComputos += entry.getValue().getComputosConsumidos();
		}
		totalComputos += computos;
		return totalComputos - totalComputosConsumir;
	}
	
	private List<Hora> verHorasEnElInforme(final String idContrato)	throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, ParseException {
		List<Hora> horas = new ArrayList<Hora>(servicioHora.seleccionarHorasRegistradasNoInformadas(idContrato));
		for(Hora hora : servicioHora.seleccionarHorasRegistradasNoFacturadas(idContrato)){
			if(!existeEnLaLista(hora, horas)){
				horas.add(hora);
			}
		}
		return horas;
	}
	
	private boolean existeEnLaLista(Hora hora, List<Hora> horas) {
		for(Hora horaExistente : horas){
			if(horaExistente.getId() == hora.getId()){
				return true;
			}
		}
		return false;
	}

	private Map<String, Informe> generarTabla(Hora hora, Map<String, Informe> tablaCantidadPorTipoHora, double computosAcumulados) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		double computos = (hora.getDuracion()/15) * obtenerComputoDeTipoDeHora(hora.getIdContrato(), hora.getNombreTipoHora());
		if(tablaCantidadPorTipoHora.containsKey(hora.getNombreTipoHora())){
			actualizarTabla(hora, tablaCantidadPorTipoHora, computos, servicioConfiguracion.seleccionarConfiguracionActual(hora.getIdContrato()).getComputosPaquete(), computosAcumulados);
		}else{
			generarNuevoInforme(hora, tablaCantidadPorTipoHora, computos, servicioConfiguracion.seleccionarConfiguracionActual(hora.getIdContrato()).getComputosPaquete(),computosAcumulados);
		}
		return tablaCantidadPorTipoHora;
	}
	
	private Map<String, Informe> generarTablaHorasLibres(Hora hora, Map<String, Informe> tablaCantidadPorTipoHora) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		double computos = (hora.getDuracion()/15) * obtenerComputoDeTipoDeHora(hora.getIdContrato(), hora.getNombreTipoHora());
		if(tablaCantidadPorTipoHora.containsKey(hora.getNombreTipoHora())){
			actualizarTablaHorasLibres(hora, tablaCantidadPorTipoHora, computos, servicioConfiguracion.seleccionarConfiguracionActual(hora.getIdContrato()).getComputosPaquete());
		}else{
			generarNuevoInformeHorasLibres(hora, tablaCantidadPorTipoHora, computos, servicioConfiguracion.seleccionarConfiguracionActual(hora.getIdContrato()).getComputosPaquete());
		}
		return tablaCantidadPorTipoHora;
	}
	
	private void generarNuevoInforme(Hora hora,	Map<String, Informe> tablaCantidadPorTipoHora, double computos, int computosPaquete, double computosAcumulados)	throws FileNotFoundException, SQLException, IOException,
			ClassNotFoundException {
		Informe informe = new Informe();
		double computosExtra = esExtra(computos, tablaCantidadPorTipoHora, computosPaquete);
		if(computosExtra > 0){
			if(computosAcumulados > 0){
				double aux = computosExtra - computosAcumulados;
				computosAcumulados = computosExtra - aux;
				computosExtra = aux;
			}
		}
		if(computosExtra > 0){
			informe.setMinutosConsumidos(hora.getDuracion());
			informe.setPrecioExtraTotal(computosExtra * servicioPrecio.verPrecioDeHora(hora).getPrecioExtra());
			informe.setPrecioTotal((computos - computosExtra) * servicioPrecio.verPrecioDeHora(hora).getPrecio());
			informe.setComputosConsumidos(computos);
		}else{
			informe.setMinutosConsumidos(hora.getDuracion());
			informe.setPrecioTotal(computos * servicioPrecio.verPrecioDeHora(hora).getPrecio());
			informe.setComputosConsumidos(computos);
		}
		tablaCantidadPorTipoHora.put(hora.getNombreTipoHora(), informe);
	}
	
	private void generarNuevoInformeHorasLibres(Hora hora,	Map<String, Informe> tablaCantidadPorTipoHora, double computos, int computosPaquete)	throws FileNotFoundException, SQLException, IOException,
	ClassNotFoundException {
		Informe informe = new Informe();
		informe.setMinutosConsumidos(hora.getDuracion());
		informe.setPrecioTotal(computos * servicioPrecio.verPrecioDeHora(hora).getPrecio());
		informe.setComputosConsumidos(computos);
		tablaCantidadPorTipoHora.put(hora.getNombreTipoHora(), informe);
	}
	
	private void actualizarTablaHorasLibres(Hora hora, Map<String, Informe> tablaCantidadPorTipoHora, double computos, int computosPaquete)	throws FileNotFoundException, SQLException, IOException,
	ClassNotFoundException {
		Informe informe = tablaCantidadPorTipoHora.get(hora.getNombreTipoHora());
		informe.setMinutosConsumidos(informe.getMinutosConsumidos() + hora.getDuracion());
		if(informe.getPrecioTotal() != null){
			informe.setPrecioTotal(informe.getPrecioTotal() + computos * servicioPrecio.verPrecioDeHora(hora).getPrecio());
		}else{
			informe.setPrecioTotal(computos * servicioPrecio.verPrecioDeHora(hora).getPrecio());
		}
		informe.setComputosConsumidos(informe.getComputosConsumidos() + computos);
	}

	private void actualizarTabla(Hora hora, Map<String, Informe> tablaCantidadPorTipoHora, double computos, int computosPaquete, double computosAcumulados)	throws FileNotFoundException, SQLException, IOException,
			ClassNotFoundException {
		Informe informe = tablaCantidadPorTipoHora.get(hora.getNombreTipoHora());
		double computosExtra = esExtra(computos, tablaCantidadPorTipoHora, computosPaquete);
		if(computosExtra > 0){
			if(computosAcumulados > 0){
				double aux = computosExtra - computosAcumulados;
				computosAcumulados = computosExtra - aux;
				computosExtra = aux;
			}
		}
		if(computosExtra > 0){
			informe.setMinutosConsumidos(informe.getMinutosConsumidos() + hora.getDuracion());
			if(informe.getPrecioExtraTotal() != null){
				informe.setPrecioExtraTotal(informe.getPrecioExtraTotal() + computosExtra * servicioPrecio.verPrecioDeHora(hora).getPrecioExtra());
			}else{
				informe.setPrecioExtraTotal(computosExtra * servicioPrecio.verPrecioDeHora(hora).getPrecio());
			}
			if(informe.getPrecioTotal() != null){
				informe.setPrecioTotal(informe.getPrecioTotal() + (computos - computosExtra) * servicioPrecio.verPrecioDeHora(hora).getPrecio());
			}else{
				informe.setPrecioTotal((computos - computosExtra) * servicioPrecio.verPrecioDeHora(hora).getPrecio());
			}
			informe.setComputosConsumidos(informe.getComputosConsumidos() + computos);
		}else{
			informe.setMinutosConsumidos(informe.getMinutosConsumidos() + hora.getDuracion());
			if(informe.getPrecioTotal() != null){
				informe.setPrecioTotal(informe.getPrecioTotal() + computos * servicioPrecio.verPrecioDeHora(hora).getPrecio());
			}else{
				informe.setPrecioTotal(computos * servicioPrecio.verPrecioDeHora(hora).getPrecio());
			}
			informe.setComputosConsumidos(informe.getComputosConsumidos() + computos);
		}
	}
	
	private List<Hora> obtenerHorasParaInformar(List<Hora> horas, Date fechaInformar, String idContrato, SimpleDateFormat formatoFecha) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		List<Hora> horasParaInformar = new ArrayList<Hora>();
		for(Hora hora : horas){
			if(formatoFecha.format(hora.getFechaInformar()).equalsIgnoreCase(formatoFecha.format(servicioContrato.proximaFechaInforme(idContrato)))){
				horasParaInformar.add(hora);
			}
		}
		return horasParaInformar;
	}
	
	private List<List<Hora>> separarPorPeriodoValidezMes(int unidad, Date fechaDesde, Date fechaHasta, Configuracion configuracion, List<Hora> horas, int periodoValidezMes, List<List<Hora>> horasSeparadas){
		Calendar fechaDesdeCalendar = Calendar.getInstance();
		fechaDesdeCalendar.setTime(fechaDesde);
		Calendar fechaHastaCalendar = Calendar.getInstance();
		fechaHastaCalendar.setTime(fechaHasta);
		List<Hora> horasParaAgregar = new ArrayList<Hora>();
		Iterator<Hora> horasIterator = horas.iterator();
		while(horasIterator.hasNext()){
			Hora hora = horasIterator.next();
			if(hora.getFechaDesde().after(fechaDesde) && hora.getFechaDesde().before(fechaHasta)){
				horasParaAgregar.add(hora);
				horasIterator.remove();
			}
		}
		horasSeparadas.add(horasParaAgregar);
		if(!CollectionUtils.isEmpty(horas)){
			fechaDesdeCalendar.add(unidad, configuracion.getPeriodoValidezMes());
			fechaHastaCalendar.add(unidad, configuracion.getPeriodoValidezMes());
			separarPorPeriodoValidezMes(unidad, fechaDesdeCalendar.getTime(), fechaHastaCalendar.getTime(), configuracion, horas, periodoValidezMes, horasSeparadas);
		}
		return horasSeparadas;
	}

	public ServicioContrato getServicioContrato() {
		return servicioContrato;
	}

	public void setServicioContrato(ServicioContrato servicioContrato) {
		this.servicioContrato = servicioContrato;
	}

	public ServicioUsuario getServicioUsuario() {
		return servicioUsuario;
	}

	public void setServicioUsuario(ServicioUsuario servicioUsuario) {
		this.servicioUsuario = servicioUsuario;
	}

	public ServicioConfiguracion getServicioConfiguracion() {
		return servicioConfiguracion;
	}

	public void setServicioConfiguracion(ServicioConfiguracion servicioConfiguracion) {
		this.servicioConfiguracion = servicioConfiguracion;
	}

	public ServicioHora getServicioHora() {
		return servicioHora;
	}

	public void setServicioHora(ServicioHora servicioHora) {
		this.servicioHora = servicioHora;
	}

	public ServicioTipoHora getServicioTipoHora() {
		return servicioTipoHora;
	}

	public void setServicioTipoHora(ServicioTipoHora servicioTipoHora) {
		this.servicioTipoHora = servicioTipoHora;
	}

	public ServicioContratoTipoHora getServicioContratoTipoHora() {
		return servicioContratoTipoHora;
	}

	public void setServicioContratoTipoHora(ServicioContratoTipoHora servicioContratoTipoHora) {
		this.servicioContratoTipoHora = servicioContratoTipoHora;
	}

	public ServicioPrecio getServicioPrecio() {
		return servicioPrecio;
	}

	public void setServicioPrecio(ServicioPrecio servicioPrecio) {
		this.servicioPrecio = servicioPrecio;
	}

	public ServicioComputosAcumulados getServicioComputosAcumulados() {
		return servicioComputosAcumulados;
	}

	public void setServicioComputosAcumulados(
			ServicioComputosAcumulados servicioComputosAcumulados) {
		this.servicioComputosAcumulados = servicioComputosAcumulados;
	}
	
}
