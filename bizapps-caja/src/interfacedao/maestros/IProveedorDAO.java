package interfacedao.maestros;

import modelo.maestros.TabProveedore;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IProveedorDAO extends JpaRepository<TabProveedore, Integer>{

	@Query("select coalesce(max(IDPro),0) from TabProveedore")
	int findMaxId();
}
