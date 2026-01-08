package com.estudosjava.course.dto;

import java.time.Instant;
import com.estudosjava.course.entities.Order;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.estudosjava.course.entities.enums.OrderStatus;

public record OrderSummaryDTO(
    Long id,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    Instant moment,
    OrderStatus orderStatus,
    String clientName,
    Double total
) {
    public OrderSummaryDTO(Order order) {
        this(order.getId(), order.getMoment(), order.getOrderStatus(), order.getClient().getName(), order.getTotal());
    }
}
