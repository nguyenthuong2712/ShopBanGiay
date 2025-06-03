package org.example.webbangiay.repository;

import org.example.webbangiay.entity.Images;
import org.example.webbangiay.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagesRepository extends JpaRepository<Images, String> {

    List<Images> findAllByProductId(String productId);

    Images findByProduct_Id(String productId);

    Images deleteByProduct_Id(String productId);
}
