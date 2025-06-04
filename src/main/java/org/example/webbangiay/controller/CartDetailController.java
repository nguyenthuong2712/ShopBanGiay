package org.example.webbangiay.controller;

import lombok.RequiredArgsConstructor;
import org.example.webbangiay.dto.response.ApiResponse;
import org.example.webbangiay.dto.response.MessageResponse;
import org.example.webbangiay.dto.response.CartResponse;
import org.example.webbangiay.entity.CartDetail;
import org.example.webbangiay.service.CartDetailService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart-details")
@RequiredArgsConstructor
public class CartDetailController {

    private final CartDetailService cartDetailService;

    @PostMapping("/add-product")
    public ApiResponse<MessageResponse> addProductToCart(
            @RequestParam String cartId,
            @RequestParam String productId,
            @RequestParam Integer quantity,
            @RequestParam String username) {
        return ApiResponse.<MessageResponse>builder()
                .code(1000)
                .result(cartDetailService.addProductInCartDetail(cartId, productId, quantity, username))
                .build();
    }

    @GetMapping("/load")
    public ApiResponse<List<CartResponse>> loadCart(
            @RequestParam String userId,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return ApiResponse.<List<CartResponse>>builder()
                .code(1000)
                .result(cartDetailService.loadCart(userId, pageNumber, pageSize))
                .build();
    }

    @GetMapping("/load-money")
    public ApiResponse<List<CartResponse>> loadCartMoney(@RequestParam String id) {
        return ApiResponse.<List<CartResponse>>builder()
                .code(1000)
                .result(cartDetailService.loadCartMoney(id))
                .build();
    }

    @GetMapping("/total-price")
    public ApiResponse<String> totalPrice(@RequestParam String id) {
        return ApiResponse.<String>builder()
                .code(1000)
                .result(cartDetailService.totalPrice(id))
                .build();
    }

    @GetMapping("/by-cart")
    public ApiResponse<List<CartDetail>> getCartDetails(@RequestParam String cartId) {
        return ApiResponse.<List<CartDetail>>builder()
                .code(1000)
                .result(cartDetailService.getCartDetail(cartId))
                .build();
    }

    @DeleteMapping("/delete")
    public ApiResponse<Void> deleteProductInCart(
            @RequestParam String id,
            @RequestParam String username) {
        cartDetailService.deleteProductInCart(id, username);
        return ApiResponse.<Void>builder()
                .code(1000)
                .message("Đã xóa sản phẩm khỏi giỏ hàng")
                .build();
    }
}
