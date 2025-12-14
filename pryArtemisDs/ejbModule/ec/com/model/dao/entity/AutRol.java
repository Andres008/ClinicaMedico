package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the aut_rol database table.
 * 
 */
@Entity
@Table(name="aut_rol")
@NamedQuery(name="AutRol.findAll", query="SELECT a FROM AutRol a")
public class AutRol implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="AUT_ROL_CODIGO_GENERATOR", sequenceName="SEQ_AUT_ROL", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="AUT_ROL_CODIGO_GENERATOR")
	private Integer codigo;

	private String descripcion;

	private String estado;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_final")
	private Date fechaFinal;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_inicial")
	private Date fechaInicial;

	private String nombre;

	//bi-directional many-to-one association to AutRolPerfil
	@OneToMany(mappedBy="autRol")
	private List<AutRolPerfil> autRolPerfils;

	//bi-directional many-to-one association to PerMedico
	@OneToMany(mappedBy="autRol")
	private List<PerMedico> perMedicos;

	public AutRol() {
	}

	public Integer getCodigo() {
		return this.codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaFinal() {
		return this.fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public Date getFechaInicial() {
		return this.fechaInicial;
	}

	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<AutRolPerfil> getAutRolPerfils() {
		return this.autRolPerfils;
	}

	public void setAutRolPerfils(List<AutRolPerfil> autRolPerfils) {
		this.autRolPerfils = autRolPerfils;
	}

	public AutRolPerfil addAutRolPerfil(AutRolPerfil autRolPerfil) {
		getAutRolPerfils().add(autRolPerfil);
		autRolPerfil.setAutRol(this);

		return autRolPerfil;
	}

	public AutRolPerfil removeAutRolPerfil(AutRolPerfil autRolPerfil) {
		getAutRolPerfils().remove(autRolPerfil);
		autRolPerfil.setAutRol(null);

		return autRolPerfil;
	}

	public List<PerMedico> getPerMedicos() {
		return this.perMedicos;
	}

	public void setPerMedicos(List<PerMedico> perMedicos) {
		this.perMedicos = perMedicos;
	}

	public PerMedico addPerMedico(PerMedico perMedico) {
		getPerMedicos().add(perMedico);
		perMedico.setAutRol(this);

		return perMedico;
	}

	public PerMedico removePerMedico(PerMedico perMedico) {
		getPerMedicos().remove(perMedico);
		perMedico.setAutRol(null);

		return perMedico;
	}

}