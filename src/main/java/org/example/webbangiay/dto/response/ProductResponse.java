package org.example.webbangiay.dto.response;

import lombok.Builder;
import lombok.Data;
import org.example.webbangiay.entity.Images;
import org.example.webbangiay.entity.Product;

import java.math.BigDecimal;

@Data
@Builder
public class ProductResponse {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer status;
    private Integer quantity;
    private String image;
    private String categoryId;

    public static ProductResponse fromProductEntity(Product product, Images images) {
        return ProductResponse
                .builder()
                .id(product.getId())
                .name(product.getName())
                .quantity(product.getQuantity())
                .description(product.getDescription())
                .price(product.getPrice())
                .status(product.getStatus())
                .image(images != null ? images.getImage() : null)
                .categoryId(product.getCategory() != null ? product.getCategory().getId() : null)
                .build();
    }

}
