package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the aut_rol_perfil database table.
 * 
 */
@Entity
@Table(name="aut_rol_perfil")
@NamedQuery(name="AutRolPerfil.findAll", query="SELECT a FROM AutRolPerfil a")
public class AutRolPerfil implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="AUT_ROL_PERFIL_CODIGO_GENERATOR", sequenceName="SEQ_AUT_ROL_PERFIL", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="AUT_ROL_PERFIL_CODIGO_GENERATOR")
	private Integer codigo;

	private String estado;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_final")
	private Date fechaFinal;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_inicial")
	private Date fechaInicial;

	//bi-directional many-to-one association to AutPerfile
	@ManyToOne
	@JoinColumn(name="codigo_perfil")
	private AutPerfile autPerfile;

	//bi-directional many-to-one association to AutRol
	@ManyToOne
	@JoinColumn(name="codigo_rol")
	private AutRol autRol;

	public AutRolPerfil() {
	}

	public Integer getCodigo() {
		return this.codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
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

	public AutPerfile getAutPerfile() {
		return this.autPerfile;
	}

	public void setAutPerfile(AutPerfile autPerfile) {
		this.autPerfile = autPerfile;
	}

	public AutRol getAutRol() {
		return this.autRol;
	}

	public void setAutRol(AutRol autRol) {
		this.autRol = autRol;
	}

}