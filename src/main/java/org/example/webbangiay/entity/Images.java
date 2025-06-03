package org.example.webbangiay.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "images")
public class Images {

    @Id
    private String id;

    private String image;

    private Boolean isDefault;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
