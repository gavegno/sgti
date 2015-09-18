package com.proyectodegrado.sgti.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.proyectodegrado.sgti.exceptions.SgtiException;
import com.proyectodegrado.sgti.fachada.FachadaTipoHora;
import com.proyectodegrado.sgti.fachada.FachadaUsuario;
import com.proyectodegrado.sgti.modelo.Usuario;

@Controller
@RequestMapping("/desktop/tecnicos")
public class UsuarioController extends AbstractController{
	
	private static final String MENSAJE_ERROR = "Ha ocurrido un error";

	private FachadaUsuario fachadaUsuario;
	
	private FachadaTipoHora fachadaTipoHora;
	
	@RequestMapping(value = "/ingresarUsuario", method = RequestMethod.POST)
	public String ingresarUsuario(Model model, @RequestParam("id") final String id, @RequestParam("contrasena") final String contrasena, @RequestParam("nombre") final String nombre, @RequestParam("apellido") final String apellido, @RequestParam("email") final String email, @RequestParam("telefono") final String telefono, @RequestParam("tipo") final String tipo, @RequestParam(value = "tipoHora", required = false) final List<String> tipoHora){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
		String mensaje = "El usuario ha sido creado correctamente";
		try {
			fachadaUsuario.ingresarUsuario(id, nombre, apellido, contrasena, email, telefono, tipo, tipoHora == null ? new ArrayList<String>() : tipoHora);
			model.addAttribute("message", mensaje);
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			mensaje = MENSAJE_ERROR;
			model.addAttribute("errorMessage", mensaje);
			return cargarPagina(model);
		} catch (SgtiException e) {
			e.printStackTrace();
			mensaje = e.getMessage();
			model.addAttribute("errorMessage", mensaje);
			return cargarPagina(model);
		} catch (NoSuchAlgorithmException e) {
			model.addAttribute("errorMessage", "Error al obtener Hash de contraseña");
			return cargarPagina(model);	
		}finally{
			context.close();
		}
		return cargarPagina(model);
	}
	
	@RequestMapping(value = "/ingresar", method = RequestMethod.GET)
	public String cargarPagina(Model model){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		fachadaTipoHora = (FachadaTipoHora) context.getBean("fachadaTipoHora");
		try {
			
			model.addAttribute("tipos", fachadaTipoHora.verTiposDeHora());
		} catch (ClassNotFoundException | IOException | SQLException e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", e.getMessage());
		}finally{
			context.close();
		}
		return "desktop/tecnicos";
	}
	
	//Carga la tabla de usuarios, donde se permitirá editarlos.
		@RequestMapping(value = "/tabla", method = RequestMethod.GET)
		public String cargarTablaUsuarios(Model model)
		{	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
			fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
			try {
				model.addAttribute("usuarios", fachadaUsuario.seleccionarUsuarios());
			} catch (IOException | SQLException | ClassNotFoundException e) {
				e.printStackTrace();
				model.addAttribute("errorMessage", MENSAJE_ERROR);
				return "desktop/tablaUsuarios";
			}finally{
				context.close();
			}
			return "desktop/tablaUsuarios";
		}
		
		//Carga la página de editar usuario, con el seleccionado en la tabla.
		@RequestMapping(value="/editar", method = RequestMethod.POST)
		public String cargarUsuario(Model model, 
				@RequestParam("id") final String id){
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
			fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
			try {			
				model.addAttribute("usuario", fachadaUsuario.seleccionarUsuario(id));
			} catch (ClassNotFoundException | IOException | SQLException e) {
				e.printStackTrace();
				model.addAttribute("errorMessage", MENSAJE_ERROR);
				return cargarTablaUsuarios(model);
			}finally{
				context.close();
			}
			return "desktop/editarTecnicos";
		}
		
		@RequestMapping(value = "/editarUsuarioOk", method = RequestMethod.POST)
		public String editarUsuario(Model model, 
				@RequestParam("id") final String id, 
				@RequestParam("nombre") final String nombre, 
				@RequestParam("apellido") final String apellido, 
				@RequestParam("email") final String email, 
				@RequestParam("telefono") final String telefono){
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
			fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
			String mensaje = "El usuario se editó correctamente";
			try {			
				fachadaUsuario.editarUsuario(id, nombre, apellido, email, telefono);
				model.addAttribute("message", mensaje);
			} catch (ClassNotFoundException | IOException | SQLException e) {
				e.printStackTrace();
				mensaje = e.getMessage();
				model.addAttribute("errorMessage", mensaje);
				return cargarTablaUsuarios(model);
			}finally{
				context.close();
			}
			return cargarTablaUsuarios(model);
		}
		
		
		//Carga la página de editar usuario, con el seleccionado en la tabla.
				@RequestMapping(value="/cargarCambiarContrasena", method = RequestMethod.GET)
				public String cargarCambiarContrasena(Model model, HttpServletRequest request){
					
					return "desktop/cambiarContrasena";
				}
		
		
		
		//Acción de cambiar la contraseña.
				@RequestMapping(value="/cambiarContrasena", method = RequestMethod.POST)
				public String cambiarContrasena(Model model, HttpServletRequest request, 
						@RequestParam("passwordActual") final String passwordActual,
						@RequestParam("passwordNueva1") final String passwordNueva1,
						@RequestParam("passwordNueva2") final String passwordNueva2){
					
					ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
					fachadaUsuario = (FachadaUsuario) context.getBean("fachadaUsuario");
					String idUsuario = (String) request.getSession().getAttribute("usuario");
					String mensaje = "Contraseña cambiada con éxito";
					String mensajeError = "";
					

					try {
						//Obtengo los datos del usuario para luego comprar la contraseña actual.
						Usuario usuario = fachadaUsuario.seleccionarUsuario(idUsuario);
						
						String contrasenaHashActual = fachadaUsuario.get_MD5_SecurePassword(passwordActual);
						
						if(usuario.getContrasena() != null && usuario.getContrasena().toLowerCase().equals(contrasenaHashActual.toLowerCase())){
							//True si la clave actual es correcta.
							//Ahora chequeo que las dos claves nuevas coincidan:
							
							if (passwordNueva1 != null && passwordNueva1.equals(passwordNueva2))
							{
								//True si ambas claves son idénticas y no son vacías.
								String contrasenaHashNueva = fachadaUsuario.get_MD5_SecurePassword(passwordNueva1);
								fachadaUsuario.cambiarContrasena(idUsuario, contrasenaHashNueva);
								model.addAttribute("message", mensaje);
							}
							else
								mensajeError = "Las contraseñas nuevas no coinciden";
						}
						else
						{
							mensajeError = "La clave actual no es correcta";
							
						}
					} catch (ClassNotFoundException | IOException | SQLException | NoSuchAlgorithmException e) {
						e.printStackTrace();
						model.addAttribute("errorMessage", MENSAJE_ERROR);
						return cargarCambiarContrasena(model, request);
					}finally{
						if (mensajeError.length() != 0)
							model.addAttribute("errorMessage", mensajeError);
						context.close();
					}
					return cargarCambiarContrasena(model, request);
				}
				
				//Se llamará en el caso de que intente cambiar la clave y la actual no es correcta.
				public String logout(Model model, HttpServletRequest request){
					request.getSession().invalidate();
					return "/desktop/login2";
				}
		
	public FachadaUsuario getFachadaUsuario() {
		return fachadaUsuario;
	}

	public void setFachadaUsuario(FachadaUsuario fachadaUsuario) {
		this.fachadaUsuario = fachadaUsuario;
	}
}
