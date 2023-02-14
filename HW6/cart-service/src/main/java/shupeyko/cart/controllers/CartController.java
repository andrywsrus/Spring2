package shupeyko.cart.controllers;


import shupeyko.api.dto.CartDto;
import shupeyko.cart.converters.CartConverter;
import shupeyko.cart.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/cart")
public class CartController {
    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping()
    public CartDto getCurrentCart() {
        return cartConverter.entityToDto(cartService.getCurrentCart());
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
        cartService.changeQuantityProduct(productId, delta);
    }
}
