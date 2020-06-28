package com.capas.uca.parcial3.controller;

import java.util.ArrayList;
import java.util.List;

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

import com.capas.uca.parcial3.domain.Materia;
import com.capas.uca.parcial3.domain.MateriaXestudiante;
import com.capas.uca.parcial3.dto.ResutDTO;
import com.capas.uca.parcial3.dto.TablaDTO;
import com.capas.uca.parcial3.service.MateriaService;
import com.capas.uca.parcial3.service.MateriaxEstudianteService;

@Controller
public class MateriaController {
	
	@Autowired
	private MateriaService MateriaService;	
	
	@Autowired
	private MateriaxEstudianteService MateriaxE;	
	
	@RequestMapping("/tablaMaterias")
    public String clientesTable(){
        return "tablaMateria";
    }
	
	@RequestMapping("/cargarclientes")
    public @ResponseBody TablaDTO cargarUsuario(@RequestParam Integer draw,

		@RequestParam Integer start, @RequestParam Integer length, 
			@RequestParam(value="search[value]", required = false) String search) {

		Page<Materia> materia = MateriaService.findAll(PageRequest.of(start/length, length, Sort.by(Direction.ASC, "idMateria")));

		List<String[]> data = new ArrayList<>();

		for(Materia u : materia) {
			if(u.getNombre().toLowerCase().startsWith(search.toLowerCase())) {
				data.add(new String[] {u.getIdMateria().toString(), u.getIdMateria().toString(), u.getNombre(), 
						u.getDescripicion(),u.getDelegateEstado()});
			}
		}
		TablaDTO dto = new TablaDTO();
		dto.setData(data);
		dto.setDraw(draw);
		dto.setRecordsFiltered(MateriaService.countAll(search.toLowerCase()));
		dto.setRecordsTotal(MateriaService.countAll(search.toLowerCase()));	

		return dto;
    }
	
 
 @RequestMapping("/editarMateria")
	public ModelAndView buscar(@RequestParam Integer id) {
		ModelAndView mav = new ModelAndView();
		Materia c = MateriaService.findOne(id);
		mav.addObject("materia", c);
		mav.setViewName("registroMateria");
		return mav;
	}
 
 @RequestMapping("/registroMateria")
	public ModelAndView registroMateria() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("materia", new Materia());
		mav.setViewName("registroMateria");
		return mav;
	}
	
	@RequestMapping("/insertarMateria")
	public ModelAndView insertarMateria(@Valid @ModelAttribute Materia materia, BindingResult result) {
		ModelAndView mav = new ModelAndView();
		if(!result.hasErrors()) {
			mav.addObject("materia", new Materia());
			mav.setViewName("tablaMateria");

			try {
				MateriaService.insertAndUpdate(materia);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		else {
			mav.setViewName("registroMateria");

		}
		return mav;
	}
	
}
