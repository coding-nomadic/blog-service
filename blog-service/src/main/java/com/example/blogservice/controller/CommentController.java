package com.example.blogservice.controller;

import com.example.blogservice.aop.Audit;
import com.example.blogservice.constants.BlogConstants;
import com.example.blogservice.models.CommentRequest;
import com.example.blogservice.models.CommentResponse;
import com.example.blogservice.service.CommentService;

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

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    @Audit(api = BlogConstants.COMMENT_API_NAME, operation = BlogConstants.POST_OPERATION)
    public CommentResponse createComment(@PathVariable(value = "postId") long postId,
                                    @Valid @RequestBody CommentRequest commentDto) {
        return commentService.createComment(postId, commentDto);
    }
    
    @GetMapping("/posts/{postId}/comments/{id}")
    @Cacheable(value = "comment.getId", key = "#postId")
    public CommentRequest getCommentById(@PathVariable(value = "postId") Long postId,
                                    @PathVariable(value = "id") Long commentId) {
        return commentService.getCommentById(postId, commentId);
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    @Audit(api = BlogConstants.COMMENT_API_NAME, operation = BlogConstants.PUT_OPERATION)
    @CachePut(value = "comment.update", key = "#commentId")
    public CommentResponse updateComment(@PathVariable(value = "postId") Long postId,
                                    @PathVariable(value = "commentId") Long commentId,
                                    @Valid @RequestBody CommentRequest commentRequest) {
        return commentService.updateComment(postId, commentId, commentRequest);
    }

    @DeleteMapping("/posts/{postId}/comments/{id}")
    @Audit(api = BlogConstants.COMMENT_API_NAME, operation = BlogConstants.DELETE_OPERATION)
    @CacheEvict(value = "comment.delete", allEntries = true)
    public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") Long postId,
                                    @PathVariable(value = "id") Long commentId) {
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
