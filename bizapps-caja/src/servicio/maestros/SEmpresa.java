package servicio.maestros;

import java.util.List;

import interfacedao.maestros.IEmpresaDAO;
import modelo.maestros.TabEmpresa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service("SEmpresa")
public class SEmpresa {

	@Autowired
	private IEmpresaDAO empresaDAO;

	public List<TabEmpresa> buscarTodosOrdenados() {
		return empresaDAO.findAll(new Sort(Direction.ASC, "nomEmp", "rifEmp"));
	}

	public TabEmpresa buscar(Integer txtCCCRCDF0010) {
		return empresaDAO.findOne(txtCCCRCDF0010);
	}

	public void guardar(TabEmpresa f0013) {
		empresaDAO.saveAndFlush(f0013);
	}

	public void eliminarVarios(List<TabEmpresa> eliminarLista) {
		empresaDAO.delete(eliminarLista);
	}

	public void eliminarUno(Integer clave) {
		empresaDAO.delete(clave);
	}

	public int buscarUltimo() {
		return empresaDAO.findMaxId();
	}
}
