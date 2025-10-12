package ec.com.controlador.gestionSistema;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import ec.com.controlador.sesion.BeanLogin;
import ec.com.model.auditoria.ManagerLog;
import ec.com.model.dao.entity.AutMenu;
import ec.com.model.dao.entity.AutPerfile;
import ec.com.model.dao.entity.AutRol;
import ec.com.model.dao.entity.AutRolPerfil;
import ec.com.model.dao.entity.AutUsuario;
import ec.com.model.dao.entity.PerPersona;
import ec.com.model.gestionSistema.ManagerGestionSistema;
import ec.com.model.gestionUsuarios.ManagerGestionUsuarios;
import ec.com.model.modulos.util.JSFUtil;
import ec.com.model.modulos.util.ModelUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SessionScoped
@Named
public class FormControlUsuariosPerfiles implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private BeanLogin beanLogin;

	@EJB
	private ManagerGestionUsuarios managerGestionUsuarios;

	@EJB
	ManagerGestionSistema managerGestionSistema;

	@EJB
	private ManagerLog managerLog;

	private AutUsuario objAutUsuario;

	private boolean blIbgreso;

	private List<AutRol> lstAutRols;
	private AutRol objAutRol;

	private List<AutUsuario> lstAutUsuario;

	private List<AutMenu> lstAutMenu;
	private AutMenu objAutMenu;
	private PerPersona objPersona;

	private AutRolPerfil objAutRolPerfil;

	private AutPerfile objAutPerfile;

	PrimeFaces current = PrimeFaces.current();

	public void inicializarMenu() {
		try {
			lstAutMenu = managerGestionUsuarios.findAllMenu();
			objAutMenu = new AutMenu();
			objAutPerfile = new AutPerfile();
			objAutPerfile.setAutMenu(new AutMenu());
			blIbgreso = false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void inicializarRoles() {
		try {
			lstAutRols = managerGestionUsuarios.findAllRols();
			objAutRol = new AutRol();
			objAutRolPerfil = new AutRolPerfil();
			objAutRolPerfil.setAutRol(new AutRol());
			objAutRolPerfil.setAutPerfile(new AutPerfile());
			objAutRolPerfil.getAutPerfile().setAutMenu(new AutMenu());
			blIbgreso = false;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void inicializarUsuarios() {
		try {
			lstAutUsuario = managerGestionUsuarios.findAllAutUsuario();
			objAutUsuario = new AutUsuario();
			objAutUsuario.setAutRol(new AutRol());
			objAutUsuario.setPerPersona(new PerPersona());
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
		}
	}

	public void inicializarActualizarUsuario(AutUsuario auxAutUsuario) {
		objAutUsuario = auxAutUsuario;
		current.executeInitScript("PF('infUsuario').show()");
		current.ajax().update(":frmUsuarios");
		blIbgreso = false;
	}

	public void inicializarActualizarRol(AutRol auxAutRol) {
		objAutRol = auxAutRol;
		current.executeInitScript("PF('infRol').show()");
		current.ajax().update(":frmRol");
		blIbgreso = false;
	}

	public void inicializarActualizarMenu(AutMenu auxAutMenu) {
		objAutMenu = auxAutMenu;
		current.executeInitScript("PF('infMenu').show()");
		current.ajax().update(":frmMenu");
		blIbgreso = false;
	}

	public void inicializarActualizarPerfil(AutPerfile auxAutPerfil) {
		objAutPerfile = auxAutPerfil;
		current.executeInitScript("PF('infPerfil').show()");
		current.ajax().update(":frmPerfil");
		blIbgreso = false;
	}

	public void buscarPersona() {
		try {
			objPersona = managerGestionUsuarios.findPersonaByCedula(objAutUsuario.getCedula());
			if (objPersona != null) {
				objAutUsuario.setPerPersona(objPersona);
				objAutUsuario.setCedula(objPersona.getCedula());
				JSFUtil.crearMensajeINFO("Busqueda correcta.");
				current.ajax().update(":frmUsuarios");
			} else {
				ModelUtil.verificarCedulaEcuador(objAutUsuario.getCedula());
				JSFUtil.crearMensajeWARN("Persona no existe.");
				objPersona = new PerPersona();
				objPersona.setCedula(objAutUsuario.getCedula());
				System.out.println(objPersona.getCedula());
				current.executeInitScript("PF('infPersonas').show()");
				current.executeInitScript("PF('infUsuario').hide()");
				current.ajax().update(":frmPersonas");
			}

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.crearMensajeERROR(e.getMessage());
			objPersona = new PerPersona();
			objAutUsuario.setPerPersona(objPersona);
			current.ajax().update(":frmUsuarios");
		}
		current.ajax().update(":frmPrincipal:growl");
	}

	public void guardarPersona() {
		try {
			if (ModelUtil.contieneCaracteresNoNumericos(objPersona.getTelefono())
					|| ModelUtil.contieneCaracteresNoNumericos(objPersona.getCelular()))
				throw new Exception("Los números telefonicos contienen caracteres no numéricos");
			if (ModelUtil.isEmpty(objPersona.getNombres()) || ModelUtil.isEmpty(objPersona.getApellidos()))
				throw new Exception("Catos personales insuficientes");
			objPersona.setApellidos(ModelUtil.cambiarMayusculas(objPersona.getApellidos()));
			objPersona.setNombres(ModelUtil.cambiarMayusculas(objPersona.getNombres()));
			objPersona.setCorreo(ModelUtil.cambiarMinusculas(objPersona.getCorreo()));
			managerGestionUsuarios.ingresarPersona(objPersona);
			inicializarUsuarios();
			objAutUsuario.setPerPersona(objPersona);
			objAutUsuario.setCedula(objPersona.getCedula());
			current.executeInitScript("PF('infPersonas').hide()");
			current.executeInitScript("PF('infUsuario').show()");
			current.ajax().update(":frmUsuarios");
			JSFUtil.crearMensajeINFO("Ingreso Correcto.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}

	}

	public void inicializarNuevoUsuario() {
		objAutUsuario = new AutUsuario();
		objAutUsuario.setAutRol(new AutRol());
		objAutUsuario.setPerPersona(new PerPersona());
		blIbgreso = true;
		current.executeInitScript("PF('infUsuario').show()");
		current.ajax().update(":frmUsuarios");
	}

	public void inicializarNuevoRol() {
		objAutRol = new AutRol();
		current.executeInitScript("PF('infRol').show()");
		current.ajax().update(":frmRol");
		blIbgreso = true;
	}

	public void inicializarNuevoPerfil(AutMenu auxAutMenu) {
		objAutPerfile = new AutPerfile();
		objAutPerfile.setAutMenu(auxAutMenu);
		current.executeInitScript("PF('infPerfil').show()");
		current.ajax().update(":frmPerfil");
		blIbgreso = true;
	}

	public void inicializarNuevoMenu() {
		objAutMenu = new AutMenu();
		current.executeInitScript("PF('infMenu').show()");
		current.ajax().update(":frmMenu");
		blIbgreso = true;
	}

	public void inicializarNuevoPerfilRol(AutRol auxAutRol) {
		objAutRolPerfil = new AutRolPerfil();
		objAutRolPerfil.setAutRol(auxAutRol);
		objAutRolPerfil.setAutPerfile(new AutPerfile());
		objAutRolPerfil.getAutPerfile().setAutMenu(new AutMenu());
		current.executeInitScript("PF('infPerfil').show()");
		current.ajax().update(":frmPerfil");
	}

	public void restablecerContraseniaUsuario(AutUsuario auxAutUsuario) {
		try {
			auxAutUsuario.setClave(ModelUtil.md5(auxAutUsuario.getCedula()));
			auxAutUsuario.setPrimerInicio("SI");
			managerGestionUsuarios.actualizarUsuario(auxAutUsuario);
			managerLog.generarLogAuditoria(beanLogin.getCredencial(), this.getClass(), "Inactivar Usuario.",
					auxAutUsuario.toString());
			inicializarUsuarios();
			JSFUtil.crearMensajeINFO("Contraseña restablecida.");
			current.ajax().update(":frmPrincipal");
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.crearMensajeERROR("Error al restablecer contraseña. " + e.getMessage());
		}
	}

	public void inactivarUsuario(AutUsuario auxAutUsuario) {
		try {
			auxAutUsuario.setFechaBaja(new Date());
			auxAutUsuario.setEstado("I");
			managerGestionUsuarios.actualizarUsuario(auxAutUsuario);
			managerLog.generarLogAuditoria(beanLogin.getCredencial(), this.getClass(), "Inactivar Usuario.",
					auxAutUsuario.toString());
			inicializarUsuarios();
			JSFUtil.crearMensajeINFO("Usuario Inactivado.");
			current.ajax().update(":frmPrincipal");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public void inactivarRol(AutRol auxAutRol) {
		try {

			auxAutRol.setEstado("I");
			managerGestionUsuarios.actualizarRol(auxAutRol);
			managerLog.generarLogAuditoria(beanLogin.getCredencial(), this.getClass(), "Inactivar Rol.",
					auxAutRol.toString());
			inicializarUsuarios();
			JSFUtil.crearMensajeINFO("Rol Inactivado.");
			current.ajax().update(":frmPrincipal");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public void inactivarPerfil(AutPerfile auxAutPerfile) {
		try {
			auxAutPerfile.setEstado("I");
			managerGestionUsuarios.actualizarPerfil(auxAutPerfile);
			managerLog.generarLogAuditoria(beanLogin.getCredencial(), this.getClass(), "Inactivar Rol.",
					auxAutPerfile.toString());
			inicializarUsuarios();
			JSFUtil.crearMensajeINFO("Rol Inactivado.");
			current.ajax().update(":frmPrincipal");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public void inactivarMenu(AutMenu auxAutMenu) {
		try {
			auxAutMenu.setEstado("I");
			managerGestionUsuarios.actualizarMenu(auxAutMenu);
			managerLog.generarLogAuditoria(beanLogin.getCredencial(), this.getClass(), "Inactivar Rol.",
					auxAutMenu.toString());
			inicializarMenu();
			JSFUtil.crearMensajeINFO("Rol Inactivado.");
			current.ajax().update(":frmPrincipal");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public void activarMenu(AutMenu auxAutMenu) {
		try {
			auxAutMenu.setEstado("A");
			managerGestionUsuarios.actualizarMenu(auxAutMenu);
			managerLog.generarLogAuditoria(beanLogin.getCredencial(), this.getClass(), "Inactivar Rol.",
					auxAutMenu.toString());
			inicializarMenu();
			JSFUtil.crearMensajeINFO("Rol activado.");
			current.ajax().update(":frmPrincipal");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public void inactivarRolPerfil(AutRolPerfil auxAutRolPerfil) {
		try {

			auxAutRolPerfil.setEstado("I");
			auxAutRolPerfil.setFechaFinal(new Date());
			managerGestionUsuarios.actualizarRolPerfil(auxAutRolPerfil);
			managerLog.generarLogAuditoria(beanLogin.getCredencial(), this.getClass(), "Inactivar Rol.",
					auxAutRolPerfil.toString());
			inicializarRoles();
			JSFUtil.crearMensajeINFO("Rol Inactivado.");
			current.ajax().update(":frmPrincipal");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public void activarRolPerfil(AutRolPerfil auxAutRolPerfil) {
		try {

			auxAutRolPerfil.setEstado("A");
			auxAutRolPerfil.setFechaInicial(new Date());
			auxAutRolPerfil.setFechaFinal(null);
			managerGestionUsuarios.actualizarRolPerfil(auxAutRolPerfil);
			managerLog.generarLogAuditoria(beanLogin.getCredencial(), this.getClass(), "Inactivar Rol.",
					auxAutRolPerfil.toString());
			inicializarRoles();
			JSFUtil.crearMensajeINFO("Rol Inactivado.");
			current.ajax().update(":frmPrincipal");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public void activarRol(AutRol auxAutRol) {
		try {

			auxAutRol.setEstado("A");
			managerGestionUsuarios.actualizarRol(auxAutRol);
			managerLog.generarLogAuditoria(beanLogin.getCredencial(), this.getClass(), "activar Rol.",
					auxAutRol.toString());
			inicializarUsuarios();
			JSFUtil.crearMensajeINFO("Rol Activado.");
			current.ajax().update(":frmPrincipal");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public void activarUsuario(AutUsuario auxAutUsuario) {
		try {
			auxAutUsuario.setFechaBaja(null);
			auxAutUsuario.setFechaAlta(new Date());
			auxAutUsuario.setEstado("A");
			managerGestionUsuarios.actualizarUsuario(auxAutUsuario);
			managerLog.generarLogAuditoria(beanLogin.getCredencial(), this.getClass(), "Activar Usuario.",
					auxAutUsuario.toString());
			inicializarUsuarios();
			JSFUtil.crearMensajeINFO("Usuario Activado.");
			current.ajax().update(":frmPrincipal");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public void actualizarUsuario() {
		try {
			managerGestionUsuarios.actualizarUsuario(objAutUsuario);
			inicializarUsuarios();
			managerLog.generarLogAuditoria(beanLogin.getCredencial(), this.getClass(), "Activar Usuario.",
					objAutUsuario.toString());
			JSFUtil.crearMensajeINFO("Actualización Exitosa.");
			current.executeInitScript("PF('infUsuario').hide()");
			current.ajax().update(":frmPrincipal");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public void actualizarRol() {
		try {
			managerGestionUsuarios.actualizarRol(objAutRol);
			inicializarUsuarios();
			JSFUtil.crearMensajeINFO("Actualización Exitosa.");
			current.executeInitScript("PF('infRol').hide()");
			current.ajax().update(":frmPrincipal");
			managerLog.generarLogAuditoria(beanLogin.getCredencial(), this.getClass(), "Activar Usuario.",
					objAutRol.toString());
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public void actualizarMenu() {
		try {
			managerGestionUsuarios.actualizarMenu(objAutMenu);
			inicializarMenu();
			JSFUtil.crearMensajeINFO("Actualización Exitosa.");
			current.executeInitScript("PF('infPerfil').hide()");
			current.ajax().update(":frmPrincipal");
			managerLog.generarLogAuditoria(beanLogin.getCredencial(), this.getClass(), "Activar Usuario.",
					objAutMenu.toString());
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public void actualizarPerfil() {
		try {
			managerGestionUsuarios.actualizarPerfil(objAutPerfile);
			inicializarMenu();
			JSFUtil.crearMensajeINFO("Actualización Exitosa.");
			current.executeInitScript("PF('infPerfil').hide()");
			current.ajax().update(":frmPrincipal");
			managerLog.generarLogAuditoria(beanLogin.getCredencial(), this.getClass(), "Activar Usuario.",
					objAutMenu.toString());
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	public void guardarUsuario() {
		if (blIbgreso)
			ingresarUsuario();
		else
			actualizarUsuario();
	}

	public void ingresarUsuario() {
		try {
			if (ModelUtil.isEmpty(objAutUsuario.getPerPersona().getCedula()))
				throw new Exception("No se ha seleccionado una persona");
			if (objAutUsuario.getAutRol().getCodigo() == 0)
				throw new Exception("No se ha seleccionado un Rol");
			if (managerGestionSistema.findByIdAutUsuario(objAutUsuario.getCedula()) != null)
				throw new Exception("Usuario ya se encuentra registrado.");
			objAutUsuario.setEstado("A");
			objAutUsuario.setPrimerInicio("SI");
			objAutUsuario.setFechaAlta(new Date());
			objAutUsuario.setClave(ModelUtil.md5(objAutUsuario.getCedula()));
			managerGestionUsuarios.ingresarUsuario(objAutUsuario);
			inicializarUsuarios();
			JSFUtil.crearMensajeINFO("Ingreso Exitoso.");
			current.executeInitScript("PF('infUsuario').hide()");
			current.ajax().update(":frmPrincipal");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
			current.ajax().update(":frmPrincipal:growl");
		}
	}

	public void ingresarMenu() {
		try {
			objAutMenu.setNombre(objAutMenu.getNombre().toUpperCase());
			objAutMenu.setObservacion(objAutMenu.getObservacion().toUpperCase());
			objAutMenu.setEstado("A");
			managerGestionUsuarios.ingresarMenu(objAutMenu);
			inicializarMenu();
			JSFUtil.crearMensajeINFO("Ingreso Exitoso.");
			current.executeInitScript("PF('infMenu').hide()");
			current.ajax().update(":frmPrincipal");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
			current.ajax().update(":frmPrincipal:growl");
		}
	}

	public void ingresarRol() {
		try {
			objAutRol.setNombre(objAutRol.getNombre().toUpperCase());
			objAutRol.setDescripcion(objAutRol.getDescripcion().toUpperCase());
			objAutRol.setEstado("A");
			objAutRol.setFechaInicial(new Date());
			managerGestionUsuarios.ingresarRol(objAutRol);
			inicializarRoles();
			JSFUtil.crearMensajeINFO("Ingreso Exitoso.");
			current.executeInitScript("PF('infRol').hide()");
			current.ajax().update(":frmPrincipal");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
			current.ajax().update(":frmPrincipal:growl");
		}
	}

	public void ingresarPerfil() {
		try {

			objAutPerfile.setNombre(objAutPerfile.getNombre().toUpperCase());
			objAutPerfile.setObservacion(objAutPerfile.getObservacion().toUpperCase());
			objAutPerfile.setEstado("A");
			managerGestionUsuarios.ingresarPerfil(objAutPerfile);
			inicializarMenu();
			JSFUtil.crearMensajeINFO("Ingreso Exitoso.");
			current.executeInitScript("PF('infPerfil).hide()");
			current.ajax().update(":frmPrincipal");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
			current.ajax().update(":frmPrincipal:growl");
		}
	}

	public void ingresarRolPerfil() {
		try {
			objAutRolPerfil.setEstado("A");
			objAutRolPerfil.setFechaInicial(new Date());
			managerGestionUsuarios.ingresarRolPerfil(objAutRolPerfil);
			inicializarRoles();
			JSFUtil.crearMensajeINFO("Ingreso Exitoso.");
			current.executeInitScript("PF('infPerfil').hide()");
			current.ajax().update(":frmPrincipal");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
			current.ajax().update(":frmPrincipal:growl");
		}
	}

	public List<SelectItem> siRolesActivos() {
		try {
			List<SelectItem> lstSelectItem = new ArrayList<SelectItem>();
			List<AutRol> lstAutRol = managerGestionUsuarios.findRolActivo();
			for (AutRol autRol : lstAutRol) {
				SelectItem select = new SelectItem(autRol.getCodigo(), autRol.getNombre());
				lstSelectItem.add(select);
			}
			return lstSelectItem;
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.crearMensajeERROR(e.getMessage());
			return null;
		}
	}

	public List<SelectItem> siMenuActivos() {
		try {
			List<SelectItem> lstSelectItem = new ArrayList<SelectItem>();
			List<AutMenu> lstAutMenu = managerGestionUsuarios.findMenuActivo();
			for (AutMenu autMenu : lstAutMenu) {
				SelectItem select = new SelectItem(autMenu.getCodigo(), autMenu.getNombre());
				lstSelectItem.add(select);
			}
			return lstSelectItem;
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.crearMensajeERROR(e.getMessage());
			return null;
		}
	}

	public List<SelectItem> siPerfilActivobyMenu(AutMenu auxAutMenu) {
		try {
			List<SelectItem> lstSelectItem = new ArrayList<SelectItem>();
			List<AutPerfile> lstAutPerfil = managerGestionUsuarios.findPerfilByMenu(auxAutMenu);
			for (AutPerfile autPerfil : lstAutPerfil) {
				SelectItem select = new SelectItem(autPerfil.getCodigo(), autPerfil.getNombre());
				lstSelectItem.add(select);
			}
			return lstSelectItem;
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.crearMensajeERROR(e.getMessage());
			return null;
		}
	}

	public BeanLogin getBeanLogin() {
		return beanLogin;
	}

	public void setBeanLogin(BeanLogin beanLogin) {
		this.beanLogin = beanLogin;
	}

	public List<AutUsuario> getLstAutUsuario() {
		return lstAutUsuario;
	}

	public void setLstAutUsuario(List<AutUsuario> lstAutUsuario) {
		this.lstAutUsuario = lstAutUsuario;
	}

	public AutUsuario getObjAutUsuario() {
		return objAutUsuario;
	}

	public void setObjAutUsuario(AutUsuario objAutUsuario) {
		this.objAutUsuario = objAutUsuario;
	}

	public boolean isBlIbgreso() {
		return blIbgreso;
	}

	public void setBlIbgreso(boolean blIbgreso) {
		this.blIbgreso = blIbgreso;
	}

	public List<AutRol> getLstAutRols() {
		return lstAutRols;
	}

	public void setLstAutRols(List<AutRol> lstAutRols) {
		this.lstAutRols = lstAutRols;
	}

	public AutRol getObjAutRol() {
		return objAutRol;
	}

	public void setObjAutRol(AutRol objAutRol) {
		this.objAutRol = objAutRol;
	}

	public List<AutMenu> getLstAutMenu() {
		return lstAutMenu;
	}

	public void setLstAutMenu(List<AutMenu> lstAutMenu) {
		this.lstAutMenu = lstAutMenu;
	}

	public AutMenu getObjAutMenu() {
		return objAutMenu;
	}

	public void setObjAutMenu(AutMenu objAutMenu) {
		this.objAutMenu = objAutMenu;
	}

	public AutRolPerfil getObjAutRolPerfil() {
		return objAutRolPerfil;
	}

	public void setObjAutRolPerfil(AutRolPerfil objAutRolPerfil) {
		this.objAutRolPerfil = objAutRolPerfil;
	}

	public AutPerfile getObjAutPerfile() {
		return objAutPerfile;
	}

	public void setObjAutPerfile(AutPerfile objAutPerfile) {
		this.objAutPerfile = objAutPerfile;
	}

	public PerPersona getObjPersona() {
		return objPersona;
	}

	public void setObjPersona(PerPersona objPersona) {
		this.objPersona = objPersona;
	}

}
