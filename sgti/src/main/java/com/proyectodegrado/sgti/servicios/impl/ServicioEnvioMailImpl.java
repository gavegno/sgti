package com.proyectodegrado.sgti.servicios.impl;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;

public class ServicioEnvioMailImpl implements ServicioEnvioMail {
	
	//@Value("${mail.username}")
	static String username;
	
	//@Value("${mail.password}")
	static String password;
	
    static Properties poperties;
    
    static Session session;
	
	 /* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioEnvioMail#enviarMail(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void enviarMail(String asunto, String email, String id, String fecha, String descripcion, String estado) {
        poperties = new Properties();
        configurarProperties();
        username = "proyectogradosgti@gmail.com";
        password = "proyecto_sgti";
        session = crearSesion();
        
        MimeMessage mensaje = new MimeMessage(session);
        try {
	        mensaje.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
	        mensaje.setSubject(asunto);
	        mensaje.setText("Actividad: " + id + "<br> Fecha: " + fecha + "<br>" + descripcion + "<br> Estado: " + estado, "ISO-8859-1", "html");
	        Transport.send(mensaje);
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
		 
	 }

	private void configurarProperties() {
		poperties.put("mail.smtp.auth", "true");
        poperties.put("mail.smtp.starttls.enable", "true");
        poperties.put("mail.smtp.host", "smtp.gmail.com");
        poperties.put("mail.smtp.port", "587");
	}
	 
	 private static Session crearSesion() {
	        Session session = Session.getInstance(poperties,
	          new javax.mail.Authenticator() {
	            @Override
	                protected PasswordAuthentication getPasswordAuthentication() {
	                        return new PasswordAuthentication(username, password);
	                }
	          });
	        return session;
	    } 
}
