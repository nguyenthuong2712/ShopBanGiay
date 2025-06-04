package org.example.webbangiay.repository;

import org.example.webbangiay.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, String> {

    List<Category> findByStatus(Integer status);

    @Query("SELECT NEW org.example.webbangiay.entity.Category(c.id, c.nameCategory, c.status, c.createDate, c.updateDate) " +
            "FROM Category c " +
            "WHERE (:status IS NULL OR c.status = :status) " +
            "AND (:nameCategory IS NULL OR c.nameCategory LIKE CONCAT('%', :nameCategory, '%')) " +
            "ORDER BY c.createDate DESC")
    Page<Category> getAllCategories(@Param("status") Integer status,
                                    @Param("nameCategory") String nameCategory,
                                    Pageable pageable);
}
