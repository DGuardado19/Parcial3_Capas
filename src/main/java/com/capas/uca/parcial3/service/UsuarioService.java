package com.capas.uca.parcial3.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.capas.uca.parcial3.domain.Estudiante;
import com.capas.uca.parcial3.domain.Usuario;

public interface UsuarioService {
	
	public List<Usuario> findAll() throws DataAccessException;

	public Usuario findOne(Integer code) throws DataAccessException;

	public void insertAndUpdate(Usuario usuario) throws DataAccessException;
}
