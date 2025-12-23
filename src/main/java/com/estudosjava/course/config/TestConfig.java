package com.estudosjava.course.config;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.estudosjava.course.entities.Category;
import com.estudosjava.course.entities.Order;
import com.estudosjava.course.entities.User;
import com.estudosjava.course.entities.enums.OrderStatus;
import com.estudosjava.course.repositories.CategoryRepository;
import com.estudosjava.course.repositories.OrderRepository;
import com.estudosjava.course.repositories.UserRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        User user = new User(null, "John Doe", "john.doe@email.com", "1234567890", "password");
        User user2 = new User(null, "Jane Smith", "jane.smith@email.com", "0987654321", "password123");

        Order order1 = new Order(null, Instant.parse("2019-06-01T10:00:00Z"), OrderStatus.WAITING_PAYMENT, user);
        Order order2 = new Order(null, Instant.parse("2024-06-02T11:30:00Z"), OrderStatus.PAID, user2);
        Order order3 = new Order(null, Instant.parse("2024-06-03T14:15:00Z"), OrderStatus.SHIPPED, user);

        Category category1 = new Category(null, "Electronics");
        Category category2 = new Category(null, "Books");
        Category category3 = new Category(null, "Clothing");

        categoryRepository.saveAll(Arrays.asList(category1, category2, category3));
        userRepository.saveAll(Arrays.asList(user, user2));
        orderRepository.saveAll(Arrays.asList(order1, order2, order3));
    }
}
