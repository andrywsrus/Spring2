package shupeyko.api.dto;




import java.util.List;


public class CartDto {

    private List<CartItemDto> products;
    private Integer totalPrice;

    public List<CartItemDto> getProducts() {
        return products;
    }

    public void setProducts(List<CartItemDto> products) {
        this.products = products;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }
}