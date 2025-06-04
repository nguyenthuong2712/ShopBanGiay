package org.example.webbangiay.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String nameCategory;
    private Integer status;
    private Date createDate;
    private Date updateDate;
    @OneToMany(mappedBy = "category",fetch = FetchType.LAZY )
    @JsonManagedReference
    private List<Product> products;

    public Category(String id, String nameCategory, Integer status, Date createDate, Date updateDate) {
        this.id = id;
        this.nameCategory = nameCategory;
        this.status = status;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
}
