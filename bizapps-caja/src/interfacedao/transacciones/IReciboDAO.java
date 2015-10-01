package interfacedao.transacciones;

import modelo.pk.TabGeneralPK;
import modelo.transacciones.TabGeneral;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IReciboDAO extends JpaRepository<TabGeneral, TabGeneralPK> {

	@Query("select coalesce(max(id.IDRec),0) from TabGeneral where id.ano=?1 and id.IDEmp=?2")
	int findMaxId(short s, Integer idEmpresa);

}
