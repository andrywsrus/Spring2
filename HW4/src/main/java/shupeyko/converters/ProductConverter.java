package shupeyko.converters;

import shupeyko.data.Product;
import shupeyko.dto.ProductDto;
import shupeyko.dto.ProductInCartDto;
import shupeyko.soap.products.ProductSoap;
import org.springframework.stereotype.Component;


@Component
public class ProductConverter {

    public Product dtoToEntity(ProductDto productDto) {
        return new Product(productDto.getId(), productDto.getTitle(), productDto.getCost());
    }

    public ProductDto entityToDto(Product product) {
        return new ProductDto(product.getId(), product.getTitle(), product.getCost());
    }

    public ProductInCartDto entityToProductInCartDto(Product product, int quantity) {
        return new ProductInCartDto(product.getId(), product.getTitle(), product.getCost(), quantity);
    }

    public Product productInCartDtoToEntity(ProductInCartDto productInCartDto) {
        return new Product(productInCartDto.getProductId(), productInCartDto.getProductTitle(), productInCartDto.getCostPerProduct());
    }

    public ProductSoap entityToProductSoap(Product product) {
        ProductSoap productSoap = new ProductSoap();
        productSoap.setId(product.getId());
        productSoap.setCost(product.getCost());
        productSoap.setTitle(product.getTitle());
        return productSoap;
    }
}