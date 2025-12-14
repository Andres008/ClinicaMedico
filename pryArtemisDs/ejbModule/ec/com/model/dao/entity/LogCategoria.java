package ec.com.model.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the log_categoria database table.
 * 
 */
@Entity
@Table(name="log_categoria")
@NamedQuery(name="LogCategoria.findAll", query="SELECT l FROM LogCategoria l")
public class LogCategoria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="LOG_CATEGORIA_CODIGOLOGCATEGORIA_GENERATOR", sequenceName="SEQ_LOG_CATEGORIA", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LOG_CATEGORIA_CODIGOLOGCATEGORIA_GENERATOR")
	@Column(name="codigo_log_categoria")
	private long codigoLogCategoria;

	private String descripcion;

	private String nombre;

	//bi-directional many-to-one association to LogGeneral
	@OneToMany(mappedBy="logCategoria")
	private List<LogGeneral> logGenerals;

	public LogCategoria() {
	}

	public long getCodigoLogCategoria() {
		return this.codigoLogCategoria;
	}

	public void setCodigoLogCategoria(long codigoLogCategoria) {
		this.codigoLogCategoria = codigoLogCategoria;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<LogGeneral> getLogGenerals() {
		return this.logGenerals;
	}

	public void setLogGenerals(List<LogGeneral> logGenerals) {
		this.logGenerals = logGenerals;
	}

	public LogGeneral addLogGeneral(LogGeneral logGeneral) {
		getLogGenerals().add(logGeneral);
		logGeneral.setLogCategoria(this);

		return logGeneral;
	}

	public LogGeneral removeLogGeneral(LogGeneral logGeneral) {
		getLogGenerals().remove(logGeneral);
		logGeneral.setLogCategoria(null);

		return logGeneral;
	}

}