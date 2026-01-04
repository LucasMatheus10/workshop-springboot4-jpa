package com.estudosjava.course.dto;

import com.estudosjava.course.entities.OrderItem;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderItemDTO(
    @NotNull
    Long productId,
    @NotBlank(message = "Nome do produto é obrigatório")
    String productName,
    @Positive(message = "Quantidade deve ser um valor positivo")
    @NotNull(message = "Quantidade é obrigatória")
    Integer quantity,
    @Positive(message = "Preço deve ser um valor positivo")
    @NotNull(message = "Preço é obrigatório")
    Double price,
    @Positive(message = "Subtotal deve ser um valor positivo")
    @NotNull(message = "Subtotal é obrigatório")
    Double subtotal
) {
    public OrderItemDTO(OrderItem orderItem) {
        this(orderItem.getProduct().getId(), orderItem.getProduct().getName(), orderItem.getQuantity(), orderItem.getPrice(), orderItem.getSubTotal());
    }
}
