package com.estudosjava.course.dto;

import com.estudosjava.course.entities.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDTO(
    @NotNull
    Long id,
    @NotBlank(message = "Nome é obrigatório")
    String name,
    @NotBlank(message = "Email é obrigatório")
    @Email
    String email,
    @NotBlank(message = "Telefone é obrigatório")
    String phone
) {  
    public UserDTO(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getPhone());
    }
}
