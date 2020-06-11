package com.uca.capas.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.uca.capas.domain.Contribuyente;
import com.uca.capas.domain.Importancia;
import com.uca.capas.service.ContribuyenteService;
import com.uca.capas.service.ImportanciaService;

@Controller
public class MainController {
	@Autowired
	private ContribuyenteService contribuyenteService;
	
	@Autowired
	private ImportanciaService importanciaService;
	
	@RequestMapping("/inicio")
	public ModelAndView inicio() {
		ModelAndView mav = new ModelAndView();
		List<Importancia> importancias = null;
		mav.addObject("contribuyente",new Contribuyente());
		try {
			importancias = importanciaService.findAll();
			mav.addObject("importancias",importancias);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mav.setViewName("index");
		return mav;
	}
	
	@RequestMapping("/insertarContribuyente")
	public ModelAndView insert(@Valid @ModelAttribute Contribuyente contribuyente, BindingResult result) {
		ModelAndView mav = new ModelAndView();
		List<Importancia> importancias = null;
		if (result.hasErrors()) {
			try {
				importancias = importanciaService.findAll();
				mav.addObject("importancias",importancias);
			} catch (Exception e) {
				e.printStackTrace();
			}
			mav.setViewName("/index");
		} else {
			mav.addObject("contribuyente",new Contribuyente());
			mav.setViewName("exito");
			Date date = new Date(System.currentTimeMillis());
			contribuyente.setF_fecha_ingreso(date);
			Importancia importancia = importanciaService.findOne(contribuyente.getC_importanciafk());
			contribuyente.setC_importancia(importancia);
			contribuyenteService.insert(contribuyente);
		}
		return mav;
	}
	
	@RequestMapping("/listado")
	public ModelAndView initMain() {
		ModelAndView mav = new ModelAndView();
		List<Contribuyente> contribuyentes = null;
		try {
			contribuyentes = contribuyenteService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}

		mav.addObject("contribuyentes", contribuyentes);
		mav.setViewName("listado");
		return mav;
	}
}
