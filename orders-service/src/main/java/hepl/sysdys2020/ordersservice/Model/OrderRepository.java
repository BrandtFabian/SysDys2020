package hepl.sysdys2020.ordersservice.Model;

import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderItems, Integer> { }
