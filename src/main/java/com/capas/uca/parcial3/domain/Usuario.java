package com.capas.uca.parcial3.domain;

import java.sql.Date;

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
@Table(schema="public",name="usuario")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idUsuario")
	private Integer idUsuario;
	
	@Column(name="nombre")
	//@Size(min="",max="")
	@NotEmpty(message="No puede ir vacio")
	private String nombre;
	
	@Column(name="apellido")
	//@Size(min="",max="")
	@NotEmpty(message="No puede ir vacio")
	private String apellido;
	
	@Column(name="fechaNac")
	//@Size(min="",max="")
	@NotEmpty(message="No puede ir vacio")
	private Date fechaNac;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idMunicipio")
	private Municipio municipio;
	
	@Transient
	private Integer idMunicipio;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idDepartamento")
	private Departamento departamento;
	
	@Transient
	private Integer idDepartamento;
	
	@Column(name="direccion")
	//@Size(min="",max="")
	@NotEmpty(message="No puede ir vacio")
	private String direccion;
	
	@Column(name="nombreUser")
	//@Size(min="",max="")
	@NotEmpty(message="No puede ir vacio")
	private String nombreUser;
	
	@Column(name="contrasenia")
	//@Size(min="",max="")
	@NotEmpty(message="No puede ir vacio")
	private String contrasenia;
	
	@Column(name="tipoUsuario")
	//@Size(min="",max="")
	//@NotEmpty(message="No puede ir vacio")
	private Boolean tipoUsuario;
	
	@Column(name="sesion")
	//@Size(min="",max="")
	//@NotEmpty(message="No puede ir vacio")
	private Boolean sesion;

	public Usuario() {
		
	}
	
	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
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

	public Date getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(Date fechaNac) {
		this.fechaNac = fechaNac;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public Integer getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(Integer idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public Integer getIdDepartamento() {
		return idDepartamento;
	}

	public void setIdDepartamento(Integer idDepartamento) {
		this.idDepartamento = idDepartamento;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getNombreUser() {
		return nombreUser;
	}

	public void setNombreUser(String nombreUser) {
		this.nombreUser = nombreUser;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public Boolean getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(Boolean tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public Boolean getSesion() {
		return sesion;
	}

	public void setSesion(Boolean sesion) {
		this.sesion = sesion;
	}
	
	public String getDelegateSesion() {
		return sesion ? "Activo":"Inactivo";
	}

}
