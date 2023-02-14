package shupeyko.services;

import shupeyko.converters.ProductConverter;
import shupeyko.data.Product;
import shupeyko.dto.Cart;
import shupeyko.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CartService {
    private Cart tempCart;
    private final ProductService productService;
    private final ProductConverter productConverter;

    @PostConstruct
    public void init() {
        tempCart = new Cart(productConverter);
    }

    public Cart getCurrentCart() {
        return tempCart;
    }

    public void add(Long productId) {
        Product product = productService.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Неудается добавить продукт с id:" + productId + " в корзину. Продукт не найден"));
        tempCart.addProduct(product);
    }

    public void delete(Long productId) {
        Product product = productService.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Неудается добавить продукт с id:" + productId + " в корзину. Продукт не найден"));
        tempCart.removeProduct(product);
    }

    public void clear(){
        tempCart.clearCart();
    }

    public void changeQuantityProduct(Long productId, Integer delta){
        tempCart.changeQuantity(productId, delta);
    }
}
