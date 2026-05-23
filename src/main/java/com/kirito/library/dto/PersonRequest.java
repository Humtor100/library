package com.kirito.library.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record PersonRequest (UUID personId,

                             @NotBlank(message = "Поле name не может быть пустым")
                             @Size(min = 1, max = 100, message = "Длина name должна быть от 1 до 100 символов")
                             String name,
                             @Min(1900)
                             @Max(2026)
                             Integer birthday) {

}
