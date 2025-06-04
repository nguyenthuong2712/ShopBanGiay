package org.example.webbangiay.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.webbangiay.dto.response.CartDetailResponse;
import org.example.webbangiay.dto.response.CartResponse;
import org.example.webbangiay.dto.response.MessageResponse;
import org.example.webbangiay.entity.Cart;
import org.example.webbangiay.entity.CartDetail;
import org.example.webbangiay.entity.Product;
import org.example.webbangiay.entity.User;
import org.example.webbangiay.repository.CartDetailRepository;
import org.example.webbangiay.repository.CartRepository;
import org.example.webbangiay.repository.ProductRepository;
import org.example.webbangiay.repository.UserRepository;
import org.example.webbangiay.service.CartDetailService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CartDetailServiceImpl implements CartDetailService {
    private final CartDetailRepository cartDetailRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    @Override
    public MessageResponse addProductInCartDetail(String cartId, String productId, Integer quantity, String username) {
        // 1. Kiểm tra người dùng
        Optional<User> optUser = userRepository.findById(username);
        if (optUser.isEmpty()) {
            return new MessageResponse("Không tìm thấy người dùng");
        }

        // 2. Lấy giỏ hàng theo ID
        Cart cart = cartRepository.findByCart(cartId);
        if (cart == null || cart.getStatus() != 1) {
            return new MessageResponse("Giỏ hàng không tồn tại hoặc không hợp lệ");
        }

        // 3. Tìm sản phẩm
        Optional<Product> optProduct = productRepository.findById(productId);
        if (optProduct.isEmpty()) {
            return new MessageResponse("Không tìm thấy sản phẩm");
        }

        Product product = optProduct.get();

        // 4. Tìm chi tiết giỏ hàng đã có chưa
        CartDetail cartDetail = cartDetailRepository.finByCartAndIdProduct_id(cart, productId);

        if (cartDetail != null) {
            // Nếu đã có → cộng dồn số lượng
            cartDetail.setQuantity(cartDetail.getQuantity() + quantity);
        } else {
            // Nếu chưa có → tạo mới
            cartDetail = CartDetail.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(quantity)
                    .price(product.getPrice())
                    .status(1)
                    .build();
        }

        // 5. Lưu cart detail
        cartDetailRepository.save(cartDetail);

        return new MessageResponse("Thêm sản phẩm vào giỏ hàng thành công");
    }

    @Override
    public List<CartResponse> loadCart(String userId, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Object[]> resultPage = cartDetailRepository.loadOnCart(userId, pageable);

        List<CartDetailResponse> items = resultPage.stream().map(obj -> {
            return CartDetailResponse.builder()
                    .cartDetailId((String) obj[0])
                    .image((String) obj[1])
                    .productName((String) obj[2])
                    .price((BigDecimal) obj[3])
                    .quantity((Integer) obj[4])
                    .build();
        }).toList();

        BigDecimal totalAmount = items.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        CartResponse cartResponse = CartResponse.builder()
                .items(items)
                .totalAmount(totalAmount)
                .build();

        return List.of(cartResponse);
    }

    @Override
    public void deleteProductInCart(String id, String username) {
        Optional<CartDetail> optionalCartDetail = cartDetailRepository.findById(id);
        if (optionalCartDetail.isPresent()) {
            CartDetail detail = optionalCartDetail.get();
            detail.setStatus(4); // DA_HUY
            cartDetailRepository.save(detail);
        }
    }

    @Override
    public List<CartDetail> getCartDetail(String idCart) {
        return cartDetailRepository.findByIdCart(idCart).stream()
                .filter(cd -> cd.getStatus() == 1)
                .toList();
    }

    @Override
    public String totalPrice(String id) {
        List<Object[]> results = cartDetailRepository.sumMoney(id);
        BigDecimal total = results.stream()
                .map(obj -> {
                    BigDecimal price = (BigDecimal) obj[0];
                    Integer quantity = (Integer) obj[1];
                    return price.multiply(BigDecimal.valueOf(quantity));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return total.toString();
    }

    @Override
    public List<CartResponse> loadCartMoney(String id) {
        List<Object[]> resultList = cartDetailRepository.loadOnCartMoney(id);

        List<CartDetailResponse> items = resultList.stream().map(obj -> {
            return CartDetailResponse.builder()
                    .cartDetailId((String) obj[0])
                    .image((String) obj[1])
                    .productName((String) obj[2])
                    .price((BigDecimal) obj[3])
                    .quantity((Integer) obj[4])
                    .build();
        }).toList();

        BigDecimal totalAmount = items.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        CartResponse response = CartResponse.builder()
                .items(items)
                .totalAmount(totalAmount)
                .build();

        return List.of(response);
    }

}
