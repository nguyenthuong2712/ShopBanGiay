package org.example.webbangiay.controller;

import lombok.RequiredArgsConstructor;
import org.example.webbangiay.dto.response.ApiResponse;
import org.example.webbangiay.dto.response.MessageResponse;
import org.example.webbangiay.service.CartService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PutMapping("/update-user")
    public ApiResponse<MessageResponse> updateCartUser(
            @RequestParam String idCart,
            @RequestParam String idUser) {

        return ApiResponse.<MessageResponse>builder()
                .code(1000)
                .result(cartService.updateCart(idCart, idUser))
                .build();
    }
}
