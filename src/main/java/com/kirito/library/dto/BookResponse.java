package com.kirito.library.dto;

import java.util.Date;
import java.util.UUID;

public record BookResponse(UUID id, String title, String author, Date date) {
}
