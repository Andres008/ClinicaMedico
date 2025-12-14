package ec.com.model.gestionAtencionMedica;

import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import ec.com.model.dao.entity.PerConsulta;
import ec.com.model.dao.entity.PerMedico;
import ec.com.model.dao.entity.PerPaciente;
import ec.com.model.dao.entity.PerPacienteMedico;
import ec.com.model.dao.entity.PerPatologia;
import ec.com.model.dao.entity.PerReceta;
import ec.com.model.dao.manager.ManagerDAOSegbecom;

/**
 * Session Bean implementation class ManagerAtencionMedica
 */
@Stateless
@LocalBean
public class ManagerAtencionMedica {

	/**
	 * Default constructor.
	 */
	@EJB
	ManagerDAOSegbecom managerDAOSegbecom;

	public ManagerAtencionMedica() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public List<PerPaciente> findAllPacientes() throws Exception {
		try {
			return managerDAOSegbecom.findAll(PerPaciente.class, "o.perPersona.apellidos , o.perPersona.nombres ASC");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al buscar Pacientes. " + e.getMessage());
		}
	}

	public void insertPerPaciente(PerPaciente objPerPaciente) throws Exception {
		try {
			managerDAOSegbecom.insertar(objPerPaciente);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getCause().getCause().getCause().getCause().getMessage().contains("llave duplicada"));
			if (e.getCause().getCause().getCause().getCause().getMessage().contains("llave duplicada"))
				throw new Exception("Paciente ya se encuentra registrado.");
			throw new Exception("Error al ingresar PerPaciente. " + e.getMessage());
		}

	}

	@SuppressWarnings("unchecked")
	public List<PerPacienteMedico> findAllPacientesByMedico(PerMedico objPerMedico) throws Exception {
		try {
			List<PerPacienteMedico> lstList = managerDAOSegbecom.findWhere(PerPacienteMedico.class,
					"o.perMedico.codigoMedico=" + objPerMedico.getCodigoMedico(),
					"o.perPaciente.perPersona.apellidos ASC");
			lstList.forEach(paciente -> paciente.getPerPaciente().getCodigoPaciente());
			return lstList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al cargar PerPacienteMedico");
		}
	}

	public void actualizarPerPaciente(PerPaciente objPerPaciente) throws Exception {
		try {
			managerDAOSegbecom.actualizar(objPerPaciente);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al guardar. " + e.getMessage());
		}

	}

	public PerPaciente findPacienteById(long codigoPaciente) throws Exception {
		try {
			PerPaciente objPaciente = (PerPaciente) managerDAOSegbecom.findById(PerPaciente.class, codigoPaciente);
			if (objPaciente != null)
				for (PerPacienteMedico iterable_element : objPaciente.getPerPacienteMedicos()) {
					iterable_element.getCodigoPacienteMedico();
					for (PerConsulta consulta : iterable_element.getPerConsultas()) {
						for (PerReceta receta : consulta.getPerRecetas()) {
							receta.getMedicamento();
						}
					}
				}
			for (PerPatologia iterable_element : objPaciente.getPerPatologias()) {
				iterable_element.getCodigoPatologia();
			}
			return objPaciente;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al buscar PerPaciente. " + e.getMessage());
		}
	}

	public PerPacienteMedico findPerPacienteMedicoById(long codigoPacienteMedico) throws Exception {
		try {
			PerPacienteMedico objPerPacienteMedico = (PerPacienteMedico) managerDAOSegbecom
					.findById(PerPacienteMedico.class, codigoPacienteMedico);
			if (objPerPacienteMedico != null) {
				for (PerConsulta iterable_element : objPerPacienteMedico.getPerConsultas()) {
					iterable_element.getCodigoConsulta();
				}
			}
			return objPerPacienteMedico;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al buscar PerPacienteMedico. " + e.getMessage());
		}
	}

	public void insertPerConsulta(PerConsulta objPerConsulta) throws Exception {
		try {
			managerDAOSegbecom.insertar(objPerConsulta);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al insertar objPerConsulta. " + e.getMessage());
		}

	}

	public PerConsulta findConsultaById(long codigoConsulta) throws Exception {
		try {
			PerConsulta consulta = (PerConsulta) managerDAOSegbecom.findById(PerConsulta.class, codigoConsulta);
			for (PerReceta iterable_element : consulta.getPerRecetas()) {
				iterable_element.getIndicacion();
			}
			return consulta;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al buscar PerConsulta. " + e.getMessage());
		}
	}

}
