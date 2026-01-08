// package com.estudosjava.course.services;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.times;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;

// import java.time.Instant;
// import java.util.Optional;
// import java.util.Set;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;

// import com.estudosjava.course.dto.OrderDTO;
// import com.estudosjava.course.dto.OrderInsertDTO;
// import com.estudosjava.course.dto.OrderItemDTO;
// import com.estudosjava.course.dto.OrderStatusDTO;
// import com.estudosjava.course.entities.Order;
// import com.estudosjava.course.entities.OrderItem;
// import com.estudosjava.course.entities.Product;
// import com.estudosjava.course.entities.User;
// import com.estudosjava.course.entities.enums.OrderStatus;
// import com.estudosjava.course.repositories.OrderItemRepository;
// import com.estudosjava.course.repositories.OrderRepository;
// import com.estudosjava.course.repositories.ProductRepository;
// import com.estudosjava.course.repositories.UserRepository;
// import com.estudosjava.course.services.exceptions.DatabaseException;
// import com.estudosjava.course.services.exceptions.ResourceNotFoundException;

// @ExtendWith(MockitoExtension.class)
// public class OrderServiceTests {

//     @InjectMocks
//     private OrderServices service;

//     @Mock
//     private OrderRepository repository;
//     @Mock
//     private UserRepository userRepository;
//     @Mock
//     private ProductRepository productRepository;
//     @Mock
//     private OrderItemRepository orderItemRepository;

//     private User client;
//     private Product product;
//     private Order order;
//     private OrderInsertDTO insertDTO;

//     @BeforeEach
//     void setUp() {
//         client = new User(1L, "Maria Brown", "maria@gmail.com", "988888888", "123456");
//         product = new Product(1L, "Laptop", "High end laptop", 2000.0, "");
        
//         order = new Order(1L, Instant.now(), OrderStatus.WAITING_PAYMENT, client);
        
//         OrderItem tempItem = new OrderItem(null, product, 2, product.getPrice());
        
//         OrderItemDTO itemDTO = new OrderItemDTO(
//             product.getId(), 
//             product.getName(), 
//             tempItem.getQuantity(), 
//             tempItem.getPrice(), 
//             tempItem.getSubTotal()
//         ); 
        
//         insertDTO = new OrderInsertDTO(Instant.now(), OrderStatus.WAITING_PAYMENT, 1L, Set.of(itemDTO));
//     }

//     @Test
//     public void insertShouldReturnOrderDTOWithCalculatedTotal() {
//         when(userRepository.getReferenceById(1L)).thenReturn(client);
//         when(repository.save(any())).thenReturn(order);
//         when(productRepository.getReferenceById(1L)).thenReturn(product);

//         OrderDTO result = service.insert(insertDTO);

//         assertNotNull(result);
//         assertEquals(4000.0, result.total());
//         verify(orderItemRepository, times(1)).save(any());
//         verify(repository, times(1)).save(any());
//     }

//     @Test
//     public void updateStatusShouldReturnOrderDTOWhenIdExists() {
//         OrderStatusDTO statusDTO = new OrderStatusDTO(OrderStatus.PAID);
//         when(repository.getReferenceById(1L)).thenReturn(order);
//         when(repository.save(any())).thenReturn(order);

//         OrderDTO result = service.update(1L, statusDTO);

//         assertNotNull(result);
//         assertEquals(OrderStatus.PAID, result.orderStatus());
//         verify(repository, times(1)).save(any());
//     }

//     @Test
//     public void cancelShouldUpdateStatusToCanceledWhenStatusIsWaitingPayment() {
//         when(repository.getReferenceById(1L)).thenReturn(order);
//         when(repository.save(any())).thenReturn(order);

//         OrderDTO result = service.cancel(1L);

//         assertNotNull(result);
//         assertEquals(OrderStatus.CANCELED, result.orderStatus());
//         verify(repository, times(1)).save(any());
//     }

//     @Test
//     public void cancelShouldThrowDatabaseExceptionWhenStatusIsNotWaitingPayment() {
//         order.setOrderStatus(OrderStatus.PAID);
//         when(repository.getReferenceById(1L)).thenReturn(order);

//         assertThrows(DatabaseException.class, () -> {
//             service.cancel(1L);
//         });
//     }

//     @Test
//     public void findByIdShouldReturnOrderDTOWhenIdExists() {
//         when(repository.findById(1L)).thenReturn(Optional.of(order));

//         OrderDTO result = service.findById(1L);

//         assertNotNull(result);
//         assertEquals(order.getId(), result.id());
//     }

//     @Test
//     public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
//         when(repository.findById(2L)).thenReturn(Optional.empty());

//         assertThrows(ResourceNotFoundException.class, () -> {
//             service.findById(2L);
//         });
//     }
// }