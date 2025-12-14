package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


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
	@SequenceGenerator(name="PER_PERSONAS_CEDULA_GENERATOR", sequenceName="SEQ_PER_PERSONAS", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PER_PERSONAS_CEDULA_GENERATOR")
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

	//bi-directional many-to-one association to PerMedico
	@OneToMany(mappedBy="perPersona")
	private List<PerMedico> perMedicos;

	//bi-directional one-to-one association to PerPaciente
	@OneToOne(mappedBy="perPersona")
	private PerPaciente perPaciente;

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

	public List<PerMedico> getPerMedicos() {
		return this.perMedicos;
	}

	public void setPerMedicos(List<PerMedico> perMedicos) {
		this.perMedicos = perMedicos;
	}

	public PerMedico addPerMedico(PerMedico perMedico) {
		getPerMedicos().add(perMedico);
		perMedico.setPerPersona(this);

		return perMedico;
	}

	public PerMedico removePerMedico(PerMedico perMedico) {
		getPerMedicos().remove(perMedico);
		perMedico.setPerPersona(null);

		return perMedico;
	}

	public PerPaciente getPerPaciente() {
		return this.perPaciente;
	}

	public void setPerPaciente(PerPaciente perPaciente) {
		this.perPaciente = perPaciente;
	}

}