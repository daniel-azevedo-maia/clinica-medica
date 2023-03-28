package com.danielazevedo.clinicamedica.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danielazevedo.clinicamedica.model.Medico;
import com.danielazevedo.clinicamedica.repository.MedicoRepository;
import com.danielazevedo.clinicamedica.service.MedicoService;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
	
	@Autowired
	private MedicoRepository medicoRepository;
	
	@Autowired
	private MedicoService medicoService;
	
	@GetMapping
	public List<Medico> listarMedicos() {
		return medicoRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Medico medico) {
		medico = medicoService.salvar(medico);
		return ResponseEntity.status(HttpStatus.CREATED).body(medico);		
	}

}
