package com.example.demo_mvc;

import com.example.demo_mvc.user.User;
import com.example.demo_mvc.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
    @Autowired private UserRepository repo;

    @Test
    public void testAddNew() {
        User user = new User();
        user.setEmail("thuha@gmail.com");
        user.setPassword("123");
        user.setFirstName("Ho");
        user.setLastName("Thu Ha");

        User savedUser = repo.save(user);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAll() {
        Iterable<User> users = repo.findAll();
        Assertions.assertThat(users).hasSizeGreaterThan(0);
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void testUpdate() {
        Integer userId = 1;
        Optional<User> userOptional = repo.findById(userId);
        User user = userOptional.get();
        user.setPassword("hovanhuy");
        repo.save(user);

        User updatedUser = repo.findById(userId).get();
        Assertions.assertThat(updatedUser.getPassword()).isEqualTo("hovanhuy");
    }

    @Test
    public void testGet() {
        Integer userId = 1;
        Optional<User> userOptional = repo.findById(userId);
        Assertions.assertThat(userOptional).isPresent();
        System.out.println(userOptional.get());
    }
    @Test
    public void testDelete() {
        Integer userId = 1;
        repo.deleteById(userId);

        Optional<User> userOptional = repo.findById(userId);
        Assertions.assertThat(userOptional).isNotPresent();

    }
}
