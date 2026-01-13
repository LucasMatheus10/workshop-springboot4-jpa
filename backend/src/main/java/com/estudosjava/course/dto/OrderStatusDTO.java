package com.estudosjava.course.dto;

import com.estudosjava.course.entities.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record OrderStatusDTO(
    @NotNull(message = "O status do pedido é obrigatório")
    OrderStatus orderStatus
){}
