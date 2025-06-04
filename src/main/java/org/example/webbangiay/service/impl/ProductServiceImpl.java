package org.example.webbangiay.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.webbangiay.dto.request.ProductSearchForm;
import org.example.webbangiay.dto.response.ProductResponse;
import org.example.webbangiay.entity.Images;
import org.example.webbangiay.entity.Product;
import org.example.webbangiay.exception.AppException;
import org.example.webbangiay.exception.ErrorCode;
import org.example.webbangiay.repository.ImagesRepository;
import org.example.webbangiay.repository.ProductRepository;
import org.example.webbangiay.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ImagesRepository imagesRepository;

    @Override
    public List<ProductResponse> getAllProducts(ProductSearchForm productSearchForm) {
        Pageable pageable = PageRequest.of(productSearchForm.getPage(), productSearchForm.getSize());
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.stream()
                .map(product -> ProductResponse
                        .fromProductEntity(product, imagesRepository.findByProduct_Id(product.getId())))
                .collect(Collectors.toList());
    }

    public ProductResponse getProductById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        Images defaultImage = imagesRepository.findByProduct_Id(id);
        return ProductResponse.fromProductEntity(product, defaultImage);
    }

    @Override
    @Transactional
    public void deleteProduct(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        // Xóa tất cả images liên quan trước
        imagesRepository.deleteByProduct_Id(id);

        // Sau đó xóa product
        productRepository.delete(product);
    }

    @Override
    @Transactional
    public ProductResponse createProduct(ProductResponse productResponse) {
        Product product = Product.builder()
                .id(UUID.randomUUID().toString())
                .name(productResponse.getName())
                .description(productResponse.getDescription())
                .price(productResponse.getPrice())
                .quantity(productResponse.getQuantity())
                .status(productResponse.getStatus() != null ? productResponse.getStatus() : 1)
                .build();

        Product savedProduct = productRepository.save(product);

        // Không có image mặc định khi tạo mới
        return ProductResponse.fromProductEntity(savedProduct, null);
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(ProductResponse productResponse) {
        Product existingProduct = productRepository.findById(productResponse.getId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        // Update các field
        existingProduct.setName(productResponse.getName());
        existingProduct.setDescription(productResponse.getDescription());
        existingProduct.setPrice(productResponse.getPrice());
        existingProduct.setQuantity(productResponse.getQuantity());
        existingProduct.setStatus(productResponse.getStatus());

        Product updatedProduct = productRepository.save(existingProduct);

        // Lấy image mặc định
        Images defaultImage = imagesRepository.findByProduct_Id(productResponse.getId());
        return ProductResponse.fromProductEntity(updatedProduct, defaultImage);
    }

}
