package com.danielazevedo.clinicamedica.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.danielazevedo.clinicamedica.model.Medico;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, String> {

	@Query("SELECT m FROM Medico m WHERE LOWER(m.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
	List<Medico> findMedicoByNomeIgnoreCase(String nome);

	
}
