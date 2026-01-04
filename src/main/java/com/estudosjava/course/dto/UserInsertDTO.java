package com.estudosjava.course.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserInsertDTO(
    @NotBlank(message = "Nome é obrigatório")
    String name,
    @NotBlank(message = "Email é obrigatório")
    @jakarta.validation.constraints.Email
    String email,
    @NotBlank(message = "Telefone é obrigatório")
    String phone,
    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    String password
) {}
