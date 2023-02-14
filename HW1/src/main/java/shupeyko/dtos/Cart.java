package shupeyko.dtos;

import org.springframework.stereotype.Component;
import shupeyko.entities.Product;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class Cart {
    private List<Product> productList;

    @PostConstruct
    private void init() {
        productList = new ArrayList<>();
    }

    public List<Product> getProductList() {
        return Collections.unmodifiableList(productList);
    }

    public List<Product> addToCart(Product product){
        productList.add(product);
        return getProductList();
    }
    public void deleteProduct(Optional<Product> product) {
        productList.remove(product.get());
    }
}
