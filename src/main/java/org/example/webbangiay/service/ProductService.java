package org.example.webbangiay.service;

import org.example.webbangiay.dto.request.ProductRequest;
import org.example.webbangiay.dto.request.ProductSearchForm;
import org.example.webbangiay.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {

    List<ProductResponse> getAllProducts(ProductSearchForm productSearchForm);

    ProductResponse getProductById(String id);

    void deleteProduct(String id);

    ProductResponse createProduct(ProductRequest request);

    ProductResponse updateProduct(String productId, ProductRequest request);
}
