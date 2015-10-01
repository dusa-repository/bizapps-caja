package interfacedao.maestros;

import modelo.maestros.TabCliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IClienteDAO extends JpaRepository<TabCliente, Integer> {
	
	@Query("select coalesce(max(IDCli),0) from TabCliente")
	int findMaxId();
}
