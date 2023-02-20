package com.example.blogservice.controller;

import com.example.blogservice.entity.Post;
import com.example.blogservice.models.PostRequest;
import com.example.blogservice.service.PostService;
import com.example.blogservice.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequestMapping(value = "/api/v1/post")
@RestController
@Slf4j
public class PostController {
    @Autowired
    private PostService postService;
    /**
     *
     * @param postRequest
     * @return
     */
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostRequest postRequest) throws IOException {
        log.info("Entered Post Request Body {}", JsonUtils.toString(postRequest));
        postService.savePost(postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
