package com.example.blogservice.controller;

import com.example.blogservice.aop.Audit;
import com.example.blogservice.constants.BlogConstants;
import com.example.blogservice.entity.Post;
import com.example.blogservice.models.PostRequest;
import com.example.blogservice.models.PostResponse;
import com.example.blogservice.service.PostService;

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

import java.util.List;

@RequestMapping(value = "/api/v1/post")
@RestController
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping
    @Audit(api = BlogConstants.POST_API_NAME, operation = BlogConstants.POST_OPERATION)
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest postRequest) {
        PostResponse postResponse = postService.savePost(postRequest);
        return new ResponseEntity<>(postResponse, HttpStatus.CREATED);
    }

    @PutMapping(path = "{id}")
    @Audit(api = BlogConstants.POST_API_NAME, operation = BlogConstants.PUT_OPERATION)
    @CachePut(value = "post.update", key = "#id")
    public PostResponse updatePost(@RequestBody PostRequest postRequest, @PathVariable Long id) {
        return postService.updatePost(postRequest, id);
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @DeleteMapping(path = "{id}")
    @Audit(api = BlogConstants.POST_API_NAME, operation = BlogConstants.DELETE_OPERATION)
    @CacheEvict(value = "post.delete", allEntries = true)
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/category/{categoryId}")
    @Cacheable(value = "post.category", key = "#categoryId")
    public List<PostRequest> getPostsByCategory(@PathVariable("categoryId") Long categoryId) {
        return postService.getPostsByCategory(categoryId);
    }
}
