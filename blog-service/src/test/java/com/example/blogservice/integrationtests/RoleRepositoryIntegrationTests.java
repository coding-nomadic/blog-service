package com.example.blogservice.integrationtests;

import com.example.blogservice.PostgresDatabaseContainerInitializer;
import com.example.blogservice.entity.Role;
import com.example.blogservice.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = {PostgresDatabaseContainerInitializer.class})
public class RoleRepositoryIntegrationTests {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        roleRepository.deleteAll();
        Role role=new Role();
        role.setName("Tenzin");
        entityManager.persist(role);
    }

    @Test
    void getByNameTests() {
        Optional<Role> role = roleRepository.findByName("Tenzin");
        assertThat(role.get().getName()).hasSize(6);
        assertThat(role.get().getName()).isEqualTo("Tenzin");
    }
}
