package hepl.sysdys2020.cart.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.EntityManager;

public interface CartRepository extends JpaRepository<CartItems, Integer> {

}
