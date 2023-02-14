package shupeyko.dto;

import shupeyko.converters.ProductConverter;
import shupeyko.data.Product;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class Cart {
    private ProductConverter productConverter;
    private List<ProductInCartDto> products;
    private Integer totalCost;

    public Cart(ProductConverter productConverter) {
        products = new ArrayList<>();
        this.productConverter = productConverter;
    }

    private void recalculate() {
        totalCost = 0;
        for (ProductInCartDto product : products) {
            totalCost += product.getCost();
        }
    }

    public void addProduct(Product product) {
        for (ProductInCartDto prod : products) {
            if (prod.getProductId().equals(product.getId())) {
                prod.setQuantity(prod.getQuantity() + 1);
                prod.recalculateCost();
                recalculate();
                return;
            }
        }
        products.add(productConverter.entityToProductInCartDto(product, 1));
        recalculate();
    }


    public void changeQuantity(Long productId, Integer delta) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductId().equals(productId)) {
                if (products.get(i).getQuantity() + delta > 0) {
                    products.get(i).setQuantity(products.get(i).getQuantity() + delta);
                    products.get(i).recalculateCost();
                } else {
                    products.remove(i);
                }
                recalculate();
                break;
            }
        }
    }

    public void clearCart() {
        products.clear();
        recalculate();
    }

    public void removeProduct(Product product) {
        products.remove(products.stream().filter(prod -> prod.getProductId().equals(product.getId())).findFirst().orElseThrow(() -> new RuntimeException("Product not found")));
        recalculate();
    }

    public List<ProductInCartDto> getProducts() {
        return Collections.unmodifiableList(products);
    }
}