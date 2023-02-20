package com.example.blogservice.service;

import com.example.blogservice.entity.Category;
import com.example.blogservice.models.CategoryRequest;
import com.example.blogservice.repository.CategoryRepository;

import java.util.List;

public interface CategoryService {

   /**
    *
    * @param categoryRequest
    * @return
    */
   public Category saveCategory(CategoryRequest categoryRequest);

   /**
    *
    * @param categoryRequest
    * @param id
    * @return
    */
   public Category updateCategory(CategoryRequest categoryRequest,Long id);
   /**
    *
    * @param id
    * @return
    */
   public CategoryRequest getCategoryById(Long id);

   /**
    *
    * @return
    */
   public List<CategoryRequest> getAllCategories();

   /**
    *
    * @param id
    */
   public void deleteById(Long id);
}
