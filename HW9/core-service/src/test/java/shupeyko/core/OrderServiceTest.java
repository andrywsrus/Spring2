package shupeyko.core;

import shupeyko.api.dto.CartDto;
import shupeyko.api.dto.CartItemDto;
import shupeyko.api.dto.OrderDetailsDto;
import shupeyko.core.data.Order;
import shupeyko.core.data.Product;
import shupeyko.core.integrations.CartServiceIntegration;
import shupeyko.core.repository.OrdersRepository;
import shupeyko.core.services.OrderService;
import shupeyko.core.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@SpringBootTest (classes = OrderService.class)

public class OrderServiceTest {
    @Autowired
    private OrderService orderService;
    @MockBean
    private CartServiceIntegration cartServiceIntegration;
    @MockBean
    private ProductService productsService;
    @MockBean
    private OrdersRepository ordersRepository;

    @Test
    public void createOrderTest() {
        CartDto cartDto = new CartDto();
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setProductId(10L);
        cartItemDto.setProductTitle("Juice");
        cartItemDto.setQuantity(5);
        cartItemDto.setPricePerProduct(BigDecimal.valueOf(5.00));
        cartItemDto.setPrice(BigDecimal.valueOf(50.00));
        cartDto.setTotalPrice(BigDecimal.valueOf(50.00));
        cartDto.setProducts(List.of(cartItemDto));
        Mockito.doReturn(cartDto).when(cartServiceIntegration).getCart(null);
        Product product = new Product(10L, "Juice", BigDecimal.valueOf(10.0));
        Mockito.doReturn(Optional.of(product)).when(productsService).findById(10L);
        OrderDetailsDto orderDetailsDto = new OrderDetailsDto("Samara", "123 45 67");
        Order order = orderService.createOrder("User", orderDetailsDto);
        Assertions.assertEquals(order.getTotalPrice(), BigDecimal.valueOf(50.00));
        Mockito.verify(ordersRepository, Mockito.times(1)).save(ArgumentMatchers.eq(order));
    }
}
