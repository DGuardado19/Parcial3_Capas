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
import com.capas.uca.parcial3.domain.Materia;
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
		List<Departamento> d = departamentoService.findAll();
		List<Municipio> m = MunicipioService.findDepartamento(c.getDepartamento().getIdDepartamento());
		List<CentroEscolar> ce = CentroEscolarService.findCentroEscolar(c.getMunicipio().getIdMunicipio());
		mav.addObject("departamentoLista", d);
		mav.addObject("municipioLista", m);
		mav.addObject("centro", ce);
		mav.addObject("estudiante", c);
		mav.setViewName("registroAlumno");
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
			} else {
				mav.setViewName("busquedaAlumno");
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
				PageRequest.of(start / length, length, Sort.by(Direction.ASC, "idestudiante")));

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

	
	@RequestMapping("/tablaExpediente2")
	public ModelAndView tablaExpediente(@RequestParam String busqueda,@RequestParam String criterio, HttpSession request) {
		ModelAndView mav = new ModelAndView();
		Usuario user = null;
		if(request.getAttribute("user") != null) {
			user = (Usuario) request.getAttribute("user");
			if(user.getTipoUsuario() == true) {
				System.out.println(busqueda);
				System.out.println(criterio);
				if(busqueda.equals("")) {
					mav.addObject("mensaje2", 1);
					mav.setViewName("busquedaAlumno");
				}
				if(criterio.equals("")) {
					mav.addObject("mensaje1", 1);
					mav.setViewName("busquedaAlumno");
				}
				if(!criterio.equals("") && !busqueda.equals("")){
					mav.addObject("busqueda", busqueda);
					mav.addObject("busqueda2", criterio);
					request.setAttribute("busqueda", busqueda);
					request.setAttribute("criterio", criterio);
					mav.setViewName("tablaExpediente");
				}
			} else {
				mav.setViewName("redirect:/tablaUsuario");
			}
		} else {
			mav.setViewName("index");
		}		
		return mav;
	}
	
	@RequestMapping("/RegresarTablaExpediente")
	public ModelAndView regresar(HttpSession request) {
		ModelAndView mav = new ModelAndView();
		String criterio = (String) request.getAttribute("criterio");
		String busqueda = (String) request.getAttribute("busqueda");
		mav.setViewName("redirect:/tablaExpediente?criterio="+criterio+"&busqueda="+busqueda);
		return mav;
	}

	@RequestMapping("/registroAlumno")
	public ModelAndView registroAlumno(@ModelAttribute Estudiante estudiante, HttpSession request) {
		ModelAndView mav = new ModelAndView();
		Usuario user = null;
		List<Departamento> departamentoLista = null;
		if(request.getAttribute("user") != null) {
			user = (Usuario) request.getAttribute("user");
			if(user.getTipoUsuario() == true) {
				try {
					departamentoLista = departamentoService.findAll();
					mav.addObject("departamentoLista", departamentoLista);
				} catch (Exception e) {
					e.printStackTrace();
				}
				mav.addObject("estudiante", estudiante);
				mav.setViewName("registroAlumno");
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
		
		//mav.addObject("busqueda", busqueda);
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

	
	@RequestMapping("/registrarMateriaCursada")
	public ModelAndView registrarMateriaAlumnos(@ModelAttribute MateriaXestudiante materiaAlumno,@RequestParam Integer id) {
		ModelAndView mav = new ModelAndView();
		
		List<Materia> materiaLista = null;
		Estudiante estu = null;
		try {
			
			materiaLista = MateriaService.showSubjects();
			estu = estudianteService.findByName2(id);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		mav.addObject("estu", estu);
		mav.addObject("materiaLista", materiaLista);
		mav.addObject("materiaAlumno", materiaAlumno);
		mav.setViewName("registrarMateriaCursada");
		return mav;
	}
	
	@RequestMapping("/insertarMateriaAlumno")
	public ModelAndView insertarMateriaAlumno(@Valid @ModelAttribute MateriaXestudiante materiaAlumno,@RequestParam Integer id, BindingResult result) {
		ModelAndView mav = new ModelAndView();
		
		List<Materia> materiaLista = null;
		Estudiante estu = null;
		
		if(result.hasErrors() || materiaAlumno.getMateria() == null) {
			mav.addObject("resultado", 1);
			mav.setViewName("registrarMateriaCursada");
		} else {
			try {
				materiaLista=MateriaService.showSubjects();
				estu = estudianteService.findOne(id);
				materiaAlumno.setEstudiante(estu);
				materiaxEstudianteService.insertAndUpdate(materiaAlumno);
			}catch(Exception e) {
				e.printStackTrace();
			}
			mav.setViewName("busquedaAlumno");
		}
		mav.addObject("materiaLista", materiaLista);
		mav.addObject("materiaAlumno", materiaAlumno);
		return mav;
	}
	
	@RequestMapping("/cargarMateriasCursadas")
	public @ResponseBody TablaDTO cargartablaMateriasCursadas(@RequestParam Integer draw,
		@RequestParam Integer start, @RequestParam Integer length, 
			@RequestParam(value="search[value]", required = false) String search, @RequestParam Integer id) {
		
		Page<CursadasDTO> materia = materiaxEstudianteService.dtoCursadas(id,
				PageRequest.of(start / length, length, Sort.by(Direction.ASC, "idMateriaXestudiante")));

		List<String[]> data = new ArrayList<>();

		for (CursadasDTO u : materia) {
				data.add(new String[] { u.getIdMateriaXestudiante().toString(), u.getNombremateria().toString(), u.getCiclo().toString(), 
						u.getAnio().toString(), String.valueOf(u.getNota()), u.getDelegateNota(),u.getIdestudiante().toString()});
		}
		TablaDTO dto = new TablaDTO();

		dto.setData(data);
		dto.setDraw(draw);
		dto.setRecordsFiltered(MateriaService.countAll2().intValue());
		dto.setRecordsTotal(MateriaService.countAll2().intValue());

		return dto;
	}
	
	@RequestMapping("/editarMateriaAlumno")
	public ModelAndView buscarMateriaAlumno(@RequestParam Integer id, @RequestParam Integer id2) {
		ModelAndView mav = new ModelAndView();

		List<Materia> materiaLista = null;
		Estudiante estu = null;
		MateriaXestudiante materiaAlumno = null;
		try {

			materiaLista = MateriaService.showSubjects();
			estu = estudianteService.findByName2(id);
			materiaAlumno = materiaxEstudianteService.findOne(id2);

		} catch (Exception e) {
			e.printStackTrace();
		}

		mav.addObject("estu", estu);
		mav.addObject("id2", id2);
		mav.addObject("materiaLista", materiaLista);
		mav.addObject("materiaAlumno", materiaAlumno);
		mav.setViewName("registrarMateriaCursada");
		return mav;
	}

	@RequestMapping("/registrarMateriaAlumno")
	public ModelAndView registrarMateriaAlumno(HttpSession request,@RequestParam String id) {
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
		
		mav.addObject("id", id);
		mav.addObject("id2", "");
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
