package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the per_examen_complementario database table.
 * 
 */
@Entity
@Table(name="per_examen_complementario")
@NamedQuery(name="PerExamenComplementario.findAll", query="SELECT p FROM PerExamenComplementario p")
public class PerExamenComplementario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PER_EXAMEN_COMPLEMENTARIO_CODEXAMENCOMPLEMENTARIO_GENERATOR", sequenceName="SEQ_PER_EXAMEN_COMPLEMENTARIO", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PER_EXAMEN_COMPLEMENTARIO_CODEXAMENCOMPLEMENTARIO_GENERATOR")
	@Column(name="cod_examen_complementario")
	private long codExamenComplementario;

	private String ruta;

	//bi-directional many-to-one association to PerConsulta
	@ManyToOne
	@JoinColumn(name="codigo_consulta")
	private PerConsulta perConsulta;

	//bi-directional many-to-one association to PerTipoExamenComple
	@ManyToOne
	@JoinColumn(name="codigo_tipo_exam_comple")
	private PerTipoExamenComple perTipoExamenComple;

	public PerExamenComplementario() {
	}

	public long getCodExamenComplementario() {
		return this.codExamenComplementario;
	}

	public void setCodExamenComplementario(long codExamenComplementario) {
		this.codExamenComplementario = codExamenComplementario;
	}

	public String getRuta() {
		return this.ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public PerConsulta getPerConsulta() {
		return this.perConsulta;
	}

	public void setPerConsulta(PerConsulta perConsulta) {
		this.perConsulta = perConsulta;
	}

	public PerTipoExamenComple getPerTipoExamenComple() {
		return this.perTipoExamenComple;
	}

	public void setPerTipoExamenComple(PerTipoExamenComple perTipoExamenComple) {
		this.perTipoExamenComple = perTipoExamenComple;
	}

}