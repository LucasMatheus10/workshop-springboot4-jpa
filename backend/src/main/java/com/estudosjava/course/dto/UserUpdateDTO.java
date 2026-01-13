package com.estudosjava.course.dto;

import com.estudosjava.course.entities.User;
import jakarta.validation.constraints.Email;

public record UserUpdateDTO(
    String name,
    @Email
    String email,
    String phone
) {  
    public UserUpdateDTO(User user) {
        this(user.getName(), user.getEmail(), user.getPhone());
    }
}
