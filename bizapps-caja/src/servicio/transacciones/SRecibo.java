package servicio.transacciones;

import java.util.List;

import interfacedao.transacciones.IReciboDAO;
import modelo.pk.TabGeneralPK;
import modelo.transacciones.TabGeneral;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service("SRecibo")
public class SRecibo {

	@Autowired
	private IReciboDAO reciboDAO;

	public List<TabGeneral> buscarTodosOrdenados() {
		return reciboDAO.findAll(new Sort(Direction.DESC, "fecRec", "estRec"));
	}

	public void guardar(TabGeneral f0013) {
		reciboDAO.saveAndFlush(f0013);
	}

	public int buscarUltimo(short s, Integer idEmpresa) {
		return reciboDAO.findMaxId(s, idEmpresa);
	}

	public TabGeneral buscar(TabGeneralPK clave) {
		return reciboDAO.findOne(clave);
	}
}
