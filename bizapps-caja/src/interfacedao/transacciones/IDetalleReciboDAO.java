package interfacedao.transacciones;

import java.util.List;

import modelo.pk.TabConceptosPK;
import modelo.transacciones.TabConceptos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IDetalleReciboDAO extends
		JpaRepository<TabConceptos, TabConceptosPK> {

	@Query("select t from TabConceptos t where t.id.ano = ?1 and t.id.IDEmp=?2 and t.id.IDRec=?3")
	List<TabConceptos> findByIdIDNotAndIdtipNotAndIdcatNotAndIdIDEmp(short ano,
			int idEmp, int idRec);

}
