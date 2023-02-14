package shupeyko.cart.services;


import shupeyko.api.ResourceNotFoundException;
import shupeyko.api.dto.ProductDto;
import shupeyko.cart.integrations.ProductServiceIntegration;
import shupeyko.cart.model.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CartService {
    private Cart tempCart;

    private final ProductServiceIntegration productServiceIntegration;

    @PostConstruct
    public void init() {
        tempCart = new Cart();
    }

    public Cart getCurrentCart() {
        return tempCart;
    }

    public void add(Long productId) {
        ProductDto product = productServiceIntegration.getProductById(productId).orElseThrow(() -> new ResourceNotFoundException("Неудается добавить продукт с id:" + productId + " в корзину. Продукт не найден"));
        tempCart.addProduct(product);
    }

    public void delete(Long productId) {
        ProductDto product = productServiceIntegration.getProductById(productId).orElseThrow(() -> new ResourceNotFoundException("Неудается добавить продукт с id:" + productId + " в корзину. Продукт не найден"));
        tempCart.removeProduct(product);
    }

    public void clear(){
        tempCart.clearCart();
    }

    public void changeQuantityProduct(Long productId, Integer delta){
        tempCart.changeQuantity(productId, delta);
    }
}
