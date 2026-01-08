package com.estudosjava.course.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.estudosjava.course.dto.OrderDTO;
import com.estudosjava.course.dto.OrderInsertDTO;
import com.estudosjava.course.dto.OrderItemDTO;
import com.estudosjava.course.dto.OrderStatusDTO;
import com.estudosjava.course.dto.OrderSummaryDTO;
import com.estudosjava.course.entities.Order;
import com.estudosjava.course.entities.OrderItem;
import com.estudosjava.course.entities.Product;
import com.estudosjava.course.entities.User;
import com.estudosjava.course.entities.enums.OrderStatus;
import com.estudosjava.course.repositories.OrderItemRepository;
import com.estudosjava.course.repositories.OrderRepository;
import com.estudosjava.course.repositories.ProductRepository;
import com.estudosjava.course.repositories.UserRepository;
import com.estudosjava.course.services.exceptions.DatabaseException;
import com.estudosjava.course.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServices {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    public List<OrderSummaryDTO> findAll() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(OrderSummaryDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        return new OrderDTO(order);
    }

    @Transactional
    public OrderDTO insert(OrderInsertDTO dto) {
        Order order = new Order();
        order.setMoment(dto.moment());
        order.setOrderStatus(dto.orderStatus());
        User client = userRepository.getReferenceById(dto.clientId());
        order.setClient(client);
        order = orderRepository.save(order);

        for (OrderItemDTO itemDto : dto.items()) {
            Product product = productRepository.getReferenceById(itemDto.productId());
            OrderItem item = new OrderItem(order, product, itemDto.quantity(), product.getPrice());
            orderItemRepository.save(item);
            order.getItems().add(item);
        }
        return new OrderDTO(order);
    }

    public OrderDTO update(Long id, OrderStatusDTO status) {
        try {
            Order entity = orderRepository.getReferenceById(id);
            entity.setOrderStatus(status.orderStatus());
            entity = orderRepository.save(entity);
            return new OrderDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    @Transactional
    public OrderDTO cancel(Long id) {
        try {
            Order entity = orderRepository.getReferenceById(id);
            
            if (entity.getOrderStatus() != OrderStatus.WAITING_PAYMENT) {
                throw new DatabaseException("Não é possível cancelar um pedido que já foi processado ou pago.");
            }

            entity.setOrderStatus(OrderStatus.CANCELED);
            entity = orderRepository.save(entity);
            return new OrderDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }
}
