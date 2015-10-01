package interfacedao.transacciones;

import modelo.pk.TabNotasPK;
import modelo.transacciones.TabNotas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface INotaDAO extends JpaRepository<TabNotas, TabNotasPK> {

	@Query("select coalesce(max(id.IDNot),0) from TabNotas where id.catNot=?1 and id.IDEmp=?2 and id.tipNot=?3")
	int findMaxId(String cate, Integer idEmpresa, String tipo);

}
