package com.kirito.library.dto;


import java.util.Date;
import java.util.UUID;

public record PersonRequest (UUID id,

                             //@NotBlank(message = "Поле title не может быть пустым")
                             //@Size(min = 1, max = 100, message = "Длина name должна быть от 1 до 100 символов")
                             String name,

                             Date birthday) {

}
