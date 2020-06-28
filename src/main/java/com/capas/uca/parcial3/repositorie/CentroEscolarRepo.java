package com.capas.uca.parcial3.repositorie;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.capas.uca.parcial3.domain.CentroEscolar;
import com.capas.uca.parcial3.domain.Municipio;

public interface CentroEscolarRepo extends JpaRepository<CentroEscolar, Integer> {
	
	@Query(nativeQuery = true, value = "select * from public.CentroEscolar")
	public List<CentroEscolar>mostrarTodos() throws DataAccessException;
	
	@Query(nativeQuery = true, value = "select * from public.CentroEscolar where fkmunicipio = ?1")
	public List<CentroEscolar>findD(Integer centro) throws DataAccessException;
}
