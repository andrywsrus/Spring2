package shupeyko.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shupeyko.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
