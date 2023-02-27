package shupeyko.core.validators;


import shupeyko.api.dto.ProductDto;
import shupeyko.core.exception.ValidationException;
import org.springframework.stereotype.Component;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductValidator {
    public void validate (ProductDto productDto) {
        List<String> errors = new ArrayList<>();
        if (productDto.getPrice().compareTo(BigDecimal.valueOf(0)) <= 0) {
            errors.add("Product price can't be zero or lower");
        }
        if (productDto.getTitle().isBlank()) {
            errors.add("Product name can't be blank");
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
