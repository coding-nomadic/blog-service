package com.example.blogservice.service;

import com.example.blogservice.entity.Category;
import com.example.blogservice.entity.Post;
import com.example.blogservice.exceptions.ResourceNotFoundException;
import com.example.blogservice.models.PostRequest;
import com.example.blogservice.models.PostResponse;
import com.example.blogservice.repository.CategoryRepository;
import com.example.blogservice.repository.PostRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    ModelMapper modelMapper;
    
    @Autowired
    CategoryRepository categoryRepository;

    public PostResponse savePost(PostRequest postRequest) {
        categoryRepository.findById(postRequest.getCategoryId()).orElseThrow(
                                        () -> new ResourceNotFoundException("Category ID not found", "102"));
        Post post = postRepository.save(modelMapper.map(postRequest, Post.class));
        return modelMapper.map(post, PostResponse.class);
    }

    public void deleteById(Long id) {
        postRepository.findById(id).orElseThrow(
                                        () -> new ResourceNotFoundException("Post ID not found for " + id, "102"));
        postRepository.deleteById(id);
    }

    public PostResponse updatePost(PostRequest postRequest, Long id) {
        Post post = postRepository.findById(id)
                                        .orElseThrow(() -> new ResourceNotFoundException("Post not found", "102"));
        Category category = categoryRepository.findById(postRequest.getCategoryId())
                                        .orElseThrow(() -> new ResourceNotFoundException("Category not found", "102"));
        post.setTitle(postRequest.getTitle());
        post.setDescription(postRequest.getDescription());
        post.setContent(postRequest.getContent());
        post.setCategory(category);
        Post postResponse = postRepository.save(post);
        return modelMapper.map(postResponse, PostResponse.class);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
        
    }

    public List<PostRequest> getPostsByCategory(Long categoryId) {
        List<Post> lists = postRepository.findByCategoryId(categoryId);
        return lists.stream().map(p -> modelMapper.map(p, PostRequest.class)).collect(Collectors.toList());
    }
}
