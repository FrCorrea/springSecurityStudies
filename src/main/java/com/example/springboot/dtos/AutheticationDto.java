package com.example.springboot.dtos;

import jakarta.validation.constraints.NotBlank;

public record AutheticationDto(@NotBlank String login, String password) {
}
