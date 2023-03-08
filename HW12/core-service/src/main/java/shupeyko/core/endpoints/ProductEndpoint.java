package shupeyko.core.endpoints;



import shupeyko.core.converters.ProductConverter;
import shupeyko.core.services.ProductService;
import shupeyko.core.soap.products.GetAllProductsRequest;
import shupeyko.core.soap.products.GetAllProductsResponse;
import shupeyko.core.soap.products.GetProductByIdRequest;
import shupeyko.core.soap.products.GetProductByIdResponse;
import shupeyko.core.data.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.Optional;

@Endpoint
@RequiredArgsConstructor
public class ProductEndpoint {
    private static final String NAMESPACE_URI = "http://www.shupeyko.study/products";
    private final ProductService productService;
    private final ProductConverter productConverter;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductByIdRequest")
    @ResponsePayload
    public GetProductByIdResponse getProductById(@RequestPayload GetProductByIdRequest request) {
        GetProductByIdResponse response = new GetProductByIdResponse();
        Optional<Product> product = productService.findById(request.getId());
        response.setProduct(productConverter.entityToProductSoap(product.get()));
        return response;
    }

    /*
        Пример запроса: POST http://localhost:8080/market/ws
        Header -> Content-Type: text/xml

        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:f="http://www.shupeyko.study/products">
            <soapenv:Header/>
            <soapenv:Body>
                <f:getAllProductsRequest/>
            </soapenv:Body>
        </soapenv:Envelope>
     */

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductsRequest")
    @ResponsePayload
    public GetAllProductsResponse getAllProducts(@RequestPayload GetAllProductsRequest request) {
       GetAllProductsResponse response = new GetAllProductsResponse();
       productService.getAllProducts().stream()
               .map(productConverter::entityToProductSoap)
               .forEach(response.getProducts()::add);
       return response;
    }
}
