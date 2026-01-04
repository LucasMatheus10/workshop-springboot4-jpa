package com.estudosjava.course.dto;

import java.util.Set;

import com.estudosjava.course.entities.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductDTO(
    @NotNull
    Long id,
    @NotBlank(message = "Nome é obrigatório")
    String name,
    @NotBlank(message = "Descrição é obrigatória")
    String description,
    @NotNull(message = "Preço é obrigatório")
    @Positive(message = "Preço deve ser um valor positivo")
    Double price,
    String imgUrl,
    Set<CategoryDTO> categories
) {
    public ProductDTO(Product product) {
        this(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getImgUrl(), product.getCategories().stream().map(CategoryDTO::new).collect(java.util.stream.Collectors.toSet()));
    }
}
