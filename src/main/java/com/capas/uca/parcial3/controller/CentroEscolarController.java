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
import com.capas.uca.parcial3.domain.Municipio;
import com.capas.uca.parcial3.dto.EstudianteDTO;
import com.capas.uca.parcial3.service.CentroEscolarService;
import com.capas.uca.parcial3.service.MateriaService;
import com.capas.uca.parcial3.service.MunicipioService;

@Controller
public class CentroEscolarController {

	@Autowired
	private CentroEscolarService CentroEscolarService;
	
	@Autowired
	private MateriaService MateriaService;
	
	@Autowired
	private MunicipioService MunicipioService;
	
	@RequestMapping("/tablaCentroEscolar")
    public String tablaCentroEscolar(){
        return "tablaCentroEscolar";
    }
 @RequestMapping("/cargarCentrosEscolares")
    public @ResponseBody EstudianteDTO cargar(@RequestParam Integer draw,
			@RequestParam Integer start, @RequestParam Integer length, 
			@RequestParam(value="search[value]", required = false) String search) {
		
		Page<CentroEscolar> centroEscolar= CentroEscolarService.findAll(PageRequest.of(start/length, length, Sort.by(Direction.ASC, "idCentroEscolar")));
		
		List<String[]> data = new ArrayList<>();
		
		for(CentroEscolar u : centroEscolar) {
			data.add(new String[] {u.getIdCentroEscolar().toString(), u.getIdCentroEscolar().toString(),
					u.getNombre(), u.getMunicipio().getNombreMunicipio(), u.getDescripcion(),u.getDelegateEstado()});
		}
		System.out.print(data);
		EstudianteDTO dto = new EstudianteDTO();
		dto.setData(data);
		dto.setDraw(draw);
		dto.setRecordsFiltered(MateriaService.countAll().intValue());
		dto.setRecordsTotal(MateriaService.countAll().intValue());	
		
		return dto;
    }
 
 @RequestMapping("/editarCentroEscolar")
	public ModelAndView editarCentroEscolar(@RequestParam Integer id) {
		ModelAndView mav = new ModelAndView();
		List<Municipio> municipioLista = null;
		// List<Usuario> usuarioLista = null;
		try {
			municipioLista = MunicipioService.findAll();
			// usuarioLista = usuarioService.findAll();

		} catch (Exception e) {
			e.printStackTrace();
		}
		CentroEscolar c = CentroEscolarService.findOne(id);
		mav.addObject("centroEscolar", c);
		mav.addObject("municipioLista", municipioLista);
		mav.setViewName("registroCentroEscolar");
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
	
}
