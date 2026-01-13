package com.estudosjava.course.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductInsertDTO(
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

}
