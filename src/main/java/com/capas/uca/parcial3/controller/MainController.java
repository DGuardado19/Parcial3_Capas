package com.capas.uca.parcial3.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.capas.uca.parcial3.domain.*;
import com.capas.uca.parcial3.dto.ResutDTO;
import com.capas.uca.parcial3.dto.TablaDTO;
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
	
	@RequestMapping("/iniciarSesion")
	public ModelAndView iniciarSesion(@Valid @ModelAttribute Usuario usuario, BindingResult result) {
		ModelAndView mav = new ModelAndView();
		Usuario user = null;
		List<Usuario> listaUsuario = null;
		try {
			listaUsuario = usuarioService.findAll();
			user = usuarioService.login(usuario.getNombreUser(), usuario.getContrasenia()); 
			if(user == null) {
				mav.setViewName("index");
			} else {
				if(user.getTipoUsuario() == false) {
					mav.addObject("usuario",listaUsuario);
					mav.setViewName("tablaUsuario");
				} else {
					mav.setViewName("busquedaAlumno");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	 @RequestMapping("/cargarMunicipios")
	    public @ResponseBody List<String[]> cargarMunicipios(@RequestParam Integer draw) {
		 List<Municipio> municipio = null;
			try {
				municipio = MunicipioService.findDepartamento(draw); 
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			List<String[]> data = new ArrayList<>();
			
			for(Municipio u : municipio) {
				data.add(new String[] {u.getIdMunicipio().toString(), u.getNombreMunicipio()});
			}
			return data;
	 }
}