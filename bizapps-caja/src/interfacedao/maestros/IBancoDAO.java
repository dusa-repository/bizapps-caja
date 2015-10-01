package interfacedao.maestros;

import modelo.maestros.TabBanco;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IBancoDAO extends JpaRepository<TabBanco, Integer> {

	@Query("select coalesce(max(IDBan),0) from TabBanco")
	int findMaxId();

}
