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

    @Query("SELECT cd FROM CartDetail cd JOIN cd.cart c WHERE c.user.username = :username")
     Page<CartDetail> findByCardDetail(@Param("username") String username, Pageable pageable);

    @Query(" SELECT cd FROM CartDetail cd JOIN cd.cart c WHERE c.status = 1 AND c.user.username = :username AND cd.status = 1" )
    List<CartDetail> loadOnCartMoney(@Param("username") String username);

    @Query("SELECT cd.price, cd.quantity FROM CartDetail cd JOIN cd.cart c WHERE c.user.username = :username")
    List<Object[]> sumMoney(@Param("username") String username);


 CartDetail findByCartAndProductId(Cart cart, String idProductId);

    List<CartDetail> findByCart_Id(String cartId);
}
