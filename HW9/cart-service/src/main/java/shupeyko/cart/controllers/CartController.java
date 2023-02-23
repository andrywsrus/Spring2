package shupeyko.cart.controllers;


import shupeyko.api.dto.CartDto;
import shupeyko.cart.converters.CartConverter;
import shupeyko.cart.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/cart")
public class CartController {
    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping()
    public CartDto getCurrentCart(@RequestHeader (name = "username" ,required = false) String username ) {
        return cartConverter.entityToDto(cartService.getCurrentCart(Objects.requireNonNullElse(username, "sharedCart")));
    }

    @GetMapping("/add")
    public void addProductToCart(@RequestHeader (name = "username" ,required = false) String username, @RequestParam Long productId) {
        if (username != null) {
            cartService.add(username, productId);
            return;
        }
        cartService.add("sharedCart", productId);
    }

    @GetMapping("/remove/{productId}")
    public void removeProductFromCart(@RequestHeader (name = "username" ,required = false) String username, @PathVariable Long productId) {
        if (username != null) {
            cartService.delete(username, productId);
            return;
        }
        cartService.delete("sharedCart", productId);
    }

    @GetMapping("/clear")
    public void clearCart(@RequestHeader (name = "username" ,required = false) String username) {
        if (username != null) {
            cartService.clear(username);
            return;
        }
        cartService.clear("sharedCart");
    }

    @GetMapping("/change_quantity")
    public void changeQuantityProductInCart(@RequestHeader (name = "username" ,required = false) String username, @RequestParam Long productId, Integer delta) {
        if (username != null) {
            cartService.changeQuantityProduct(username, productId, delta);
            return;
        }
        cartService.changeQuantityProduct("sharedCart", productId, delta);
    }
}
