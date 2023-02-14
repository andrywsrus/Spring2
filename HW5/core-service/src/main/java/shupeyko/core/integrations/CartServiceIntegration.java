package shupeyko.core.integrations;

import shupeyko.api.dto.CartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;



@Component
@RequiredArgsConstructor
public class CartServiceIntegration {
    private final RestTemplate restTemplate;

    @Value("${jwt.secret}")
    private String secret;

    public CartDto getCart() {
        return restTemplate.getForObject("http://localhost:8190/winter-carts/api/v1/cart" , CartDto.class);
    }

    public void clearCart(){
        ResponseEntity<Void> response = restTemplate.getForEntity("http://localhost:8190/winter-carts/api/v1/cart/clear",Void.class);
    }
}