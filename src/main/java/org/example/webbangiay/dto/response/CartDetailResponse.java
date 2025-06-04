package org.example.webbangiay.dto.response;

import lombok.Builder;
import lombok.Data;
import org.example.webbangiay.entity.CartDetail;
import org.example.webbangiay.entity.Images;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CartDetailResponse {
    private String cartDetailId;
    private String image;
    private String productName;
    private BigDecimal price;
    private Integer quantity;

    public static CartDetailResponse fromCartDetailEntity(CartDetail cartDetail) {
        // Lấy ảnh mặc định
        List<Images> imagesList = cartDetail.getProduct().getImages();
        String imageUrl = imagesList.stream()
                .filter(Images::getIsDefault)
                .map(Images::getImage)
                .findFirst()
                .orElse(null);

        return CartDetailResponse.builder()
                .cartDetailId(cartDetail.getId())
                .productName(cartDetail.getProduct().getName())
                .price(cartDetail.getPrice())
                .quantity(cartDetail.getQuantity())
                .image(imageUrl)
                .build();
    }
}
