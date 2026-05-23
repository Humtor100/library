package com.kirito.library.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record BookResponse(
        UUID id,
        String title,
        String author,
        Integer date,
        UUID ownerId,
        String ownerName,
        LocalDateTime takenAt
) {
}