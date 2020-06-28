package com.capas.uca.parcial3.repositorie;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.capas.uca.parcial3.domain.MateriaXestudiante;

public interface MateriaxEstudianteRepo extends JpaRepository<MateriaXestudiante, Integer> {
	
	@Query(nativeQuery = true, value = "select * from public.MateriaxEstudiante")
	public List<MateriaXestudiante> mostrarTodos() throws DataAccessException;
	
	
	@Query(nativeQuery = true, value = "select * from public.materiaXestudiante where "
			+ "     fkestudiante = ?1 ")
	public List<MateriaXestudiante> mostrarUno(Integer code) throws DataAccessException;
	
	@Query(nativeQuery=true,value="select mat.fkestudiante, "
			+ "es.nombre, es.apellido  from public.materiaXestudiante mat, public.estudiante es, public.materia mate" + 
			" where mat.fkestudiante = es.idestudiante " + 
			" and mat.fkmateria = mate.idmateria and es.nombre LIKE ?1%   "
			+ "    and es.apellido LIKE ?2% "
			+ "  group by es.nombre, mat.fkestudiante, es.apellido  ")
	public List<Object[]> pruebaDTO(String nombre, String apellido, Pageable page) throws DataAccessException; 
	
}
