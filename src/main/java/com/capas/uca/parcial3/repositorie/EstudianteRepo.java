package com.capas.uca.parcial3.repositorie;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capas.uca.parcial3.domain.Estudiante;

public interface EstudianteRepo extends JpaRepository<Estudiante, Integer> {

}
