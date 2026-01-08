package com.estudosjava.course.dto;

import java.time.Instant;
import java.util.Set;
import com.estudosjava.course.entities.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

public record OrderInsertDTO(
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    Instant moment,
    OrderStatus orderStatus,
    Long clientId,
    Set<OrderItemDTO> items
) {}
