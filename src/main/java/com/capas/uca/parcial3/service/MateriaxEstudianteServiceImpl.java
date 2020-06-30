package com.capas.uca.parcial3.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.capas.uca.parcial3.domain.Estudiante;
import com.capas.uca.parcial3.domain.MateriaXestudiante;
import com.capas.uca.parcial3.dto.ResutDTO;
import com.capas.uca.parcial3.repositorie.EstudianteRepo;
import com.capas.uca.parcial3.repositorie.MateriaxEstudianteRepo;

@Service
public class MateriaxEstudianteServiceImpl implements MateriaxEstudianteService {

	@Autowired
	MateriaxEstudianteRepo Repo;

	@Autowired
	EstudianteRepo RepoE;

	@PersistenceContext(unitName = "capas2")
	EntityManager entityManager;

	@Override
	public List<MateriaXestudiante> findAll() throws DataAccessException {
		// TODO Auto-generated method stub
		return Repo.mostrarTodos();
	}

	@Override
	public List<MateriaXestudiante> findcode(Integer code) throws DataAccessException {
		// TODO Auto-generated method stub
		return Repo.mostrarUno(code);
	}

	@Override
	public Page<ResutDTO> dtoPrueba(String nombre, String apellido, Pageable page) throws DataAccessException {
		// TODO Auto-generated method stub
		List<ResutDTO> estudiantes = Repo.pruebaDTO(nombre, apellido, page).stream().map(obj -> {
			ResutDTO e = new ResutDTO();
			List<MateriaXestudiante> k = null;
			k= Repo.mostrarUno(Integer.parseInt(obj[0].toString()));
			Integer reprobado =0, aprobado =0;
			float promedio=0;
			for(int i =0; i < k.size();i++) {
				if(k.get(i).getNota()>=6) {
					aprobado++;
				}
				else {
					reprobado++;
				}
				promedio += k.get(i).getNota();
			}
			e.setId(Integer.parseInt(obj[0].toString()));
			e.setNombre(obj[1].toString());
			e.setApellido(obj[2].toString());
			e.setAprobadas(aprobado);
			e.setReprobadas(reprobado);
			e.setProm(promedio/(aprobado+reprobado));
			return e;
		}).collect(Collectors.toList());
		Page<ResutDTO> page2 = new PageImpl<>(estudiantes);
		return page2; 
	}

}
