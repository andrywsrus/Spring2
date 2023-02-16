package shupeyko.core.repository;

import shupeyko.core.data.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUsername(String username);
}
