package com.estudosjava.course.dto;

import java.util.List;

import com.estudosjava.course.entities.User;

public record UserOrdersDTO(
    Long id,
    String name,
    String email,
    String phone,
    List<OrderDTO> orders
) {
    public UserOrdersDTO(User entity) {
        this(
            entity.getId(),
            entity.getName(),
            entity.getEmail(),
            entity.getPhone(),
            entity.getOrders().stream().map(OrderDTO::new).toList()
        );
    }
}
