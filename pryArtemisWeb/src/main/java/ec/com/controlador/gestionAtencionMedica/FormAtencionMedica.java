package ec.com.controlador.gestionAtencionMedica;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.ByteArrayInputStream;

import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import ec.com.controlador.gestionSistema.FormControlUsuariosPerfiles;
import ec.com.controlador.sesion.BeanLogin;
import ec.com.model.auditoria.ManagerLog;
import ec.com.model.dao.entity.PerConsulta;
import ec.com.model.dao.entity.PerExamenComplementario;
import ec.com.model.dao.entity.PerPaciente;
import ec.com.model.dao.entity.PerPacienteMedico;
import ec.com.model.dao.entity.PerPersona;
import ec.com.model.dao.entity.PerReceta;
import ec.com.model.gestionAtencionMedica.ManagerAtencionMedica;
import ec.com.model.gestionUsuarios.ManagerGestionUsuarios;
import ec.com.model.modulos.util.JSFUtil;
import ec.com.model.modulos.util.ModelUtil;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@SessionScoped
@Named
public class FormAtencionMedica implements Serializable {

	/**
	 * 
	 */
	@EJB
	private ManagerAtencionMedica managerAtencionMedica;
	@EJB
	private ManagerGestionUsuarios managerGestionUsuarios;
	@EJB
	private ManagerLog managerLog;

	@Inject
	private BeanLogin beanLogin;

	@Inject
	private FormControlUsuariosPerfiles formControlUsuariosPerfiles;

	List<PerPacienteMedico> lstPerPacienteMedico;
	private PerPaciente objPerPaciente;
	private PerConsulta objPerConsulta;
	private PerPacienteMedico objPerPacienteMedico;
	private PerReceta objPerReceta;
	private Boolean pnlPacientes, blIbgreso, pnlDatosPaciente, pnlConsulta;
	PrimeFaces current = PrimeFaces.current();
	private static byte[] reportPdf;

	private static final long serialVersionUID = 1L;

	public FormAtencionMedica() {

	}

	@PostConstruct
	public void inicializarVariables() {
		formControlUsuariosPerfiles.setObjPersona(new PerPersona());
	}

	public void inicializarPaneles() {
		pnlPacientes = false;
		blIbgreso = false;
		pnlDatosPaciente = false;
		pnlConsulta = false;
	}

	public void nuevaConsulta() {
		inicializarPaneles();
		pnlConsulta = true;
		objPerConsulta = new PerConsulta();
		objPerConsulta.setPerExamenComplementarios(new ArrayList<PerExamenComplementario>());
		objPerConsulta.setPerRecetas(new ArrayList<PerReceta>());
		objPerReceta = new PerReceta();
		objPerConsulta.setPerPacienteMedico(objPerPacienteMedico);
		current.ajax().update(":frmPrincipal");
	}

	public void agregarMedicamento() {
		objPerReceta.setPerConsulta(objPerConsulta);
		objPerConsulta.getPerRecetas().add(objPerReceta);
		objPerReceta = new PerReceta();
		current.ajax().update(":frmPrincipal");
	}

	public void reporteReceta(PerConsulta objConsulta) {
		try {
			if (objConsulta.getCodigoConsulta() != 0)
				objConsulta = managerAtencionMedica.findConsultaById(objConsulta.getCodigoConsulta());
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("Codigo", "Ejemplo");
			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(
					objConsulta.getPerRecetas());
			File jasper = new File(beanLogin.getPathReportes() + "receta.jasper");
			JasperPrint jasperPrint;
			jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, beanCollectionDataSource);
			reportPdf = JasperExportManager.exportReportToPdf(jasperPrint);
			current.executeScript("PF('dlgReporte').show()");
			current.ajax().update(":frmReporte");
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.crearMensajeERROR(e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "reporteReceta",
					e.getMessage());
			current.ajax().update(":frmPrincipal");
		}

	}

	public void eliminarReceta(PerReceta objReceta) {
		objPerConsulta.getPerRecetas().remove(objReceta);
		current.ajax().update(":frmPrincipal");
	}

	public void guardarAtencion() {
		try {
			objPerConsulta.setFecha(new Date());
			managerAtencionMedica.insertPerConsulta(objPerConsulta);
			inicializarPacientes();
			JSFUtil.crearMensajeINFO("Consulta finalizada correctamente.");
			current.ajax().update(":frmPrincipal");
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.crearMensajeERROR(e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "guardarAtencion",
					e.getMessage());
		}

	}

	public void inicializarPacientes() {
		try {
			objPerPaciente = new PerPaciente();
			objPerPaciente.setPerPersona(new PerPersona());
			objPerPaciente.setPerPacienteMedicos(new ArrayList<PerPacienteMedico>());
			lstPerPacienteMedico = managerAtencionMedica
					.findAllPacientesByMedico(beanLogin.getCredencial().getObjPerMedico());
			inicializarPaneles();
			current.executeInitScript("PF('infUsuario').hide()");
			current.executeInitScript("PF('infPersonas').hide()");
			current.ajax().update(":frmPrincipal");
			pnlPacientes = true;

		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
		}

	}

	public void inicializarNuevoPaciente() {
		blIbgreso = true;
		objPerPaciente = new PerPaciente();
		objPerPaciente.setPerPersona(new PerPersona());
		objPerPaciente.setPerPacienteMedicos(new ArrayList<PerPacienteMedico>());
		current.executeInitScript("PF('infUsuario').show()");
		current.ajax().update(":frmUsuarios");
	}

	public void cargarDatosPaciente(PerPacienteMedico objPacienteAux) {
		try {
			inicializarPaneles();
			objPerPacienteMedico = managerAtencionMedica
					.findPerPacienteMedicoById(objPacienteAux.getCodigoPacienteMedico());
			objPerPacienteMedico.setPerPaciente(
					managerAtencionMedica.findPacienteById(objPerPacienteMedico.getPerPaciente().getCodigoPaciente()));
			pnlDatosPaciente = true;
			current.ajax().update(":frmPrincipal");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
		}

	}

	public void guardarPaciente() {
		if (blIbgreso)
			ingresarPaciente();
		else
			actualizarPaciente();
	}

	private void actualizarPaciente() {
		// TODO Auto-generated method stub

	}

	private void ingresarPaciente() {
		try {
			if (lstPerPacienteMedico.stream()
					.filter(paciente -> paciente.getPerPaciente().getPerPersona().getCedula()
							.equals(objPerPaciente.getPerPersona().getCedula()))
					.collect(Collectors.toList()).size() > 0)
				throw new Exception("Paciente se encuentra registrado");
			if (ModelUtil.isEmpty(objPerPaciente.getPerPersona().getCedula()))
				throw new Exception("Favor buscar la persona que desea registrar como paciente.");
			if (ModelUtil.isEmpty(objPerPaciente.getSexo()))
				throw new Exception("Registrar el sexo del paciente es obligatorio.");
			if (objPerPaciente.getCodigoPaciente() == 0) {
				if (lstPerPacienteMedico.stream()
						.filter(paciente -> paciente.getPerPaciente().getPerPersona().getCedula()
								.equals(objPerPaciente.getPerPersona().getCedula()))
						.collect(Collectors.toList()).size() > 0)
					throw new Exception("Paciente ya registrado");
				else {
					objPerPaciente.setFechaAlta(new Date());
					PerPacienteMedico objPacienteMedico = new PerPacienteMedico();
					objPacienteMedico.setPerPaciente(objPerPaciente);
					objPacienteMedico.setPerMedico(beanLogin.getCredencial().getObjPerMedico());
					objPacienteMedico.setFechaAlta(new Date());
					objPerPaciente.getPerPacienteMedicos().add(objPacienteMedico);
					managerAtencionMedica.insertPerPaciente(objPerPaciente);
				}
			} else {
				PerPacienteMedico objPacienteMedico = new PerPacienteMedico();
				objPacienteMedico.setPerPaciente(objPerPaciente);
				objPacienteMedico.setPerMedico(beanLogin.getCredencial().getObjPerMedico());
				objPacienteMedico.setFechaAlta(new Date());
				objPerPaciente.getPerPacienteMedicos().add(objPacienteMedico);
				managerAtencionMedica.actualizarPerPaciente(objPerPaciente);
			}

			managerLog.generarLogUsabilidad(beanLogin.getCredencial(), this.getClass(), "ingresarPaciente",
					objPerPaciente.toString());
			JSFUtil.crearMensajeINFO("Se creo correctamente el paciente.");
			inicializarPacientes();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "ingresarPaciente",
					e.getMessage());
		}

	}

	public String edadPaciente(Date fechaNacimiento) {
		return ModelUtil.calcularEdadString(fechaNacimiento);
	}

	public void buscarPersona() {
		try {
			formControlUsuariosPerfiles.setObjPersona(
					managerGestionUsuarios.findPersonaByCedula(objPerPaciente.getPerPersona().getCedula()));
			if (formControlUsuariosPerfiles.getObjPersona() != null) {
				if (formControlUsuariosPerfiles.getObjPersona().getPerPaciente() != null) {
					objPerPaciente = managerAtencionMedica.findPacienteById(
							formControlUsuariosPerfiles.getObjPersona().getPerPaciente().getCodigoPaciente());
					current.ajax().update(":frmUsuarios");
				} else {
					objPerPaciente.setPerPersona(formControlUsuariosPerfiles.getObjPersona());
					JSFUtil.crearMensajeINFO("Busqueda correcta.");
					current.ajax().update(":frmUsuarios");
				}

			} else {
				ModelUtil.verificarCedulaEcuador(objPerPaciente.getPerPersona().getCedula());
				JSFUtil.crearMensajeWARN("Persona no existe.");
				formControlUsuariosPerfiles.setObjPersona(new PerPersona());
				formControlUsuariosPerfiles.getObjPersona().setCedula(objPerPaciente.getPerPersona().getCedula());
				current.executeInitScript("PF('infPersonas').show()");
				current.executeInitScript("PF('infUsuario').hide()");
				current.ajax().update(":frmPersonas");
			}

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.crearMensajeERROR(e.getMessage());
		}
		current.ajax().update(":frmPrincipal:growl");
	}

	public StreamedContent getReport() {
		if (reportPdf != null) {
			InputStream fis = new ByteArrayInputStream(reportPdf);
			return DefaultStreamedContent.builder().contentType("application/pdf; charset=UTF-8").name("miArchivo.pdf")
					.stream(() -> fis).build();
		}
		return null;
	}

	public PerPaciente getObjPerPaciente() {
		return objPerPaciente;
	}

	public void setObjPerPaciente(PerPaciente objPerPaciente) {
		this.objPerPaciente = objPerPaciente;
	}

	public BeanLogin getBeanLogin() {
		return beanLogin;
	}

	public void setBeanLogin(BeanLogin beanLogin) {
		this.beanLogin = beanLogin;
	}

	public Boolean getPnlPacientes() {
		return pnlPacientes;
	}

	public void setPnlPacientes(Boolean pnlPacientes) {
		this.pnlPacientes = pnlPacientes;
	}

	public Boolean getBlIbgreso() {
		return blIbgreso;
	}

	public void setBlIbgreso(Boolean blIbgreso) {
		this.blIbgreso = blIbgreso;
	}

	public FormControlUsuariosPerfiles getFormControlUsuariosPerfiles() {
		return formControlUsuariosPerfiles;
	}

	public void setFormControlUsuariosPerfiles(FormControlUsuariosPerfiles formControlUsuariosPerfiles) {
		this.formControlUsuariosPerfiles = formControlUsuariosPerfiles;
	}

	public Boolean getPnlDatosPaciente() {
		return pnlDatosPaciente;
	}

	public void setPnlDatosPaciente(Boolean pnlDatosPaciente) {
		this.pnlDatosPaciente = pnlDatosPaciente;
	}

	public List<PerPacienteMedico> getLstPerPacienteMedico() {
		return lstPerPacienteMedico;
	}

	public void setLstPerPacienteMedico(List<PerPacienteMedico> lstPerPacienteMedico) {
		this.lstPerPacienteMedico = lstPerPacienteMedico;
	}

	public PerPacienteMedico getObjPerPacienteMedico() {
		return objPerPacienteMedico;
	}

	public void setObjPerPacienteMedico(PerPacienteMedico objPerPacienteMedico) {
		this.objPerPacienteMedico = objPerPacienteMedico;
	}

	public Boolean getPnlConsulta() {
		return pnlConsulta;
	}

	public void setPnlConsulta(Boolean pnlConsulta) {
		this.pnlConsulta = pnlConsulta;
	}

	public PerConsulta getObjPerConsulta() {
		return objPerConsulta;
	}

	public void setObjPerConsulta(PerConsulta objPerConsulta) {
		this.objPerConsulta = objPerConsulta;
	}

	public PerReceta getObjPerReceta() {
		return objPerReceta;
	}

	public void setObjPerReceta(PerReceta objPerReceta) {
		this.objPerReceta = objPerReceta;
	}

	public static byte[] getReportPdf() {
		return reportPdf;
	}

	public static void setReportPdf(byte[] reportPdf) {
		FormAtencionMedica.reportPdf = reportPdf;
	}

}
