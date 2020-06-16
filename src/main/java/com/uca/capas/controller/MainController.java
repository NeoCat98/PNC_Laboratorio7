package com.uca.capas.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.uca.capas.domain.Estudiante;
import com.uca.capas.service.EstudianteService;

@Controller
public class MainController {
	@Autowired
	private EstudianteService estudianteService;
	
	@RequestMapping("/inicio")
	public ModelAndView inicio() {
		ModelAndView mav = new ModelAndView();
		List<Estudiante> estudiantes = null;
		try {
			estudiantes = estudianteService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mav.addObject("estudiantes", estudiantes);
		mav.setViewName("listado");
		return mav;
	}
	
	
	@RequestMapping("/agregarEstudiante")
	public ModelAndView initMain() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("estudiante",new Estudiante());
		mav.setViewName("index");
		
		return mav;
	}
	
	@RequestMapping("/insertarEstudiante")
	public ModelAndView insert(@Valid @ModelAttribute Estudiante estudiante, BindingResult result) {
		ModelAndView mav = new ModelAndView();
		if (result.hasErrors()) {
			mav.setViewName("index");
		} else {
			mav.addObject("estudiante",new Estudiante());
			mav.setViewName("index");
			estudianteService.insert(estudiante);
		}
		
		return mav;
	}
	
	@RequestMapping("/editarEstudiante")
	public ModelAndView actualizar(@Valid @ModelAttribute Estudiante estudiante,BindingResult result) {
		ModelAndView mav = new ModelAndView();
		if(estudiante.getCodigoEstudiante() != null) estudianteService.insert(estudiante);
		List<Estudiante> estudiantes = estudianteService.findAll();
		mav.addObject("estudiantes", estudiantes);
		mav.setViewName("listado");
		return mav;
	}
	
	@RequestMapping("/actualizarEstudiante")
	public ModelAndView editar(@RequestParam(value="id") Integer codigo) {
		ModelAndView mav = new ModelAndView();
		Estudiante estudiante;
		estudiante = estudianteService.findOne(codigo);
		if(estudiante == null) {
			mav.setViewName("listado");
		}
		else {
			mav.addObject("estudiante", estudiante);
			mav.setViewName("editar");
		}
		return mav;
		
	}
	
	@RequestMapping("/eliminarEstudiante")
	public ModelAndView delete(@RequestParam(value="codigo") int id) {
		ModelAndView mav = new ModelAndView();
		List<Estudiante> estudiantes = null;
		try {
			estudianteService.delete(id);
			estudiantes = estudianteService.findAll();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		mav.addObject("estudiantes", estudiantes);
		mav.setViewName("listado");
		return mav;
		
	}
}
