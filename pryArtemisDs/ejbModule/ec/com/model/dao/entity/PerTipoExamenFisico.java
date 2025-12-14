package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the per_tipo_examen_fisico database table.
 * 
 */
@Entity
@Table(name="per_tipo_examen_fisico")
@NamedQuery(name="PerTipoExamenFisico.findAll", query="SELECT p FROM PerTipoExamenFisico p")
public class PerTipoExamenFisico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PER_TIPO_EXAMEN_FISICO_CODTIPOEXAMENFISICO_GENERATOR", sequenceName="SEQ_PER_TIPO_EXAMEN_FISICO", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PER_TIPO_EXAMEN_FISICO_CODTIPOEXAMENFISICO_GENERATOR")
	@Column(name="cod_tipo_examen_fisico")
	private long codTipoExamenFisico;

	@Column(name="tipo_examen_fisico")
	private String tipoExamenFisico;

	public PerTipoExamenFisico() {
	}

	public long getCodTipoExamenFisico() {
		return this.codTipoExamenFisico;
	}

	public void setCodTipoExamenFisico(long codTipoExamenFisico) {
		this.codTipoExamenFisico = codTipoExamenFisico;
	}

	public String getTipoExamenFisico() {
		return this.tipoExamenFisico;
	}

	public void setTipoExamenFisico(String tipoExamenFisico) {
		this.tipoExamenFisico = tipoExamenFisico;
	}

}