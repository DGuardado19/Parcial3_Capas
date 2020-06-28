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

import com.capas.uca.parcial3.domain.CentroEscolar;
import com.capas.uca.parcial3.domain.Departamento;
import com.capas.uca.parcial3.domain.Estudiante;
import com.capas.uca.parcial3.domain.MateriaXestudiante;
import com.capas.uca.parcial3.domain.Municipio;
import com.capas.uca.parcial3.dto.ResutDTO;
import com.capas.uca.parcial3.dto.TablaDTO;
import com.capas.uca.parcial3.service.CentroEscolarService;
import com.capas.uca.parcial3.service.DepartamentoService;
import com.capas.uca.parcial3.service.EstudianteService;
import com.capas.uca.parcial3.service.MateriaService;
import com.capas.uca.parcial3.service.MateriaxEstudianteService;
import com.capas.uca.parcial3.service.MunicipioService;

@Controller
public class EstudianteController {

	@Autowired
	private EstudianteService estudianteService;

	@Autowired
	private MunicipioService MunicipioService;

	@Autowired
	private DepartamentoService departamentoService;

	@Autowired
	private MateriaxEstudianteService materiaxEstudianteService;

	@Autowired
	private CentroEscolarService CentroEscolarService;

	@Autowired
	private MateriaService MateriaService;

	

	@RequestMapping("/editarEstudiante")
	public ModelAndView buscarEstudiante(@RequestParam Integer id) {
		ModelAndView mav = new ModelAndView();
		Estudiante c = estudianteService.findOne(id);
		mostrarComboBox(mav);
		mav.addObject("estudiante", c);
		mav.setViewName("registroAlumno");
		return mav;
	}

	@RequestMapping("/estudiantesTable")
	public String estudiantesTable() {
		return "tablaExpediente";
	}

	@RequestMapping("/busquedaAlumno")
	public ModelAndView busquedaAlumno(@RequestParam("busqueda")String hola) {
		ModelAndView mav = new ModelAndView();
		
		
		
		mav.setViewName("busquedaAlumno");
		return mav;
	}
	////////////////// FUNCION

	@RequestMapping("/cargarEstudiantes")
	public @ResponseBody TablaDTO cargarEstudiante(@RequestParam Integer draw, @RequestParam Integer start,
			@RequestParam Integer length, @RequestParam(value = "search[value]", required = false) String search) {

		Page<ResutDTO> materia = materiaxEstudianteService.dtoPrueba("ro", "",
				PageRequest.of(start / length, length, Sort.by(Direction.ASC, "fkestudiante")));

		List<String[]> data = new ArrayList<>();

		for (ResutDTO u : materia) {
			if (u.getNombre().toLowerCase().startsWith(search.toLowerCase())) {
				data.add(new String[] { u.getNombre().toString(), u.getNombre().toString(), u.getApellido().toString(),
						u.getAprobadas().toString(), u.getReprobadas().toString(), String.valueOf(u.getProm()) });
			}
		}
		System.out.print("HOLAAAAAAAA       " + data);

		TablaDTO dto = new TablaDTO();

		dto.setData(data);
		dto.setDraw(draw);
		dto.setRecordsFiltered(MateriaService.countAll2().intValue());
		dto.setRecordsTotal(MateriaService.countAll2().intValue());

		return dto;
	}

	
	@RequestMapping("/tablaExpediente")
	public ModelAndView tablaExpediente() {
		ModelAndView mav = new ModelAndView();
		List<Estudiante> listaEstudiante = null;
		// List<MateriaXestudiante> lista = null;
		// List<String> lista2 = new ArrayList<>();

		try {
			listaEstudiante = estudianteService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// mav.addObject("lista", lista);
		mav.addObject("listaEstudiante", listaEstudiante);
		mav.setViewName("tablaExpediente");

		return mav;
	}

	@RequestMapping("/registroAlumno")
	public ModelAndView registroAlumno(@ModelAttribute Estudiante estudiante) {
		ModelAndView mav = new ModelAndView();
		try {
			mostrarComboBox(mav);
		} catch (Exception e) {
			e.printStackTrace();
		}

		mav.addObject("estudiante", estudiante);
		return mav;
	}

	@RequestMapping("/registroEstudiante")
	public ModelAndView alumno(@Valid @ModelAttribute Estudiante estudiante, BindingResult result) {
		ModelAndView mav = new ModelAndView();
		if (result.hasErrors()) {
			mostrarComboBox(mav);
		} else {
			try {
				mostrarComboBox(mav);
				estudianteService.insertAndUpdate(estudiante);
				mav.addObject("estudiante", estudiante);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return mav;
	}

	public void mostrarComboBox(ModelAndView mav) {

		List<Departamento> departamentoLista = null;
		List<Municipio> municipioLista = null;
		List<CentroEscolar> centroescolar = null;

		try {
			centroescolar = CentroEscolarService.findAll();
			departamentoLista = departamentoService.findAll();
			municipioLista = MunicipioService.findAll();

			mav.addObject("departamentoLista", departamentoLista);
			mav.addObject("municipioLista", municipioLista);
			mav.addObject("centro", centroescolar);

			mav.setViewName("registroAlumno");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping("/tablaMateriasCursadas")
	public ModelAndView tablaMateriasCursadas() {
		ModelAndView mav = new ModelAndView();
		List<MateriaXestudiante> cursada = null;
		List<String> aprobada = new ArrayList<>();
		try {
			cursada = materiaxEstudianteService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}

		mav.addObject("cursadaList", cursada);
		mav.addObject("aprobadaList", aprobada);
		mav.setViewName("tablaMateriasCursadas");
		return mav;
	}

	@RequestMapping("/registrarMateriaAlumno")
	public ModelAndView registrarMateriaAlumno() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("registrarMateriaCursada");
		return mav;
	}
	


	@RequestMapping("/cargarCentroEscolar")
    public @ResponseBody List<String[]> cargarCentroEscolar(@RequestParam Integer draw) {
	 List<CentroEscolar> centro = null;
		try {
			centro =  CentroEscolarService.findCentroEscolar(draw);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		List<String[]> data = new ArrayList<>();
		
		for(CentroEscolar u : centro) {
			data.add(new String[] {u.getIdCentroEscolar().toString(),u.getNombre()});
		}
		return data;
 }
}
