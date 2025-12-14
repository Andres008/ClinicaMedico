package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the per_patologia database table.
 * 
 */
@Entity
@Table(name="per_patologia")
@NamedQuery(name="PerPatologia.findAll", query="SELECT p FROM PerPatologia p")
public class PerPatologia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PER_PATOLOGIA_CODIGOPATOLOGIA_GENERATOR", sequenceName="SEQ_PER_PATOLOGIA", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PER_PATOLOGIA_CODIGOPATOLOGIA_GENERATOR")
	@Column(name="codigo_patologia")
	private long codigoPatologia;

	private String descripcion;

	//bi-directional many-to-one association to PerPaciente
	@ManyToOne
	@JoinColumn(name="codigo_paciente")
	private PerPaciente perPaciente;

	//bi-directional many-to-one association to PerTipoPatologia
	@ManyToOne
	@JoinColumn(name="codigo_tipo_patologia")
	private PerTipoPatologia perTipoPatologia;

	public PerPatologia() {
	}

	public long getCodigoPatologia() {
		return this.codigoPatologia;
	}

	public void setCodigoPatologia(long codigoPatologia) {
		this.codigoPatologia = codigoPatologia;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public PerPaciente getPerPaciente() {
		return this.perPaciente;
	}

	public void setPerPaciente(PerPaciente perPaciente) {
		this.perPaciente = perPaciente;
	}

	public PerTipoPatologia getPerTipoPatologia() {
		return this.perTipoPatologia;
	}

	public void setPerTipoPatologia(PerTipoPatologia perTipoPatologia) {
		this.perTipoPatologia = perTipoPatologia;
	}

}