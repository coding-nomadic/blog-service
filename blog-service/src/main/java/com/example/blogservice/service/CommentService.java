package com.example.blogservice.service;

import com.example.blogservice.entity.Comment;
import com.example.blogservice.models.CommentRequest;

import java.util.List;

public interface CommentService {

    /**
     * 
     * @param postId
     * @param commentRequest
     * @return
     */
    Comment createComment(long postId, CommentRequest commentRequest);

    /**
     * 
     * @param postId
     * @return
     */
    List<Comment> getCommentsByPostId(long postId);

    /**
     * 
     * @param postId
     * @param commentId
     * @return
     */
    Comment getCommentById(Long postId, Long commentId);

    /**
     * 
     * @param postId
     * @param commentId
     * @param commentRequest
     * @return
     */
    Comment updateComment(Long postId, long commentId, CommentRequest commentRequest);

    /**
     * 
     * @param postId
     * @param commentId
     */
    void deleteComment(Long postId, Long commentId);

}
