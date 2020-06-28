package com.capas.uca.parcial3.repositorie;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.capas.uca.parcial3.domain.Materia;

public interface MateriaRepo extends JpaRepository<Materia, Integer> {
	@Query(nativeQuery = true, value = "select * from public.Materia")
	public List<Materia>mostrarTodos() throws DataAccessException;
	@Query(nativeQuery = true, value = "select COUNT(*) from materia where LOWER(nombre) like ?1%")
	public Integer countM(String search) throws DataAccessException;
}
