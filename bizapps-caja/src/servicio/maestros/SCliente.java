package servicio.maestros;

import java.util.List;

import interfacedao.maestros.IClienteDAO;
import modelo.maestros.TabCliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service("SCliente")
public class SCliente {

	@Autowired
	private IClienteDAO clienteDAO;

	public List<TabCliente> buscarTodosOrdenados() {
		return clienteDAO.findAll(new Sort(Direction.ASC, "NomCli"));
	}

	public TabCliente buscar(Integer txtCCCRCDF0010) {
		return clienteDAO.findOne(txtCCCRCDF0010);
	}

	public void guardar(TabCliente f0013) {
		clienteDAO.saveAndFlush(f0013);
	}

	public void eliminarVarios(List<TabCliente> eliminarLista) {
		clienteDAO.delete(eliminarLista);
	}

	public void eliminarUno(Integer clave) {
		clienteDAO.delete(clave);
	}

	public int buscarUltimo() {
		return clienteDAO.findMaxId();
	}
}
