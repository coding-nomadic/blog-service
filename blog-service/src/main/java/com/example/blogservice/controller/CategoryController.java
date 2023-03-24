package com.example.blogservice.controller;

import com.example.blogservice.aop.Audit;
import com.example.blogservice.constants.BlogConstants;
import com.example.blogservice.models.CategoryRequest;
import com.example.blogservice.models.CategoryResponse;
import com.example.blogservice.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RequestMapping(path = "/api/v1/category")
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * @param categoryRequest
     * @return
     * @throws IOException
     */
    @PostMapping
    @Audit(api = BlogConstants.CATEGORY_API_NAME, operation = BlogConstants.POST_OPERATION)
    public CategoryResponse createCategory(@RequestBody CategoryRequest categoryRequest) {
        return categoryService.saveCategory(categoryRequest);
    }

    @PutMapping(path = "{id}")
    @Audit(api = BlogConstants.CATEGORY_API_NAME, operation = BlogConstants.PUT_OPERATION)
    @CachePut(value = "category.update", key = "#id")
    public CategoryResponse updateCategory(@RequestBody CategoryRequest categoryRequest, @PathVariable Long id) {
        return categoryService.updateCategory(categoryRequest, id);
    }

    /**
     *
     * @param id
     * @return
     * @throws IOException
     */
    @GetMapping("{id}")
    @Cacheable(value = "category.getId", key = "#id")
    public CategoryRequest getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    /**
     *
     * @return
     */
    @GetMapping
    public List<CategoryRequest> getCategories() {
        return categoryService.getAllCategories();
    }

    /**
     *
     * @param id
     * @return
     * @throws IOException
     */
    @DeleteMapping("{id}")
    @Audit(api = BlogConstants.CATEGORY_API_NAME, operation = BlogConstants.DELETE_OPERATION)
    @CacheEvict(value = "category.delete", allEntries = true)
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        categoryService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
