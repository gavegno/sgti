package com.proyectodegrado.sgti.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proyectodegrado.sgti.exceptions.SgtiException;
import com.proyectodegrado.sgti.fachada.FachadaActividad;
import com.proyectodegrado.sgti.fachada.FachadaContratoTecnicos;
import com.proyectodegrado.sgti.fachada.FachadaHora;
import com.proyectodegrado.sgti.fachada.FachadaTipoHora;
import com.proyectodegrado.sgti.fachada.FachadaUsuario;
import com.proyectodegrado.sgti.modelo.Actividad;
import com.proyectodegrado.sgti.modelo.Cliente;
import com.proyectodegrado.sgti.modelo.Contrato;
import com.proyectodegrado.sgti.modelo.Hora;
import com.proyectodegrado.sgti.modelo.HoraYUsuario;
import com.proyectodegrado.sgti.modelo.TipoHora;
import com.proyectodegrado.sgti.modelo.Usuario;


@Controller
@RequestMapping("/desktop/movil")
public class RestMovilController {
	
	private FachadaUsuario fachadaUsuario;
	private FachadaContratoTecnicos fachadaContratoTecnicos;
	private FachadaTipoHora fachadaTipoHora;
	private FachadaActividad fachadaActividad;
	private FachadaHora fachadaHora;
	
	@RequestMapping(value = "/usr", method = RequestMethod.GET, headers="Accept=application/json", produces = {"application/json"})
	public @ResponseBody Usuario usuarioWService()
	{	Usuario u = new Usuario();
		List<TipoHora> t = new ArrayList<TipoHora>();
		u.setUsuarioTipoHora(t);
		return u;
	}
	
	
	@RequestMapping(value = "/pruebahora", method = RequestMethod.GET, headers="Accept=application/json", produces = {"application/json"})
	public @ResponseBody Hora horaprueba()
	{	
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaHora = (FachadaHora) context.getBean("fachadaHora");
		try {
			Hora hora = fachadaHora.seleccionarHora(22);
			return hora;
		} catch (ClassNotFoundException | IOException | SQLException
				| ParseException e) {
			e.printStackTrace();
			return null;
		} 
		finally{
			context.close();
		}
	}
	
	
	@RequestMapping(value = "/cliente", method = RequestMethod.GET, headers="Accept=application/json", produces = {"application/json"})
	public @ResponseBody Cliente usuarioWebSservice()
	{	Cliente cliente = new Cliente();
		cliente.setNombre("Portezuelo");
		cliente.setTelefono("099859197");
		return cliente;
	}
	
	@RequestMapping(value= "/authorized", method=RequestMethod.POST, headers="Accept=application/json", consumes={"application/json"})
	@ResponseBody
	public boolean logueo(@RequestBody Usuario usuario){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		System.out.println("Usuario obtenido: " + usuario.getId());
		System.out.println("Contra obtenida: " + usuario.getContrasena());
		
		try {
			Usuario usuarioObtenido = fachadaUsuario.seleccionarUsuario(usuario.getId());
			String contrasenaHash = fachadaUsuario.get_MD5_SecurePassword(usuario.getContrasena());
			
			if(usuarioObtenido.getContrasena() != null && usuarioObtenido.getContrasena().toLowerCase().equals(contrasenaHash.toLowerCase()))
			{
				fachadaUsuario.asignarIMEI(usuarioObtenido.getId(), usuario.getImei());
				return true;
			}
			else
			{
				return false;
			}
		
		}
		
		catch(ClassNotFoundException | IOException | SQLException | NoSuchAlgorithmException e) {
			e.printStackTrace();
			return false;
		}
		finally{
			context.close();
		}
	}
	
	@RequestMapping(value = "/contratosPorTecnico", method = RequestMethod.POST, headers="Accept=application/json", produces = {"application/json"}, consumes="application/json")
	public @ResponseBody List<Contrato> contratosPorTecnico(@RequestBody Usuario usuario)
	{
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaContratoTecnicos = (FachadaContratoTecnicos) context.getBean("fachadaContratoTecnicos"); 
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
			System.out.println("/contratosPorTecnico");
			List<Contrato> lista = null;
			String imei = usuario.getImei().trim();
			System.out.println("Usuario: "+usuario.getId());
			System.out.println("Imei: "+usuario.getImei());
		
		try {
			if (imei.equalsIgnoreCase(fachadaUsuario.seleccionarUsuario(usuario.getId()).getImei().trim()))
			{
				System.out.println("Los imei son iguales");
				lista = fachadaContratoTecnicos.listarContratosPorTecnicoTodos(usuario.getId());
				System.out.println("Cantidad de items enviados: "+lista.size());
			}
			else
				System.out.println("ERROR: Los imei difieren");
				
			return lista;

		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			return lista;
		}
		finally{
			context.close();
		}
	}
	
	@RequestMapping(value= "/desasociar", method=RequestMethod.POST, headers="Accept=application/json", consumes={"application/json"})
	@ResponseBody
	public boolean desasociarCelular(@RequestBody Usuario usuario){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		System.out.println("Usuario obtenido: " + usuario.getId());
		System.out.println("IMEI obtenido: " + usuario.getImei());
		
		try {
			Usuario usuarioObtenido = fachadaUsuario.seleccionarUsuario(usuario.getId());
			if (usuarioObtenido.getImei().equalsIgnoreCase(usuario.getImei())){
			
				fachadaUsuario.asignarIMEI(usuarioObtenido.getId(), "");
				return true;
			}else{
				return false;
			}
		}catch(ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally{
			context.close();
		}
	}
	
	@RequestMapping(value = "/tiposHoraParaContrato", method = RequestMethod.POST, headers="Accept=application/json", produces = {"application/json"}, consumes="application/json")
	public @ResponseBody List<TipoHora> tiposDeHoraPorContratoYTecnico(@RequestBody Usuario usuario)
	{
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaContratoTecnicos = (FachadaContratoTecnicos) context.getBean("fachadaContratoTecnicos"); 
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		fachadaTipoHora = (FachadaTipoHora) context.getBean("fachadaTipoHora");
		
		System.out.println("/tiposHoraParaContrato");
		List<TipoHora> lista = null;
		String imei = usuario.getImei().trim();
		boolean esTecnico = false;
		
		try {
			if (imei.equalsIgnoreCase(fachadaUsuario.seleccionarUsuario(usuario.getId()).getImei().trim()))
			{
				if (!fachadaUsuario.usuarioEsSocio(usuario.getId())){
					esTecnico = true;
				}
				
				if (esTecnico){
					//el nombre del contrato viene almacenado en el campo nombre del usuario.
					lista = fachadaTipoHora.verTiposHoraPorTecnicoYContrato(usuario.getId(), usuario.getNombre());
				}
				else
				{
					//el nombre del contrato viene almacenado en el campo nombre del usuario.
					lista = fachadaTipoHora.verTiposHoraPorContrato(usuario.getNombre());
				}
			}
			else{
				System.out.println("ERROR: Los imei difieren");
			}	
			return lista;

		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			return lista;
		}
		finally{
			context.close();
		}
	}

	@RequestMapping(value = "/actividadesPorContratoYTecnico", method = RequestMethod.POST, headers="Accept=application/json", produces = {"application/json"}, consumes="application/json")
	
	public @ResponseBody List<Actividad> actividadesPorContratoYTecnico(@RequestBody Usuario usuario)
	{
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml"); 
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		fachadaActividad = (FachadaActividad) context.getBean("fachadaActividad");
		
		System.out.println("/actividadesPorContratoYTecnico");
		List<Actividad> lista = null;
		String imei = usuario.getImei().trim();
		boolean esTecnico = false;
		
		try {
			if (imei.equalsIgnoreCase(fachadaUsuario.seleccionarUsuario(usuario.getId()).getImei().trim()))
			{
				if (!fachadaUsuario.usuarioEsSocio(usuario.getId())){
					esTecnico = true;
				}
				
				if (esTecnico){
					lista = fachadaActividad.verActividadesPendientesPorUsuarioYContrato(usuario.getId(), usuario.getNombre());
				}
				else
				{
					lista = fachadaActividad.verActividadesPendientesPorContrato(usuario.getNombre());
				}
			}
			else{
				System.out.println("ERROR: Los imei difieren");
			}
			return lista;
	
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			return lista;
		}
		finally{
			context.close();
		}
	}
	
	@RequestMapping(value = "/ingresoHora", method = RequestMethod.PUT, headers="Accept=application/json", consumes={"application/json"}, produces={"application/json"})
	public @ResponseBody Integer ingresoHoraMovil(@RequestBody HoraYUsuario horayusuario)
	{
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml"); 
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		fachadaHora = (FachadaHora) context.getBean("fachadaHora");
			
		System.out.println("/ingresoHora");
		String imei = horayusuario.getImei().trim();
		int duracion = 0;
		
		try{
			if (imei.equalsIgnoreCase(fachadaUsuario.seleccionarUsuario(horayusuario.getIdUsuario()).getImei().trim()))
			{
				fachadaHora.registrarHora(
					horayusuario.getFechaDesde(), 
					horayusuario.getFechaHasta(), 
					horayusuario.getTipoHora(), 
					"false", //remoto 
					horayusuario.getIdUsuario(), 
					horayusuario.getIdContrato(),
					horayusuario.getActividad(), 
					horayusuario.getDescripcion(), 
					horayusuario.getComentario()
					);
						
				long diferenciaEnMinutos = fachadaHora.diferenciaEnMinutos(horayusuario.getFechaDesde(), horayusuario.getFechaHasta());
				duracion = (int) diferenciaEnMinutos;
			}else
				System.out.println("ERROR: Los imei difieren");
			
			return duracion;
	
		}catch (ClassNotFoundException | IOException | SQLException | ParseException e) {
			return duracion;
		}catch (SgtiException e) {
			duracion = -1;
			e.getMessage();
			return duracion;
		}
		finally{
			context.close();
		}
	}
	
	@RequestMapping(value = "/horayusr", method = RequestMethod.GET, headers="Accept=application/json", produces = {"application/json"})
	public @ResponseBody HoraYUsuario usuarioWebService()
	{	
		HoraYUsuario horaYUsuario = new HoraYUsuario();
		return horaYUsuario; 
	}
	
	
	@RequestMapping(value = "/pruebaUsuario", method = RequestMethod.PUT, headers="Accept=application/json", consumes={"application/json"}, produces = {"application/json"})
	public @ResponseBody Integer ingresoPruebaUsuario(@RequestBody Usuario u)
	{
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml"); 
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		
		System.out.println("/ingresoClientePrueba");
		
		try{
			fachadaUsuario.ingresarUsuario(u.getId(), u.getNombre(), u.getApellido(), u.getContrasena(), u.getEmail(), u.getTelefono(), u.getTipo(), new ArrayList<String>());
			return 1;
			
		}catch (ClassNotFoundException | IOException | SQLException | NoSuchAlgorithmException | SgtiException e) {
			e.printStackTrace();
			return 0;
		}
		finally
		{
			context.close();
		}			
	}
}


