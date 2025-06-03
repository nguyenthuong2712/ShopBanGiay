package org.example.webbangiay.repository;

import org.example.webbangiay.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {
    @Query("SELECT gh FROM Cart gh where gh.id = :id")
    Cart findByCartId(String id);
}
