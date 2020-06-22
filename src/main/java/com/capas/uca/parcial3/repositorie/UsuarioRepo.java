package com.capas.uca.parcial3.repositorie;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capas.uca.parcial3.domain.Usuario;

public interface UsuarioRepo extends JpaRepository<Usuario, Integer> {

}
