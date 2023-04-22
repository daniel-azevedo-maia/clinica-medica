package com.danielazevedo.clinicamedica.controller;

import java.util.List;
import java.util.Optional;

import javax.swing.JOptionPane;

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

import com.danielazevedo.clinicamedica.model.Medico;
import com.danielazevedo.clinicamedica.model.Paciente;
import com.danielazevedo.clinicamedica.repository.MedicoRepository;
import com.danielazevedo.clinicamedica.repository.PacienteRepository;
import com.danielazevedo.clinicamedica.service.PacienteService;

@RestController
@RequestMapping("/pacientes")
@CrossOrigin(origins = "*")
public class PacienteController {

	@Autowired
	private PacienteRepository pacienteRepository;
	
	@Autowired
	private MedicoRepository medicoRepository;

	@Autowired
	private PacienteService pacienteService;

	@GetMapping
	public List<Paciente> listarPacientes() {
		return pacienteRepository.findAll();
	}

	// Primeiro cadastra o paciente, depois cadastra um médico a ele.
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Paciente paciente) {
		paciente = pacienteService.salvar(paciente);
		return ResponseEntity.status(HttpStatus.CREATED).body(paciente);
	}
	
	@PostMapping("/{pacienteId}/adicionarmedico/{crm}")
	public ResponseEntity<?> adicionarMedico(@PathVariable Long pacienteId,
			@PathVariable String crm) {

		
		Optional<Paciente> paciente = pacienteRepository.findById(pacienteId);
		Optional<Medico> medico = medicoRepository.findById(crm);
		
		if(paciente.isPresent() && medico.isPresent()) {
			if(!medico.get().getPacientes().contains(paciente.get())) {
				paciente.get().getMedicos().add(medico.get());
	            medico.get().getPacientes().add(paciente.get()); 
				pacienteRepository.save(paciente.get());
				medicoRepository.save(medico.get());
				return ResponseEntity.status(HttpStatus.CREATED).build();
			} else {
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			}
		}
		
		return ResponseEntity.notFound().build();
		
		
	}

	@GetMapping("/{pacienteId}")
	public ResponseEntity<Paciente> buscarPorId(@PathVariable Long pacienteId) {
		Optional<Paciente> paciente = pacienteRepository.findById(pacienteId);

		if (paciente.isPresent()) {
			return ResponseEntity.ok(paciente.get());
		}

		return ResponseEntity.notFound().build();
	}

	@GetMapping("/nome/{nome}")
	public List<Paciente> buscarPorNome(@PathVariable String nome) {
		return pacienteRepository.findPacienteByNomeIgnoreCase(nome);
	}
	
	@GetMapping("/{pacienteId}/medicos")
    public ResponseEntity<List<Medico>> listarMedicosPorPaciente(@PathVariable Long pacienteId) {
        Optional<Paciente> paciente = pacienteRepository.findById(pacienteId);

        if (paciente.isPresent()) {
            return ResponseEntity.ok(paciente.get().getMedicos());
        }

        return ResponseEntity.notFound().build();
    }
	
	 @PutMapping("/{pacienteId}")
	    public ResponseEntity<?> atualizarPaciente(@PathVariable Long pacienteId, @RequestBody Paciente pacienteAtualizado) {
	        Optional<Paciente> pacienteAtual = pacienteRepository.findById(pacienteId);

	        if (pacienteAtual.isPresent()) {
	            BeanUtils.copyProperties(pacienteAtualizado, pacienteAtual.get(), "id");
	            pacienteRepository.save(pacienteAtual.get());
	            return ResponseEntity.ok(pacienteAtual.get());
	        }

	        return ResponseEntity.notFound().build();
	    }
	 
	 @DeleteMapping("/{pacienteId}")
	 public ResponseEntity<?> deletarPaciente(@PathVariable Long pacienteId) {
	     Optional<Paciente> pacienteOptional = pacienteRepository.findById(pacienteId);

	     if (pacienteOptional.isPresent()) {
	         Paciente paciente = pacienteOptional.get();
	         
	         // Desassociar o paciente dos médicos antes de excluí-lo
	         for (Medico medico : paciente.getMedicos()) {
	             medico.getPacientes().remove(paciente);
	         }
	         paciente.getMedicos().clear();

	         pacienteRepository.save(paciente); // Salvar as alterações das associações
	         pacienteRepository.delete(paciente);

	         return ResponseEntity.noContent().build();
	     }

	     return ResponseEntity.notFound().build();
	 }

	 
	 @DeleteMapping("/{pacienteId}/medicos/{crm}")
	    public ResponseEntity<?> deletarMedicoDePaciente(@PathVariable Long pacienteId, @PathVariable String crm) {
	        Optional<Paciente> paciente = pacienteRepository.findById(pacienteId);
	        Optional<Medico> medico = medicoRepository.findById(crm);

	        if (paciente.isPresent() && medico.isPresent()) {
	            paciente.get().getMedicos().remove(medico.get());
	            medico.get().getPacientes().remove(paciente.get());
	            pacienteRepository.save(paciente.get());
	            return ResponseEntity.noContent().build();
	        }

	        return ResponseEntity.notFound().build();
	    }

}