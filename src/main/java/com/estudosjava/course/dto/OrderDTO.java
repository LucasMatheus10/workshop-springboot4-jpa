package com.estudosjava.course.dto;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

import com.estudosjava.course.entities.Order;
import com.estudosjava.course.entities.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

public record OrderDTO(
    Long id,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    Instant moment,
    OrderStatus orderStatus,
    UserDTO client,
    Double total,
    Set<OrderItemDTO> items
) {
    public OrderDTO(Order order) {
        this(order.getId(), order.getMoment(), order.getOrderStatus(), new UserDTO(order.getClient()), order.getTotal(), order.getItems().stream().map(OrderItemDTO::new).collect(Collectors.toSet()));
    }
}
