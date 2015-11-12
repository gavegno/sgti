package com.proyectodegrado.sgti.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.proyectodegrado.sgti.fachada.FachadaUsuario;
import com.proyectodegrado.sgti.modelo.Cliente;
import com.proyectodegrado.sgti.modelo.Usuario;


@Controller
@RequestMapping("/desktop/movil")
public class RestMovilController {
	
	private FachadaUsuario fachadaUsuario;
	
	@RequestMapping(value = "/cliente", method = RequestMethod.GET, headers="Accept=application/json", produces = {"application/json"})
	public @ResponseBody Cliente usuarioWebService()
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
			
			if(usuarioObtenido.getContrasena() != null 
					&& 
					usuarioObtenido.getContrasena().toLowerCase().equals(contrasenaHash.toLowerCase()))
			{
				
				fachadaUsuario.asignarIMEI(usuarioObtenido.getId(), usuario.getImei());
				return true;
			}
			else
			{
				//return salida;
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
	
	@RequestMapping(value= "/pruebaAuth", method = RequestMethod.POST, headers="Accept=application/json", produces={"application/json"}, consumes={"application/json"})
	@ResponseBody
	public String pruebaAuth(@RequestBody Cliente cliente){
		
			
		if (cliente.getId() == 123)	
				return "true";
			else
				return "false";
		
	}
	
	@RequestMapping(value = "/usuario", method = RequestMethod.GET, headers="Accept=application/json", produces = {"application/json"})
	public @ResponseBody Usuario usuarioWeb()
	{	Usuario u = new Usuario();
		u.setNombre("peteco");
		return u;
	}
	

}
