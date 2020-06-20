package com.capas.Parcial3Final.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.persistence.SequenceGenerator;

@Entity
@Table(schema="public",name="materia")
public class Materia {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idMateria")
	private Integer idMateria;
	
	@Column(name="nombre")
	//@Size(min="",max="")
	@NotEmpty(message="No puede ir vacio")
	private String nombre;
	
	@Column(name="descripicion")
	//@Size(min="",max="")
	@NotEmpty(message="No puede ir vacio")
	private String descripicion;
	
	@Column(name="estado")
	//@Size(min="",max="")
	//@NotEmpty(message="No puede ir vacio")
	private Boolean estado;
	
	@OneToMany(mappedBy="materia",fetch=FetchType.LAZY)
	private List<MateriaXestudiante> materiaXestudiante;
	
	public Materia() {
		
	}


	public Integer getIdMateria() {
		return idMateria;
	}


	public void setIdMateria(Integer idMateria) {
		this.idMateria = idMateria;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getDescripicion() {
		return descripicion;
	}


	public void setDescripicion(String descripicion) {
		this.descripicion = descripicion;
	}


	public Boolean getEstado() {
		return estado;
	}


	public void setEstado(Boolean estado) {
		this.estado = estado;
	}


	public List<MateriaXestudiante> getMateriaXestudiante() {
		return materiaXestudiante;
	}


	public void setMateriaXestudiante(List<MateriaXestudiante> materiaXestudiante) {
		this.materiaXestudiante = materiaXestudiante;
	}
	
	
	
}
