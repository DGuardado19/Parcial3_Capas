package com.capas.Parcial3Final.domain;

import java.sql.Date;
import java.util.List;

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
@Table(schema="public",name="estudiante")
public class Estudiante {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idEstudiante")
	private Integer idEstudiante;
	
	@Column(name="nombre")
	//@Size(min="",max="")
	@NotEmpty(message="No puede ir vacio")
	private String nombre;
	
	@Column(name="apellido")
	//@Size(min="",max="")
	@NotEmpty(message="No puede ir vacio")
	private String apellido;
	
	@Column(name="carnet")
	//@Size(min="",max="")
	@NotEmpty(message="No puede ir vacio")
	private String carnet;
	
	@Column(name="fechaNac")
	//@Size(min="",max="")
	@NotEmpty(message="No puede ir vacio")
	private Date fechaNac;
	
	@Column(name="direccion")
	//@Size(min="",max="")
	@NotEmpty(message="No puede ir vacio")
	private String direccion;
	
	@Column(name="telefonoFijo")
	//@Size(min="",max="")
	@NotEmpty(message="No puede ir vacio")
	private String telefonoFijo;
	
	@Column(name="telefonoMovil")
	//@Size(min="",max="")
	@NotEmpty(message="No puede ir vacio")
	private String telefonoMovil;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idCentroEscolar")
	private CentroEscolar centroEscolar;
	
	@Transient
	private Integer idCentroEscolar;
	
	@Column(name="nombrePadre")
	//@Size(min="",max="")
	@NotEmpty(message="No puede ir vacio")
	private String nombrePadre;
	
	@Column(name="nombreMadre")
	//@Size(min="",max="")
	@NotEmpty(message="No puede ir vacio")
	private String nombreMadre;
	
	@OneToMany(mappedBy="estudiante",fetch=FetchType.LAZY)
	private List<MateriaXestudiante> materiaXestudiante;
	
	public Estudiante() {
		
	}

	public Integer getIdEstudiante() {
		return idEstudiante;
	}

	public void setIdEstudiante(Integer idEstudiante) {
		this.idEstudiante = idEstudiante;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCarnet() {
		return carnet;
	}

	public void setCarnet(String carnet) {
		this.carnet = carnet;
	}

	public Date getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(Date fechaNac) {
		this.fechaNac = fechaNac;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefonoFijo() {
		return telefonoFijo;
	}

	public void setTelefonoFijo(String telefonoFijo) {
		this.telefonoFijo = telefonoFijo;
	}

	public String getTelefonoMovil() {
		return telefonoMovil;
	}

	public void setTelefonoMovil(String telefonoMovil) {
		this.telefonoMovil = telefonoMovil;
	}

	public CentroEscolar getCentroEscolar() {
		return centroEscolar;
	}

	public void setCentroEscolar(CentroEscolar centroEscolar) {
		this.centroEscolar = centroEscolar;
	}

	public Integer getIdCentroEscolar() {
		return idCentroEscolar;
	}

	public void setIdCentroEscolar(Integer idCentroEscolar) {
		this.idCentroEscolar = idCentroEscolar;
	}

	public String getNombrePadre() {
		return nombrePadre;
	}

	public void setNombrePadre(String nombrePadre) {
		this.nombrePadre = nombrePadre;
	}

	public String getNombreMadre() {
		return nombreMadre;
	}

	public void setNombreMadre(String nombreMadre) {
		this.nombreMadre = nombreMadre;
	}

	public List<MateriaXestudiante> getMateriaXestudiante() {
		return materiaXestudiante;
	}

	public void setMateriaXestudiante(List<MateriaXestudiante> materiaXestudiante) {
		this.materiaXestudiante = materiaXestudiante;
	}
	
	

}
