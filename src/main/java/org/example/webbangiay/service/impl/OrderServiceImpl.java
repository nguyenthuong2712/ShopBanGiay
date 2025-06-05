package org.example.webbangiay.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.webbangiay.dto.request.OrderRequest;
import org.example.webbangiay.entity.Order;
import org.example.webbangiay.repository.OrderRepository;
import org.example.webbangiay.repository.ProductRepository;
import org.example.webbangiay.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAllByOrderByOrderDateDesc();
    }

    @Override
    @Transactional
    public Order createOrder(OrderRequest orderRequest) {
        Order order = OrderRequest.createOrder(orderRequest);
        order = orderRepository.saveAndFlush(order);
        return order;
    }

}
