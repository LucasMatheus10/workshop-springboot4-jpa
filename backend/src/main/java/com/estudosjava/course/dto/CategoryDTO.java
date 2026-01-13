package com.estudosjava.course.dto;

import com.estudosjava.course.entities.Category;

public record CategoryDTO(
    Long id,
    String name
) {
    public CategoryDTO(Category category) {
        this(category.getId(), category.getName());
    }
}
