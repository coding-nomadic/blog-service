package com.example.blogservice.models;

import java.io.Serializable;

import lombok.Data;

@Data
public class CategoryRequest implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String description;
}
