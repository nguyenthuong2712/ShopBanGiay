package org.example.webbangiay.controller;

import org.example.webbangiay.dto.request.OrderRequest;
import org.example.webbangiay.entity.Order;
import org.example.webbangiay.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {

        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {

        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest request) {
        Order savedOrder = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }
}
