package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the per_paciente_medico database table.
 * 
 */
@Entity
@Table(name = "per_paciente_medico")
@NamedQuery(name = "PerPacienteMedico.findAll", query = "SELECT p FROM PerPacienteMedico p")
public class PerPacienteMedico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "PER_PACIENTE_MEDICO_CODIGOPACIENTEMEDICO_GENERATOR", sequenceName = "SEQ_PER_PACIENTE_MEDICO", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PER_PACIENTE_MEDICO_CODIGOPACIENTEMEDICO_GENERATOR")
	@Column(name = "codigo_paciente_medico")
	private long codigoPacienteMedico;

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_alta")
	private Date fechaAlta;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_ultima_consulta")
	private Date fechaUltimaConsulta;

	// bi-directional many-to-one association to PerConsulta
	@OneToMany(mappedBy = "perPacienteMedico")
	private List<PerConsulta> perConsultas;

	// bi-directional many-to-one association to PerMedico
	@ManyToOne
	@JoinColumn(name = "codigo_medico")
	private PerMedico perMedico;

	// bi-directional many-to-one association to PerPaciente
	@ManyToOne
	@JoinColumn(name = "codigo_paciente")
	private PerPaciente perPaciente;

	public PerPacienteMedico() {
	}

	public long getCodigoPacienteMedico() {
		return this.codigoPacienteMedico;
	}

	public void setCodigoPacienteMedico(long codigoPacienteMedico) {
		this.codigoPacienteMedico = codigoPacienteMedico;
	}

	public Date getFechaAlta() {
		return this.fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public List<PerConsulta> getPerConsultas() {
		return this.perConsultas;
	}

	public void setPerConsultas(List<PerConsulta> perConsultas) {
		this.perConsultas = perConsultas;
	}

	public PerConsulta addPerConsulta(PerConsulta perConsulta) {
		getPerConsultas().add(perConsulta);
		perConsulta.setPerPacienteMedico(this);

		return perConsulta;
	}

	public PerConsulta removePerConsulta(PerConsulta perConsulta) {
		getPerConsultas().remove(perConsulta);
		perConsulta.setPerPacienteMedico(null);

		return perConsulta;
	}

	public PerMedico getPerMedico() {
		return this.perMedico;
	}

	public void setPerMedico(PerMedico perMedico) {
		this.perMedico = perMedico;
	}

	public PerPaciente getPerPaciente() {
		return this.perPaciente;
	}

	public void setPerPaciente(PerPaciente perPaciente) {
		this.perPaciente = perPaciente;
	}

	public Date getFechaUltimaConsulta() {
		return fechaUltimaConsulta;
	}

	public void setFechaUltimaConsulta(Date fechaUltimaConsulta) {
		this.fechaUltimaConsulta = fechaUltimaConsulta;
	}
}