package interfacedao.maestros;

import modelo.maestros.TabConcepto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IConceptoDAO extends JpaRepository<TabConcepto, Integer>{

	@Query("select coalesce(max(IDCon),0) from TabConcepto")
	int findMaxId();
}
