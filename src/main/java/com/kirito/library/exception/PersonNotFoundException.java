package com.kirito.library.exception;

import java.util.UUID;

public class PersonNotFoundException extends RuntimeException {

    public PersonNotFoundException(UUID personId) {
        super("Person with id " + personId + " not found");
    }
}
