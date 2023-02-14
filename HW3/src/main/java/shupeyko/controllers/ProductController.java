package shupeyko.controllers;

import shupeyko.converters.ProductConverter;
import shupeyko.data.Product;
import shupeyko.dto.ProductDto;
import shupeyko.services.ProductService;
import shupeyko.validators.ProductValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    private final ProductService productService;
    private final ProductConverter productConverter;

    private final ProductValidator productValidator;
    @GetMapping()
    public Page<ProductDto> showProducts(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "min_cost", required = false) Integer minCost,
            @RequestParam(name = "max_cost", required = false) Integer maxCost,
            @RequestParam(name = "part_title", required = false) String partTitle
    ) {
        if (page < 1) {
            page = 1;
        }
        return productService.find(minCost, maxCost, partTitle, page).map(productConverter::entityToDto);
    }

    @DeleteMapping()
    public void deleteProductById(@RequestParam Long id) {
        productService.deleteProductById(id);
    }

    @GetMapping("/{id}")
    public ProductDto findById(@PathVariable Long id) {
        return productConverter.entityToDto(productService.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @GetMapping("/change_cost")
    public void changeCostById(@RequestParam Long productId, @RequestParam Integer delta) {
        productService.changeCostById(productId, delta);
    }

    @PostMapping()
    public void addProduct(@RequestBody ProductDto productDto) {
        productValidator.validate(productDto);
        productDto.setId(null);
        productService.saveProduct(productConverter.dtoToEntity(productDto));
    }

    @PutMapping()
    public ProductDto updateProduct(@RequestBody Product product) {
        return productConverter.entityToDto(productService.saveProduct(product));
    }
}