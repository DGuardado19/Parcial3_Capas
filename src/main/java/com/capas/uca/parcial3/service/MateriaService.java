package com.capas.uca.parcial3.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.capas.uca.parcial3.domain.Materia;

public interface MateriaService {
	
	public List<Materia> findAll() throws DataAccessException;
	public Materia findOne(Integer code) throws DataAccessException;
	public void insertAndUpdate(Materia materia) throws DataAccessException;
	public Page<Materia> findAll(Pageable page) throws DataAccessException;
	public Integer countAll(String search);
	public Long countAll2();
}
