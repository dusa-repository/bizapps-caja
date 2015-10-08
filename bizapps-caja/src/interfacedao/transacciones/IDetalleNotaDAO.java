package interfacedao.transacciones;

import java.sql.Timestamp;
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

	@Query(value = "select d.*, (select nomPro from tabProveedores where IDPro =n.IDPro), n.UsuNot, n.FecNot from tabDetalles d, tabNotas n where n.TipNot like ?7"
			+ " and n.CatNot like ?6 and n.IDEmp like ?3 and n.IDPro like ?4"
			+ " and n.UsuNot like ?5 and n.FecNot between ?1 and ?2 and d.TipNot like ?7 "
			+ "and d.CatNot like ?6 and d.IDEmp like ?3 and d.IDNot = n.IDNot "
			+ "order by d.CatNot asc, d.TipNot asc,n.IDPro asc,d.IDNot asc,n.FecNot asc", nativeQuery = true)
	List<Object[]> findByParameters(Timestamp desde, Timestamp hasta,
			String empresa, String proveedor, String usuario, String clase,
			String tipo);

}
