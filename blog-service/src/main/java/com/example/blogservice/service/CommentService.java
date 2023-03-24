package com.example.blogservice.service;

import com.example.blogservice.entity.Comment;
import com.example.blogservice.entity.Post;
import com.example.blogservice.exceptions.BlogServiceException;
import com.example.blogservice.exceptions.ResourceNotFoundException;
import com.example.blogservice.models.CommentRequest;
import com.example.blogservice.models.CommentResponse;
import com.example.blogservice.repository.CommentRepository;
import com.example.blogservice.repository.PostRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class CommentService {

    private static final String POST_NOT_FOUND = "Post not found";
    private static final String COMMENT_NOT_FOUND = "Comment not found";
    private static final String COMMENT_NOT_BELONG = "Comment does not belong to post";
    @Autowired
    private ModelMapper mapper;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    public CommentResponse createComment(long postId, CommentRequest commentRequest) {
        Comment comment = mapper.map(commentRequest, Comment.class);
        Post post = postRepository.findById(postId)
                                        .orElseThrow(() -> new ResourceNotFoundException("Post ID not found", "102"));
        comment.setPost(post);
        Comment commentResponse = commentRepository.save(comment);
        return mapper.map(commentResponse, CommentResponse.class);
    }

    public CommentRequest getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId)
                                        .orElseThrow(() -> new ResourceNotFoundException(POST_NOT_FOUND, "102"));
        Comment comment = commentRepository.findById(commentId)
                                        .orElseThrow(() -> new ResourceNotFoundException(POST_NOT_FOUND, "102"));
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogServiceException(COMMENT_NOT_BELONG, "102");
        }
        return mapper.map(comment, CommentRequest.class);
    }

    public CommentResponse updateComment(Long postId, long commentId, CommentRequest commentRequest) {
        Post post = postRepository.findById(postId)
                                        .orElseThrow(() -> new ResourceNotFoundException(POST_NOT_FOUND, "102"));
        Comment comment = commentRepository.findById(commentId)
                                        .orElseThrow(() -> new ResourceNotFoundException(COMMENT_NOT_FOUND, "id"));
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogServiceException(COMMENT_NOT_BELONG, "102");
        }
        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());
        Comment commentResponse=commentRepository.save(comment);
        return mapper.map(commentResponse, CommentResponse.class);
    }

    public void deleteComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId)
                                        .orElseThrow(() -> new ResourceNotFoundException(POST_NOT_FOUND, "id"));
        Comment comment = commentRepository.findById(commentId)
                                        .orElseThrow(() -> new ResourceNotFoundException(COMMENT_NOT_FOUND, "id"));
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogServiceException(COMMENT_NOT_BELONG, "102");
        }
        commentRepository.delete(comment);
    }
}
