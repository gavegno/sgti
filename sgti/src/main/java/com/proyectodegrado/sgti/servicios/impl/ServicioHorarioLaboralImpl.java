package com.proyectodegrado.sgti.servicios.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import com.proyectodegrado.sgti.daos.HorarioLaboralDAO;
import com.proyectodegrado.sgti.modelo.Dia;
import com.proyectodegrado.sgti.modelo.HorarioLaboral;

public class ServicioHorarioLaboralImpl implements ServicioHorarioLaboral {
	
	private HorarioLaboralDAO horarioLaboralDao;
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioHorarioLaboral#insertar(com.proyectodegrado.sgti.modelo.HorarioLaboral)
	 */
	@Override
	public void insertar(HorarioLaboral horarioLaboral) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		for(Dia dia : horarioLaboral.getDias()){
			horarioLaboralDao.agregar(horarioLaboral.getId(), dia.getNombre(), dia.getHoraDesde(), dia.getHoraHasta());
		}
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioHorarioLaboral#insertarDíaAHorarioLaboral(com.proyectodegrado.sgti.modelo.HorarioLaboral, com.proyectodegrado.sgti.modelo.Dia)
	 */
	@Override
	public void insertarDíaAHorarioLaboral(HorarioLaboral horarioLaboral, Dia dia) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException {
		horarioLaboralDao.agregar(horarioLaboral.getId(), dia.getNombre(), dia.getHoraDesde(), dia.getHoraHasta());
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioHorarioLaboral#seleccionarHorarioLaboral(java.lang.String)
	 */
	@Override
	public HorarioLaboral seleccionarHorarioLaboral(String idHorarioLaboral) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		return horarioLaboralDao.seleccionarHorarioLaboral(idHorarioLaboral);
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioHorarioLaboral#editarDiaDeHorarioLaboral(java.lang.String, com.proyectodegrado.sgti.modelo.Dia)
	 */
	@Override
	public void editarDiaDeHorarioLaboral(String idHorarioLboral, Dia dia) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		horarioLaboralDao.editarDiaDeHorarioLaboral(idHorarioLboral, dia.getNombre(), dia.getHoraDesde(), dia.getHoraHasta());
	}
	
	/* (non-Javadoc)
	 * @see com.proyectodegrado.sgti.servicios.impl.ServicioHorarioLaboral#borrarDiaDeHorarioLaboral(java.lang.String, com.proyectodegrado.sgti.modelo.Dia)
	 */
	@Override
	public void borrarDiaDeHorarioLaboral(String idHorarioLaboral, Dia dia) throws FileNotFoundException, IOException, SQLException, ClassNotFoundException{
		horarioLaboralDao.borrarDiaDeHorarioLaboral(idHorarioLaboral, dia.getNombre());
	}

	public HorarioLaboralDAO getHorarioLaboralDao() {
		return horarioLaboralDao;
	}

	public void setHorarioLaboralDao(HorarioLaboralDAO horarioLaboralDao) {
		this.horarioLaboralDao = horarioLaboralDao;
	}
}
