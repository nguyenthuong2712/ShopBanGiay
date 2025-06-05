package org.example.webbangiay.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.webbangiay.dto.request.ProductRequest;
import org.example.webbangiay.dto.request.ProductSearchForm;
import org.example.webbangiay.dto.response.ProductResponse;
import org.example.webbangiay.entity.Category;
import org.example.webbangiay.entity.Images;
import org.example.webbangiay.entity.Product;
import org.example.webbangiay.exception.AppException;
import org.example.webbangiay.exception.ErrorCode;
import org.example.webbangiay.repository.CategoryRepository;
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

    @PersistenceContext
    private EntityManager entityManager;


    private final ProductRepository productRepository;
    private final ImagesRepository imagesRepository;
    private final CategoryRepository categoryRepository;

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
    public ProductResponse createProduct(ProductRequest request) {
        System.out.println("CATEGORY ID: " + request.getCategoryId());
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        category = entityManager.merge(category);

        Product product = Product.builder()
                .id(UUID.randomUUID().toString())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .status(request.getStatus() != null ? request.getStatus() : 1)
                .category(category)
                .build();

        System.out.println("CategoryId: " + request.getCategoryId());

        try {
            Product savedProduct = productRepository.save(product);
            System.out.println("SAVED PRODUCT: " + savedProduct);
            return ProductResponse.fromProductEntity(savedProduct, null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException(ErrorCode.UNCATEGORIZED);
        }
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(String productId, ProductRequest request) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        existingProduct.setName(request.getName());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setQuantity(request.getQuantity());
        existingProduct.setStatus(request.getStatus());

        Product updatedProduct = productRepository.save(existingProduct);

        Images defaultImage = imagesRepository.findByProduct_Id(productId);
        return ProductResponse.fromProductEntity(updatedProduct, defaultImage);
    }
}
