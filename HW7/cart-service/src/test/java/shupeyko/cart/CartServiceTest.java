package shupeyko.cart;

import shupeyko.api.dto.ProductDto;
import shupeyko.cart.integrations.ProductServiceIntegration;
import shupeyko.cart.services.CartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.math.BigDecimal;

@SpringBootTest
public class CartServiceTest {

    @Autowired
    private CartService cartService;

    @MockBean
    private ProductServiceIntegration productServiceIntegration;

    @Test
    public void addToCartTest() {
        ProductDto product = new ProductDto();
        product.setId(5L);
        product.setTitle("Tomato");
        product.setPrice(BigDecimal.valueOf(10.0));
        ProductDto productApple = new ProductDto();
        productApple.setId(1L);
        productApple.setTitle("Apple");
        productApple.setPrice(BigDecimal.valueOf(20.0));
        Mockito.doReturn(product).when(productServiceIntegration).findById(5L);
        Mockito.doReturn(productApple).when(productServiceIntegration).findById(1L);
        cartService.add(5L);
        cartService.add(5L);
        cartService.add(1L);
        Mockito.verify(productServiceIntegration, Mockito.times(2)).findById(ArgumentMatchers.eq(5L));
        Assertions.assertEquals(2, cartService.getCurrentCart().getProducts().size());
        Assertions.assertEquals(BigDecimal.valueOf(40.0), cartService.getCurrentCart().getTotalPrice());
    }

    @Test
    public void changeQuantityProductTest(){
        cartService.changeQuantityProduct(1L,5);
        Assertions.assertEquals(6,cartService.getCurrentCart().getProducts()
                .stream()
                .filter(prod -> prod.getProductId().equals(1L))
                .findFirst().get().getQuantity());
        Assertions.assertEquals(BigDecimal.valueOf(140.0),cartService.getCurrentCart().getTotalPrice());
    }

    @Test
    public void deleteTest() {
        ProductDto product = new ProductDto();
        product.setId(5L);
        product.setTitle("Tomato");
        product.setPrice(BigDecimal.valueOf(10.0));
        Mockito.doReturn(product).when(productServiceIntegration).findById(5L);
        cartService.delete(5L);
        Assertions.assertEquals(1, cartService.getCurrentCart().getProducts().size());
        Assertions.assertEquals(BigDecimal.valueOf(120.0),cartService.getCurrentCart().getTotalPrice());
    }
}
