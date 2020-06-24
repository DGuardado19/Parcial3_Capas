package com.capas.uca.parcial3.repositorie;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.capas.uca.parcial3.domain.Usuario;

public interface UsuarioRepo extends JpaRepository<Usuario, Integer> {
	
	@Query(nativeQuery = true, value = "select * from public.Usuario")
	public List<Usuario>mostrarTodos() throws DataAccessException;
	
	@Query(nativeQuery = true, value = "select * from public.Usuario where nombreUser= :user and contrasenia = :pass")
	public Usuario login(String user, String pass) throws DataAccessException;
}
