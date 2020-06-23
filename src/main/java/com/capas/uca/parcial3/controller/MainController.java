package com.capas.uca.parcial3.controller;

import java.sql.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import com.capas.uca.parcial3.domain.*;
import com.capas.uca.parcial3.service.CentroEscolarService;
import com.capas.uca.parcial3.service.DepartamentoService;
import com.capas.uca.parcial3.service.EstudianteService;
import com.capas.uca.parcial3.service.MateriaService;
import com.capas.uca.parcial3.service.MateriaxEstudianteService;
import com.capas.uca.parcial3.service.MunicipioService;
import com.capas.uca.parcial3.service.UsuarioService;

@Controller
public class MainController {
	
	@Autowired
	private MateriaService MateriaService;
	@Autowired
	private CentroEscolarService CentroEscolarService;
	@Autowired
	private DepartamentoService departamentoService;
	@Autowired
	private EstudianteService estudianteService;
	@Autowired
	private MateriaxEstudianteService materiaxEstudianteService;
	@Autowired
	private MunicipioService MunicipioService;
	@Autowired
	private UsuarioService usuarioService;

	
	
	@RequestMapping("/index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("index");
		return mav;
	}
	
	@RequestMapping("/registro")
	public ModelAndView registro() {
		ModelAndView mav = new ModelAndView();
		List<Departamento> departamentoLista = null;
		List<Municipio> municipioLista = null;
		//List<Usuario> usuarioLista = null;
		try {
			departamentoLista = departamentoService.findAll();
			municipioLista = MunicipioService.findAll();
			//usuarioLista = usuarioService.findAll();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		mav.addObject("departamentoLista", departamentoLista);
		mav.addObject("municipioLista", municipioLista);
		//mav.addObject("usuarioLista",usuarioLista);
		mav.setViewName("registroUsuario");
		return mav;
	}
	
	@RequestMapping("/tablaMaterias")
	public ModelAndView tablaMaterias() {
		ModelAndView mav = new ModelAndView();
		List<Materia> materia = null;
		try {
			materia = MateriaService.findAll();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		mav.addObject("materiaList", materia);
		mav.setViewName("tablaMateria");
		return mav;
	}
	
	@RequestMapping("/registroMateria")
	public ModelAndView registroMateria() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("registroMateria");
		return mav;
	}
	
	@RequestMapping("/registroCentroEscolar")
	public ModelAndView registroCentroEscolar() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("registroCentroEscolar");
		return mav;
	}
	
	@RequestMapping("/tablaCentroEscolar")
	public ModelAndView tablaCentroEscolar() {
		ModelAndView mav = new ModelAndView();
		List<CentroEscolar> centroescolar = null;
		try {
			centroescolar = CentroEscolarService.findAll();
		} catch(Exception e) {
			e.printStackTrace();
		}
		mav.addObject("centro", centroescolar);
		mav.setViewName("tablaCentroEscolar");
		return mav;
	}
	
	@RequestMapping("/tablaUsuario")
	public ModelAndView tablaUsuario() {
		
		ModelAndView mav = new ModelAndView();
		
		List<Usuario> listaUsuario = null;
		
		try {
			listaUsuario = usuarioService.findAll();
		}catch(Exception e) {
			e.printStackTrace();
		}
		mav.addObject("usuario",listaUsuario);
		mav.setViewName("tablaUsuario");
		return mav;
	}
	
	@RequestMapping("/busquedaAlumno")
	public ModelAndView busquedaAlumno() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("busquedaAlumno");
		return mav;
	}
	
	@RequestMapping("/tablaExpediente")
	public ModelAndView tablaExpediente() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("tablaExpediente");
		return mav;
	}
	
	@RequestMapping("/registroAlumno")
	public ModelAndView registroAlumno(@ModelAttribute Estudiante estudiante) {
		ModelAndView mav = new ModelAndView();
	
		List<Departamento> departamentoLista = null;
		List<Municipio> municipioLista = null;
		List<CentroEscolar> centroLista = null;
		try {
			
			centroLista = CentroEscolarService.findAll();
			departamentoLista = departamentoService.findAll();
			municipioLista = MunicipioService.findAll();
		
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		mav.addObject("departamentoLista", departamentoLista);
		mav.addObject("municipioLista", municipioLista);
		mav.addObject("centroEscolar", centroLista);
		mav.addObject("estudiante", estudiante);
		
		mav.setViewName("registroAlumno");
		return mav;
	}
	
	@RequestMapping("/registroEstudiante")
	public ModelAndView alumno(@Valid @ModelAttribute Estudiante estudiante,BindingResult result) {
		ModelAndView mav = new ModelAndView();
		if(result.hasErrors()) {
			mostrarComboBox(mav);
		}else {
			try {
				mostrarComboBox(mav);
				estudianteService.insertAndUpdate(estudiante);
				mav.addObject("estudiante", estudiante);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
			
		return mav;
	}
	
	
	public void mostrarComboBox(ModelAndView mav) {
		
		List<Departamento> departamentoLista = null;
		List<Municipio> municipioLista = null;
		List<CentroEscolar> centroLista = null;
		
		centroLista = CentroEscolarService.findAll();
		departamentoLista = departamentoService.findAll();
		municipioLista = MunicipioService.findAll();
		
		mav.addObject("departamentoLista", departamentoLista);
		mav.addObject("municipioLista", municipioLista);
		mav.addObject("centroEscolar", centroLista);
		
		mav.setViewName("registroAlumno");
		
	}
	
	@RequestMapping("/tablaMateriasCursadas")
	public ModelAndView tablaMateriasCursadas() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("tablaMateriasCursadas");
		return mav;
	}
	
	@RequestMapping("/registrarMateriaAlumno")
	public ModelAndView registrarMateriaAlumno() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("registrarMateriaCursada");
		return mav;
	}
}