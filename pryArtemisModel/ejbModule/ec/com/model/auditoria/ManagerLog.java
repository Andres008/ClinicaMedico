package ec.com.model.auditoria;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import ec.com.model.dao.entity.LogCategoria;
import ec.com.model.dao.entity.LogGeneral;
import ec.com.model.dao.manager.ManagerDAOSegbecom;
import ec.com.model.gestionSistema.Credencial;

/**
 * Session Bean implementation class ManagerLog
 */
@Stateless(mappedName = "managerLog")
@LocalBean
public class ManagerLog {

	public static final long LOG_GENERAL = 1;
	public static final long LOG_USABILIDAD = 2;
	public static final long LOG_AUDITORIA = 3;
	public static final long LOG_ERROR_GENERAL = 4;
	public static final long LOG_HISTORICO_GENERAL = 4;

	@EJB
	ManagerDAOSegbecom managerDAOSegbecom;

	/**
	 * Default constructor.
	 */
	public ManagerLog() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Genera una entrada en el log general del sistema.
	 * 
	 * @param idTipoEvento El tipo de evento. Deben utilizarse las siguientes
	 *                     constantes:
	 *                     <ul>
	 *                     <li><b>ManagerLog.LOG_GENERAL</b> Bitacora general del
	 *                     sistema. Incluye eventos sin clasificar.
	 *                     <li><b>ManagerLog.LOG_USABILIDAD</b>Bitacora para
	 *                     estadisticas de uso del sistema. Permitira a los
	 *                     desarrolladores verificar el porcentaje de uso de los
	 *                     diversas funciones del sistema.
	 *                     <li><b>ManagerLog.LOG_AUDITORIA</b>Bitacora para
	 *                     auditoria informatica del sistema.
	 *                     <li><b>ManagerLog.LOG_ERROR_GENERAL</b>Bitacora para
	 *                     errores en general del sistema.
	 *                     <li><b>ManagerLog.LOG_HISTORICO_GENERAL</b>Bitacora para
	 *                     datos historicos en general del sistema.
	 *                     <li><b>ManagerLog.LOG_HISTORICO_FICHA_SOCIOEC</b>Bitacora
	 *                     para datos historicos de fichas socioeconomicas.
	 *                     </ul>
	 * @param credencial   Credencial del usuario que accedio al sistema.
	 * @param clase        Clase java que genera el evento.
	 * @param metodo       Nombre del metodo que genera el evento.
	 * @param detalle      Detalle del evento.
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	private void generarLog(long idTipoEvento, Credencial credencial, Class clase, String metodo, String detalle) throws Exception {
		LogGeneral nuevoEvento;
		nuevoEvento = new LogGeneral();
		LogCategoria tipoEvento = null;
		Date fecha = new Date();

		// buscamos el tipo de evento correspondiente:
		try {
			tipoEvento = findByIdLogTiposEvento(idTipoEvento);
			if (tipoEvento == null)
				System.out.println("ERROR LOG NO EXISTE TIPO EVENTO: " + idTipoEvento);
		} catch (Exception e1) {
			System.out.println("ERROR BUSCANDO LOG TIPO EVENTO: " + e1.getMessage());
		}
		nuevoEvento.setDireccionIp(credencial.getDireccionIP());
		nuevoEvento.setFechaEvento(fecha);
		nuevoEvento.setPerMedico(credencial.getObjPerMedico());
		nuevoEvento.setMensaje(detalle);
		nuevoEvento.setMetodo(clase.getCanonicalName() + "[" + metodo + "]");
		nuevoEvento.setLogCategoria(tipoEvento);

		try {
			managerDAOSegbecom.insertar(nuevoEvento);
		} catch (Exception e) {
			throw new Exception("ERROR LOG: " + e.getMessage());
		}
	}

	public LogCategoria findByIdLogTiposEvento(long idTipoEvento) throws Exception {
		LogCategoria evento = (LogCategoria) managerDAOSegbecom.findById(LogCategoria.class,
				idTipoEvento);
		return evento;
	}

	@SuppressWarnings("rawtypes")
	public void generarLogGeneral(Credencial credencial, Class clase, String metodo, String detalle) throws Exception {
		generarLog(LOG_GENERAL, credencial, clase, metodo, detalle);
	}

	@SuppressWarnings("rawtypes")
	public void generarLogUsabilidad(Credencial credencial, Class clase, String metodo, String detalle) throws Exception {
		generarLog(LOG_USABILIDAD, credencial, clase, metodo, detalle);
	}

	@SuppressWarnings("rawtypes")
	public void generarLogAuditoria(Credencial credencial, Class clase, String metodo, String detalle) throws Exception {
		generarLog(LOG_AUDITORIA, credencial, clase, metodo, detalle);
	}

	@SuppressWarnings("rawtypes")
	public void generarLogErrorGeneral(Credencial credencial, Class clase, String metodo, String detalle){
		try {
			generarLog(LOG_ERROR_GENERAL, credencial, clase, metodo, detalle);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
