package org.example.webbangiay.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class InvalidatedToken {
    @Id
    private String id;
    private Date expiryTime;
}
