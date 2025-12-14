package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the per_tipo_examen_comple database table.
 * 
 */
@Entity
@Table(name="per_tipo_examen_comple")
@NamedQuery(name="PerTipoExamenComple.findAll", query="SELECT p FROM PerTipoExamenComple p")
public class PerTipoExamenComple implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PER_TIPO_EXAMEN_COMPLE_CODIGOTIPOEXAMCOMPLE_GENERATOR", sequenceName="SEQ_PER_TIPO_EXAMEN_COMPLE", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PER_TIPO_EXAMEN_COMPLE_CODIGOTIPOEXAMCOMPLE_GENERATOR")
	@Column(name="codigo_tipo_exam_comple")
	private long codigoTipoExamComple;

	@Column(name="tipo_examen_complementario")
	private String tipoExamenComplementario;

	//bi-directional many-to-one association to PerExamenComplementario
	@OneToMany(mappedBy="perTipoExamenComple")
	private List<PerExamenComplementario> perExamenComplementarios;

	public PerTipoExamenComple() {
	}

	public long getCodigoTipoExamComple() {
		return this.codigoTipoExamComple;
	}

	public void setCodigoTipoExamComple(long codigoTipoExamComple) {
		this.codigoTipoExamComple = codigoTipoExamComple;
	}

	public String getTipoExamenComplementario() {
		return this.tipoExamenComplementario;
	}

	public void setTipoExamenComplementario(String tipoExamenComplementario) {
		this.tipoExamenComplementario = tipoExamenComplementario;
	}

	public List<PerExamenComplementario> getPerExamenComplementarios() {
		return this.perExamenComplementarios;
	}

	public void setPerExamenComplementarios(List<PerExamenComplementario> perExamenComplementarios) {
		this.perExamenComplementarios = perExamenComplementarios;
	}

	public PerExamenComplementario addPerExamenComplementario(PerExamenComplementario perExamenComplementario) {
		getPerExamenComplementarios().add(perExamenComplementario);
		perExamenComplementario.setPerTipoExamenComple(this);

		return perExamenComplementario;
	}

	public PerExamenComplementario removePerExamenComplementario(PerExamenComplementario perExamenComplementario) {
		getPerExamenComplementarios().remove(perExamenComplementario);
		perExamenComplementario.setPerTipoExamenComple(null);

		return perExamenComplementario;
	}

}