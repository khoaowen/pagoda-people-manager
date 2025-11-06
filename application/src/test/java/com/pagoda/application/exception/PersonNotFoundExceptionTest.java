package com.pagoda.application.exception;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PersonNotFoundExceptionTest {

    @Test
    void shouldExposeHelpfulErrorMessage() {
        UUID id = UUID.randomUUID();
        PersonNotFoundException exception = new PersonNotFoundException(id);

        assertThat(exception.getMessage()).isEqualTo("Person not found with ID: " + id);
    }
}
