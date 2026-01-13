package com.estudosjava.course.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO(
    @NotBlank String email, 
    @NotBlank String password
) {}