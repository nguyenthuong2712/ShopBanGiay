package org.example.webbangiay.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageResponse {

    private String image;

    private boolean isDefault;
}
