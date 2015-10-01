package servicio.maestros;

import java.util.List;

import interfacedao.maestros.IConceptoDAO;
import modelo.maestros.TabConcepto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service("SConcepto")
public class SConcepto {

	@Autowired
	private IConceptoDAO conceptoDAO;

	public List<TabConcepto> buscarTodosOrdenados() {
		return conceptoDAO.findAll(new Sort(Direction.ASC, "desCon"));
	}

	public TabConcepto buscar(Integer txtCCCRCDF0010) {
		return conceptoDAO.findOne(txtCCCRCDF0010);
	}

	public void guardar(TabConcepto f0013) {
		conceptoDAO.saveAndFlush(f0013);
	}

	public void eliminarVarios(List<TabConcepto> eliminarLista) {
		conceptoDAO.delete(eliminarLista);
	}

	public void eliminarUno(Integer clave) {
		conceptoDAO.delete(clave);
	}

	public int buscarUltimo() {
		return conceptoDAO.findMaxId();
	}
}
