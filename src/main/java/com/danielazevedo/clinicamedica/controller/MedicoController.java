package com.danielazevedo.clinicamedica.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danielazevedo.clinicamedica.domain.exception.EntidadeEmUsoException;
import com.danielazevedo.clinicamedica.domain.exception.EntidadeNaoEncontradaException;
import com.danielazevedo.clinicamedica.model.Medico;
import com.danielazevedo.clinicamedica.model.Paciente;
import com.danielazevedo.clinicamedica.repository.MedicoRepository;
import com.danielazevedo.clinicamedica.repository.PacienteRepository;
import com.danielazevedo.clinicamedica.service.MedicoService;

@RestController
@RequestMapping("/medicos")
@CrossOrigin(origins = "*")
public class MedicoController {

	@Autowired
	private MedicoRepository medicoRepository;

	@Autowired
	private PacienteRepository pacienteRepository;

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

	@GetMapping("/{crm}")
	public ResponseEntity<Medico> buscarPorCRM(@PathVariable String crm) {
		Optional<Medico> medico = medicoRepository.findById(crm);

		if (medico.isPresent()) {
			return ResponseEntity.ok(medico.get());
		}

		return ResponseEntity.notFound().build();
	}

	@GetMapping("/nome/{nome}")
	public List<Medico> buscarMedicoPorNome(@PathVariable String nome) {
		return medicoRepository.findMedicoByNomeIgnoreCase(nome);
	}

	@GetMapping("/{crm}/pacientes")
	public ResponseEntity<List<Paciente>> listarPacientesPorMedico(@PathVariable String crm) {
		Optional<Medico> medico = medicoRepository.findById(crm);

		if (medico.isPresent()) {
			return ResponseEntity.ok(medico.get().getPacientes());
		}

		return ResponseEntity.notFound().build();

	}

	@PutMapping("/{crm}")
	public ResponseEntity<?> atualizarMedico(@PathVariable String crm, @RequestBody Medico medicoAtualizado) {
		Optional<Medico> medicoAtual = medicoRepository.findById(crm);

		if (medicoAtual.isPresent()) {
			BeanUtils.copyProperties(medicoAtualizado, medicoAtual.get(), "crm");
			medicoRepository.save(medicoAtual.get());
			return ResponseEntity.ok(medicoAtual.get());
		}

		return ResponseEntity.notFound().build();

	}

	@DeleteMapping("/{crm}")
	public ResponseEntity<?> deletarMedico(@PathVariable String crm) {

		try {
			medicoService.excluir(crm);
			return ResponseEntity.noContent().build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}

	}

	@DeleteMapping("/{crm}/pacientes/{pacienteId}")
	public ResponseEntity<?> deletarPacienteDeMedico(@PathVariable String crm, @PathVariable Long pacienteId) {

		Optional<Medico> medico = medicoRepository.findById(crm);
		Optional<Paciente> paciente = pacienteRepository.findById(pacienteId);

		if (medico.isPresent() && paciente.isPresent()) {
			medico.get().getPacientes().remove(paciente.get());
			paciente.get().getMedicos().remove(medico.get());
			medicoRepository.save(medico.get());
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.notFound().build();

	}

}