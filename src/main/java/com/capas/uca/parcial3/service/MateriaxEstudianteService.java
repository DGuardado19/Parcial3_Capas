package com.capas.uca.parcial3.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.capas.uca.parcial3.domain.MateriaXestudiante;

public interface MateriaxEstudianteService {

	public List<MateriaXestudiante> findAll() throws DataAccessException;
	public List<MateriaXestudiante> findcode(Integer code) throws DataAccessException;

}
