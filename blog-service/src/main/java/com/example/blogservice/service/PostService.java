package com.example.blogservice.service;

import com.example.blogservice.entity.Post;
import com.example.blogservice.models.PostRequest;

import java.util.List;

public interface PostService {
    /**
     * 
     * @param postRequest
     * @return
     */
    public Post savePost(PostRequest postRequest);

    /**
     * 
     * @param postRequest
     * @return
     */
    public Post updatePost(PostRequest postRequest, Long id);

    /**
     * 
     * @return
     */
    public List<PostRequest> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    /**
     * 
     * @param id
     */
    public void deleteById(Long id);

    /**
     * 
     * @param categoryId
     * @return
     */
    public List<PostRequest> getPostsByCategory(Long categoryId);

}
