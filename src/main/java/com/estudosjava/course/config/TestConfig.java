package com.estudosjava.course.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.estudosjava.course.entities.User;
import com.estudosjava.course.repositories.UserRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner{

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        User user = new User(null, "John Doe", "john.doe@email.com", "1234567890", "password");
        User user2 = new User(null, "Jane Smith", "jane.smith@email.com", "0987654321", "password123");
        userRepository.saveAll(Arrays.asList(user, user2));
    }
}
