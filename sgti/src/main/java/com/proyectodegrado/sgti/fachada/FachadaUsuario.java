package com.proyectodegrado.sgti.fachada;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	
	public void ingresarUsuario(String id, String nombre, String apellido, String contrasena, String email, String telefono, String tipo, List<String> tiposDeTipoHora) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		List<TipoHora> tiposHora = new ArrayList<TipoHora>();
		for(String tipoDeTipoHora : tiposDeTipoHora){
			TipoHora tipoHora = servicioTipoHora.seleccionarPorTipo(tipoDeTipoHora);
			tiposHora.add(tipoHora);
		}
		Usuario usuario = new Usuario(id, nombre, apellido, contrasena, email, telefono, tipo, true, null);
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
	
	public List<String> verTiposDeHora() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		List<String> nombreTipos = new ArrayList<String>();
		for(TipoHora tipoHora : servicioTipoHora.seleccionarTipos()){
			nombreTipos.add(tipoHora.getTipo());
		}
		return nombreTipos;
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
