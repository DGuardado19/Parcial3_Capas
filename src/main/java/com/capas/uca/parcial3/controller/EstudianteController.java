package com.capas.uca.parcial3.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.capas.uca.parcial3.domain.Estudiante;
import com.capas.uca.parcial3.domain.MateriaXestudiante;
import com.capas.uca.parcial3.domain.Municipio;
import com.capas.uca.parcial3.domain.Usuario;
import com.capas.uca.parcial3.dto.CursadasDTO;
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
		mostrarComboBoxRegistro(mav);
		mav.addObject("estudiante", c);
		//mav.setViewName("registroAlumno");
		return mav;
	}

	@RequestMapping("/estudiantesTable")
	public String estudiantesTable() {
		return "tablaExpediente";
	}

	@RequestMapping("/busquedaAlumno")
	public ModelAndView busquedaAlumno(HttpSession request) {
		ModelAndView mav = new ModelAndView();
		Usuario user = null;
		if(request.getAttribute("user") != null) {
			user = (Usuario) request.getAttribute("user");
			if(user.getTipoUsuario() == false) {
				mav.setViewName("redirect:/tablaUsuario");
			}
		} else {
			mav.setViewName("redirect:/index");
		}
		return mav;
	}
	
	@RequestMapping("/cargarEstudiantes")
	public @ResponseBody TablaDTO cargarEstudiante(@RequestParam Integer draw, @RequestParam Integer start,
			@RequestParam Integer length, @RequestParam(value = "search[value]", required = false) String search,@RequestParam String variable,
			@RequestParam String criterio) {
		
		String val1="", val2="";
		
		if(criterio.equals("1")){
			val1=variable;
		}else {
			val2=variable;
		}
		
		Page<ResutDTO> materia = materiaxEstudianteService.dtoPrueba(val1, val2,
				PageRequest.of(start / length, length, Sort.by(Direction.ASC, "fkestudiante")));

		List<String[]> data = new ArrayList<>();

		for (ResutDTO u : materia) {
			if (u.getNombre().toLowerCase().startsWith(search.toLowerCase())) {
				data.add(new String[] { u.getId().toString(), u.getNombre().toString(), u.getApellido().toString(),
						u.getAprobadas().toString(), u.getReprobadas().toString(), String.valueOf(u.getProm()) });
			}
		}
		TablaDTO dto = new TablaDTO();

		dto.setData(data);
		dto.setDraw(draw);
		dto.setRecordsFiltered(MateriaService.countAll2().intValue());
		dto.setRecordsTotal(MateriaService.countAll2().intValue());

		return dto;
	}

	
	@RequestMapping("/tablaExpediente")
	public ModelAndView tablaExpediente(@RequestParam String busqueda,@RequestParam String criterio, HttpSession request) {
		ModelAndView mav = new ModelAndView();
		Usuario user = null;
		if(request.getAttribute("user") != null) {
			user = (Usuario) request.getAttribute("user");
			if(user.getTipoUsuario() == true) {
				mav.addObject("busqueda", busqueda);
				mav.addObject("busqueda2", criterio);
				mav.setViewName("tablaExpediente");
			} else {
				mav.setViewName("redirect:/tablaUsuario");
			}
		} else {
			mav.setViewName("index");
		}		
		return mav;
	}

	@RequestMapping("/registroAlumno")
	public ModelAndView registroAlumno(@ModelAttribute Estudiante estudiante, HttpSession request) {
		ModelAndView mav = new ModelAndView();
		Usuario user = null;
		if(request.getAttribute("user") != null) {
			user = (Usuario) request.getAttribute("user");
			if(user.getTipoUsuario() == true) {
				try {
					mostrarComboBoxRegistro(mav);
				} catch (Exception e) {
					e.printStackTrace();
				}

				mav.addObject("estudiante", estudiante);
			} else {
				mav.setViewName("redirect:/tablaUsuario");
			}
		} else {
			mav.setViewName("redirect:/index");
		}		
		return mav;
	}

	@RequestMapping("/registroEstudiante")
	public ModelAndView alumno(@Valid @ModelAttribute Estudiante estudiante, BindingResult result) {
		ModelAndView mav = new ModelAndView();
		if (result.hasErrors() || estudiante.getMunicipio()==null || 
				estudiante.getDepartamento()==null || estudiante.getCentroEscolar()==null) {
			erroraxo(estudiante,mav);
			mostrarComboBoxRegistro(mav);
			} 
		else {
			try {
				mostrarComboBoxBusqueda(mav);
				estudianteService.insertAndUpdate(estudiante);
				mav.addObject("estudiante", estudiante);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return mav;
	}
	
	public void erroraxo(@ModelAttribute Estudiante estudiante, ModelAndView mav) {
		if(estudiante.getDepartamento()==null) {
			mav.addObject("resultado", 1);
			System.out.println("HOLAAA");
		}
		if(estudiante.getMunicipio()==null) {
			mav.addObject("resultado1", 1);
		}
		if(estudiante.getCentroEscolar()==null) {
			mav.addObject("resultado2", 1);
			
		}
	}
	
	public void mostrarComboBoxBusqueda(ModelAndView mav) {

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

			mav.setViewName("busquedaAlumno");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void mostrarComboBoxRegistro(ModelAndView mav) {

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
	
	
	///////////////////////////////adaptando /////////////////////////////
	@RequestMapping("/materiasCursadas")
	public ModelAndView materiasCursadasTable(@RequestParam Integer id) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("id", id);
		mav.setViewName("tablaMateriasCursadas");
		return mav;
	}
	@RequestMapping("/materiasCursada")
	public String materiasCursadasTable2() {
		return "registrarMateriaCursada";
	}
	
	@RequestMapping("/cargarTablaMateriasCursadas")
	public ModelAndView cargartablaMateriasCursadas() {
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


	
	/*@RequestMapping("/registrarMateriaAlumno")
	public ModelAndView registrarMateriaAlumno() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("registrarMateriaCursada");
		return mav;
	}*/
	@RequestMapping("/cargarMateriasCursadas")
	public @ResponseBody TablaDTO cargartablaMateriasCursadas(@RequestParam Integer draw,
		@RequestParam Integer start, @RequestParam Integer length, 
			@RequestParam(value="search[value]", required = false) String search, @RequestParam Integer id) {
		
		Page<CursadasDTO> materia = materiaxEstudianteService.dtoCursadas(id,
				PageRequest.of(start / length, length, Sort.by(Direction.ASC, "nota")));

		List<String[]> data = new ArrayList<>();

		for (CursadasDTO u : materia) {
			if (u.getNombremateria().toLowerCase().startsWith(search.toLowerCase())) {
				data.add(new String[] {u.getIdmateria().toString(), u.getNombremateria().toString(), u.getCiclo().toString(), 
						u.getAnio().toString(), String.valueOf(u.getNota()), u.getDelegateNota(),u.getIdestudiante().toString()});
			}
		}
		TablaDTO dto = new TablaDTO();

		dto.setData(data);
		dto.setDraw(draw);
		dto.setRecordsFiltered(MateriaService.countAll2().intValue());
		dto.setRecordsTotal(MateriaService.countAll2().intValue());

		return dto;
	}
	
	
	////////////////////////////////////////////////////////////////////
	
	
	/*@RequestMapping("/tablaMateriasCursadas")
	public ModelAndView tablaMateriasCursadas(HttpSession request) {
		ModelAndView mav = new ModelAndView();
		Usuario user = null;
		if(request.getAttribute("user") != null) {
			user = (Usuario) request.getAttribute("user");
			if(user.getTipoUsuario() == true) {
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
			} else {
				mav.setViewName("redirect:/tablaUsuario");
			}
		} else {
			mav.setViewName("redirect:/index");
		}		
		return mav;
	}*/

	@RequestMapping("/registrarMateriaAlumno")
	public ModelAndView registrarMateriaAlumno(HttpSession request) {
		ModelAndView mav = new ModelAndView();
		Usuario user = null;
		if(request.getAttribute("user") != null) {
			user = (Usuario) request.getAttribute("user");
			if(user.getTipoUsuario() == true) {
				mav.setViewName("registrarMateriaCursada");
			} else {
				mav.setViewName("redirect:/tablaUsuario");
			}
		} else {
			mav.setViewName("redirect:/index");
		}
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
