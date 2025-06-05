package org.example.webbangiay.controller;

import lombok.RequiredArgsConstructor;
import org.example.webbangiay.dto.response.ApiResponse;
import org.example.webbangiay.dto.response.MessageResponse;
import org.example.webbangiay.dto.response.CartResponse;
import org.example.webbangiay.entity.CartDetail;
import org.example.webbangiay.service.CartDetailService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart-detail")
public class CartDetailController {


    private final CartDetailService cartDetailService;

    @PostMapping("/add-product")
    public ApiResponse<MessageResponse> addProductToCart(
            @RequestParam String cartId,
            @RequestParam String productId,
            @RequestParam Integer quantity,
            Principal principal) {

        return ApiResponse.<MessageResponse>builder()
                .code(1000)
                .result(cartDetailService.addProductInCartDetail(cartId, productId, quantity, principal.getName()))
                .build();
    }

    @GetMapping("/load")
    public ApiResponse<List<CartResponse>> loadCart(
            Principal principal,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize) {

        return ApiResponse.<List<CartResponse>>builder()
                .code(1000)
                .result(cartDetailService.loadCart(principal.getName(), pageNumber, pageSize))
                .build();
    }

    @GetMapping("/load-money")
    public ApiResponse<List<CartResponse>> loadCartMoney(Principal principal) {
        return ApiResponse.<List<CartResponse>>builder()
                .code(1000)
                .result(cartDetailService.loadCartMoney(principal.getName()))
                .build();
    }

    @GetMapping("/total-price")
    public ApiResponse<String> totalPrice(Principal principal) {
        return ApiResponse.<String>builder()
                .code(1000)
                .result(cartDetailService.totalPrice(principal.getName()))
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
            Principal principal) {

        cartDetailService.deleteProductInCart(id, principal.getName());
        return ApiResponse.<Void>builder()
                .code(1000)
                .message("Đã xóa sản phẩm khỏi giỏ hàng")
                .build();
    }
}

