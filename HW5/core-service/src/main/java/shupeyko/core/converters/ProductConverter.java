package shupeyko.core.converters;


import shupeyko.api.dto.CartItemDto;
import shupeyko.api.dto.ProductDto;
import shupeyko.core.soap.products.ProductSoap;
import shupeyko.core.data.Product;
import org.springframework.stereotype.Component;


@Component
public class ProductConverter {

    public Product dtoToEntity(ProductDto productDto) {
        return new Product(productDto.getId(), productDto.getTitle(), productDto.getPrice());
    }

    public ProductDto entityToDto(Product product) {
        return new ProductDto(product.getId(), product.getTitle(), product.getPrice());
    }

    public CartItemDto entityToCartItem(Product product, int quantity) {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setProductTitle(product.getTitle());
        cartItemDto.setProductId(product.getId());
        cartItemDto.setQuantity(quantity);
        cartItemDto.setPricePerProduct(product.getPrice());
        return cartItemDto;
    }

    public ProductSoap entityToProductSoap(Product product) {
        ProductSoap productSoap = new ProductSoap();
        productSoap.setId(product.getId());
        productSoap.setCost(product.getPrice());
        productSoap.setTitle(product.getTitle());
        return productSoap;
    }
}