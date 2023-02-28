package com.example.blogservice.service;

import com.example.blogservice.entity.Category;
import com.example.blogservice.entity.Post;
import com.example.blogservice.exceptions.ResourceNotFoundException;
import com.example.blogservice.models.PostRequest;
import com.example.blogservice.repository.CategoryRepository;
import com.example.blogservice.repository.PostRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
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
    public Post updatePost(PostRequest postRequest, Long id) {
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
    public List<PostRequest> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                                        : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> listOfPosts = posts.getContent();
        return listOfPosts.stream().map(p -> modelMapper.map(p, PostRequest.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostRequest> getPostsByCategory(Long categoryId) {
        List<Post> lists = postRepository.findByCategoryId(categoryId);
        return lists.stream().map(p -> modelMapper.map(p, PostRequest.class)).collect(Collectors.toList());
    }

}
