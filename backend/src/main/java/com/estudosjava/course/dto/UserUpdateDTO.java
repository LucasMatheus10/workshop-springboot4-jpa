package com.estudosjava.course.dto;

import com.estudosjava.course.entities.User;
import jakarta.validation.constraints.Email;
import com.estudosjava.course.entities.enums.UserRole;
public record UserUpdateDTO(
    String name,
    @Email
    String email,
    String phone,
    UserRole role
) {  
    public UserUpdateDTO(User user) {
        this(user.getName(), user.getEmail(), user.getPhone(), user.getRole());
    }
}
