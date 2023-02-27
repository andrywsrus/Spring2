package shupeyko.cart.services;

import shupeyko.api.dto.ProductDto;
import shupeyko.cart.integrations.ProductServiceIntegration;
import shupeyko.cart.model.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CartService {
    private Map<String, Cart> carts;

    private final ProductServiceIntegration productServiceIntegration;

    @PostConstruct
    public void init() {
        carts = new HashMap<>();

    }

    public Cart getCurrentCart(String username) {
        if (!carts.containsKey(username)) {
            carts.put(username, new Cart());
        }
        return carts.get(username);
    }

    public void add(String username, Long productId) {
        ProductDto product = productServiceIntegration.findById(productId);
        getCurrentCart(username).addProduct(product);

    }

    public void delete(String username, Long productId) {
        ProductDto product = productServiceIntegration.findById(productId);
        getCurrentCart(username).removeProduct(product);
    }

    public void clear(String username) {
        getCurrentCart(username).clearCart();
    }

    public void changeQuantityProduct(String username, Long productId, Integer delta) {
        getCurrentCart(username).changeQuantity(productId, delta);
    }
}
