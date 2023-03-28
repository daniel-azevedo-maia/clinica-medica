package com.danielazevedo.clinicamedica.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danielazevedo.clinicamedica.model.Medico;
import com.danielazevedo.clinicamedica.repository.MedicoRepository;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
	
	@Autowired
	private MedicoRepository medicoRepository;
	
	@GetMapping
	public List<Medico> listarMedicos() {
		return medicoRepository.findAll();
	}

}
