package com.danielazevedo.clinicamedica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.danielazevedo.clinicamedica.model.Medico;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, String> {
	
}
