package com.danielazevedo.clinicamedica.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danielazevedo.clinicamedica.model.Medico;
import com.danielazevedo.clinicamedica.repository.MedicoRepository;

@Service
public class MedicoService {
	
	@Autowired
	private MedicoRepository medicoRepository;
	
	public Medico salvar(Medico medico) {
		return medicoRepository.save(medico);
	}

}
