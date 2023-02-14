package shupeyko.core.repository;

import shupeyko.core.data.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Order, Long> {
    @Query("select o from  Order o where  o.user.username = ?1")
    List<Order> findAllByUsername(String username);
}
