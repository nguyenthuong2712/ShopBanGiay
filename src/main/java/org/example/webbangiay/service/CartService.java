package org.example.webbangiay.service;

import org.example.webbangiay.dto.response.MessageResponse;
import org.springframework.stereotype.Service;

@Service
public interface CartService {
MessageResponse updateCart(String idCart,String idUser);
}
