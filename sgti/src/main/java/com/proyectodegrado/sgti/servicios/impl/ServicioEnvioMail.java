package com.proyectodegrado.sgti.servicios.impl;

public interface ServicioEnvioMail {

	public abstract void enviarMail(String asunto, String email, String id, String fecha, String descripcion, String realizada);

}