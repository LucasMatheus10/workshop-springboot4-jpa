package com.estudosjava.course.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryInsertDTO(
    @NotBlank(message = "Nome é obrigatório")
    String name
) {}
