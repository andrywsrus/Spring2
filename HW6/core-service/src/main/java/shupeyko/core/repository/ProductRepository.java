package shupeyko.core.repository;

import shupeyko.core.data.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    List<Product> findAllByPriceGreaterThan(Integer price);

    List<Product> findAllByPriceLessThan(Integer price);

    List<Product> findAllByPriceBetween(Integer min, Integer max);
}