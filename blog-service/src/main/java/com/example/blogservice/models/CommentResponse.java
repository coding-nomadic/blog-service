package com.example.blogservice.models;

import java.io.Serializable;

import lombok.Data;

@Data
public class CommentResponse implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private long id;

    private String name;

    private String email;

    private String body;
}
