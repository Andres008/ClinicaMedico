package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the per_paciente database table.
 * 
 */
@Entity
@Table(name = "per_paciente")
@NamedQuery(name = "PerPaciente.findAll", query = "SELECT p FROM PerPaciente p")
public class PerPaciente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "PER_PACIENTE_CODIGOPACIENTE_GENERATOR", sequenceName = "SEQ_PER_PACIENTE", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PER_PACIENTE_CODIGOPACIENTE_GENERATOR")
	@Column(name = "codigo_paciente")
	private long codigoPaciente;

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_alta")
	private Date fechaAlta;

	private String sexo;

	// bi-directional one-to-one association to PerPersona
	@OneToOne
	@JoinColumn(name = "cedula")
	private PerPersona perPersona;

	// bi-directional many-to-one association to PerPacienteMedico
	@OneToMany(mappedBy = "perPaciente", cascade = CascadeType.ALL)
	private List<PerPacienteMedico> perPacienteMedicos;

	// bi-directional many-to-one association to PerPatologia
	@OneToMany(mappedBy = "perPaciente", cascade = CascadeType.ALL)
	private List<PerPatologia> perPatologias;

	public PerPaciente() {
	}

	public long getCodigoPaciente() {
		return this.codigoPaciente;
	}

	public void setCodigoPaciente(long codigoPaciente) {
		this.codigoPaciente = codigoPaciente;
	}

	public Date getFechaAlta() {
		return this.fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getSexo() {
		return this.sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
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
		perPacienteMedico.setPerPaciente(this);

		return perPacienteMedico;
	}

	public PerPacienteMedico removePerPacienteMedico(PerPacienteMedico perPacienteMedico) {
		getPerPacienteMedicos().remove(perPacienteMedico);
		perPacienteMedico.setPerPaciente(null);

		return perPacienteMedico;
	}

	public List<PerPatologia> getPerPatologias() {
		return this.perPatologias;
	}

	public void setPerPatologias(List<PerPatologia> perPatologias) {
		this.perPatologias = perPatologias;
	}

	public PerPatologia addPerPatologia(PerPatologia perPatologia) {
		getPerPatologias().add(perPatologia);
		perPatologia.setPerPaciente(this);

		return perPatologia;
	}

	public PerPatologia removePerPatologia(PerPatologia perPatologia) {
		getPerPatologias().remove(perPatologia);
		perPatologia.setPerPaciente(null);

		return perPatologia;
	}

}