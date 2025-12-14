package ec.com.model.gestionSistema;

import java.io.EOFException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import ec.com.model.dao.entity.AutMenu;
import ec.com.model.dao.entity.AutParametrosGenerale;
import ec.com.model.dao.entity.AutPerfile;
import ec.com.model.dao.entity.AutRol;
import ec.com.model.dao.entity.AutRolPerfil;
import ec.com.model.dao.entity.PerMedico;
import ec.com.model.dao.entity.VAutMenuRol;
import ec.com.model.dao.manager.ManagerDAOSegbecom;
import ec.com.model.modulos.util.ModelUtil;

/**
 * Session Bean implementation class ManagerAutorizacion
 */
@Stateless(mappedName = "managerGestionSistema")
@LocalBean
public class ManagerGestionSistema {

	@EJB
	ManagerDAOSegbecom managerDAOSegbecom;

	public ManagerGestionSistema() {
		// TODO Auto-generated constructor stub
	}

	public Credencial obtenerAcceso(long pIdUsuario, String pClave) throws Exception {
		PerMedico usuario = findByIdPerMedico(pIdUsuario);
		Credencial credencial = new Credencial();

		if (usuario == null)
			throw new Exception("Usuario no existe, verifique su código.");

		if (usuario.getEstado().equalsIgnoreCase("N"))
			throw new Exception("El usuario no está activo.");

		if (!pClave.equals(usuario.getClave()))
			throw new Exception("Verifique su contraseña.");

		// System.out.println(usuario.getApellidos()+" "+usuario.getNombres()+"
		// "+pIdUsuario);
		credencial = new Credencial();
		credencial.setObjPerMedico(usuario);
		// credencial.setCorreo(usuario.getGesPersona().getCorreo());
		credencial.setPrimerInicio(usuario.getPrimerInicio());

		credencial.setUsrFotografia(cargarFotografia(credencial.getObjPerMedico().getPerPersona().getCedula()));
		return credencial;

	}

	private byte[] cargarFotografia(String cedula) {
		try {
			String valorPath = buscarValorParametroNombre("PATH_FOTO_DEFAULT");
			File arch = new File(valorPath + cedula + ".png");
			if (!arch.exists())
				return Files.readAllBytes(Paths.get("/mnt/nfs/fotografiaUsuario/undefined.png"));
			return Files.readAllBytes(Paths.get(arch.getAbsolutePath()));
		} catch (Exception e) {
			return null;
		}
	}

	public PerMedico findByIdPerMedico(long idUsuario) throws Exception {
		PerMedico usuario = (PerMedico) managerDAOSegbecom.findById(PerMedico.class, idUsuario);
		return usuario;
	}

	@SuppressWarnings("unchecked")
	public List<VAutMenuRol> findVAutMenuRol(AutRol objAutRol) throws Exception {
		try {
			return managerDAOSegbecom.findWhere(VAutMenuRol.class, "o.idRol=" + objAutRol.getCodigo(), "o.orden ASC");
		} catch (Exception e) {
			throw new Exception("Error al buscar VAutMenuRol. " + e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public List<AutRolPerfil> findRolPerfilbyRol(AutRol objAutRol, VAutMenuRol vAutMenuRol) throws Exception {
		try {
			return managerDAOSegbecom.findWhere(AutRolPerfil.class,
					"o.autRol.id=" + objAutRol.getCodigo()
							+ " and o.estado='A' and o.autPerfile.estado='A' and o.autPerfile.autMenu.id="
							+ vAutMenuRol.getId(),
					"o.autPerfile.orden ASC");
		} catch (Exception e) {
			throw new Exception("Error al buscar Rol Perfil");
		}
	}

	public void ingresarAutRol(AutRol objAutRol) throws Exception {
		try {
			managerDAOSegbecom.insertar(objAutRol);
		} catch (Exception e) {
			throw new Exception("Error al ingresar Rol. " + e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public List<AutRol> buscarTodosAutRol() throws Exception {
		try {
			return managerDAOSegbecom.findAll(AutRol.class, "o.estado ASC, o.nombre ASC");
		} catch (Exception e) {
			throw new Exception("Error al obtener el listado de Roles. " + e.getMessage());
		}
	}

	public void actualizarObjeto(Object object) throws Exception {
		try {
			managerDAOSegbecom.actualizar(object);
		} catch (Exception e) {
			throw new Exception("Error al actualizar " + object.getClass());
		}

	}

	@SuppressWarnings("unchecked")
	public List<AutMenu> buscarTodosAutMenu() throws Exception {
		try {
			List<AutMenu> lstAutMenu = managerDAOSegbecom.findAll(AutMenu.class, "o.orden ASC");
			lstAutMenu.forEach(autMenu -> {
				autMenu.getAutPerfiles().forEach(perfil -> {
					perfil.getCodigo();
					perfil.getAutRolPerfils().forEach(rol -> {
						rol.getAutRol().getNombre();
					});
				});
			});
			return lstAutMenu;
		} catch (Exception e) {
			throw new Exception("Error al buscar listado de menu");
		}

	}

	public void insertarAutMenu(AutMenu objAutMenu) throws Exception {
		try {
			managerDAOSegbecom.insertar(objAutMenu);
		} catch (Exception e) {
			throw new Exception("Error al ingresar Menu. " + e.getMessage());
		}
	}

	public void ingresarAutPerfil(AutPerfile objPerfil) throws Exception {
		try {
			managerDAOSegbecom.insertar(objPerfil);
		} catch (Exception e) {
			throw new Exception("Error al ingresar Perfil. " + e.getMessage());
		}

	}

	public void ingresarAutRolPerfil(AutRolPerfil objAutRolPerfil) throws Exception {
		try {
			managerDAOSegbecom.insertar(objAutRolPerfil);
		} catch (Exception e) {
			throw new Exception("Error al ingresar Rol Perfil. " + e.getMessage());
		}

	}

	public String buscarValorParametroNombre(String nombre) throws Exception {
		try {
			@SuppressWarnings("unchecked")
			List<AutParametrosGenerale> lstParametro = managerDAOSegbecom.findWhere(AutParametrosGenerale.class,
					"o.nombre='" + nombre.toUpperCase() + "' and o.estado='A'", null);
			if (lstParametro.isEmpty())
				throw new Exception("Parametro no encontrado.");
			if (lstParametro.size() > 1)
				throw new Exception("Más de un parametro encontrado.");
			return lstParametro.get(0).getValor();
		} catch (Exception e) {
			throw new Exception("Error al buscar parametro, " + nombre + ". " + e.getMessage());

		}
	}

	public void eliminarAutRolPerfil(AutRolPerfil autRolPerfil) throws Exception {
		try {
			managerDAOSegbecom.eliminar(AutRolPerfil.class, autRolPerfil.getCodigo());
		} catch (Exception e) {
			throw new Exception("Error al eliminar AutRolPerfil");
		}

	}

	@SuppressWarnings("unchecked")
	public List<AutParametrosGenerale> buscarTodosParametros() throws Exception {
		try {
			return managerDAOSegbecom.findAll(AutParametrosGenerale.class, "o.estado ASC, o.nombre ASC");
		} catch (Exception e) {
			throw new Exception("Error al buscar Parametros.");
		}
	}

	public List<AutParametrosGenerale> buscarParametroByNombre(String nombre) throws Exception {
		@SuppressWarnings("unchecked")
		List<AutParametrosGenerale> lstParametros = managerDAOSegbecom.findWhere(AutParametrosGenerale.class,
				"o.nombre='" + nombre + "'", null);
		return lstParametros;
	}

	public void ingresarAutParametrosGenerale(AutParametrosGenerale objAutParametrosGenerale) throws Exception {
		try {
			managerDAOSegbecom.insertar(objAutParametrosGenerale);
		} catch (Exception e) {
			throw new Exception("Error al insertar parametro. " + e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public List<AutRol> buscarAutRolByTipoUsuario(long idTipoSocio) throws Exception {
		return managerDAOSegbecom.findWhere(AutRol.class, "o.usrTipoSocio.idTipoSocio=" + idTipoSocio, "o.nombre ASC");
	}

	public PerMedico findMedicoByCedulaUnic0(String idUsuario) throws Exception {
		try {
			@SuppressWarnings("unchecked")
			List<PerMedico> lstMedido = managerDAOSegbecom.findWhere(PerMedico.class,
					"o.perPersona.cedula='" + idUsuario + "'", null);
			;
			if (lstMedido.size() == 1)
				return lstMedido.get(0);
			throw new Exception("Existe mas de un registro para esta cédula. ");

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
}
