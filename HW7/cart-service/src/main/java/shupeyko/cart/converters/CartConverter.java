package shupeyko.cart.converters;

import shupeyko.api.dto.CartDto;
import shupeyko.cart.model.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CartConverter {
    private final CartItemConverter cartItemConverter;
    public CartDto entityToDto(Cart cart){
        CartDto cartDto = new CartDto();
        cartDto.setTotalPrice(cart.getTotalPrice());
        cartDto.setProducts(cart.getProducts().stream().map(cartItemConverter::entityToDto).collect(Collectors.toList()));
        return cartDto;
    }
}
