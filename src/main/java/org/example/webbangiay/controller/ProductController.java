package org.example.webbangiay.controller;

import lombok.RequiredArgsConstructor;
import org.example.webbangiay.dto.request.ProductSearchForm;
import org.example.webbangiay.dto.response.ApiResponse;
import org.example.webbangiay.dto.response.ProductDto;
import org.example.webbangiay.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ApiResponse<List<ProductDto>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        ProductSearchForm searchForm = ProductSearchForm.builder()
                .page(page)
                .size(size)
                .build();

        return ApiResponse.<List<ProductDto>>builder()
                .code(1000)
                .result(productService.getAllProducts(searchForm))
                .build();
    }

    @GetMapping("/{productId}")
    public ApiResponse<ProductDto> getProductById(@PathVariable String productId) {
        return ApiResponse.<ProductDto>builder()
                .code(1000)
                .result(productService.getProductById(productId))
                .build();
    }

    @PostMapping("add-product")
    public ApiResponse<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        return ApiResponse.<ProductDto>builder()
                .code(1000)
                .result(productService.createProduct(productDto))
                .build();
    }

    @PutMapping("/{productId}")
    public ApiResponse<ProductDto> updateProduct(
            @PathVariable String productId,
            @RequestBody ProductDto productDto) {

        productDto.setId(productId);

        return ApiResponse.<ProductDto>builder()
                .code(1000)
                .result(productService.updateProduct(productDto))
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
