package org.example.webbangiay.service;

import org.example.webbangiay.dto.request.OrderRequest;
import org.example.webbangiay.entity.Order;

import java.util.List;

public interface OrderService {

    List<Order> getAllOrders();

    public Order createOrder(OrderRequest order);
}
