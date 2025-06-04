package org.example.webbangiay.service;

import org.example.webbangiay.dto.response.CartResponse;
import org.example.webbangiay.dto.response.MessageResponse;
import org.example.webbangiay.entity.CartDetail;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartDetailService {
    MessageResponse addProductInCartDetail(String idCart,String idProduct,Integer quantity,String username);

    List<CartResponse>loadCart(String id, Integer pageNumber, Integer pageSize);

    void deleteProductInCart(String id,String username);

    List<CartDetail> getCartDetail(String idCart);

    String totalPrice(String id);

    List<CartResponse>loadCartMoney(String id);
}
