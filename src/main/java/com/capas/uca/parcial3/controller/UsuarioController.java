package com.capas.uca.parcial3.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.capas.uca.parcial3.domain.Departamento;
import com.capas.uca.parcial3.domain.Municipio;
import com.capas.uca.parcial3.domain.Usuario;
import com.capas.uca.parcial3.service.DepartamentoService;
import com.capas.uca.parcial3.service.MunicipioService;
import com.capas.uca.parcial3.service.UsuarioService;

@Controller
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private MunicipioService MunicipioService;
	@Autowired
	private DepartamentoService departamentoService;
	
	@RequestMapping("/tablaUsuario")
	public ModelAndView tablaUsuario() {
		ModelAndView mav = new ModelAndView();		
		List<Usuario> listaUsuario = null;

		try {
			listaUsuario = usuarioService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mav.addObject("usuario", listaUsuario);
		mav.setViewName("tablaUsuario");
		return mav;
	}
	
	@RequestMapping("/registro")
	public ModelAndView registro() {
		ModelAndView mav = new ModelAndView();
		List<Departamento> departamentoLista = null;
		List<Municipio> municipioLista = null;
		Usuario usuarioLista = new Usuario();
		try {
			departamentoLista = departamentoService.findAll();

		} catch (Exception e) {
			e.printStackTrace();
		}

		mav.addObject("departamentoLista", departamentoLista);
		mav.addObject("municipioLista", municipioLista);
		mav.addObject("usuario",usuarioLista);
		mav.setViewName("registroUsuario");
		return mav;
	}
	
	@RequestMapping("/ingresarUsuario")
	public ModelAndView ingresarUsuario(@RequestParam("pass") String pass,@Valid @ModelAttribute Usuario usuario, BindingResult result) {
		ModelAndView mav = new ModelAndView();
		List<Usuario> listaUsuario = null;
		List<Departamento> departamentoLista = null;
		List<Municipio> municipioLista = null;
		if(result.hasErrors() || usuario.getDepartamento() == null || usuario.getMunicipio() == null || !usuario.getContrasenia().equals(pass)) {
			if(usuario.getDepartamento() == null) {
				mav.addObject("resultado", 1);
			}
			if(usuario.getMunicipio() == null) {
				mav.addObject("resultado2", 1);
			}
			if(usuario.getTipoUsuario() == null) {
				mav.addObject("resultado3", 1);
			}
			if(pass != usuario.getContrasenia()) {
				mav.addObject("resultado4", 1);
			}
			departamentoLista = departamentoService.findAll();
			municipioLista = MunicipioService.findAll();
			mav.addObject("departamentoLista", departamentoLista);
			mav.addObject("municipioLista", municipioLista);
			mav.setViewName("registroUsuario");
		} else {
			try {
				usuario.setSesion(false);
				if(usuario.getEstado()==null) {
					usuario.setEstado(false);
	            }
				usuarioService.insertAndUpdate(usuario);
				listaUsuario = usuarioService.findAll();
				mav.addObject("usuario",listaUsuario);
				System.out.println(usuario.getDepartamento()); 
				mav.setViewName("tablaUsuario");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		return mav;
	}
	
}
