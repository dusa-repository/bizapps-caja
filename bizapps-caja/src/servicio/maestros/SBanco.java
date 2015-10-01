package servicio.maestros;

import java.util.List;

import interfacedao.maestros.IBancoDAO;
import modelo.maestros.TabBanco;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service("SBanco")
public class SBanco {

	@Autowired
	private IBancoDAO bancoDAO;

	public List<TabBanco> buscarTodosOrdenados() {
		return bancoDAO.findAll(new Sort(Direction.ASC, "nomBan"));
	}

	public TabBanco buscar(Integer txtCCCRCDF0010) {
		return bancoDAO.findOne(txtCCCRCDF0010);
	}

	public void guardar(TabBanco f0013) {
		bancoDAO.saveAndFlush(f0013);
	}

	public void eliminarVarios(List<TabBanco> eliminarLista) {
		bancoDAO.delete(eliminarLista);
	}

	public void eliminarUno(Integer clave) {
		bancoDAO.delete(clave);
	}

	public int buscarUltimo() {
		return bancoDAO.findMaxId();
	}
}
