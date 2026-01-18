package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the per_cie database table.
 * 
 */
@Entity
@Table(name="per_cie")
@NamedQuery(name="PerCie.findAll", query="SELECT p FROM PerCie p")
public class PerCie implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="codigo_cie")
	private String codigoCie;

	private String descripcion;

	//bi-directional many-to-one association to PerConsulta
	@OneToMany(mappedBy="perCie")
	private List<PerConsulta> perConsultas;

	public PerCie() {
	}

	public String getCodigoCie() {
		return this.codigoCie;
	}

	public void setCodigoCie(String codigoCie) {
		this.codigoCie = codigoCie;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<PerConsulta> getPerConsultas() {
		return this.perConsultas;
	}

	public void setPerConsultas(List<PerConsulta> perConsultas) {
		this.perConsultas = perConsultas;
	}

	public PerConsulta addPerConsulta(PerConsulta perConsulta) {
		getPerConsultas().add(perConsulta);
		perConsulta.setPerCie(this);

		return perConsulta;
	}

	public PerConsulta removePerConsulta(PerConsulta perConsulta) {
		getPerConsultas().remove(perConsulta);
		perConsulta.setPerCie(null);

		return perConsulta;
	}

}