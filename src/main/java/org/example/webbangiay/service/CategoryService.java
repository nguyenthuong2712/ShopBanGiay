package org.example.webbangiay.service;

import org.example.webbangiay.dto.request.CategoryRequest;
import org.example.webbangiay.dto.response.MessageResponse;
import org.example.webbangiay.entity.Category;
import org.springframework.stereotype.Service;
import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.util.List;


@Service
public interface CategoryService {
    List<Category> getAll();

    List<Category> getAllCategory(Integer status,String nameCategory,Integer pageNumber,Integer pageSize);

    Category findById(String id);

    MessageResponse create(CategoryRequest request,String username )throws IOException, CsvValidationException;

    MessageResponse update(String id,CategoryRequest request,String username )throws IOException, CsvValidationException;

    MessageResponse delete(String id);
}
