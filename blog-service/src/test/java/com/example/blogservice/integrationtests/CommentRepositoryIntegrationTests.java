package com.example.blogservice.integrationtests;

import com.example.blogservice.PostgresDatabaseContainerInitializer;
import com.example.blogservice.entity.Category;
import com.example.blogservice.entity.Comment;
import com.example.blogservice.entity.Post;
import com.example.blogservice.repository.CommentRepository;
import com.example.blogservice.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = {PostgresDatabaseContainerInitializer.class})
public class CommentRepositoryIntegrationTests {
    @Autowired
    private CommentRepository commentRepository;


    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        commentRepository.deleteAll();
        Comment comment=new Comment();
        comment.setBody("test");
        comment.setName("test");
        comment.setEmail("test");
        comment.setPost(new Post(null,"test","test_descp","test_content",new HashSet<Comment>(),new Category()));
        entityManager.persist(comment);
    }

    @Test
    @Transactional
    void findByCategoryIdTests() {
        List<Comment> lists=commentRepository.findAll();
        assertThat(lists.get(0).getName()).isEqualTo("test");
    }
}
