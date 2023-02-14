package shupeyko.cart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private Long productId;
    private String productTitle;
    private Integer pricePerProduct;
    private Integer quantity;
    private Integer price;

    public CartItem(Long productId, String productTitle, Integer pricePerProduct, Integer quantity) {
        this.productId = productId;
        this.productTitle = productTitle;
        this.pricePerProduct = pricePerProduct;
        this.quantity = quantity;
        recalculateCost();
    }

    private void recalculateCost() {
        price = pricePerProduct * quantity;
    }
    public void changeQuantity(int delta){
        quantity+=delta;
        recalculateCost();
    }
}