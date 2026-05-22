package com.kirito.library.dto;

import java.util.Date;
import java.util.UUID;

public record BookRequest(UUID id, String title, String author, Date date) {
}
