package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the v_aut_menu_rol database table.
 * 
 */
@Entity
@Table(name="v_aut_menu_rol")
@NamedQuery(name="VAutMenuRol.findAll", query="SELECT v FROM VAutMenuRol v")
public class VAutMenuRol implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	@Column(name="id_rol")
	private Integer idRol;

	private String nombre;

	private BigDecimal orden;

	public VAutMenuRol() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdRol() {
		return this.idRol;
	}

	public void setIdRol(Integer idRol) {
		this.idRol = idRol;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getOrden() {
		return this.orden;
	}

	public void setOrden(BigDecimal orden) {
		this.orden = orden;
	}

}