package shupeyko.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInCartDto {
    private Long productId;
    private String productTitle;
    private Integer costPerProduct;
    private Integer quantity;
    private Integer cost;

    public ProductInCartDto(Long productId, String productTitle, Integer costPerProduct, Integer quantity) {
        this.productId = productId;
        this.productTitle = productTitle;
        this.costPerProduct = costPerProduct;
        this.quantity = quantity;
        recalculateCost();
    }

    public void recalculateCost() {
        cost = costPerProduct * quantity;
    }
}