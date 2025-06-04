package org.example.webbangiay.controller;

import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import org.example.webbangiay.dto.request.CategoryRequest;
import org.example.webbangiay.dto.response.ApiResponse;
import org.example.webbangiay.dto.response.MessageResponse;
import org.example.webbangiay.entity.Category;
import org.example.webbangiay.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;
    @GetMapping("/show")
    public ApiResponse<List<Category>> getAllCategory() {
        return ApiResponse.<List<Category>>builder()
                .code(1000)
                .result(categoryService.getAll())
                .build();
    }

    @GetMapping("/hien-thi")
    public ApiResponse<List<Category>> getAllCategories(
            @RequestParam(name = "nameCategory", required = false) String nameCategory,
            @RequestParam(name = "status",required = false) Integer status,
            @RequestParam(name = "pageNumber",defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize",defaultValue = "10") Integer pageSize) {
        return ApiResponse.<List<Category>>builder()
                .code(1000)
                .result(categoryService.getAllCategory(status, nameCategory, pageNumber, pageSize))
                .build();
    }

    @GetMapping("/detail")
    public ApiResponse<Category> getCategoryDetail(@RequestParam String id) {
        Category category = categoryService.findById(id);
        return ApiResponse.<Category>builder()
                .code(category != null ? 1000 : 1004) // 1004: not found
                .result(category)
                .message(category != null ? "Thành công" : "Không tìm thấy danh mục")
                .build();
    }

    @PostMapping("/create")
    public ApiResponse<MessageResponse> createCategory(@RequestBody CategoryRequest request,
                                                       @RequestParam String username)
            throws IOException, CsvValidationException {
        MessageResponse response = categoryService.create(request, username);
        return ApiResponse.<MessageResponse>builder()
                .code(1000)
                .message("Thêm danh mục thành công")
                .result(response)
                .build();
    }

    @PutMapping("/update")
    public ApiResponse<MessageResponse> updateCategory(@RequestParam String id,
                                                       @RequestBody CategoryRequest request,
                                                       @RequestParam String username)
            throws IOException, CsvValidationException {
        MessageResponse response = categoryService.update(id, request, username);
        return ApiResponse.<MessageResponse>builder()
                .code(1000)
                .message("Cập nhật danh mục thành công")
                .result(response)
                .build();
    }

    @DeleteMapping("/delete")
    public ApiResponse<MessageResponse> deleteCategory(@RequestParam String id) {
        MessageResponse response = categoryService.delete(id);
        return ApiResponse.<MessageResponse>builder()
                .code(1000)
                .message("Xóa danh mục thành công")
                .result(response)
                .build();
    }
}
