package com.capas.uca.parcial3.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capas.uca.parcial3.repositorie.MateriaxEstudianteRepo;

@Service
public class MateriaxEstudianteServiceImpl implements MateriaxEstudianteService {
	
	@Autowired
	MateriaxEstudianteRepo Repo;
	
	@PersistenceContext(unitName = "capas2")
	EntityManager entityManager;
}
