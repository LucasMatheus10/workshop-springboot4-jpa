package com.estudosjava.course.dto;

import java.util.Set;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record OrderInsertDTO(
    @NotNull(message = "O ID do cliente é obrigatório")
    Long clientId,
    @NotEmpty(message = "O pedido deve conter ao menos um item")
    Set<OrderItemDTO> items
) {}
