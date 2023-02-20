package com.example.blogservice.service;

import com.example.blogservice.entity.Category;
import com.example.blogservice.entity.Post;
import com.example.blogservice.exceptions.ResourceNotFoundException;
import com.example.blogservice.models.PostRequest;
import com.example.blogservice.repository.CategoryRepository;
import com.example.blogservice.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
@Service
public class PostServiceImpl implements PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Post savePost(PostRequest postRequest) {
        Category category = categoryRepository.findById(postRequest.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category ID not found", "102"));
        // convert DTO to entity
        Post post = modelMapper.map(postRequest, Post.class);
        return postRepository.save(post);
    }
}
