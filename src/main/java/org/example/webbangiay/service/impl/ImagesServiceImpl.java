package org.example.webbangiay.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.webbangiay.dto.response.ImageResponse;
import org.example.webbangiay.entity.Images;
import org.example.webbangiay.entity.Product;
import org.example.webbangiay.repository.ImagesRepository;
import org.example.webbangiay.repository.ProductRepository;
import org.example.webbangiay.service.ImagesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImagesServiceImpl implements ImagesService {

    private final ProductRepository productRepository;
    private final ImagesRepository imagesRepository;

    @Value("${upload.directory:uploads}")
    private String uploadDir;

    @Override
    public ImageResponse createImageWithCustomName(MultipartFile file, String fileName, String productId) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));

        createUploadDirectoryIfNeeded();

        String originalFileName = file.getOriginalFilename();
        String extension = getFileExtension(originalFileName);
        String uniqueFileName = fileName != null ? fileName + extension : UUID.randomUUID() + "_" + originalFileName;

        Path filePath = Paths.get(uploadDir, uniqueFileName);
        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save file", e);
        }

        Images image = Images.builder()
                .id(UUID.randomUUID().toString())
                .image(uniqueFileName)
                .isDefault(false)
                .product(product)
                .build();

        imagesRepository.save(image);

        return ImageResponse.builder()
                .image(uniqueFileName)
                .build();
    }

    @Override
    public List<ImageResponse> getAllImagesByProductId(String productId) {
        // Lấy tất cả hình ảnh của sản phẩm
        List<Images> images = imagesRepository.findAllByProductId(productId);

        // Chuyển đổi entity thành response DTO
        return images.stream()
                .map(image -> ImageResponse.builder()
                        .image(image.getImage())
                        .build())
                .toList();
    }

    @Override
    public ImageResponse updateDefaultImageById(String imageId) {
        Optional<Images> image = imagesRepository.findById(imageId);

        if (image.isEmpty()) {
            throw new IllegalArgumentException("No images found for product with id: " + imageId);
        }

        image.get().setIsDefault(true);

        Images savedImage = imagesRepository.saveAndFlush(image.get());

        return ImageResponse.builder()
                .image(savedImage.getImage())
                .isDefault(savedImage.getIsDefault())
                .build();
    }

    private void createUploadDirectoryIfNeeded() {
        try {
            Path path = Paths.get(uploadDir);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create upload directory", e);
        }
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName != null ? fileName.lastIndexOf('.') : -1;
        return (dotIndex > 0) ? fileName.substring(dotIndex) : "";
    }
}
