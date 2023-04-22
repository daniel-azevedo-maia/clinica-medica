package com.danielazevedo.clinicamedica.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.danielazevedo.clinicamedica.model.Paciente;


@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

	@Query("SELECT p FROM Paciente p WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
	List<Paciente> findPacienteByNomeIgnoreCase(String nome);
	
}
