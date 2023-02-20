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
    public Post update0Post(PostRequest postRequest,Long id);
    
    /**
     * 
     * @return
     */
    public List<Post>getAllPosts();

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
    public List<Post> getPostsByCategory(Long categoryId);

}
