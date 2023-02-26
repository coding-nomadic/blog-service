package com.example.blogservice.controller;

import com.example.blogservice.aop.Audit;
import com.example.blogservice.constants.BlogConstants;
import com.example.blogservice.entity.Post;
import com.example.blogservice.models.PostRequest;
import com.example.blogservice.service.PostService;
import com.example.blogservice.utils.JsonUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

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
    @Audit(api = BlogConstants.POST_API_NAME, operation = BlogConstants.POST_OPERATION)
    public ResponseEntity<Post> createPost(@RequestBody PostRequest postRequest) throws IOException {
        log.info("Entered Post Request Body {}", JsonUtils.toString(postRequest));
        postService.savePost(postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 
     * @param postRequest
     * @param id
     * @return
     * @throws IOException
     */
    @PutMapping(path = "{id}")
    @Audit(api = BlogConstants.POST_API_NAME, operation = BlogConstants.PUT_OPERATION)
    public ResponseEntity<Void> updatePost(@RequestBody PostRequest postRequest, @PathVariable Long id)
                                    throws IOException {
        log.info("Entered Update Request Body {}", JsonUtils.toString(postRequest), id);
        postService.updatePost(postRequest, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public List<PostRequest> getAllPosts(
                                    @RequestParam(value = "pageNo", defaultValue = BlogConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                    @RequestParam(value = "pageSize", defaultValue = BlogConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                    @RequestParam(value = "sortBy", defaultValue = BlogConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                    @RequestParam(value = "sortDir", defaultValue = BlogConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }
    /**
     * 
     * @param id
     * @return
     * @throws IOException
     */
    @DeleteMapping(path = "{id}")
    @Audit(api = BlogConstants.POST_API_NAME, operation = BlogConstants.DELETE_OPERATION)
    public ResponseEntity<Void> deletePost(@PathVariable Long id) throws IOException {
        log.info("Entered Delete Request Body {}", id);
        postService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 
     * @param categoryId
     * @return
     * @throws IOException
     */
    @GetMapping(path = "/category/{id}")
    public ResponseEntity<List<PostRequest>> getPostsByCategory(@PathVariable("id") Long categoryId) throws IOException {
        List<PostRequest> postDtos = postService.getPostsByCategory(categoryId);
        log.info("Fetched All Posts By Category ID {}", JsonUtils.toString(postDtos));
        return ResponseEntity.ok(postDtos);
    }
}
