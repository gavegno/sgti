package com.proyectodegrado.sgti.modelo;

import java.util.List;

public class Usuario {
	
	private String id;
	private String nombre;
	private String apellido;
	private String contrasena;
	private String email;
	private String telefono;
	private String tipo;
	private boolean activo;
	private List<TipoHora> usuarioTipoHora;
	private String imei;
	
	public Usuario(String id, String nombre, String apellido, String contrasena, String email, String telefono, String tipo, boolean activo, List<TipoHora> usuarioTipoHora) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.contrasena = contrasena;
		this.email = email;
		this.telefono = telefono;
		this.tipo = tipo;
		this.activo = activo;
		this.usuarioTipoHora = usuarioTipoHora;
	}

	public Usuario() {
		super();
		this.id = null;
		this.nombre = null;
		this.apellido = null;
		this.contrasena = null;
		this.email = null;
		this.telefono = null;
		this.tipo = null;
		this.activo = false;
		this.usuarioTipoHora = null;
		this.imei = null;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public List<TipoHora> getUsuarioTipoHora() {
		return usuarioTipoHora;
	}

	public void setUsuarioTipoHora(List<TipoHora> usuarioTipoHora) {
		this.usuarioTipoHora = usuarioTipoHora;
	}
	
}
