package com.kirito.library.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Date;
import java.util.UUID;

public record BookRequest(UUID bookId,
                          @NotBlank(message = "Поле title не может быть пустым")
                          @Size(min = 1, max = 100, message = "Длина title должна быть от 1 до 100 символов")
                          String title,
                          @NotBlank(message = "Поле author не может быть пустым")
                          @Size(min = 1, max = 100, message = "Длина name должна быть от 1 до 100 символов")
                          String author,
                          @Min(1000)
                          @Max(2026)
                          Integer date) {
}
