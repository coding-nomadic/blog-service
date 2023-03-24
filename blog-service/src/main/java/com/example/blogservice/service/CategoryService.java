package com.example.blogservice.service;

import com.example.blogservice.entity.Category;
import com.example.blogservice.exceptions.ResourceNotFoundException;
import com.example.blogservice.models.CategoryRequest;
import com.example.blogservice.models.CategoryResponse;
import com.example.blogservice.repository.CategoryRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ModelMapper modelMapper;

    public CategoryResponse saveCategory(CategoryRequest categoryRequest) {
        Category category = modelMapper.map(categoryRequest, Category.class);
        Category categoryResponse = categoryRepository.save(category);
        return modelMapper.map(categoryResponse, CategoryResponse.class);
    }

    public CategoryResponse updateCategory(CategoryRequest categoryRequest, Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                                        () -> new ResourceNotFoundException("Category Id not found", "102"));
        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());
        category.setId(id);
        Category categoryResponse = categoryRepository.save(category);
        return modelMapper.map(categoryResponse, CategoryResponse.class);
    }

    public CategoryRequest getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                                        () -> new ResourceNotFoundException("Category ID not found", "102"));
        return modelMapper.map(category, CategoryRequest.class);
    }

    public List<CategoryRequest> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(category -> modelMapper.map(category, CategoryRequest.class))
                                        .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
