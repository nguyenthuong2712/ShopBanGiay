package org.example.webbangiay.service;

import org.example.webbangiay.dto.response.ImageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface ImagesService {

    /**
     * Tạo và lưu trữ image với tên tùy chỉnh
     * @param file MultipartFile chứa dữ liệu image
     * @param fileName tên file tùy chỉnh
     * @param productId ID của sản phẩm
     * @return ImageResponse chứa thông tin image đã được lưu
     * @throws Exception nếu có lỗi trong quá trình xử lý file
     */
    ImageResponse createImageWithCustomName(MultipartFile file, String fileName, String productId) throws Exception;

    List<ImageResponse> getAllImagesByProductId(String productId);

    ImageResponse updateDefaultImageById(String productId);
}
