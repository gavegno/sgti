package com.itapua.app.sgti.modelo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Usuario on 09/11/2015.
 */
public class Usuario {
    @SerializedName("id")
    private String id;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("apellido")
    private String apellido;

    @SerializedName("contrasena")
    private String contrasena;

    @SerializedName("email")
    private String email;

    @SerializedName("telefono")
    private String telefono;

    @SerializedName("tipo")
    private String tipo;

    @SerializedName("activo")
    private boolean activo;

    @SerializedName("imei")
    private String imei;

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

    public Usuario() {
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", tipo='" + tipo + '\'' +
                ", activo=" + activo +
                ", imei='" + imei + '\'' +
                '}';
    }

    public Usuario(String id, String nombre, String apellido, String contrasena, String email, String telefono, String tipo, boolean activo, String imei) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.contrasena = contrasena;
        this.email = email;
        this.telefono = telefono;
        this.tipo = tipo;
        this.activo = activo;
        this.imei = imei;
    }
}
