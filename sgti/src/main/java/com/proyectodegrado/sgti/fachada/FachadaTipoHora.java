package com.proyectodegrado.sgti.fachada;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.proyectodegrado.sgti.exceptions.SgtiException;
import com.proyectodegrado.sgti.modelo.ContratoTipoHora;
import com.proyectodegrado.sgti.modelo.TipoHora;
import com.proyectodegrado.sgti.modelo.TipoHoraComputo;
import com.proyectodegrado.sgti.servicios.ServicioContratoTipoHora;
import com.proyectodegrado.sgti.servicios.ServicioTipoHora;

public class FachadaTipoHora {
	
	private ServicioTipoHora servicioTipoHora;
	
	private ServicioContratoTipoHora servicioContratoTipoHora;
	
	public void insertarTipoHora(String tipo) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException, SgtiException{
		TipoHora tipoHora = new TipoHora();
		tipoHora.setTipo(tipo);
		servicioTipoHora.agregar(tipoHora);
	}
	
	public void insertarContratoTipoHora(String idContrato, String nombreTipoHora, double computo) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		TipoHora tipoHora = servicioTipoHora.seleccionarPorTipo(nombreTipoHora);
		TipoHoraComputo tipoHoraComputo = new TipoHoraComputo(tipoHora, computo);
		servicioContratoTipoHora.insertar(idContrato, tipoHoraComputo);
	}
	
	public void editarTipoHora (int id, String tipo) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		servicioTipoHora.editarTipoHora(id, tipo);
	}
	
	public void editarComputosDeTipoHora (String idContrato, int idTipoHora, double computo) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		TipoHora tipoHora = servicioTipoHora.seleccionarPorId(idTipoHora);
		TipoHoraComputo tipoHoraComputo = new TipoHoraComputo(tipoHora, computo);
		servicioContratoTipoHora.editarContratoTipoHora(idContrato, tipoHoraComputo);
		
	}
	
	public void borrarContratoTiposHora (String idContrato, int idTipoHora) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		servicioContratoTipoHora.borrarContratoTiposHora(idContrato, idTipoHora);
	}
	
	public List<TipoHoraComputo> verTiposDeHoraPorContrato(String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		return servicioContratoTipoHora.verContratoTipoHora(idContrato);
	}
	
	public void borrarTipoHora (int id) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		servicioTipoHora.borrarTipoHora(id);
	}
	
	public List<TipoHora> verTiposDeHora() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		return servicioTipoHora.seleccionarTipos();
	}
	
	public List<TipoHora> verTiposDeHoraQueNoTengaEnUsoContrato(String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, SgtiException{
		return servicioTipoHora.seleccionarTiposQueNoEsteUsandoElContrato(idContrato);
	}
	
	public List<TipoHora> seleccionarTiposQueNoTengaElTecnico(String idUsuario) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, SgtiException{
		return servicioTipoHora.seleccionarTiposQueNoTengaElTecnico(idUsuario);
	}
	
	public List<TipoHora> seleccionarTiposAsignadosAlTecnico(String idUsuario) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException, SgtiException{
		return servicioTipoHora.seleccionarTiposAsignadosAlTecnico(idUsuario);
	}
	
	public List<TipoHora> verTiposHoraPorContrato(String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException{
		return servicioTipoHora.verTiposHoraPorContrato(idContrato);
	}
	
	public List<ContratoTipoHora> verContratoTiposHoraParaGestionarHoras() throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		return servicioContratoTipoHora.verContratoTiposHoraParaGestionarHoras();
	}
	
	public List<ContratoTipoHora> verContratoTiposHoraParaGestionarHorasPorTecnico(String idUsuario) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		return servicioContratoTipoHora.verContratoTiposHoraParaGestionarHorasPorTecnico(idUsuario);
	}
	
	public List<TipoHora> verTiposHoraPorTecnicoYContrato(String idUsuario, String idContrato) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		return servicioContratoTipoHora.verTiposHoraPorTecnicoYContrato(idUsuario, idContrato);
	}
	
	public void sacarTipoHoraATecnico (String idUsuario, int idTipoHora) throws FileNotFoundException, ClassNotFoundException, IOException, SQLException
	{
		servicioTipoHora.sacarTipoHoraATecnico(idUsuario, idTipoHora);
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

	public void setServicioContratoTipoHora(
			ServicioContratoTipoHora servicioContratoTipoHora) {
		this.servicioContratoTipoHora = servicioContratoTipoHora;
	}
}
