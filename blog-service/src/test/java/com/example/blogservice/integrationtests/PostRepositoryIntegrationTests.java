package com.example.blogservice.integrationtests;

import com.example.blogservice.PostgresDatabaseContainerInitializer;
import com.example.blogservice.entity.Category;
import com.example.blogservice.entity.Comment;
import com.example.blogservice.entity.Post;
import com.example.blogservice.entity.Role;
import com.example.blogservice.repository.CategoryRepository;
import com.example.blogservice.repository.PostRepository;
import com.example.blogservice.repository.RoleRepository;
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
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = {PostgresDatabaseContainerInitializer.class})
public class PostRepositoryIntegrationTests {
    @Autowired
    private PostRepository postRepository;


    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        postRepository.deleteAll();
        Category category=new Category();
        category.setId(null);
        category.setName("test");
        category.setDescription("test_desc");
        category.setPosts(new ArrayList<Post>());
        entityManager.persist(category);
        Post post=new Post(null,"test","test_descp","test_content",new HashSet<Comment>(),category);
        entityManager.persist(post);
    }

    @Test
    @Transactional
    void findByCategoryIdTests() {
        List<Post> lists=postRepository.findAll();
        assertThat(lists.get(0).getCategory().getDescription()).isEqualTo("test_desc");
    }
}
