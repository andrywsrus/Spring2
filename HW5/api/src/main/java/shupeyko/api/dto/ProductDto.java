package shupeyko.api.dto;


public class ProductDto {
    private Long id;
    private String title;
    private Integer price;

    public ProductDto(Long id, String title, Integer price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }

    public ProductDto() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getPrice() {
        return price;
    }
}
