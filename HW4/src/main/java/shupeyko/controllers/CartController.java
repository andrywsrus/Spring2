package shupeyko.controllers;

import shupeyko.dto.Cart;
import shupeyko.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/cart")
public class CartController {
    private final CartService cartService;

    @GetMapping()
    public Cart getCurrentCart() {
        return cartService.getCurrentCart();
    }

    @GetMapping("/add")
    public void addProductToCart(@RequestParam Long productId) {
        cartService.add(productId);
    }

    @GetMapping("/remove/{productId}")
    public void removeProductFromCart(@PathVariable Long productId) {
        cartService.delete(productId);
    }
    @GetMapping("/clear")
    public void clearCart() {
        cartService.clear();
    }

    @GetMapping("/change_quantity")
    public void changeQuantityProductInCart(@RequestParam Long productId, Integer delta) {
        cartService.changeQuantityProduct(productId,delta);
    }
}
