package com.example.springboot.dtos;

import com.example.springboot.models.user.UserRole;
import jakarta.validation.constraints.NotBlank;

public record RegisterDto(@NotBlank String login, String password, UserRole role) {
}
