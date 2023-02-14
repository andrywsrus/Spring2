package shupeyko.api.dto;


public class CartItemDto {
    private Long productId;
    private String productTitle;
    private Integer pricePerProduct;
    private Integer quantity;
    private Integer price;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public Integer getPricePerProduct() {
        return pricePerProduct;
    }

    public void setPricePerProduct(Integer pricePerProduct) {
        this.pricePerProduct = pricePerProduct;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }


}