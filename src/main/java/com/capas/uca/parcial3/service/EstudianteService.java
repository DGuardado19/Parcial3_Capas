package com.capas.uca.parcial3.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.capas.uca.parcial3.domain.Estudiante;

public interface EstudianteService {
	
	public List<Estudiante> findAll() throws DataAccessException;
	public List<Estudiante> findByApel(String cadena) throws DataAccessException;
	public List<Estudiante> findByName(String cadena) throws DataAccessException;
	public List<Estudiante> findByNameAndApel(String cadena, String cadena2) throws DataAccessException;

	public Estudiante findOne(Integer code) throws DataAccessException;

	public void insertAndUpdate(Estudiante estudiante) throws DataAccessException;
}
