package com.proyectodegrado.sgti.daos;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.proyectodegrado.sgti.consultas.ConsultasComputoAcumulado;
import com.proyectodegrado.sgti.modelo.ComputoAcumulado;

public class ComputosAcumuladosDAO {
	
	private ConsultasComputoAcumulado consultasComputoAcumulado;
	
	public void agregar(Date fecha, String idContrato, double computos) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		consultasComputoAcumulado.insertarComputosAcumulados(new java.sql.Date(fecha.getTime()), idContrato, computos);
	}
	
	public List<ComputoAcumulado> verComputosAcumulados (String idContrato) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		List<ComputoAcumulado> computosAcumulados = new ArrayList<ComputoAcumulado>();
		ResultSet resultSet = consultasComputoAcumulado.verComputosAcumulados(idContrato);
		while(resultSet.next()){
			ComputoAcumulado computoAcumulado = new ComputoAcumulado();
			computoAcumulado.setId(resultSet.getInt("id"));
			computoAcumulado.setIdContrato(resultSet.getString("idContrato"));
			computoAcumulado.setFecha(resultSet.getDate("fecha"));
			computoAcumulado.setComputos(resultSet.getDouble("computos"));
			computosAcumulados.add(computoAcumulado);
		}
		return computosAcumulados;
	}
	
	public void borrar(int id) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		consultasComputoAcumulado.borrarComputosAcumulados(id);
	}
	
	public void editar(int id, double computos) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		consultasComputoAcumulado.editarComputosAcumulados(id, computos);
	}

	public ConsultasComputoAcumulado getConsultasComputoAcumulado() {
		return consultasComputoAcumulado;
	}

	public void setConsultasComputoAcumulado(
			ConsultasComputoAcumulado consultasComputoAcumulado) {
		this.consultasComputoAcumulado = consultasComputoAcumulado;
	}
	
}
