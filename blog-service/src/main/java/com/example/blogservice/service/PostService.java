package com.example.blogservice.service;

import com.example.blogservice.entity.Post;
import com.example.blogservice.models.PostRequest;

public interface PostService {
    public Post savePost(PostRequest postRequest);

}
