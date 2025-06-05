package org.example.webbangiay.dto.request;

import lombok.*;
import org.example.webbangiay.entity.Order;
import org.example.webbangiay.entity.Product;
import org.example.webbangiay.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {
    private String fullName;
    private String phoneNumber;
    private String address;
    private String note;
    private String productName;
    private int quantity;
    private BigDecimal totalAmount;
    private List<Product> products;

    public static Order createOrder(OrderRequest request) {
        Order order = new Order();
        order.setCustomerName(request.getFullName());
        order.setPhoneNumber(request.getPhoneNumber());
        order.setAddress(request.getAddress());
        order.setNote(request.getNote());
        order.setProducts(request.getProducts());
        order.setQuantity(request.getQuantity());
        order.setTotalPrice(request.getTotalAmount());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.DANG_XU_LY);
        return order;
    }

}
