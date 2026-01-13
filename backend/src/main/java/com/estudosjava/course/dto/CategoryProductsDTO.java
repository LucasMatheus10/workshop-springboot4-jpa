package com.estudosjava.course.dto;

import java.util.List;

import com.estudosjava.course.entities.Category;

public record CategoryProductsDTO(
    Long id,
    String name,
    List<ProductDTO> products
) {
    public CategoryProductsDTO(Category category) {
        this(category.getId(), category.getName(), category.getProducts().stream().map(ProductDTO::new).toList());
    }
}