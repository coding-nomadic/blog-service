package com.example.blogservice.service;

import com.example.blogservice.entity.Comment;
import com.example.blogservice.entity.Post;
import com.example.blogservice.exceptions.BlogServiceException;
import com.example.blogservice.exceptions.ResourceNotFoundException;
import com.example.blogservice.models.CommentRequest;
import com.example.blogservice.repository.CommentRepository;
import com.example.blogservice.repository.PostRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private static final String POST_NOT_FOUND = "Post not found";
    private static final String COMMENT_NOT_FOUND = "Comment not found";
    private static final String COMMENT_NOT_BELONG = "Comment does not belong to post";
    @Autowired
    private ModelMapper mapper;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Override
    public Comment createComment(long postId, CommentRequest commentRequest) {
        Comment comment = mapper.map(commentRequest, Comment.class);
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post ID not found", "102"));
        comment.setPost(post);
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getCommentsByPostId(long postId) {
        return commentRepository.findByPostId(postId);
    }

    @Override
    public Comment getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(POST_NOT_FOUND, "102"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException(POST_NOT_FOUND, "102"));
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogServiceException(COMMENT_NOT_BELONG, "102");
        }
        return comment;
    }

    @Override
    public Comment updateComment(Long postId, long commentId, CommentRequest commentRequest) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(POST_NOT_FOUND, "102"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException(COMMENT_NOT_FOUND, "id"));
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogServiceException(COMMENT_NOT_BELONG, "102");
        }
        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(POST_NOT_FOUND, "id"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException(COMMENT_NOT_FOUND, "id"));
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogServiceException(COMMENT_NOT_BELONG, "102");
        }
        commentRepository.delete(comment);
    }
}
