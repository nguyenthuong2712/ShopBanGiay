package org.example.webbangiay.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.webbangiay.dto.request.ProductSearchForm;
import org.example.webbangiay.dto.response.ProductDto;
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
    public List<ProductDto> getAllProducts(ProductSearchForm productSearchForm) {
        Pageable pageable = PageRequest.of(productSearchForm.getPage(), productSearchForm.getSize());
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.stream()
                .map(product -> ProductDto.fromProductEntity(product, imagesRepository.findByProduct_Id(product.getId())))
                .collect(Collectors.toList());
    }

    public ProductDto getProductById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        Images defaultImage = imagesRepository.findByProduct_Id(id);
        return ProductDto.fromProductEntity(product, defaultImage);
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
    public ProductDto createProduct(ProductDto productDto) {
        Product product = Product.builder()
                .id(UUID.randomUUID().toString())
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .quantity(productDto.getQuantity())
                .status(productDto.getStatus() != null ? productDto.getStatus() : 1) // Default status = 1 (active)
                .build();

        Product savedProduct = productRepository.save(product);

        // Không có image mặc định khi tạo mới
        return ProductDto.fromProductEntity(savedProduct, null);
    }

    @Override
    @Transactional
    public ProductDto updateProduct(ProductDto productDto) {
        Product existingProduct = productRepository.findById(productDto.getId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        // Update các field
        existingProduct.setName(productDto.getName());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setQuantity(productDto.getQuantity());
        existingProduct.setStatus(productDto.getStatus());

        Product updatedProduct = productRepository.save(existingProduct);

        // Lấy image mặc định
        Images defaultImage = imagesRepository.findByProduct_Id(productDto.getId());
        return ProductDto.fromProductEntity(updatedProduct, defaultImage);
    }

}
