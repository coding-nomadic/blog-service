package com.example.blogservice.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tbl_comment")
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String email;
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

}
