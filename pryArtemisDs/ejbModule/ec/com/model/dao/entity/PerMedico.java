package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the per_medico database table.
 * 
 */
@Entity
@Table(name="per_medico")
@NamedQuery(name="PerMedico.findAll", query="SELECT p FROM PerMedico p")
public class PerMedico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PER_MEDICO_CODIGOMEDICO_GENERATOR", sequenceName="SEQ_PER_MEDICO", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PER_MEDICO_CODIGOMEDICO_GENERATOR")
	@Column(name="codigo_medico")
	private long codigoMedico;

	private String cie;

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

	//bi-directional many-to-one association to LogGeneral
	@OneToMany(mappedBy="perMedico")
	private List<LogGeneral> logGenerals;

	//bi-directional many-to-one association to AutRol
	@ManyToOne
	@JoinColumn(name="codigo_rol")
	private AutRol autRol;

	//bi-directional many-to-one association to PerPersona
	@ManyToOne
	@JoinColumn(name="cedula")
	private PerPersona perPersona;

	//bi-directional many-to-one association to PerPacienteMedico
	@OneToMany(mappedBy="perMedico")
	private List<PerPacienteMedico> perPacienteMedicos;

	public PerMedico() {
	}

	public long getCodigoMedico() {
		return this.codigoMedico;
	}

	public void setCodigoMedico(long codigoMedico) {
		this.codigoMedico = codigoMedico;
	}

	public String getCie() {
		return this.cie;
	}

	public void setCie(String cie) {
		this.cie = cie;
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

	public List<LogGeneral> getLogGenerals() {
		return this.logGenerals;
	}

	public void setLogGenerals(List<LogGeneral> logGenerals) {
		this.logGenerals = logGenerals;
	}

	public LogGeneral addLogGeneral(LogGeneral logGeneral) {
		getLogGenerals().add(logGeneral);
		logGeneral.setPerMedico(this);

		return logGeneral;
	}

	public LogGeneral removeLogGeneral(LogGeneral logGeneral) {
		getLogGenerals().remove(logGeneral);
		logGeneral.setPerMedico(null);

		return logGeneral;
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

	public List<PerPacienteMedico> getPerPacienteMedicos() {
		return this.perPacienteMedicos;
	}

	public void setPerPacienteMedicos(List<PerPacienteMedico> perPacienteMedicos) {
		this.perPacienteMedicos = perPacienteMedicos;
	}

	public PerPacienteMedico addPerPacienteMedico(PerPacienteMedico perPacienteMedico) {
		getPerPacienteMedicos().add(perPacienteMedico);
		perPacienteMedico.setPerMedico(this);

		return perPacienteMedico;
	}

	public PerPacienteMedico removePerPacienteMedico(PerPacienteMedico perPacienteMedico) {
		getPerPacienteMedicos().remove(perPacienteMedico);
		perPacienteMedico.setPerMedico(null);

		return perPacienteMedico;
	}

}