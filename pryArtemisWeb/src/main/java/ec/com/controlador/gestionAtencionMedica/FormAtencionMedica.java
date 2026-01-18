package ec.com.controlador.gestionAtencionMedica;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
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
import ec.com.model.dao.entity.PerCie;
import ec.com.model.dao.entity.PerConsulta;
import ec.com.model.dao.entity.PerExamenComplementario;
import ec.com.model.dao.entity.PerPaciente;
import ec.com.model.dao.entity.PerPacienteMedico;
import ec.com.model.dao.entity.PerPatologia;
import ec.com.model.dao.entity.PerPersona;
import ec.com.model.dao.entity.PerReceta;
import ec.com.model.dao.entity.PerTipoExamenComple;
import ec.com.model.dao.entity.PerTipoPatologia;
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
	private static byte[] reportPdf;
	private List<PerCie> lstPerCies;
	private List<PerTipoPatologia> lstTipoPatologias;
	private PerPatologia objPerPatologia;
	private PerExamenComplementario objPerExamenComplementario;
	private List<PerTipoExamenComple> lstPerTipoExamenComples;

	private static final long serialVersionUID = 1L;

	public FormAtencionMedica() {

	}

	@PostConstruct
	public void inicializarVariables() {
		try {
			formControlUsuariosPerfiles.setObjPersona(new PerPersona());
			lstPerCies = new ArrayList<PerCie>();
			lstTipoPatologias = managerAtencionMedica.findAllTipoPatologia();
			objPerConsulta = new PerConsulta();
			inicializarPatologia();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
		}

	}

	public void inicializarPatologia() {
		objPerPatologia = new PerPatologia();
		objPerPatologia.setPerTipoPatologia(new PerTipoPatologia());
	}

	public void inicializarPaneles() {
		pnlPacientes = false;
		blIbgreso = false;
		pnlDatosPaciente = false;
		pnlConsulta = false;
	}

	public void inicializarNuevaConsulta() {
		objPerConsulta = new PerConsulta();
		objPerConsulta.setPerExamenComplementarios(new ArrayList<PerExamenComplementario>());
		objPerConsulta.setPerRecetas(new ArrayList<PerReceta>());
		objPerConsulta.setPerCie(new PerCie());
		objPerReceta = new PerReceta();
		objPerConsulta.setFecha(new Date());
		objPerConsulta.setPerPacienteMedico(objPerPacienteMedico);
		PrimeFaces.current().executeInitScript("PF('infNuevaConsulta').show()");
		PrimeFaces.current().ajax().update(":frmNuevaConsulta");
	}

	public void nuevaConsulta() {
		try {
			lstPerCies = managerAtencionMedica.fillAllCIE();
			lstPerTipoExamenComples = managerAtencionMedica.findAllPerTipoExamenComples();
			objPerExamenComplementario = new PerExamenComplementario();
			objPerExamenComplementario.setPerTipoExamenComple(new PerTipoExamenComple());
			inicializarPaneles();
			pnlConsulta = true;
			PrimeFaces.current().executeInitScript("PF('infNuevaConsulta').hide()");
			PrimeFaces.current().ajax().update(":frmPrincipal");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
		}

	}

	public void agregarMedicamento() {
		objPerReceta.setPerConsulta(objPerConsulta);
		objPerConsulta.getPerRecetas().add(objPerReceta);
		objPerReceta = new PerReceta();
		PrimeFaces.current().ajax().update(":frmPrincipal");
	}

	public void agregarExamenComplementario() {
		try {
			objPerExamenComplementario.setPerConsulta(objPerConsulta);
			objPerExamenComplementario.setPerTipoExamenComple(managerAtencionMedica.findPerTipoExamenCompleById(
					objPerExamenComplementario.getPerTipoExamenComple().getCodigoTipoExamComple()));
			objPerConsulta.getPerExamenComplementarios().add(objPerExamenComplementario);
			objPerExamenComplementario = new PerExamenComplementario();
			objPerExamenComplementario.setPerTipoExamenComple(new PerTipoExamenComple());
			JSFUtil.crearMensajeINFO("Información ingresa corectamente");

		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
		}
		PrimeFaces.current().ajax().update(":frmPrincipal");
	}

	public void agregarPatologia() {
		try {
			PerPaciente paciente = managerAtencionMedica
					.findPacienteById(objPerPacienteMedico.getPerPaciente().getCodigoPaciente());
			objPerPatologia.setPerPaciente(paciente);
			paciente.getPerPatologias().add(objPerPatologia);
			managerAtencionMedica.actualizarPerPaciente(paciente);
			objPerPacienteMedico = managerAtencionMedica
					.findPerPacienteMedicoById(objPerPacienteMedico.getCodigoPacienteMedico());
			objPerPacienteMedico.setPerPaciente(
					managerAtencionMedica.findPacienteById(objPerPacienteMedico.getPerPaciente().getCodigoPaciente()));
			inicializarPatologia();
			JSFUtil.crearMensajeINFO("Información ingresa corectamente");
			PrimeFaces.current().ajax().update(":frmPrincipal:tvPaciente");
			PrimeFaces.current().ajax().update(":frmPrincipal:tblPatologia");
			PrimeFaces.current().ajax().update(":frmPrincipal:growl");
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.crearMensajeERROR("Error al registrar patología. " + e.getMessage());
		}

	}

	public void reporteReceta(PerConsulta objConsulta) {
		try {
			if (objConsulta.getCodigoConsulta() != 0) {
				objConsulta = managerAtencionMedica.findConsultaById(objConsulta.getCodigoConsulta());
			}

			String medicacion = "", indicacion = "";

			for (PerReceta receta : objConsulta.getPerRecetas()) {
				medicacion = medicacion + receta.getMedicamento() + "\n" + "\n";
				indicacion = indicacion + receta.getIndicacion() + "\n" + "\n";
			}
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			Map<String, Object> parametros = new HashMap<>();
			parametros.put("medicacion", medicacion);
			parametros.put("indicacion", indicacion);
			parametros.put("ciudad", "Ibarra");
			parametros.put("fecha", formato.format(objConsulta.getFecha()));
			parametros.put("paciente",
					objConsulta.getPerPacienteMedico().getPerPaciente().getPerPersona().getApellidos() + " "
							+ objConsulta.getPerPacienteMedico().getPerPaciente().getPerPersona().getNombres());
			parametros.put("edad",
					ModelUtil.calcularEdad(
							objConsulta.getPerPacienteMedico().getPerPaciente().getPerPersona().getFechaNacimiento())
							+ "");
			objConsulta.setPerCie(managerAtencionMedica.findCIEbyId(objConsulta.getPerCie().getCodigoCie()));
			parametros.put("cie",
					objConsulta.getPerCie().getCodigoCie() + " - " + objConsulta.getPerCie().getDescripcion());
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(objConsulta.getPerRecetas());
			File jasper = new File(beanLogin.getPathReportes() + "receta.jasper");
			JasperPrint jp = JasperFillManager.fillReport(jasper.getPath(), parametros, ds);

			reportPdf = JasperExportManager.exportReportToPdf(jp);

			
		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.crearMensajeERROR(e.getMessage());
			managerLog.generarLogErrorGeneral(beanLogin.getCredencial(), this.getClass(), "reporteReceta",
					e.getMessage());
			PrimeFaces.current().ajax().update(":frmPrincipal");
		}
	}
	
	public void cargarExamenes(PerConsulta objConsulta) {
		objPerConsulta= objConsulta;
		PrimeFaces.current().ajax().update(":frmExamenes");
		PrimeFaces.current().executeScript("PF('dlgExamenes').show()");
	}

	/**
	 * 
	 * MÉTODO QUE PERMITE DESCARGAR UN DOCUMENTO
	 * 
	 * 
	 * 
	 * @param String ruta
	 * 
	 */

	public StreamedContent getDescargarDocumentoDtoAdjunto(PerConsulta objConsulta) throws Exception {

		if (objConsulta.getCodigoConsulta() != 0) {
			objConsulta = managerAtencionMedica.findConsultaById(objConsulta.getCodigoConsulta());
		}

		String medicacion = "", indicacion = "";

		for (PerReceta receta : objConsulta.getPerRecetas()) {
			medicacion = medicacion + receta.getMedicamento() + "\n" + "\n";
			indicacion = indicacion + receta.getIndicacion() + "\n" + "\n";
		}
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("medicacion", medicacion);
		parametros.put("indicacion", indicacion);
		parametros.put("ciudad", "Ibarra");
		parametros.put("fecha", formato.format(objConsulta.getFecha()));
		parametros.put("paciente", objConsulta.getPerPacienteMedico().getPerPaciente().getPerPersona().getApellidos()
				+ " " + objConsulta.getPerPacienteMedico().getPerPaciente().getPerPersona().getNombres());
		parametros.put("edad", ModelUtil.calcularEdad(
				objConsulta.getPerPacienteMedico().getPerPaciente().getPerPersona().getFechaNacimiento()) + "");
		objConsulta.setPerCie(managerAtencionMedica.findCIEbyId(objConsulta.getPerCie().getCodigoCie()));
		parametros.put("cie",
				objConsulta.getPerCie().getCodigoCie() + " - " + objConsulta.getPerCie().getDescripcion());
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(objConsulta.getPerRecetas());
		File jasper = new File(beanLogin.getPathReportes() + "receta.jasper");
		JasperPrint jp = JasperFillManager.fillReport(jasper.getPath(), parametros, ds);

		reportPdf = JasperExportManager.exportReportToPdf(jp);

		StreamedContent download = new DefaultStreamedContent();

		InputStream input = new ByteArrayInputStream(reportPdf);
		download = DefaultStreamedContent.builder().contentType("application/pdf; charset=UTF-8")
				.name(objConsulta.getPerPacienteMedico().getPerPaciente().getPerPersona().getApellidos())
				.stream(() -> input).build();
		return download;

	}

	public void eliminarReceta(PerReceta objReceta) {
		objPerConsulta.getPerRecetas().remove(objReceta);
		PrimeFaces.current().ajax().update(":frmPrincipal");
	}

	public void guardarAtencion() {
		try {
			objPerConsulta.setFecha(new Date());
			managerAtencionMedica.insertPerConsulta(objPerConsulta);
			objPerPacienteMedico.setFechaUltimaConsulta(new Date());
			managerAtencionMedica.actualizarPerPacienteMedico(objPerPacienteMedico);
			inicializarPacientes();
			JSFUtil.crearMensajeINFO("Consulta finalizada correctamente.");
			PrimeFaces.current().ajax().update(":frmPrincipal");
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
			PrimeFaces.current().executeInitScript("PF('infUsuario').hide()");
			PrimeFaces.current().executeInitScript("PF('infPersonas').hide()");
			PrimeFaces.current().ajax().update(":frmPrincipal");
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
		PrimeFaces.current().executeInitScript("PF('infUsuario').show()");
		PrimeFaces.current().ajax().update(":frmUsuarios");
	}

	public void cargarDatosPaciente(PerPacienteMedico objPacienteAux) {
		try {
			inicializarPaneles();
			objPerPacienteMedico = managerAtencionMedica
					.findPerPacienteMedicoById(objPacienteAux.getCodigoPacienteMedico());
			objPerPacienteMedico.setPerPaciente(
					managerAtencionMedica.findPacienteById(objPerPacienteMedico.getPerPaciente().getCodigoPaciente()));
			pnlDatosPaciente = true;
			PrimeFaces.current().ajax().update(":frmPrincipal");
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
				objPerPaciente = managerAtencionMedica
						.findPacienteByCedula(formControlUsuariosPerfiles.getObjPersona().getCedula());
				if (objPerPaciente == null) {
					objPerPaciente = new PerPaciente();
					objPerPaciente.setPerPacienteMedicos(new ArrayList<PerPacienteMedico>());
					objPerPaciente.setPerPersona(formControlUsuariosPerfiles.getObjPersona());
					JSFUtil.crearMensajeINFO("Busqueda correcta.");
					PrimeFaces.current().ajax().update(":frmUsuarios");
				} else {
					JSFUtil.crearMensajeINFO("Busqueda correcta.");
					PrimeFaces.current().ajax().update(":frmUsuarios");
				}
			} else {
				ModelUtil.verificarCedulaEcuador(objPerPaciente.getPerPersona().getCedula());
				JSFUtil.crearMensajeWARN("Persona no existe.");
				formControlUsuariosPerfiles.setObjPersona(new PerPersona());
				formControlUsuariosPerfiles.getObjPersona().setCedula(objPerPaciente.getPerPersona().getCedula());
				PrimeFaces.current().executeInitScript("PF('infPersonas').show()");
				PrimeFaces.current().executeInitScript("PF('infUsuario').hide()");
				PrimeFaces.current().ajax().update(":frmPersonas");
			}

		} catch (Exception e) {
			e.printStackTrace();
			JSFUtil.crearMensajeERROR(e.getMessage());
		}
		PrimeFaces.current().ajax().update(":frmPrincipal:growl");
	}

	public StreamedContent getReport() {
		if (reportPdf == null || reportPdf.length == 0) {
			return null;
		}

		return DefaultStreamedContent.builder().contentType("application/pdf").name("miArchivo.pdf")
				.stream(() -> new ByteArrayInputStream(reportPdf)) // <-- NUEVO stream cada vez
				.build();
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

	public List<PerCie> getLstPerCies() {
		return lstPerCies;
	}

	public void setLstPerCies(List<PerCie> lstPerCies) {
		this.lstPerCies = lstPerCies;
	}

	public List<PerTipoPatologia> getLstTipoPatologias() {
		return lstTipoPatologias;
	}

	public void setLstTipoPatologias(List<PerTipoPatologia> lstTipoPatologias) {
		this.lstTipoPatologias = lstTipoPatologias;
	}

	public PerPatologia getObjPerPatologia() {
		return objPerPatologia;
	}

	public void setObjPerPatologia(PerPatologia objPerPatologia) {
		this.objPerPatologia = objPerPatologia;
	}

	public List<PerTipoExamenComple> getLstPerTipoExamenComples() {
		return lstPerTipoExamenComples;
	}

	public void setLstPerTipoExamenComples(List<PerTipoExamenComple> lstPerTipoExamenComples) {
		this.lstPerTipoExamenComples = lstPerTipoExamenComples;
	}

	public PerExamenComplementario getObjPerExamenComplementario() {
		return objPerExamenComplementario;
	}

	public void setObjPerExamenComplementario(PerExamenComplementario objPerExamenComplementario) {
		this.objPerExamenComplementario = objPerExamenComplementario;
	}

}
