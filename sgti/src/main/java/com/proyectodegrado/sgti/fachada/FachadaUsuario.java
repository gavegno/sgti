package com.proyectodegrado.sgti.fachada;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.security.NoSuchAlgorithmException;

import com.proyectodegrado.sgti.exceptions.SgtiException;
import com.proyectodegrado.sgti.modelo.TipoHora;
import com.proyectodegrado.sgti.modelo.Usuario;
import com.proyectodegrado.sgti.servicios.ServicioTipoHora;
import com.proyectodegrado.sgti.servicios.ServicioUsuario;
import com.proyectodegrado.sgti.servicios.impl.ServicioUsuarioContraparteImpl;
import com.proyectodegrado.sgti.servicios.impl.ServicioUsuarioSocioImpl;
import com.proyectodegrado.sgti.servicios.impl.ServicioUsuarioTecnicoImpl;

public class FachadaUsuario {
	
	private ServicioUsuarioSocioImpl servicioUsuarioSocio;
	
	private ServicioUsuarioTecnicoImpl servicioUsuarioTecnico;
	
	private ServicioUsuarioContraparteImpl servicioUsuarioContraparte;
	
	private ServicioUsuario servicioUsuario;
	
	private ServicioTipoHora servicioTipoHora;
	
	public void ingresarUsuario(String id, String nombre, String apellido, String contrasena, String email, String telefono, String tipo, List<String> tiposDeTipoHora) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, SgtiException, NoSuchAlgorithmException{
		List<TipoHora> tiposHora = new ArrayList<TipoHora>();
		for(String tipoDeTipoHora : tiposDeTipoHora){
			TipoHora tipoHora = servicioTipoHora.seleccionarPorTipo(tipoDeTipoHora);
			tiposHora.add(tipoHora);
		}
	
		String contrasenaHash = servicioUsuario.get_MD5_SecurePassword(contrasena);
		
		Usuario usuario = new Usuario(id, nombre, apellido, contrasenaHash, email, telefono, tipo, true, null);
		if(tipo.equalsIgnoreCase("SOCIO")){
			servicioUsuarioSocio.agregar(usuario);
		}else if(tipo.equalsIgnoreCase("TECNICO")){
			servicioUsuarioTecnico.agregar(usuario);
		}else{
			servicioUsuarioContraparte.agregar(usuario);
		}
		usuario.setUsuarioTipoHora(tiposHora);
		servicioUsuario.agregarTipoHoraUsuario(usuario);
	}
	
	public void cambiarContrasena(String id, String contrasena) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		Usuario usuario = new Usuario();
		usuario.setId(id);
		usuario.setContrasena(contrasena);
		servicioUsuario.cambiarContrasena(usuario);
		
	}
	
	public void editarUsuario(String id, String nombre, String apellido, String email, String telefono) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		Usuario usuario = new Usuario();
		usuario.setId(id);
		usuario.setNombre(nombre);
		usuario.setApellido(apellido);
		usuario.setEmail(email);
		usuario.setTelefono(telefono);
		servicioUsuario.editarUsuario(usuario);
		
	}
	
	public void agregarTipoHoraUsuario(String id, Integer idTipoHora) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		Usuario usuario = new Usuario();
		usuario.setId(id);
		servicioUsuario.agregarTipoHoraUsuarioSimple(usuario, idTipoHora);
	}
	
	public void eliminacionLogicaUsuario (String id) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		Usuario usuario = new Usuario();
		usuario.setId(id);
		servicioUsuario.eliminarUsuario(usuario);
	}
	
	public boolean usuarioEsSocio (String id) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		Usuario usuario = servicioUsuario.selecionarUsuario(id);
		return (usuario.getTipo().equalsIgnoreCase("SOCIO"));		
	}
	
	public void asignarIMEI (String id, String imei) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		Usuario usuario = servicioUsuario.selecionarUsuario(id);
		usuario.setImei(imei);
		
		servicioUsuario.asignarImeiUsuario(usuario);
	}
	
	
	
	public String get_MD5_SecurePassword(String passwordToHash) throws NoSuchAlgorithmException{
		return servicioUsuario.get_MD5_SecurePassword(passwordToHash);
	}
	
	public Usuario seleccionarUsuario(String id) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		return servicioUsuario.selecionarUsuario(id);
	}
	
	public List<Usuario> seleccionarUsuarios() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		return servicioUsuario.seleccionarUsuarios();
	}
	
	public List<Usuario> verUsuariosContraparte() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		return servicioUsuarioContraparte.seleccionarUsuarios();
	}
	
	public List<Usuario> seleccionarUsuariosTecnico() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		return servicioUsuarioTecnico.seleccionarUsuarios();
	}
	
	public List<Usuario> seleccionarUsuariosSocio() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		return servicioUsuarioSocio.seleccionarUsuarios();
	}
	
	public ServicioUsuarioSocioImpl getServicioUsuarioSocio() {
		return servicioUsuarioSocio;
	}

	public void setServicioUsuarioSocio(
			ServicioUsuarioSocioImpl servicioUsuarioSocio) {
		this.servicioUsuarioSocio = servicioUsuarioSocio;
	}

	public ServicioUsuarioTecnicoImpl getServicioUsuarioTecnico() {
		return servicioUsuarioTecnico;
	}

	public void setServicioUsuarioTecnico(
			ServicioUsuarioTecnicoImpl servicioUsuarioTecnico) {
		this.servicioUsuarioTecnico = servicioUsuarioTecnico;
	}

	public ServicioUsuarioContraparteImpl getServicioUsuarioContraparte() {
		return servicioUsuarioContraparte;
	}

	public void setServicioUsuarioContraparte(
			ServicioUsuarioContraparteImpl servicioUsuarioContraparte) {
		this.servicioUsuarioContraparte = servicioUsuarioContraparte;
	}

	public ServicioUsuario getServicioUsuario() {
		return servicioUsuario;
	}

	public void setServicioUsuario(ServicioUsuario servicioUsuario) {
		this.servicioUsuario = servicioUsuario;
	}

	public ServicioTipoHora getServicioTipoHora() {
		return servicioTipoHora;
	}

	public void setServicioTipoHora(ServicioTipoHora servicioTipoHora) {
		this.servicioTipoHora = servicioTipoHora;
	}
	
	

}
