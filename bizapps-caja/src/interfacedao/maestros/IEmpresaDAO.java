package interfacedao.maestros;

import modelo.maestros.TabEmpresa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IEmpresaDAO extends JpaRepository<TabEmpresa, Integer>{

	@Query("select coalesce(max(IDEmp),0) from TabEmpresa")
	int findMaxId();
}
