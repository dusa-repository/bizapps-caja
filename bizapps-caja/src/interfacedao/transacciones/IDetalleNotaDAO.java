package interfacedao.transacciones;

import java.util.List;

import modelo.pk.TabDetallesPK;
import modelo.transacciones.TabDetalles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IDetalleNotaDAO extends
		JpaRepository<TabDetalles, TabDetallesPK> {

	@Query("select t from TabDetalles t where t.id.IDNot = ?1 and t.id.tipNot=?2 and t.id.catNot=?3 and t.id.IDEmp=?4")
	List<TabDetalles> findByIdIDNotAndIdtipNotAndIdcatNotAndIdIDEmp(
			Integer idNot, String tipNot, String catNot, Integer idEmp);

}
