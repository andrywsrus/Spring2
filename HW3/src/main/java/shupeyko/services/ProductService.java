package shupeyko.services;

import shupeyko.aspect.Timer;
import shupeyko.data.Product;
import shupeyko.repository.ProductRepository;
import shupeyko.repository.specifications.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Timer
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductSpecification productSpecification;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public void changeCostById(Long productId, Integer delta) {
        productRepository.changeCostById(productId, delta);
    }

    public Page<Product> find(Integer minPrice, Integer maxPrice, String partTitle, Integer page) {
        Specification<Product> specification = Specification.where(null);
        if (minPrice != null) {
            specification = specification.and(productSpecification.costGreaterOrEqualsThan(minPrice));
        }
        if (maxPrice != null) {
            specification = specification.and(productSpecification.costLessOrEqualsThan(maxPrice));
        }
        if (partTitle != null) {
            specification = specification.and(productSpecification.titleLike(partTitle));
        }
        return productRepository.findAll(specification, PageRequest.of(page - 1, 50));
    }

    public List<Product> moreThanMinCost(Integer minCost) {
        return productRepository.findAllByCostGreaterThan(minCost);
    }

    public List<Product> lessThanMaxCost(Integer maxCost) {
        return productRepository.findAllByCostLessThan(maxCost);
    }

    public List<Product> findAllByCostBetween(Integer minCost, Integer maxCost) {
        return productRepository.findAllByCostBetween(minCost, maxCost);
    }
}