package com.danielazevedo.clinicamedica.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded=true)
@Data
@Entity
public class Medico {

	@EqualsAndHashCode.Include
	@Id
	private String crm;
	private String nome;

	@Enumerated(EnumType.STRING)
	private Especialidade especialidade;

	@ManyToMany
	@JoinTable(name = "medico_paciente", joinColumns = @JoinColumn(name = "medico_crm"), inverseJoinColumns = @JoinColumn(name = "paciente_id"))
	private List<Paciente> pacientes = new ArrayList<>();

}
