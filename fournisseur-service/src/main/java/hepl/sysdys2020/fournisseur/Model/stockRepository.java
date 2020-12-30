package hepl.sysdys2020.fournisseur.Model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface stockRepository extends JpaRepository<stockItems, Integer> { }
