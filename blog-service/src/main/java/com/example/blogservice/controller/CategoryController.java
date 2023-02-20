package com.example.blogservice.controller;

import com.example.blogservice.entity.Category;
import com.example.blogservice.models.CategoryRequest;
import com.example.blogservice.service.CategoryService;
import com.example.blogservice.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequestMapping(path = "/api/v1/category")
@RestController
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * @param categoryRequest
     * @return
     * @throws IOException
     */
    @PostMapping
    public ResponseEntity<Void> createCategory(@RequestBody CategoryRequest categoryRequest) throws IOException {
        log.info("Entered Category Request Body {}", JsonUtils.toString(categoryRequest));
        categoryService.saveCategory(categoryRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Void> updateCategory(@RequestBody CategoryRequest categoryRequest,@PathVariable Long id) throws IOException {
        log.info("Entered Category Update Request Body {}", JsonUtils.toString(categoryRequest));
        categoryService.updateCategory(categoryRequest,id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    /**
     *
     * @param id
     * @return
     * @throws IOException
     */
    @GetMapping("{id}")
    public ResponseEntity<CategoryRequest> getCategoryById(@PathVariable Long id) throws IOException {
        log.info("Entered Category Request ID {}", id);
        CategoryRequest categoryRequest = categoryService.getCategoryById(id);
        return new ResponseEntity<>(categoryRequest, HttpStatus.OK);
    }

    /**
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<List<CategoryRequest>> getCategories() {
        log.info("Entered Category Request to fetch  all categories");
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    /**
     *
     * @param id
     * @return
     * @throws IOException
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) throws IOException {
        log.info("Entered Category Request for Delete ID {}", id);
        categoryService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
