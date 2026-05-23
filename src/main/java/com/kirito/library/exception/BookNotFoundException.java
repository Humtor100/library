package com.kirito.library.exception;

import java.util.UUID;

public class BookNotFoundException extends RuntimeException{

    public BookNotFoundException(UUID bookId) {
        super("Book with id " + bookId + " not found");
    }
}
