package org.example.webbangiay.repository;

import org.example.webbangiay.entity.Cart;
import org.example.webbangiay.entity.CartDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartDetailRepository extends JpaRepository<CartDetail, String> {
// lấy giỏ hàng theo tài khoản và phân trang
@Query("""
    SELECT cd.id, i.image, p.name, cd.price, cd.quantity
    FROM CartDetail cd
    JOIN cd.product p
    JOIN p.images i
    JOIN cd.cart c
    JOIN c.user u
    WHERE i.isDefault = true AND c.status = 1 AND u.id = :id
""")
Page<Object[]>loadOnCart (@Param("id") String id, Pageable pageable);

// Lấy giỏ hàng không phân trang
@Query("""
    SELECT cd.id, i.image, p.name, cd.price, cd.quantity
    FROM CartDetail cd
    JOIN cd.product p
    JOIN p.images i
    JOIN cd.cart c
    JOIN c.user u
    WHERE i.isDefault = true AND c.status = 1 AND u.id = :id
""")
List<Object[]> loadOnCartMoney(@Param("id") String id);
//Tính tổng tiền
@Query("""
    SELECT cd.price, cd.quantity
    FROM CartDetail cd
    JOIN cd.cart c
    JOIN c.user u
    WHERE c.status = 1 AND u.id = :id
""")
List<Object[]>sumMoney(@Param("id") String id );

CartDetail findByCartAndProductId(Cart cart, String idProductId);
//CartDetail finByCart(Cart cart);
List<CartDetail> findByCart_Id(String cartId);
}
