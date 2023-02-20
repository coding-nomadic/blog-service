package com.example.blogservice.controller;

import com.example.blogservice.entity.Comment;
import com.example.blogservice.models.CommentRequest;
import com.example.blogservice.service.CommentService;

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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * 
     * @param postId
     * @param commentDto
     * @return
     */
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<Comment> createComment(@PathVariable(value = "postId") long postId,
                                    @Valid @RequestBody CommentRequest commentDto) {
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    /**
     * 
     * @param postId
     * @return
     */
    @GetMapping("/posts/{postId}/comments")
    public List<Comment> getCommentsByPostId(@PathVariable(value = "postId") Long postId) {
        return commentService.getCommentsByPostId(postId);
    }

    /**
     * 
     * @param postId
     * @param commentId
     * @return
     */
    @GetMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable(value = "postId") Long postId,
                                    @PathVariable(value = "id") Long commentId) {
        Comment commentDto = commentService.getCommentById(postId, commentId);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    /**
     * 
     * @param postId
     * @param commentId
     * @param commentRequest
     * @return
     */
    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable(value = "postId") Long postId,
                                    @PathVariable(value = "id") Long commentId,
                                    @Valid @RequestBody CommentRequest commentRequest) {
        Comment updatedComment = commentService.updateComment(postId, commentId, commentRequest);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    /**
     * 
     * @param postId
     * @param commentId
     * @return
     */
    @DeleteMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") Long postId,
                                    @PathVariable(value = "id") Long commentId) {
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
    }
}
