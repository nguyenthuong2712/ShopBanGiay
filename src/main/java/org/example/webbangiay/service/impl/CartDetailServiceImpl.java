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
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartDetailServiceImpl implements CartDetailService {
    private final CartDetailRepository cartDetailRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public MessageResponse addProductInCartDetail(String cartId, String productId, Integer quantity, String username) {
        // 1. Kiểm tra người dùng
        Optional<User> optUser = userRepository.findByUsername(username);
        if (optUser.isEmpty()) {
            return new MessageResponse("Không tìm thấy người dùng");
        }
        User user = optUser.get();

        Cart cart;
        // 2. Nếu cartId null hoặc không tìm thấy giỏ hàng → tạo mới
        if (cartId == null || cartId.isBlank() || !cartRepository.existsById(cartId)) {
            cart = Cart.builder()
                    .user(user)
                    .status(1)
                    .note("")
                    .createdate(new Date())
                    .updatedate(new Date())
                    .build();
            cart = cartRepository.save(cart);
        } else {
            cart = cartRepository.findById(cartId).orElse(null);
            if (cart == null || cart.getStatus() != 1) {
                return new MessageResponse("Giỏ hàng không tồn tại hoặc không hợp lệ");
            }
        }

        // 3. Kiểm tra sản phẩm
        Optional<Product> optProduct = productRepository.findById(productId);
        if (optProduct.isEmpty()) {
            return new MessageResponse("Không tìm thấy sản phẩm");
        }

        Product product = optProduct.get();

        // 4. Tìm chi tiết giỏ hàng (dựa trên cart và product)
        CartDetail cartDetail = cartDetailRepository.findByCartAndProductId(cart, productId);

        if (cartDetail != null && cartDetail.getStatus() == 1) {
            // Đã có → cộng dồn
            cartDetail.setQuantity(cartDetail.getQuantity() + quantity);
        } else {
            // Chưa có → tạo mới
            cartDetail = CartDetail.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(quantity)
                    .price(product.getPrice())
                    .status(1)
                    .build();
        }

        cartDetailRepository.save(cartDetail);

        return new MessageResponse("Thêm sản phẩm vào giỏ hàng thành công");
    }

    @Override
    public List<CartResponse> loadCart(String username, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<CartDetail> cartDetails = cartDetailRepository.findByCardDetail(username, pageable);

        List<CartDetailResponse> cartDetailResponses = cartDetails.getContent()
                .stream()
                .map(CartDetailResponse::fromCartDetailEntity)
                .toList();

        BigDecimal totalAmount = cartDetails.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        CartResponse cartResponse = CartResponse.builder()
                .items(cartDetailResponses)
                .totalAmount(totalAmount)
                .build();

        return List.of(cartResponse);
    }

    @Override
    public void deleteProductInCart(String id, String username) {
        Optional<CartDetail> optionalCartDetail = cartDetailRepository.findById(id);
        if (optionalCartDetail.isPresent()) {
            CartDetail detail = optionalCartDetail.get();
            // Nếu cần kiểm tra người sở hữu sản phẩm → xử lý ở đây
            detail.setStatus(4); // DA_HUY
            cartDetailRepository.save(detail);
        }
    }

    @Override
    public List<CartDetail> getCartDetail(String idCart) {
        return cartDetailRepository.findByCart_Id(idCart).stream()
                .filter(cd -> cd.getStatus() == 1)
                .toList();
    }

    @Override
    public String totalPrice(String username) {
        List<Object[]> results = cartDetailRepository.sumMoney(username);
        BigDecimal total = results
                .stream()
                .map(obj -> {
                    BigDecimal price = (BigDecimal) obj[0];
                    Integer quantity = (Integer) obj[1];
                    return price.multiply(BigDecimal.valueOf(quantity));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return total.toString();
    }

    @Override
    public List<CartResponse> loadCartMoney(String username) {
        List<CartDetail> cartDetails = cartDetailRepository.loadOnCartMoney(username);

        List<CartDetailResponse> items = cartDetails
                .stream()
                .map(CartDetailResponse::fromCartDetailEntity)
                .toList();

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

