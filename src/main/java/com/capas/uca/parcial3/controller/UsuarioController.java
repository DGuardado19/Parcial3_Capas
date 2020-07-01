package com.capas.uca.parcial3.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.capas.uca.parcial3.domain.CentroEscolar;
import com.capas.uca.parcial3.domain.Departamento;
import com.capas.uca.parcial3.domain.Materia;
import com.capas.uca.parcial3.domain.Municipio;
import com.capas.uca.parcial3.domain.Usuario;
import com.capas.uca.parcial3.dto.TablaDTO;
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
	public ModelAndView tablaUsuario(HttpSession request) {
		ModelAndView mav = new ModelAndView();		
		Usuario user = null;
		if(request.getAttribute("user") != null) {
			user = (Usuario) request.getAttribute("user");
			if(user.getTipoUsuario() == true) {
				mav.setViewName("redirect:/busquedaAlumno");
			} 
		} else {
			mav.setViewName("redirect:/index");
		}
		return mav;
	}
	
	@RequestMapping("/cargarUsuario")
    public @ResponseBody TablaDTO cargarUsuario(@RequestParam Integer draw,
		@RequestParam Integer start, @RequestParam Integer length, 
			@RequestParam(value="search[value]", required = false) String search) {
		Page<Usuario> Usuario = usuarioService.mostrarTodo(search.toLowerCase(), PageRequest.of(start/length, length, Sort.by(Direction.ASC, "idUsuario")));
		List<String[]> data = new ArrayList<>();

		for(Usuario u : Usuario) {
			data.add(new String[] {u.getIdUsuario().toString(), u.getIdUsuario().toString(), u.getNombre(), u.getApellido(), 
					u.getFechaNac().toString(), u.getNombreUser(), u.getDelegateEstado()});
		}
		TablaDTO dto = new TablaDTO();
		dto.setData(data);
		dto.setDraw(draw);
		dto.setRecordsFiltered(usuarioService.countUser(search.toLowerCase()));
		dto.setRecordsTotal(usuarioService.countUser(search.toLowerCase()));	

		return dto;
    }
	
	@RequestMapping("/registro")
	public ModelAndView registro(@RequestParam Integer tipo, HttpSession request) {
		ModelAndView mav = new ModelAndView();		
		List<Departamento> departamentoLista = null;
		List<Municipio> municipioLista = null;
		Usuario usuarioLista = new Usuario();
		try {
			departamentoLista = departamentoService.findAll();

		} catch (Exception e) {
			e.printStackTrace();
		} 
		String cart = (String)request.getAttribute("Hola");
		System.out.println(cart); 
		mav.addObject("departamentoLista", departamentoLista);
		mav.addObject("municipioLista", municipioLista);
		mav.addObject("usuario",usuarioLista);
		mav.addObject("tipo", tipo);
		mav.setViewName("registroUsuario");
		/*if(tipo == 1) {
			Usuario user = null;
			if(request.getAttribute("user") != null) {
				user = (Usuario) request.getAttribute("user");
				if(user.getTipoUsuario() == true) {
					mav.setViewName("redirect:/busquedaAlumno");
				} 
			} else {
				mav.setViewName("redirect:/index");
			}
		} */
		return mav;
	}
	
	@RequestMapping("/editarUsuario")
	public ModelAndView editarUsuario(@RequestParam Integer tipo, @RequestParam Integer id) {
		ModelAndView mav = new ModelAndView();
		List<Departamento> departamentoLista = null;
		List<Municipio> municipioLista = null;
		Usuario usuarioLista = usuarioService.findOne(id);
		
		try {
			departamentoLista = departamentoService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}

		mav.addObject("departamentoLista", departamentoLista);
		mav.addObject("municipioLista", municipioLista);
		mav.addObject("usuario",usuarioLista);
		mav.addObject("tipo", tipo);
		mav.addObject("id", id);
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
				System.out.println(usuario.getDepartamento()); 
				mav.setViewName("redirect:/tablaUsuario");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		return mav;
	}
	
}
