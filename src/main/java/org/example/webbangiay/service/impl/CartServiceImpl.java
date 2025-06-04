package org.example.webbangiay.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.webbangiay.dto.response.MessageResponse;
import org.example.webbangiay.repository.CartRepository;
import org.example.webbangiay.repository.UserRepository;
import org.example.webbangiay.service.CartService;
import org.example.webbangiay.service.acccount_service.UserService;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    @Override
    public MessageResponse updateCart(String idCart, String idUser) {
        var cart = cartRepository.findById(idCart);
        var user = userRepository.findById(idUser);
        cart.get().setUser(user.get());
        cartRepository.save(cart.get());
        return MessageResponse.builder()
                .message("Update Thành Công")
                .build();
    }
}
