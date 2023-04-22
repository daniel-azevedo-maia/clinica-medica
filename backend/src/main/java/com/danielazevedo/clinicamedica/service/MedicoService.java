package com.danielazevedo.clinicamedica.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.danielazevedo.clinicamedica.domain.exception.EntidadeEmUsoException;
import com.danielazevedo.clinicamedica.domain.exception.EntidadeNaoEncontradaException;
import com.danielazevedo.clinicamedica.model.Medico;
import com.danielazevedo.clinicamedica.repository.MedicoRepository;

@Service
public class MedicoService {

	@Autowired
	private MedicoRepository medicoRepository;

	public Medico salvar(Medico medico) {
		return medicoRepository.save(medico);
	}

	public void excluir(String crm) {
		try {
			medicoRepository.deleteById(crm);

		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format("Não existe um cadastro de médico com CRM %s", crm));
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Médico de CRM %s não pode ser removido, pois está em uso", crm));
		}
	}

}
