package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the per_tipo_patologia database table.
 * 
 */
@Entity
@Table(name="per_tipo_patologia")
@NamedQuery(name="PerTipoPatologia.findAll", query="SELECT p FROM PerTipoPatologia p")
public class PerTipoPatologia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="codigo_tipo_patologia")
	private long codigoTipoPatologia;

	@Column(name="tipo_patologia")
	private String tipoPatologia;

	//bi-directional many-to-one association to PerPatologia
	@OneToMany(mappedBy="perTipoPatologia")
	private List<PerPatologia> perPatologias;

	public PerTipoPatologia() {
	}

	public long getCodigoTipoPatologia() {
		return this.codigoTipoPatologia;
	}

	public void setCodigoTipoPatologia(long codigoTipoPatologia) {
		this.codigoTipoPatologia = codigoTipoPatologia;
	}

	public String getTipoPatologia() {
		return this.tipoPatologia;
	}

	public void setTipoPatologia(String tipoPatologia) {
		this.tipoPatologia = tipoPatologia;
	}

	public List<PerPatologia> getPerPatologias() {
		return this.perPatologias;
	}

	public void setPerPatologias(List<PerPatologia> perPatologias) {
		this.perPatologias = perPatologias;
	}

	public PerPatologia addPerPatologia(PerPatologia perPatologia) {
		getPerPatologias().add(perPatologia);
		perPatologia.setPerTipoPatologia(this);

		return perPatologia;
	}

	public PerPatologia removePerPatologia(PerPatologia perPatologia) {
		getPerPatologias().remove(perPatologia);
		perPatologia.setPerTipoPatologia(null);

		return perPatologia;
	}

}