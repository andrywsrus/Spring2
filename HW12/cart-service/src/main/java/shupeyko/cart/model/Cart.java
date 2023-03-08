package shupeyko.cart.model;


import shupeyko.api.dto.ProductDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class Cart {

    private List<CartItem> products;
    private BigDecimal totalPrice;

    public Cart() {
        products = new ArrayList<>();
    }

    private void recalculate() {
        totalPrice = BigDecimal.valueOf(0);
        for (CartItem product : products) {
            totalPrice = totalPrice.add(product.getPrice());
        }
    }

    public void addProduct(ProductDto product) {
        for (CartItem prod : products) {
            if (prod.getProductId().equals(product.getId())) {
                prod.changeQuantity(1);
                recalculate();
                return;
            }
        }
        products.add(new CartItem(product.getId(), product.getTitle(), product.getPrice(), 1));
        recalculate();
    }

    public void changeQuantity(Long productId, Integer delta) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductId().equals(productId)) {
                if (products.get(i).getQuantity() + delta > 0) {
                    products.get(i).changeQuantity(delta);
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

    public void removeProduct(Long productId) {
        products.remove(products.stream().filter(prod -> prod.getProductId().equals(productId)).findFirst().orElseThrow(() -> new RuntimeException("Product not found")));
        recalculate();
    }

    public void mergeAndClearMergedCart(Cart mergedCart) {
        for (int i = 0; i < mergedCart.products.size(); i++) {
            boolean merge = false;
            for (int j = 0; j < products.size(); j++) {
                if (products.get(j).getProductId().equals(mergedCart.products.get(i).getProductId())) {
                    products.get(j).changeQuantity(mergedCart.products.get(i).getQuantity());
                    merge = true;
                    break;
                }
            }
            if (!merge) {
                products.add(mergedCart.products.get(i));
            }
        }
        recalculate();
        mergedCart.clearCart();
    }
}