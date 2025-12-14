package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the aut_menu database table.
 * 
 */
@Entity
@Table(name="aut_menu")
@NamedQuery(name="AutMenu.findAll", query="SELECT a FROM AutMenu a")
public class AutMenu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="AUT_MENU_CODIGO_GENERATOR", sequenceName="SEQ_AUT_MENU", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="AUT_MENU_CODIGO_GENERATOR")
	private Integer codigo;

	private String estado;

	private String nombre;

	private String observacion;

	private BigDecimal orden;

	//bi-directional many-to-one association to AutPerfile
	@OneToMany(mappedBy="autMenu")
	private List<AutPerfile> autPerfiles;

	public AutMenu() {
	}

	public Integer getCodigo() {
		return this.codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getObservacion() {
		return this.observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public BigDecimal getOrden() {
		return this.orden;
	}

	public void setOrden(BigDecimal orden) {
		this.orden = orden;
	}

	public List<AutPerfile> getAutPerfiles() {
		return this.autPerfiles;
	}

	public void setAutPerfiles(List<AutPerfile> autPerfiles) {
		this.autPerfiles = autPerfiles;
	}

	public AutPerfile addAutPerfile(AutPerfile autPerfile) {
		getAutPerfiles().add(autPerfile);
		autPerfile.setAutMenu(this);

		return autPerfile;
	}

	public AutPerfile removeAutPerfile(AutPerfile autPerfile) {
		getAutPerfiles().remove(autPerfile);
		autPerfile.setAutMenu(null);

		return autPerfile;
	}

}