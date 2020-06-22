package com.capas.uca.parcial3.repositorie;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.capas.uca.parcial3.domain.Estudiante;

public interface EstudianteRepo extends JpaRepository<Estudiante, Integer> {
	
	List<Estudiante>findByNombreAndApellido(String nombre, String apellido) throws DataAccessException;
	List<Estudiante>findByApellidoStartingWith(String nombre) throws DataAccessException;
	List<Estudiante>findByNombreStartingWith(String nombre) throws DataAccessException;

	@Query(nativeQuery = true, value = "select * from public.estudiante")
	public List<Estudiante>mostrarTodos() throws DataAccessException;
}
