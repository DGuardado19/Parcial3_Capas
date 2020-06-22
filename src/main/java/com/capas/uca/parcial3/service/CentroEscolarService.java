package com.capas.uca.parcial3.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.capas.uca.parcial3.domain.CentroEscolar;
import com.capas.uca.parcial3.domain.Estudiante;


public interface CentroEscolarService {
	public List<CentroEscolar> findAll() throws DataAccessException;
	public CentroEscolar findOne(Integer code) throws DataAccessException;

	public void insertAndUpdate(CentroEscolar centroEscolar) throws DataAccessException;
}
