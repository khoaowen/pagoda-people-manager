package com.pagoda.application.exception;

import java.util.UUID;

public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException(UUID id) {
        super("Person not found with ID: " + id);
    }
}
