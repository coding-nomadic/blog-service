package com.example.blogservice.service;

import com.example.blogservice.entity.Category;
import com.example.blogservice.entity.Post;
import com.example.blogservice.exceptions.ResourceNotFoundException;
import com.example.blogservice.models.PostRequest;
import com.example.blogservice.repository.CategoryRepository;
import com.example.blogservice.repository.PostRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        categoryRepository.findById(postRequest.getCategoryId()).orElseThrow(
                                        () -> new ResourceNotFoundException("Category ID not found", "102"));
        // convert DTO to entity
        Post post = modelMapper.map(postRequest, Post.class);
        return postRepository.save(post);
    }

    @Override
    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public Post update0Post(PostRequest postRequest, Long id) {
        Post post = postRepository.findById(id)
                                        .orElseThrow(() -> new ResourceNotFoundException("Post not found", "102"));

        Category category = categoryRepository.findById(postRequest.getCategoryId())
                                        .orElseThrow(() -> new ResourceNotFoundException("Category not found", "102"));
        post.setTitle(postRequest.getTitle());
        post.setDescription(postRequest.getDescription());
        post.setContent(postRequest.getContent());
        post.setCategory(category);
        return postRepository.save(post);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> getPostsByCategory(Long categoryId) {
        return postRepository.findByCategoryId(categoryId);
    }
}
