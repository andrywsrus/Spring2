package shupeyko.core.services;

import shupeyko.core.repository.specifications.ProductSpecification;
import shupeyko.core.data.Product;
import shupeyko.core.repository.ProductRepository;
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
    public void changePriceById(Long productId, Integer delta) {
        productRepository.changePriceById(productId, delta);
    }

    public Page<Product> find(Integer minPrice, Integer maxPrice, String partTitle, Integer page) {
        Specification<Product> specification = Specification.where(null);
        if (minPrice != null) {
            specification = specification.and(productSpecification.priceGreaterOrEqualsThan(minPrice));
        }
        if (maxPrice != null) {
            specification = specification.and(productSpecification.priceLessOrEqualsThan(maxPrice));
        }
        if (partTitle != null) {
            specification = specification.and(productSpecification.titleLike(partTitle));
        }
        return productRepository.findAll(specification, PageRequest.of(page - 1, 50));
    }

    public List<Product> moreThanMinPrice(Integer minPrice) {
        return productRepository.findAllByPriceGreaterThan(minPrice);
    }

    public List<Product> lessThanMaxPrice(Integer maxPrice) {
        return productRepository.findAllByPriceLessThan(maxPrice);
    }

    public List<Product> findAllByPriceBetween(Integer minPrice, Integer maxPrice) {
        return productRepository.findAllByPriceBetween(minPrice, maxPrice);
    }
}