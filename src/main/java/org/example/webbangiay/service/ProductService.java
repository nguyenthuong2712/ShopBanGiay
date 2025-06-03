package org.example.webbangiay.service;

import org.example.webbangiay.dto.request.ProductSearchForm;
import org.example.webbangiay.dto.response.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> getAllProducts(ProductSearchForm productSearchForm);

    ProductDto getProductById(String id);

    void deleteProduct(String id);

    ProductDto createProduct(ProductDto productDto);

    ProductDto updateProduct(ProductDto productDto);
}
