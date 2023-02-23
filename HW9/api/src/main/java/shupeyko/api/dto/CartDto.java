package shupeyko.api.dto;

import java.math.BigDecimal;
import java.util.List;


public class CartDto {

    private List<CartItemDto> products;
    private BigDecimal totalPrice;

    public List<CartItemDto> getProducts() {
        return products;
    }

    public void setProducts(List<CartItemDto> products) {
        this.products = products;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}