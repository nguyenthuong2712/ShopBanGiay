package org.example.webbangiay.controller;

import lombok.RequiredArgsConstructor;
import org.example.webbangiay.dto.request.ProductRequest;
import org.example.webbangiay.dto.request.ProductSearchForm;
import org.example.webbangiay.dto.response.ApiResponse;
import org.example.webbangiay.dto.response.ProductResponse;
import org.example.webbangiay.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ApiResponse<List<ProductResponse>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        ProductSearchForm searchForm = ProductSearchForm.builder()
                .page(page)
                .size(size)
                .build();

        return ApiResponse.<List<ProductResponse>>builder()
                .code(1000)
                .result(productService.getAllProducts(searchForm))
                .build();
    }

    @GetMapping("/{productId}")
    public ApiResponse<ProductResponse> getProductById(@PathVariable String productId) {
        return ApiResponse.<ProductResponse>builder()
                .code(1000)
                .result(productService.getProductById(productId))
                .build();
    }

    @PostMapping("add-product")
    public ApiResponse<ProductResponse> createProduct(@RequestBody ProductRequest request) {
        System.out.println("REQUEST BODY: " + request);
        return ApiResponse.<ProductResponse>builder()
                .code(1000)
                .result(productService.createProduct(request))
                .build();
    }


    @PutMapping("/{productId}")
    public ApiResponse<ProductResponse> updateProduct(
            @PathVariable String productId,
            @RequestBody ProductRequest productRequest) {

        return ApiResponse.<ProductResponse>builder()
                .code(1000)
                .result(productService.updateProduct(productId, productRequest))
                .build();
    }

    @DeleteMapping("/{productId}")
    public ApiResponse<Void> deleteProduct(@PathVariable String productId) {
        productService.deleteProduct(productId);
        return ApiResponse.<Void>builder()
                .code(1000)
                .message("Product deleted successfully")
                .build();
    }
}
