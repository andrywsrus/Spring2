package shupeyko.core.converters;


import shupeyko.api.dto.CartItemDto;
import shupeyko.core.data.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemConverter {

    public CartItemDto entityToDto(OrderItem orderItem) {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setProductId(orderItem.getProduct().getId());
        cartItemDto.setProductTitle(orderItem.getProduct().getTitle());
        cartItemDto.setQuantity(orderItem.getQuantity());
        cartItemDto.setPricePerProduct(orderItem.getPricePerProduct());
        cartItemDto.setPrice(orderItem.getPrice());
        return cartItemDto;
    }
}
