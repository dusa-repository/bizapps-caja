package servicio.transacciones;

import java.util.List;

import interfacedao.transacciones.IDetalleReciboDAO;
import modelo.pk.TabGeneralPK;
import modelo.transacciones.TabConceptos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SDetalleRecibo")
public class SDetalleRecibo {

	@Autowired
	private IDetalleReciboDAO detalleReciboDAO;

	public List<TabConceptos> buscar(TabGeneralPK clave) {
		return detalleReciboDAO.findByIdIDNotAndIdtipNotAndIdcatNotAndIdIDEmp(
				clave.getAno(), clave.getIDEmp(), clave.getIDRec());
	}

	public void limpiar(TabGeneralPK clave) {
		List<TabConceptos> lista = detalleReciboDAO
				.findByIdIDNotAndIdtipNotAndIdcatNotAndIdIDEmp(clave.getAno(),
						clave.getIDEmp(), clave.getIDRec());
		if (!lista.isEmpty())
			detalleReciboDAO.delete(lista);
	}

	public void guardar(TabConceptos detalle) {
		detalleReciboDAO.save(detalle);
	}
}
