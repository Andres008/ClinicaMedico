package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the per_personas database table.
 * 
 */
@Entity
@Table(name="per_personas")
@NamedQuery(name="PerPersona.findAll", query="SELECT p FROM PerPersona p")
public class PerPersona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String cedula;

	private String apellidos;

	private String celular;

	private String correo;

	private String direccion;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_nacimiento")
	private Date fechaNacimiento;

	private String nombres;

	private String telefono;

	//bi-directional one-to-one association to AutUsuario
	@OneToOne(mappedBy="perPersona")
	private AutUsuario autUsuario;

	public PerPersona() {
	}

	public String getCedula() {
		return this.cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getCelular() {
		return this.celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getCorreo() {
		return this.correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Date getFechaNacimiento() {
		return this.fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public AutUsuario getAutUsuario() {
		return this.autUsuario;
	}

	public void setAutUsuario(AutUsuario autUsuario) {
		this.autUsuario = autUsuario;
	}

}