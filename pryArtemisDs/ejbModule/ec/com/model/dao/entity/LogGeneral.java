package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the log_general database table.
 * 
 */
@Entity
@Table(name="log_general")
@NamedQuery(name="LogGeneral.findAll", query="SELECT l FROM LogGeneral l")
public class LogGeneral implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="LOG_GENERAL_CODIGO_GENERATOR", sequenceName="SEQ_LOG_GENERAL", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LOG_GENERAL_CODIGO_GENERATOR")
	private Integer codigo;

	@Column(name="direccion_ip")
	private String direccionIp;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_evento")
	private Date fechaEvento;

	private String mensaje;

	private String metodo;

	//bi-directional many-to-one association to PerMedico
	@ManyToOne
	@JoinColumn(name="codigo_medico")
	private PerMedico perMedico;

	//bi-directional many-to-one association to LogCategoria
	@ManyToOne
	@JoinColumn(name="codigo_log_categoria")
	private LogCategoria logCategoria;

	public LogGeneral() {
	}

	public Integer getCodigo() {
		return this.codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDireccionIp() {
		return this.direccionIp;
	}

	public void setDireccionIp(String direccionIp) {
		this.direccionIp = direccionIp;
	}

	public Date getFechaEvento() {
		return this.fechaEvento;
	}

	public void setFechaEvento(Date fechaEvento) {
		this.fechaEvento = fechaEvento;
	}

	public String getMensaje() {
		return this.mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getMetodo() {
		return this.metodo;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}

	public PerMedico getPerMedico() {
		return this.perMedico;
	}

	public void setPerMedico(PerMedico perMedico) {
		this.perMedico = perMedico;
	}

	public LogCategoria getLogCategoria() {
		return this.logCategoria;
	}

	public void setLogCategoria(LogCategoria logCategoria) {
		this.logCategoria = logCategoria;
	}

}