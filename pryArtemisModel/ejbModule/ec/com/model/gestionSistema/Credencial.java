/**
 * 
 */
package ec.com.model.gestionSistema;

import ec.com.model.dao.entity.PerMedico;

/**
 * Token de seguridad que se conforma del ID del usuario, el codigo de seguridad
 * (codificado con MD5) y la direccion IP del host cliente.
 * 
 * @author mrea
 *
 */
public class Credencial {
	private PerMedico objPerMedico;
	private String direccionIP;
	private String correo;
	private String primerInicio;
	private byte[] usrFotografia;

	public Credencial() {
	}

	public PerMedico getObjPerMedico() {
		return objPerMedico;
	}

	public void setObjPerMedico(PerMedico objPerMedico) {
		this.objPerMedico = objPerMedico;
	}

	public String getDireccionIP() {
		return direccionIP;
	}

	public void setDireccionIP(String direccionIP) {
		this.direccionIP = direccionIP;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getPrimerInicio() {
		return primerInicio;
	}

	public void setPrimerInicio(String primerInicio) {
		this.primerInicio = primerInicio;
	}

	public byte[] getUsrFotografia() {
		return usrFotografia;
	}

	public void setUsrFotografia(byte[] usrFotografia) {
		this.usrFotografia = usrFotografia;
	}

	

}
