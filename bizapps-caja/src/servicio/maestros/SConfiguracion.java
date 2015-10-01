package servicio.maestros;

import interfacedao.maestros.IConfiguracionDAO;
import modelo.maestros.TabConfigur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SConfiguracion")
public class SConfiguracion {

	@Autowired
	private IConfiguracionDAO configuracionDAO;

	public void guardar(TabConfigur confi) {
		configuracionDAO.save(confi);
	}

	public TabConfigur buscar(int i) {
		return configuracionDAO.findOne(i);
	}
}
