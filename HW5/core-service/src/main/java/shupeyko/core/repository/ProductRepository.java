package shupeyko.core.repository;

import shupeyko.core.data.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    @Modifying
    @Query("update Product p set p.price = p.price + :price where p.id= :id")
    void changePriceById(@Param(value = "id") Long id, @Param(value = "price") Integer price);

    List<Product> findAllByPriceGreaterThan(Integer price);

    List<Product> findAllByPriceLessThan(Integer price);

    List<Product> findAllByPriceBetween(Integer min, Integer max);
}