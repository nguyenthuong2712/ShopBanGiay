package org.example.webbangiay.service.impl;

import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import org.example.webbangiay.dto.request.CategoryRequest;
import org.example.webbangiay.dto.response.MessageResponse;
import org.example.webbangiay.entity.Category;
import org.example.webbangiay.repository.CategoryRepository;
import org.example.webbangiay.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryrepository;

    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    @Override
    public List<Category> getAll() {
        return categoryrepository.findByStatus(1);
    }

    @Override
    public List<Category> getAllCategory(Integer status, String nameCategory, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<Category> pagelist = categoryrepository.getAllCategories(status,nameCategory,pageable);
        return pagelist.getContent();
    }

    @Override
    public Category findById(String id) {
        return categoryrepository.findById(id).orElse(null);
    }

    @Override
    public MessageResponse create(CategoryRequest request, String username)
            throws IOException, CsvValidationException {
        Category category = new Category();
        category.setNameCategory(request.getNameCategory());
        category.setStatus(request.getStatus());
        category.setCreateDate(timestamp);
        categoryrepository.save(category);
        return MessageResponse.builder()
                .message("Thêm thành công")
                .build();
    }

    @Override
    public MessageResponse update(String id,CategoryRequest request, String username)
            throws IOException, CsvValidationException {
        Optional<Category> categoryOptional = categoryrepository.findById(id);
        if(categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            category.setNameCategory(request.getNameCategory());
            category.setStatus(request.getStatus());
            category.setUpdateDate(timestamp);
            categoryrepository.save(category);
            return MessageResponse.builder()
                    .message("Cập nhật thành công")
                    .build();
        }else {
            return MessageResponse.builder()
                    .message("Không tìm thấy danh mục với ID: " + id)
                    .build();
        }
    }

    @Override
    public MessageResponse delete(String id) {
        Optional<Category> categoryOptional = categoryrepository.findById(id);
        if(categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            category.setStatus(2);
            category.setUpdateDate(timestamp);
            categoryrepository.save(category);
            return MessageResponse.builder()
                    .message("Xóa thành công")
                    .build();
        }else {
            return MessageResponse.builder()
                    .message("Không tìm thấy danh mục với ID: "+id)
                    .build();
        }
    }
}
