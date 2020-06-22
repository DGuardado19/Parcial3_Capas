package com.capas.uca.parcial3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.capas.uca.parcial3.domain.CentroEscolar;
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
		mav.setViewName("registroUsuario");
		return mav;
	}
	
	@RequestMapping("/tablaMaterias")
	public ModelAndView tablaMaterias() {
		ModelAndView mav = new ModelAndView();
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
		mav.setViewName("tablaCentroEscolar");
		return mav;
	}
	
	@RequestMapping("/tablaUsuario")
	public ModelAndView tablaUsuario() {
		ModelAndView mav = new ModelAndView();
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
	public ModelAndView registroAlumno() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("registroAlumno");
		return mav;
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
