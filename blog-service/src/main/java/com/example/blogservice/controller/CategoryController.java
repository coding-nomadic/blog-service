package com.example.blogservice.controller;

import com.example.blogservice.aop.Audit;
import com.example.blogservice.constants.BlogConstants;
import com.example.blogservice.models.CategoryRequest;
import com.example.blogservice.service.CategoryService;
import com.example.blogservice.utils.JsonUtils;

import org.springframework.beans.factory.annotation.Autowired;
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

import lombok.extern.slf4j.Slf4j;

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
    @Audit(api = BlogConstants.CATEGORY_API_NAME, operation = BlogConstants.POST_OPERATION)
    public ResponseEntity<Void> createCategory(@RequestBody CategoryRequest categoryRequest) throws IOException {
        log.info("Entered Category Request Body {}", JsonUtils.toString(categoryRequest));
        categoryService.saveCategory(categoryRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(path = "{id}")
    @Audit(api = BlogConstants.CATEGORY_API_NAME, operation = BlogConstants.PUT_OPERATION)
    public ResponseEntity<Void> updateCategory(@RequestBody CategoryRequest categoryRequest, @PathVariable Long id)
                                    throws IOException {
        log.info("Entered Category Update Request Body {}", JsonUtils.toString(categoryRequest));
        categoryService.updateCategory(categoryRequest, id);
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
    @Audit(api = BlogConstants.CATEGORY_API_NAME, operation = BlogConstants.DELETE_OPERATION)
    public ResponseEntity<Void> deleteById(@PathVariable Long id) throws IOException {
        log.info("Entered Category Request for Delete ID {}", id);
        categoryService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
