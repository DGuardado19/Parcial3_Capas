package com.capas.uca.parcial3.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.capas.uca.parcial3.domain.MateriaXestudiante;
import com.capas.uca.parcial3.repositorie.MateriaxEstudianteRepo;

@Service
public class MateriaxEstudianteServiceImpl implements MateriaxEstudianteService {
	
	@Autowired
	MateriaxEstudianteRepo Repo;
	
	@PersistenceContext(unitName = "capas2")
	EntityManager entityManager;
	
	@Override
	public List<MateriaXestudiante> findAll() throws DataAccessException {
		// TODO Auto-generated method stub
		return Repo.mostrarTodos();
	}

	@Override
	public List<MateriaXestudiante> findcode(Integer code) throws DataAccessException {
		// TODO Auto-generated method stub
		return Repo.mostraruno(code);
	}
	
}
