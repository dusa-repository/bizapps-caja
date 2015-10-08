package interfacedao.transacciones;

import java.sql.Timestamp;
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

	@Query(value = "select n.*, (select NomEmp from tabEmpresa where IDEmp =n.IDEmp) as empresa, "
			+ "(select NomCli from tabCliente where IDCli =n.IDCli) as cliente , "
			+ "(select NomBan from tabBancos where IDBan =n.IDBan) as banco  ,"
			+ "(select DesCon from tabConcepto where IDCon =d.IDCon) as concepto , "
			+ "d.TotRec from tabConceptos d, tabGeneral n where n.IDEmp like ?3 "
			+ "and n.IDCli like ?4 and n.FecRec between ?1 and ?2 and d.IDEmp like ?3 and d.IDCon like ?5"
			+ " and d.IDRec = n.IDRec order by n.IDEmp asc,n.IDCli asc,n.IDRec asc,d.IDCon asc", nativeQuery = true)
	List<Object[]> findByParameters(Timestamp desde, Timestamp hasta,
			String empresa, String cliente, String concepto);

}
