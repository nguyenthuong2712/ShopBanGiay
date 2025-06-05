package org.example.webbangiay.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "product")
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private Integer status;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Images> images;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
