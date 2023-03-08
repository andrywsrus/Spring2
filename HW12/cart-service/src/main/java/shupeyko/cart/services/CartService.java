package shupeyko.cart.services;

import shupeyko.api.dto.ProductDto;
import shupeyko.cart.integrations.ProductServiceIntegration;
import shupeyko.cart.model.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productServiceIntegration;
    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${cart-service.cart-prefix}")
    private String cartPrefix;


    public Cart getCurrentCart(String uuid) {
        String targetUuid = cartPrefix + uuid;
        if (Boolean.FALSE.equals(redisTemplate.hasKey(targetUuid))) {
            redisTemplate.opsForValue().set(targetUuid, new Cart());
        }
        return (Cart) redisTemplate.opsForValue().get(targetUuid);
    }

    public void add(String uuid, Long productId) {
        ProductDto product = productServiceIntegration.findById(productId);
        execute(uuid, cart -> cart.addProduct(product));
    }

    public void delete(String uuid, Long productId) {
        execute(uuid, cart -> cart.removeProduct(productId));
    }

    public void clear(String uuid) {
        execute(uuid, Cart::clearCart);
    }

    public void changeQuantityProduct(String uuid, Long productId, Integer delta) {
        execute(uuid, cart -> cart.changeQuantity(productId, delta));
    }

    private void execute(String uuid, Consumer<Cart> operation) {
        Cart cart = getCurrentCart(uuid);
        operation.accept(cart);
        updateCart(uuid,cart);
    }

    public void mergeGuestCartWithUserCart(String uuid, String username){
        Cart cartGuest = getCurrentCart(uuid);
        Cart cartUser = getCurrentCart(username);
        cartUser.mergeAndClearMergedCart(cartGuest);
        updateCart(uuid,cartGuest);
        updateCart(username,cartUser);
    }
    public String getCartUuid(String username, String uuid) {
        if (username != null) {
            return username;
        }
        return uuid;
    }
    private void updateCart(String uuid, Cart cart){
        redisTemplate.opsForValue().set(cartPrefix+uuid,cart);
    }
}
