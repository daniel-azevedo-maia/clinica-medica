package com.danielazevedo.clinicamedica.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.danielazevedo.clinicamedica.domain.exception.EntidadeEmUsoException;
import com.danielazevedo.clinicamedica.domain.exception.EntidadeNaoEncontradaException;
import com.danielazevedo.clinicamedica.model.Paciente;
import com.danielazevedo.clinicamedica.repository.PacienteRepository;

@Service
public class PacienteService {

	@Autowired
	private PacienteRepository pacienteRepository;

	public Paciente salvar(Paciente paciente) {
		return pacienteRepository.save(paciente);
	}

	public void excluir(Long pacienteId) {
		try {
			pacienteRepository.deleteById(pacienteId);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro de paciente com código %d", pacienteId));
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Paciente de código %d não pode ser removido, pois está em uso", pacienteId));
		}
	}

}
