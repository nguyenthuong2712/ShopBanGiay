package org.example.webbangiay.repository;

import org.example.webbangiay.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, String> {
    List<Category> findByStatus(Integer status);
}
