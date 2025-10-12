package ec.com.model.modulos.util;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public class JSFUtil {
	/**
	 * Crea un mensaje JSF
	 * @param severidad Puede tomar el valor de:
	 * <ul>
	 * <li>FacesMessage.SEVERITY_FATAL
	 * <li>FacesMessage.SEVERITY_ERROR
	 * <li>FacesMessage.SEVERITY_WARN
	 * <li>FacesMessage.SEVERITY_INFO
	 * </ul>
	 * @param mensaje Contenido del mensaje
	 */
	public static void crearMensaje(Severity severidad,String mensaje){
		FacesMessage msg = new FacesMessage();
		msg.setSeverity(severidad);
		msg.setSummary(mensaje);
		//msg.setDetail(detalle);
        FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	/**
	 * Crea un mensaje JSF
	 * @param severidad Puede tomar el valor de:
	 * <ul>
	 * <li>FacesMessage.SEVERITY_FATAL
	 * <li>FacesMessage.SEVERITY_ERROR
	 * <li>FacesMessage.SEVERITY_WARN
	 * <li>FacesMessage.SEVERITY_INFO
	 * </ul>
	 * @param mensaje Contenido del mensaje.
	 * @param detalle Detalle del mensaje.
	 */
	public static void crearMensaje(Severity severidad,String mensaje,String detalle){
		FacesMessage msg = new FacesMessage();
		msg.setSeverity(severidad);
		msg.setSummary(mensaje);
		msg.setDetail(detalle);
        FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	/**
	 * Crea un mensaje JSF de tipo de severidad FATAL.
	 * @param mensaje Contenido del mensaje.
	 * @param detalle Detalle del mensaje. Puede ser null.
	 */
	public static void crearMensajeFATAL(String detalle){
		crearMensaje(FacesMessage.SEVERITY_FATAL, "Atención se produjo un ERROR FATAL",detalle);
	}
	
	/**
	 * Crea un mensaje JSF de tipo de severidad ERROR.
	 * @param mensaje Contenido del mensaje.
	 * @param detalle Detalle del mensaje. Puede ser null.
	 */
	public static void crearMensajeERROR(String detalle){
		crearMensaje(FacesMessage.SEVERITY_ERROR, "Atención se produjo un ERROR",detalle);
	}
	
	/**
	 * Crea un mensaje JSF de tipo de severidad WARNING.
	 * @param mensaje Contenido del mensaje.
	 * @param detalle Detalle del mensaje. Puede ser null.
	 */
	public static void crearMensajeWARN(String detalle){
		crearMensaje(FacesMessage.SEVERITY_WARN, "Atención se produjo una ADVERTENCIA.",detalle);
	}
	
	/**
	 * Crea un mensaje JSF de tipo de severidad INFORMATIVE.
	 * @param mensaje Contenido del mensaje.
	 * @param detalle Detalle del mensaje. Puede ser null.
	 */
	public static void crearMensajeINFO(String detalle){
		crearMensaje(FacesMessage.SEVERITY_INFO, "Atención.",detalle);
	}
}
