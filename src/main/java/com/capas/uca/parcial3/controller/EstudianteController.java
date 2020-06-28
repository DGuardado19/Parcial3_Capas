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
import com.capas.uca.parcial3.dto.TablaDTO;
import com.capas.uca.parcial3.service.CentroEscolarService;
import com.capas.uca.parcial3.service.DepartamentoService;
import com.capas.uca.parcial3.service.EstudianteService;
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
	
	@RequestMapping("/busquedaAlumno")
	public ModelAndView busquedaAlumno() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("busquedaAlumno");
		return mav;
	}

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
	public String estudiantesTable(){
		return "tablaExpediente";
	}
	
	@RequestMapping("/cargarEstudiantes")
	public @ResponseBody TablaDTO cargarEstudiante(@RequestParam Integer draw,
			@RequestParam Integer start, @RequestParam Integer length,
			@RequestParam(value="search[value]",required=false)String search) {
			
		Page<Estudiante> estudiante = estudianteService.findAll(PageRequest.of(start/length, length,Sort.by(Direction.ASC,"idEstudiante")));
		
 		List<String[]> data = new ArrayList<>();
 		
 		for(Estudiante u: estudiante) {
 			/*data.add(new String[] {u.getIdEstudiante().toString(),
 					u.getNombre(),u.getApellido(),u.getCarnet(),u.getFechaNac().toString(),
 					u.getDireccion(),u.getMunicipio().toString(),u.getDepartamento().toString(),
 					u.getTelefonoFijo(),u.getTelefonoMovil(),u.getCentroEscolar().toString(),
 					u.getNombrePadre(),u.getNombreMadre()});*/
 			
 			data.add(new String[] {u.getIdEstudiante().toString(),
 					u.getNombre(),u.getApellido()});
 			
 			
 		}
 		
 		TablaDTO dto = new TablaDTO();
 		dto.setData(data);
 		dto.setDraw(draw);
 		dto.setRecordsFiltered(estudianteService.countAll().intValue());
 		dto.setRecordsTotal(estudianteService.countAll().intValue());
 		
		return dto;
		
	}
	
	@RequestMapping("/tablaExpediente")
	public ModelAndView tablaExpediente() {
		ModelAndView mav = new ModelAndView();
		List<Estudiante> listaEstudiante = null;
		//List<MateriaXestudiante> lista = null;
		//List<String> lista2 = new ArrayList<>();
		
		try {
			listaEstudiante = estudianteService.findAll();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		//mav.addObject("lista", lista);
		mav.addObject("listaEstudiante",listaEstudiante);
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
	
}
