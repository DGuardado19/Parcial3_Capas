package com.capas.uca.parcial3.controller;

import java.sql.Date;
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

import com.capas.uca.parcial3.domain.*;
import com.capas.uca.parcial3.dto.MateriaxEstudianteDTO;
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
	
	
	 @RequestMapping("/clientestable")
	    public String clientesTable(){
	        return "tablaMateria2";
	    }
	 @RequestMapping("/cargarclientes")
	    public @ResponseBody MateriaxEstudianteDTO cargarUsuario(@RequestParam Integer draw,
				@RequestParam Integer start, @RequestParam Integer length, 
				@RequestParam(value="search[value]", required = false) String search) {
			
			Page<Materia> materia = MateriaService.findAll(PageRequest.of(start/length, length, Sort.by(Direction.ASC, "idMateria")));
			
			List<String[]> data = new ArrayList<>();
			
			for(Materia u : materia) {
				data.add(new String[] {u.getIdMateria().toString(), u.getNombre(), 
						u.getDescripicion(),u.getDelegateEstado()});
			}
			System.out.print(data);
			MateriaxEstudianteDTO dto = new MateriaxEstudianteDTO();
			dto.setData(data);
			dto.setDraw(draw);
			dto.setRecordsFiltered(MateriaService.countAll().intValue());
			dto.setRecordsTotal(MateriaService.countAll().intValue());	
			
			return dto;
	    }
	 @RequestMapping("/editarMateria")
		public ModelAndView buscar(@RequestParam Integer id) {
			ModelAndView mav = new ModelAndView();
			Materia c = MateriaService.findOne(id);
			mav.addObject("materia", c);
			mav.setViewName("editarMateria");
			return mav;
		}
	  @RequestMapping("/updateMateria")
		public ModelAndView guardarCliente(@ModelAttribute Materia materia) {
			ModelAndView mav = new ModelAndView();
			//Mando a llamar al servicio encargado de guardar a la entidad
			MateriaService.insertAndUpdate(materia);
			mav.setViewName("tablaMateria2");
			return mav;
		}
	
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
		// List<Usuario> usuarioLista = null;
		try {
			departamentoLista = departamentoService.findAll();
			municipioLista = MunicipioService.findAll();
			// usuarioLista = usuarioService.findAll();

		} catch (Exception e) {
			e.printStackTrace();
		}

		mav.addObject("departamentoLista", departamentoLista);
		mav.addObject("municipioLista", municipioLista);
		// mav.addObject("usuarioLista",usuarioLista);
		mav.setViewName("registroUsuario");
		return mav;
	}

	@RequestMapping("/tablaMaterias")
	public ModelAndView tablaMaterias() {
		ModelAndView mav = new ModelAndView();
		List<Materia> materia = null;
		try {
			materia = MateriaService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mav.addObject("materiaList", materia);
		mav.setViewName("tablaMateria");
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
		mav.setViewName("registroMateria");
		if(!result.hasErrors()) {
			mav.addObject("materia", new Materia());
			try {
				MateriaService.insertAndUpdate(materia);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return mav;
	}
	
	

	@RequestMapping("/registroCentroEscolar")
	public ModelAndView registroCentroEscolar() {
		ModelAndView mav = new ModelAndView();
		List<Municipio> municipioLista = null;
		// List<Usuario> usuarioLista = null;
		try {
			municipioLista = MunicipioService.findAll();
			// usuarioLista = usuarioService.findAll();

		} catch (Exception e) {
			e.printStackTrace();
		}
		mav.addObject("centroEscolar", new CentroEscolar());
		mav.addObject("municipioLista", municipioLista);
		mav.setViewName("registroCentroEscolar");
		return mav;
	}
	
	@RequestMapping("/registroCentroEscolarProcesar")
	public ModelAndView insertarCentroEscolar(@Valid @ModelAttribute CentroEscolar centroEscolar, BindingResult result) {
		ModelAndView mav = new ModelAndView();
		List<Municipio> municipioLista = null;
		mav.setViewName("registroCentroEscolar");
		if(!result.hasErrors()) {
			mav.addObject("centroEscolar", new CentroEscolar());
			try {
				CentroEscolarService.insertAndUpdate(centroEscolar);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		try {
			municipioLista = MunicipioService.findAll();
			// usuarioLista = usuarioService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		mav.addObject("municipioLista", municipioLista);
		return mav;
	}

	@RequestMapping("/tablaCentroEscolar")
	public ModelAndView tablaCentroEscolar() {
		ModelAndView mav = new ModelAndView();
		List<CentroEscolar> centroescolar = null;
		try {
			centroescolar = CentroEscolarService.findAll();
		} catch (Exception e) {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		mav.addObject("usuario", listaUsuario);
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
		for (int i = 0; i < cursada.size(); i++) {
			if (cursada.get(i).getNota() >= 6) {
				aprobada.add("aprobada");
			} else {
				aprobada.add("reprobada");
			}

			System.out.println(aprobada.get(i));
		}
		System.out.println(aprobada.size());

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