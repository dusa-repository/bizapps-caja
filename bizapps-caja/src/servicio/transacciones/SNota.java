package servicio.transacciones;

import java.util.List;

import interfacedao.transacciones.INotaDAO;
import modelo.maestros.TabBanco;
import modelo.pk.TabNotasPK;
import modelo.transacciones.TabNotas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service("SNota")
public class SNota {

	@Autowired
	private INotaDAO notaDAO;

	public List<TabNotas> buscarTodosOrdenados() {
		return notaDAO.findAll(new Sort(Direction.DESC, "fecNot", "estNot"));
	}

	public void guardar(TabNotas f0013) {
		notaDAO.saveAndFlush(f0013);
	}

	public int buscarUltimo(String cate, Integer idEmpresa, String tipo) {
		return notaDAO.findMaxId(cate, idEmpresa, tipo);
	}

	public TabNotas buscar(TabNotasPK clave) {
		return notaDAO.findOne(clave);
	}

	public void limpiar(TabNotasPK clave) {
		notaDAO.delete(clave);
	}
}
