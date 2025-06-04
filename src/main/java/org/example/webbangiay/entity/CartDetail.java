package org.example.webbangiay.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class CartDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "idproduct")
    @JsonBackReference
    private Product product;

    @ManyToOne
    @JoinColumn(name = "idcart")
    @JsonManagedReference
    private Cart cart;

    private Integer quantity;

    private BigDecimal price;

    private Integer status;
}
