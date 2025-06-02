package org.example.webbangiay.service.impl;

import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import org.example.webbangiay.dto.request.CategoryRequest;
import org.example.webbangiay.dto.response.MessageResponse;
import org.example.webbangiay.entity.Category;
import org.example.webbangiay.repository.CategoryRepository;
import org.example.webbangiay.service.CategoryService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;


@RequiredArgsConstructor
@Service
public class CategoryServiceIplm implements CategoryService {
    private final CategoryRepository categoryrepository;

    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    @Override
    public List<Category> getAll() {
        return categoryrepository.findByStatus(1);
    }

    @Override
    public Category findById(String id) {
        return categoryrepository.findById(id).orElse(null);
    }

    @Override
    public MessageResponse create(CategoryRequest request, String username) throws IOException, CsvValidationException {
        Category category = new Category();
        category.setNameCategory(request.getNameCategory());
        category.setStatus(request.getStatus());
        category.setCreateDate();
        return null;
    }

    @Override
    public MessageResponse update(String id,CategoryRequest request, String username) throws IOException, CsvValidationException {
        return null;
    }

    @Override
    public MessageResponse delete(String id) {
        return null;
    }
}
