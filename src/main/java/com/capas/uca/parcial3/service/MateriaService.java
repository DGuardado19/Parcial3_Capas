package com.capas.uca.parcial3.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.capas.uca.parcial3.domain.Materia;

public interface MateriaService {
	
	public List<Materia> findAll() throws DataAccessException;
	public Materia findOne(Integer code) throws DataAccessException;
	public void insertAndUpdate(Materia materia) throws DataAccessException;
}
