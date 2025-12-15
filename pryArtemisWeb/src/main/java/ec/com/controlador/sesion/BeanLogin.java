package ec.com.controlador.sesion;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.InputStream;

import javax.activation.MimetypesFileTypeMap;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.model.DefaultStreamedContent;

import ec.com.model.auditoria.ManagerLog;
import ec.com.model.gestionSistema.Credencial;
import ec.com.model.gestionSistema.ManagerGestionSistema;
import ec.com.model.modulos.util.JSFUtil;
import ec.com.model.modulos.util.ModelUtil;

@Named("beanLogin")

@SessionScoped

public class BeanLogin implements Serializable {

	/**
	
	 * 
	
	 */

	/**
	 * 
	 * Token de seguridad.
	 * 
	 */

	private Credencial credencial;

	@EJB
	ManagerLog managerLog;

	@EJB
	private ManagerGestionSistema managerGestionSistema;

	private static final long serialVersionUID = 1L;

	private String pathReporte;
	private String imagenActual;
	private String tamPantalla;
	private byte[] archivo;

	public BeanLogin() {
		imagenActual = "";
		tamPantalla = "auto";
	}

	/**
	 * 
	 * Cierre de sesion.
	 * 
	 * 
	 * 
	 * @return redireccion a index.xhtml
	 * 
	 */

	public String actionSalirSistema() {

		if (credencial != null)
			try {
				managerLog.generarLogAuditoria(credencial, this.getClass(), "actionSalirSistema", "Cierre de sesion");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

		return "/login.xhtml?faces-redirect=true";

	}

	public int tiempoCierreSesion() {
		String valorPath = "";
		try {
			valorPath = managerGestionSistema.buscarValorParametroNombre("CIERRE_SESSION");
			int valor = Integer.parseInt(valorPath);
			return valor * 60 * 1000;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	}

	public void verificarCredencial() throws IOException {

		if (credencial == null) {

			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

			externalContext.redirect(externalContext.getRequestContextPath() + "/faces/index.xhtml");

		}

	}

	
	public DefaultStreamedContent cargarFotografia() {

		try {
			InputStream fis;
			fis = new ByteArrayInputStream(this.credencial.getUsrFotografia());
			return DefaultStreamedContent.builder().contentType("image/png; charset=UTF-8").name("usuario.png")
					.stream(() -> fis).build();
		} catch (Exception e) {
			return null;
		}
	}

	public int anioActual() {

		return ModelUtil.getAnioActual();

	}

	public String actionMostrarMenuPrincipal() {

		/*
		 * 
		 * if(credencial==null) return actionSalirSistema();
		 * 
		 */

		return "/modulos/menu?faces-redirect=true";

	}

	public String actionMostrarMenuInicial() {

		if (credencial == null)

			return actionSalirSistema();

		return "/modulos/gestion/plantilla?faces-redirect=true";

	}

	public String actionRuta(/* AutPerfil autPerfil */) {

		// return autPerfil.getRuta()+"?faces-redirect=true";

		return "";

	}

	public Date fechaActual() {

		return new Date();

	}

	public Date fechaMinimaCalendario() {

		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

		try {

			return formato.parse("01/01/1900");

		} catch (ParseException e) {

			JSFUtil.crearMensajeERROR(e.getMessage());

			e.printStackTrace();

		}

		return null;

	}
	
	public String getPathReportes() {
		String valorPath = "";
		try {
			//valorPath = managerParametros.getValorParametro("PATH_REPORTE");
			valorPath = "D:\\Desarrollo\\Reportes\\Receta\\";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return valorPath;
	}

	/*******************************************************************
	 ********************* 
	 * Manejo de Path de reportes*********************
	 ********************************************************************
	 ******************************************************************** 
	 * 
	 * 
	 * 
	 */

	/*******************************************************************
	 * 
	 * Metodo que retorna el path de almacenamiento de los reportes.
	 * 
	 * 
	 * 
	 * @return valor Path
	 * 
	 */

	/*******************************************************************
	 ********************* 
	 * Manejo de Path de Imagenes*********************
	 ********************************************************************
	 ******************************************************************** 
	 * 
	 * 
	 * 
	 */

	/*******************************************************************
	 * 
	 * Metodo que retorna el path de almacenamiento de Imagenes de los reportes
	 * 
	 * 
	 * 
	 * @return valor Path
	 * 
	 */

	public String getPathImagesReportes() {

		String valorPath = "";

		try {

			valorPath = managerGestionSistema.buscarValorParametroNombre("PATH_IMAGENES");

		} catch (Exception e) { // TODO Auto-generated catch block e.printStackTrace();

		}

		return valorPath;

	}

	/*******************************************************************
	 **************** 
	 * Manejo de Sesion para todo el sistema***************
	 ********************************************************************
	 ******************************************************************** 
	 * 
	 * 
	 * 
	 */

	/*******************************************************************
	 * 
	 * Metodo que retorna en minutos el tiempo maximo de inactividad para cerrar la
	 * 
	 * session a un usuario.
	 * 
	 * 
	 * 
	 * @return valor en segundos del tiempo
	 * 
	 */

	/**
	 * 
	 * Metodo que retorna la frase de para el cierre de sesion.
	 * 
	 * 
	 * 
	 * @return String de la frase del mensaje
	 * 
	 */

	public String getFraseCierreSesion() {

		return "Usted ha excedido el tiempo de inactividad, confirme la solicitud para volver a ingresar al sistema.";

	}

	/**
	 * 
	 * Metodo que retorna el titulo del cierre de sesion.
	 * 
	 * 
	 * 
	 * @return Frase del titulo del mensaje
	 * 
	 */

	public String getTituloCierreSesion() {

		return "Por su seguridad la sesion se ha cerrado.";

	}

	/**
	 * 
	 * Metodo que retorna el titulo del boton.
	 * 
	 * 
	 * 
	 * @return Frase del titulo del boton
	 * 
	 */

	public String getTituloBotonCierreSesion() {

		return "Confirmar";

	}

	public Credencial getCredencial() {

		return credencial;

	}

	public void setCredencial(Credencial credencial) {

		this.credencial = credencial;

	}

	public String getPathReporte() {

		if (ModelUtil.isEmpty(pathReporte)) {

			try {

				pathReporte = managerGestionSistema.buscarValorParametroNombre("PATH_REPORTES");

			} catch (Exception e) {

				// TODO Auto-generated catch block

				e.printStackTrace();

			}

		}

		return pathReporte;

	}

	public void setPathReporte(String pathReporte) {

		this.pathReporte = pathReporte;

	}

	public String getImagenActual() {
		return imagenActual;
	}

	public void setImagenActual(String imagenActual) {
		this.imagenActual = imagenActual;
	}

	public String getTamPantalla() {
		return tamPantalla;
	}

	public void setTamPantalla(String tamPantalla) {
		this.tamPantalla = tamPantalla;
	}

}
