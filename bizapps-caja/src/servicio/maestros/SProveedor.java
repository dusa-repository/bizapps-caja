package servicio.maestros;

import java.util.List;

import interfacedao.maestros.IProveedorDAO;
import modelo.maestros.TabProveedore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service("SProveedor")
public class SProveedor {

	@Autowired
	private IProveedorDAO provvedorDAO;

	public List<TabProveedore> buscarTodosOrdenados() {
		return provvedorDAO.findAll(new Sort(Direction.ASC, "nomPro", "rifPro"));
	}

	public TabProveedore buscar(Integer txtCCCRCDF0010) {
		return provvedorDAO.findOne(txtCCCRCDF0010);
	}

	public void guardar(TabProveedore f0013) {
		provvedorDAO.saveAndFlush(f0013);
	}

	public void eliminarVarios(List<TabProveedore> eliminarLista) {
		provvedorDAO.delete(eliminarLista);
	}

	public void eliminarUno(Integer clave) {
		provvedorDAO.delete(clave);
	}

	public int buscarUltimo() {
		return provvedorDAO.findMaxId();
	}
}
