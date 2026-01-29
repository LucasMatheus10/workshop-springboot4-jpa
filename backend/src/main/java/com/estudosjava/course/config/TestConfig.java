package com.estudosjava.course.config;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.estudosjava.course.entities.Category;
import com.estudosjava.course.entities.Order;
import com.estudosjava.course.entities.OrderItem;
import com.estudosjava.course.entities.Payment;
import com.estudosjava.course.entities.Product;
import com.estudosjava.course.entities.User;
import com.estudosjava.course.entities.enums.OrderStatus;
import com.estudosjava.course.entities.enums.UserRole;
import com.estudosjava.course.repositories.CategoryRepository;
import com.estudosjava.course.repositories.OrderItemRepository;
import com.estudosjava.course.repositories.OrderRepository;
import com.estudosjava.course.repositories.ProductRepository;
import com.estudosjava.course.repositories.UserRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public void run(String... args) throws Exception {
        
        Category cat1 = new Category(null, "Electronics");
        Category cat2 = new Category(null, "Books");
        Category cat3 = new Category(null, "Clothing");

        Product p1 = new Product(null, "The Lord of the Rings", "A fantasy novel", 90.5, "https://images.unsplash.com/photo-1544947950-fa07a98d237f?auto=format&fit=crop&q=80&w=1000");
        Product p2 = new Product(null, "Smart TV", "42 inch smart TV", 2190.0, "https://www.hisense.com.br/_next/image?url=%2FA4N%2F1.png&w=2048&q=100");
        Product p3 = new Product(null, "Macbook Pro", "Apple laptop", 6250.0, "https://t4.ftcdn.net/jpg/06/01/14/23/360_F_601142328_VnY6DMf1sC0RULodemaCSrvXSlFhO1lA.jpg");
        Product p4 = new Product(null, "PC Gamer", "High performance gaming PC", 3200.0, "https://images.unsplash.com/photo-1587202372775-e229f172b9d7?auto=format&fit=crop&q=80&w=1000");
        Product p5 = new Product(null, "Rails for Dummies", "A beginner's guide to Rails", 100.99, "https://images.unsplash.com/photo-1516116216624-53e697fedbea?auto=format&fit=crop&q=80&w=1000");
        Product p6 = new Product(null, "Headset", "Noise-cancelling headphones", 299.99, "https://www.pngall.com/wp-content/uploads/5/Headset-PNG-Image-HD.png");
        Product p7 = new Product(null, "Camiseta Tech", "Camiseta algodão 100%", 59.90, "https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?q=80&w=1000");
        Product p8 = new Product(null, "Moletom Gamer", "Moletom com capuz confortável", 150.0, "https://images.unsplash.com/photo-1556821840-3a63f95609a7?q=80&w=1000");
        Product p9 = new Product(null, "Boné Estilizado", "Boné aba curva preto", 45.0, "https://images.unsplash.com/photo-1588850561407-ed78c282e89b?q=80&w=1000");

        categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3));
        productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9));

        p1.getCategories().add(cat2);
        p2.getCategories().add(cat1);
        p3.getCategories().add(cat1);
        p4.getCategories().add(cat1);
        p5.getCategories().add(cat2);
        p6.getCategories().add(cat1);
        p7.getCategories().add(cat3);
        p8.getCategories().add(cat3);
        p9.getCategories().add(cat3);

        productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9));

        User u1 = new User(null, "John Doe", "john.doe@email.com", "1234567890", passwordEncoder.encode("password"));
        u1.setRole(UserRole.ADMIN);

        User u2 = new User(null, "Jane Smith", "jane.smith@email.com", "0987654321", passwordEncoder.encode("password123"));
        u2.setRole(UserRole.USER);

        userRepository.saveAll(Arrays.asList(u1, u2));

        Order o1 = new Order();
        o1.setClient(u1);
        
        Order o2 = new Order(null, Instant.parse("2024-06-02T11:30:00Z"), OrderStatus.WAITING_PAYMENT, u2);
        Order o3 = new Order(null, Instant.parse("2024-06-03T14:15:00Z"), OrderStatus.SHIPPED, u1);

        orderRepository.saveAll(Arrays.asList(o1, o2, o3));

        OrderItem oi1 = new OrderItem(o1, p1, 2, p1.getPrice());
        OrderItem oi2 = new OrderItem(o1, p3, 1, p3.getPrice());
        OrderItem oi3 = new OrderItem(o2, p3, 2, p3.getPrice());
        OrderItem oi4 = new OrderItem(o3, p5, 2, p5.getPrice());

        orderItemRepository.saveAll(Arrays.asList(oi1, oi2, oi3, oi4));

        Payment pay1 = new Payment(null, Instant.now(), o1);
        o1.setPayment(pay1);
        o1.setOrderStatus(OrderStatus.PAID);

        orderRepository.save(o1);
    }
}