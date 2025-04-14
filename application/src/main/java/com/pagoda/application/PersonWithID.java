package com.pagoda.application;

import com.pagoda.core.model.Person;

import java.util.UUID;

public record PersonWithID(
        Person person,
        UUID id
) {
}
