package com.estudosjava.course.dto;

import java.util.Set;

import com.estudosjava.course.entities.Product;

import jakarta.validation.constraints.Positive;

public record ProductUpdateDTO(
    String name,
    String description,
    @Positive(message = "Preço deve ser um valor positivo")
    Double price,
    String imgUrl,
    Set<CategoryDTO> categories
) {
    public ProductUpdateDTO (Product product) {
        this(product.getName(), product.getDescription(), product.getPrice(), product.getImgUrl(), product.getCategories().stream().map(CategoryDTO::new).collect(java.util.stream.Collectors.toSet()));
    }
}
