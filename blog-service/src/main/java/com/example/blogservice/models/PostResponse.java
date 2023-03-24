package com.example.blogservice.models;

import java.io.Serializable;
import java.util.Set;

import lombok.Data;

@Data
public class PostResponse implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private long id;

    private String title;

    private String description;

    private String content;
    
    private Set<CommentRequest> comments;

    private Long categoryId;

}
