package servicio.transacciones;

import java.util.List;

import interfacedao.transacciones.IDetalleNotaDAO;
import modelo.pk.TabNotasPK;
import modelo.transacciones.TabDetalles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SDetalleNota")
public class SDetalleNota {

	@Autowired
	private IDetalleNotaDAO detalleNOTA;

	public void limpiar(TabNotasPK clave) {
		List<TabDetalles> lista = detalleNOTA
				.findByIdIDNotAndIdtipNotAndIdcatNotAndIdIDEmp(
						clave.getIDNot(), clave.getTipNot(), clave.getCatNot(),
						clave.getIDEmp());
		if (!lista.isEmpty())
			detalleNOTA.delete(lista);
	}

	public void guardar(TabDetalles detalle) {
		detalleNOTA.save(detalle);
	}

	public List<TabDetalles> buscar(TabNotasPK clave) {
		return detalleNOTA.findByIdIDNotAndIdtipNotAndIdcatNotAndIdIDEmp(
				clave.getIDNot(), clave.getTipNot(), clave.getCatNot(),
				clave.getIDEmp());
	}
}
