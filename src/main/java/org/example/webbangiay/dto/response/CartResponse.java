package org.example.webbangiay.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponse {
    private List<CartDetailResponse> items;  // danh sách các CartDetailResponse
    private BigDecimal totalAmount;          // tổng tiền = sum(price * quantity)
}
