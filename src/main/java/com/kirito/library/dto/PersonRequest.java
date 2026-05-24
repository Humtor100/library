package com.kirito.library.dto;

import jakarta.validation.constraints.*;

public record PersonRequest (
                             @NotBlank(message = "Поле name не может быть пустым")
                             @Size(min = 1, max = 100, message = "Длина name должна быть от 1 до 100 символов")
                             String name,
                             @NotNull(message = "Год рождения обязателен")
                             @Min(1900)
                             @Max(2026)
                             Integer birthYear) {

}
