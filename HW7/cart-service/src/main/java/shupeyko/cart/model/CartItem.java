package shupeyko.cart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private Long productId;
    private String productTitle;
    private BigDecimal pricePerProduct;
    private Integer quantity;
    private BigDecimal price;

    public CartItem(Long productId, String productTitle, BigDecimal pricePerProduct, Integer quantity) {
        this.productId = productId;
        this.productTitle = productTitle;
        this.pricePerProduct = pricePerProduct;
        this.quantity = quantity;
        recalculateCost();
    }

    private void recalculateCost() {
        price = pricePerProduct.multiply(BigDecimal.valueOf(quantity));
    }
    public void changeQuantity(int delta){
        quantity+=delta;
        recalculateCost();
    }
}