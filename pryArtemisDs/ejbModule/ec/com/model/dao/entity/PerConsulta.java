package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the per_consulta database table.
 * 
 */
@Entity
@Table(name = "per_consulta")
@NamedQuery(name = "PerConsulta.findAll", query = "SELECT p FROM PerConsulta p")
public class PerConsulta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "PER_CONSULTA_CODIGOCONSULTA_GENERATOR", sequenceName = "SEQ_PER_CONSULTA", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PER_CONSULTA_CODIGOCONSULTA_GENERATOR")
	@Column(name = "codigo_consulta")
	private long codigoConsulta;

	@Temporal(TemporalType.DATE)
	private Date fecha;

	private String motivo;

	private String observacion;

	// bi-directional many-to-one association to PerPacienteMedico
	@ManyToOne
	@JoinColumn(name = "codigo_paciente_medico")
	private PerPacienteMedico perPacienteMedico;

	// bi-directional many-to-one association to PerExamenComplementario
	@OneToMany(mappedBy = "perConsulta")
	private List<PerExamenComplementario> perExamenComplementarios;

	// bi-directional many-to-one association to PerReceta
	@OneToMany(mappedBy = "perConsulta", cascade = CascadeType.ALL)
	private List<PerReceta> perRecetas;

	public PerConsulta() {
	}

	public long getCodigoConsulta() {
		return this.codigoConsulta;
	}

	public void setCodigoConsulta(long codigoConsulta) {
		this.codigoConsulta = codigoConsulta;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getMotivo() {
		return this.motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getObservacion() {
		return this.observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public PerPacienteMedico getPerPacienteMedico() {
		return this.perPacienteMedico;
	}

	public void setPerPacienteMedico(PerPacienteMedico perPacienteMedico) {
		this.perPacienteMedico = perPacienteMedico;
	}

	public List<PerExamenComplementario> getPerExamenComplementarios() {
		return this.perExamenComplementarios;
	}

	public void setPerExamenComplementarios(List<PerExamenComplementario> perExamenComplementarios) {
		this.perExamenComplementarios = perExamenComplementarios;
	}

	public PerExamenComplementario addPerExamenComplementario(PerExamenComplementario perExamenComplementario) {
		getPerExamenComplementarios().add(perExamenComplementario);
		perExamenComplementario.setPerConsulta(this);

		return perExamenComplementario;
	}

	public PerExamenComplementario removePerExamenComplementario(PerExamenComplementario perExamenComplementario) {
		getPerExamenComplementarios().remove(perExamenComplementario);
		perExamenComplementario.setPerConsulta(null);

		return perExamenComplementario;
	}

	public List<PerReceta> getPerRecetas() {
		return this.perRecetas;
	}

	public void setPerRecetas(List<PerReceta> perRecetas) {
		this.perRecetas = perRecetas;
	}

	public PerReceta addPerReceta(PerReceta perReceta) {
		getPerRecetas().add(perReceta);
		perReceta.setPerConsulta(this);

		return perReceta;
	}

	public PerReceta removePerReceta(PerReceta perReceta) {
		getPerRecetas().remove(perReceta);
		perReceta.setPerConsulta(null);

		return perReceta;
	}

}