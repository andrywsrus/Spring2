package shupeyko.cart.controllers;


import shupeyko.api.dto.CartDto;
import shupeyko.api.dto.StringResponse;
import shupeyko.cart.converters.CartConverter;
import shupeyko.cart.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/cart")
public class CartController {
    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping("/generate_uuid")
    public StringResponse generateUuid() {
        return new StringResponse(UUID.randomUUID().toString());
    }

    @GetMapping("/{uuid}") //
    public CartDto getCurrentCart(@PathVariable String uuid, @RequestHeader(name = "username", required = false) String username) {
        String targetUuid = cartService.getCartUuid(username, uuid);
        return cartConverter.entityToDto(cartService.getCurrentCart(targetUuid));
    }

    @GetMapping("/{uuid}/add")
    public void addProductToCart(@PathVariable String uuid, @RequestHeader(name = "username", required = false) String username, @RequestParam Long productId) {
        String targetUuid = cartService.getCartUuid(username, uuid);
        cartService.add(targetUuid, productId);
    }

    @GetMapping("/{uuid}/remove/{productId}")
    public void removeProductFromCart(@PathVariable String uuid, @RequestHeader(name = "username", required = false) String username, @PathVariable Long productId) {
        String targetUuid = cartService.getCartUuid(username, uuid);
        cartService.delete(targetUuid, productId);
    }

    @GetMapping("/{uuid}/clear")
    public void clearCart(@PathVariable String uuid, @RequestHeader(name = "username", required = false) String username) {
        String targetUuid = cartService.getCartUuid(username, uuid);
        cartService.clear(targetUuid);
    }

    @GetMapping("/{uuid}/merge")
    public void mergeCart(@PathVariable String uuid, @RequestHeader(name = "username") String username) {
        cartService.mergeGuestCartWithUserCart(uuid, username);
    }

    @GetMapping("/{uuid}/change_quantity")
    public void changeQuantityProductInCart(@PathVariable String uuid, @RequestHeader(name = "username", required = false) String username, @RequestParam Long productId, Integer delta) {
        String targetUuid = cartService.getCartUuid(username, uuid);
        cartService.changeQuantityProduct(targetUuid, productId, delta);
    }
}