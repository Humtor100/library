package com.kirito.library.dto;

import java.util.UUID;

// dto для книг человека
public record BookShortResponse(
        UUID bookId,
        String title,
        String author,
        Integer year,
        boolean expired
) {
}
