package com.capas.uca.parcial3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
	
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
