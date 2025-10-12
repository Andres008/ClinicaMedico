package ec.com.model.gestionUsuarios;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import ec.com.model.dao.entity.AutMenu;
import ec.com.model.dao.entity.AutPerfile;
import ec.com.model.dao.entity.AutRol;
import ec.com.model.dao.entity.AutRolPerfil;
import ec.com.model.dao.entity.AutUsuario;
import ec.com.model.dao.entity.PerPersona;
import ec.com.model.dao.manager.ManagerDAOSegbecom;

@Stateless
@LocalBean
public class ManagerGestionUsuarios {

	@EJB
	ManagerDAOSegbecom managerDAOSegbecom;

	@SuppressWarnings("unchecked")
	public List<AutUsuario> findAllAutUsuario() throws Exception {
		try {
			return managerDAOSegbecom.findAll(AutUsuario.class, "o.perPersona.apellidos ASC");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al buscar AutUsuario. " + e.getMessage());
		}

	}

	public void actualizarUsuario(AutUsuario objAutUsuario) throws Exception {
		try {
			managerDAOSegbecom.actualizar(objAutUsuario);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al actualizar AutUsaurio. " + e.getMessage());
		}

	}

	@SuppressWarnings("unchecked")
	public List<AutRol> findRolActivo() throws Exception {
		try {
			return managerDAOSegbecom.findWhere(AutRol.class, "o.estado='A'", "o.nombre ASC");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al buscar Roles Activos. " + e.getMessage());
		}
	}

	public void ingresarUsuario(AutUsuario objAutUsuario) throws Exception {
		try {
			managerDAOSegbecom.insertar(objAutUsuario);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al insertar AutUsuario. " + e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public List<AutRol> findAllRols() throws Exception {
		try {
			List<AutRol> lstRols = managerDAOSegbecom.findAll(AutRol.class, "o.nombre ASC");
			for (AutRol autRol : lstRols) {
				for (AutRolPerfil autRolPerfil : autRol.getAutRolPerfils())
					autRolPerfil.getAutPerfile().getNombre();
			}
			return lstRols;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al buscar AutRol. " + e.getMessage());
		}
	}

	public void actualizarRol(AutRol objAutRol) throws Exception {
		try {
			managerDAOSegbecom.actualizar(objAutRol);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al actualizar AutRol. " + e.getMessage());
		}
	}

	public void ingresarRol(AutRol objAutRol) throws Exception {
		try {
			managerDAOSegbecom.insertar(objAutRol);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al ingresar AutRol. " + e.getMessage());
		}
	}

	public List<AutMenu> findAllMenu() throws Exception {
		try {
			@SuppressWarnings("unchecked")
			List<AutMenu> lstAutMenus = managerDAOSegbecom.findAll(AutMenu.class, "o.nombre");
			for (AutMenu autMenu : lstAutMenus) {
				for (AutPerfile perfil : autMenu.getAutPerfiles())
					perfil.getNombre();
			}
			return lstAutMenus;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al buscar AutMenu. " + e.getMessage());
		}

	}

	@SuppressWarnings("unchecked")
	public List<AutMenu> findMenuActivo() throws Exception {
		List<AutMenu> lstAutMenus;
		try {
			lstAutMenus = managerDAOSegbecom.findWhere(AutMenu.class, "o.estado='A'", "o.nombre ASC");
			return lstAutMenus;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al buscar AutMenu." + e.getMessage());
		}

	}

	@SuppressWarnings("unchecked")
	public List<AutPerfile> findPerfilByMenu(AutMenu auxAutMenu) throws Exception {
		try {
			if (auxAutMenu == null)
				return null;
			return managerDAOSegbecom.findWhere(AutPerfile.class, "o.autMenu.codigo=" + auxAutMenu.getCodigo(),
					"o.nombre");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al burcar Perfil. " + e.getMessage());
		}
	}

	public void ingresarRolPerfil(AutRolPerfil objAutRolPerfil) throws Exception {
		try {
			managerDAOSegbecom.insertar(objAutRolPerfil);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al inserta AutRolPerfil. " + e.getMessage());
		}

	}

	public void actualizarRolPerfil(AutRolPerfil auxAutRolPerfil) throws Exception {
		try {
			managerDAOSegbecom.actualizar(auxAutRolPerfil);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al actualizar AutRolPerfil. " + e.getMessage());
		}

	}

	public void actualizarMenu(AutMenu objAutMenu) throws Exception {
		try {
			managerDAOSegbecom.actualizar(objAutMenu);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al actualizar.");
		}

	}

	public void actualizarPerfil(AutPerfile auxAutPerfile) throws Exception {
		try {
			managerDAOSegbecom.actualizar(auxAutPerfile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("Error al actualizar.");
		}

	}

	public void ingresarPerfil(AutPerfile objAutPerfile) throws Exception {
		try {
			managerDAOSegbecom.insertar(objAutPerfile);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al ingresar AutPeril. " + e.getMessage());
		}

	}

	public void ingresarMenu(AutMenu objAutMenu) throws Exception {
		try {
			managerDAOSegbecom.insertar(objAutMenu);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al ingresar Menú. " + e.getMessage());
		}

	}

	public PerPersona findPersonaByCedula(String cedula) throws Exception {
		try {
			return (PerPersona) managerDAOSegbecom.findById(PerPersona.class, cedula);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al buscar persona");
		}
	}

	public void ingresarPersona(PerPersona objPersona) throws Exception {
		try {
			managerDAOSegbecom.insertar(objPersona);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al ingresar datos de persona, " + e.getMessage());
		}
	}

}
