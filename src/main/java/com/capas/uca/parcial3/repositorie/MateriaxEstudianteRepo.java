package com.capas.uca.parcial3.repositorie;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.capas.uca.parcial3.domain.MateriaXestudiante;

public interface MateriaxEstudianteRepo extends JpaRepository<MateriaXestudiante, Integer> {
	
	@Query(nativeQuery = true, value = "select * from public.MateriaxEstudiante")
	public List<MateriaXestudiante> mostrarTodos() throws DataAccessException;
	
	
	@Query(nativeQuery = true, value = "select * from public.materiaXestudiante"
			+ "where idestudiante = ?1")
	public List<MateriaXestudiante> mostraruno(Integer code) throws DataAccessException;
	
}
