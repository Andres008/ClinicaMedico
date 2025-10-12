package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the aut_usuario database table.
 * 
 */
@Entity
@Table(name="aut_usuario")
@NamedQuery(name="AutUsuario.findAll", query="SELECT a FROM AutUsuario a")
public class AutUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String cedula;

	private String clave;

	private String estado;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_alta")
	private Date fechaAlta;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_baja")
	private Date fechaBaja;

	@Column(name="primer_inicio")
	private String primerInicio;

	@Column(name="url_foto")
	private String urlFoto;

	//bi-directional many-to-one association to AutRol
	@ManyToOne
	@JoinColumn(name="codigo_rol")
	private AutRol autRol;

	//bi-directional one-to-one association to PerPersona
	@OneToOne
	@JoinColumn(name="cedula")
	private PerPersona perPersona;

	//bi-directional many-to-one association to LogGeneral
	@OneToMany(mappedBy="autUsuario")
	private List<LogGeneral> logGenerals;

	public AutUsuario() {
	}

	public String getCedula() {
		return this.cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaAlta() {
		return this.fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaBaja() {
		return this.fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public String getPrimerInicio() {
		return this.primerInicio;
	}

	public void setPrimerInicio(String primerInicio) {
		this.primerInicio = primerInicio;
	}

	public String getUrlFoto() {
		return this.urlFoto;
	}

	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}

	public AutRol getAutRol() {
		return this.autRol;
	}

	public void setAutRol(AutRol autRol) {
		this.autRol = autRol;
	}

	public PerPersona getPerPersona() {
		return this.perPersona;
	}

	public void setPerPersona(PerPersona perPersona) {
		this.perPersona = perPersona;
	}

	public List<LogGeneral> getLogGenerals() {
		return this.logGenerals;
	}

	public void setLogGenerals(List<LogGeneral> logGenerals) {
		this.logGenerals = logGenerals;
	}

	public LogGeneral addLogGeneral(LogGeneral logGeneral) {
		getLogGenerals().add(logGeneral);
		logGeneral.setAutUsuario(this);

		return logGeneral;
	}

	public LogGeneral removeLogGeneral(LogGeneral logGeneral) {
		getLogGenerals().remove(logGeneral);
		logGeneral.setAutUsuario(null);

		return logGeneral;
	}

}