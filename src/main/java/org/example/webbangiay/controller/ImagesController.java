package org.example.webbangiay.controller;

import lombok.RequiredArgsConstructor;
import org.example.webbangiay.dto.response.ApiResponse;
import org.example.webbangiay.dto.response.ImageResponse;
import org.example.webbangiay.service.ImagesService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImagesController {

    private final ImagesService imagesService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<ImageResponse> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("productId") String productId,
            @RequestParam(value = "fileName", required = false) String fileName) throws Exception {

        return ApiResponse.<ImageResponse>builder()
                .code(1000)
                .result(imagesService.createImageWithCustomName(file, fileName, productId))
                .build();
    }

    @GetMapping("/product/{productId}")
    public ApiResponse<List<ImageResponse>> getAllImagesByProductId(@PathVariable String productId) {
        return ApiResponse.<List<ImageResponse>>builder()
                .code(1000)
                .result(imagesService.getAllImagesByProductId(productId))
                .build();
    }

    @PutMapping("/{imageId}/default")
    public ApiResponse<ImageResponse> updateDefaultImage(@PathVariable String imageId) {
        return ApiResponse.<ImageResponse>builder()
                .code(1000)
                .result(imagesService.updateDefaultImageById(imageId))
                .build();
    }
}
