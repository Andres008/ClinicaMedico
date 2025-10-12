package ec.com.controlador.sesion;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ec.com.model.gestionSistema.Credencial;

@Named
@ViewScoped
public class ControlFormularios implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public ControlFormularios() {
		super();
	}

	public void verificarSesion() {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
	 		//TppContratista tppContratista = (TppContratista) context.getExternalContext().getSessionMap().get("tppContratista");
	 		Credencial credencial = (Credencial) context.getExternalContext().getSessionMap().get("credencial");
	 		if(credencial==null) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("/pryArtemisWeb/faces/access.xhtml");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
