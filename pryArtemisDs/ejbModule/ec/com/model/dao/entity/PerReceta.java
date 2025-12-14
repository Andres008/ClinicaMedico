package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the per_receta database table.
 * 
 */
@Entity
@Table(name="per_receta")
@NamedQuery(name="PerReceta.findAll", query="SELECT p FROM PerReceta p")
public class PerReceta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PER_RECETA_CODIGORECETA_GENERATOR", sequenceName="SEQ_PER_RECETA", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PER_RECETA_CODIGORECETA_GENERATOR")
	@Column(name="codigo_receta")
	private long codigoReceta;

	private String indicacion;

	private String medicamento;

	//bi-directional many-to-one association to PerConsulta
	@ManyToOne
	@JoinColumn(name="codigo_consulta")
	private PerConsulta perConsulta;

	public PerReceta() {
	}

	public long getCodigoReceta() {
		return this.codigoReceta;
	}

	public void setCodigoReceta(long codigoReceta) {
		this.codigoReceta = codigoReceta;
	}

	public String getIndicacion() {
		return this.indicacion;
	}

	public void setIndicacion(String indicacion) {
		this.indicacion = indicacion;
	}

	public String getMedicamento() {
		return this.medicamento;
	}

	public void setMedicamento(String medicamento) {
		this.medicamento = medicamento;
	}

	public PerConsulta getPerConsulta() {
		return this.perConsulta;
	}

	public void setPerConsulta(PerConsulta perConsulta) {
		this.perConsulta = perConsulta;
	}

}