package com.capas.uca.parcial3.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.persistence.SequenceGenerator;

@Entity
@Table(schema="public",name="materiaXestudiante")
public class MateriaXestudiante {
	
	//private Integer idMateriaXestudiante;
	
	@Id
	@Column(name="idMateriaXestudiante")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idMateria")
	private Materia materia;
	
	@Transient
	private Integer idMateria;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idEstudiante")
	private Estudiante estudiante;
	
	@Transient
	@Id
	private Integer idEstudiante;
	
	@Column(name="anio")
	//@Size(min="",max="")
	//@NotEmpty(message="No puede ir vacio")
	private Integer anio;
	
	@Column(name="ciclo")
	//@Size(min="",max="")
	//@NotEmpty(message="No puede ir vacio")
	private Integer ciclo;
	
	@Column(name="nota")
	//@Size(min="",max="")
	//@NotEmpty(message="No puede ir vacio")
	private Float nota;
	
	public MateriaXestudiante() {
		
	}
	
	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}

	public Materia getMateria() {
		return materia;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}

	public Integer getIdMateria() {
		return idMateria;
	}

	public void setIdMateria(Integer idMateria) {
		this.idMateria = idMateria;
	}

	public Estudiante getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

	public Integer getIdEstudiante() {
		return idEstudiante;
	}

	public void setIdEstudiante(Integer idEstudiante) {
		this.idEstudiante = idEstudiante;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public Integer getCiclo() {
		return ciclo;
	}

	public void setCiclo(Integer ciclo) {
		this.ciclo = ciclo;
	}

	public Float getNota() {
		return nota;
	}

	public void setNota(Float nota) {
		this.nota = nota;
	}
	
	

}
