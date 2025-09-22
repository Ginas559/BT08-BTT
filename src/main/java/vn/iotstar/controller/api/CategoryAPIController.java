package vn.iotstar.controller.api;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import vn.iotstar.entity.Category;
import vn.iotstar.model.Response;
import vn.iotstar.service.ICategoryService;
import vn.iotstar.service.IStorageService;

@RestController
@RequestMapping(path = "/api/category")
public class CategoryAPIController {

    @Autowired
    private ICategoryService categoryService;
    @Autowired
    IStorageService storageService;

    @GetMapping
    public ResponseEntity<Response> getAllCategory() {
        return new ResponseEntity<>(
            new Response(true, "Thành công", categoryService.findAll()),
            HttpStatus.OK
        );
    }

    // Giữ nguyên POST theo tài liệu của em (dù REST thuần nên là GET /{id})
    @PostMapping(path = "/getCategory")
    public ResponseEntity<Response> getCategory(@Validated @RequestParam("id") Long id) {
        Optional<Category> category = categoryService.findById(id);
        if (category.isPresent()) {
            return new ResponseEntity<>(new Response(true, "Thành công", category.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Response(false, "Thất bại", null), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/addCategory")
    public ResponseEntity<Response> addCategory(@Validated @RequestParam("categoryName") String categoryName,
                                                @RequestParam(value = "icon", required = false) MultipartFile icon) {
        Optional<Category> optCategory = categoryService.findByCategoryName(categoryName);
        if (optCategory.isPresent()) {
            return new ResponseEntity<>(
                new Response(false, "Category đã tồn tại trong hệ thống", null),
                HttpStatus.BAD_REQUEST
            );
        } else {
            Category category = new Category();
            // lưu file nếu có
            if (icon != null && !icon.isEmpty()) {
                String uu = UUID.randomUUID().toString();
                category.setIcon(storageService.getSorageFilename(icon, uu));
                storageService.store(icon, category.getIcon());
            }
            category.setCategoryName(categoryName);
            categoryService.save(category);
            return new ResponseEntity<>(new Response(true, "Thêm thành công", category), HttpStatus.OK);
        }
    }

    @PutMapping(path = "/updateCategory")
    public ResponseEntity<Response> updateCategory(@Validated @RequestParam("categoryId") Long categoryId,
                                                   @Validated @RequestParam("categoryName") String categoryName,
                                                   @RequestParam(value = "icon", required = false) MultipartFile icon) {
        Optional<Category> optCategory = categoryService.findById(categoryId);
        if (optCategory.isEmpty()) {
            return new ResponseEntity<>(new Response(false, "Không tìm thấy Category", null), HttpStatus.BAD_REQUEST);
        }

        Category cat = optCategory.get();
        if (icon != null && !icon.isEmpty()) {
            String uu = UUID.randomUUID().toString();
            cat.setIcon(storageService.getSorageFilename(icon, uu));
            storageService.store(icon, cat.getIcon());
        }
        cat.setCategoryName(categoryName);
        categoryService.save(cat);

        return new ResponseEntity<>(new Response(true, "Cập nhật thành công", cat), HttpStatus.OK);
    }

    @DeleteMapping(path = "/deleteCategory")
    public ResponseEntity<Response> deleteCategory(@Validated @RequestParam("categoryId") Long categoryId) {
        Optional<Category> optCategory = categoryService.findById(categoryId);
        if (optCategory.isEmpty()) {
            return new ResponseEntity<>(new Response(false, "Không tìm thấy Category", null), HttpStatus.BAD_REQUEST);
        }
        categoryService.delete(optCategory.get());
        return new ResponseEntity<>(new Response(true, "Xóa thành công", optCategory.get()), HttpStatus.OK);
    }
}
