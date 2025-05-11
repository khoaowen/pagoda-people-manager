package com.pagoda.application;

import com.pagoda.core.model.Person;

import java.util.UUID;

public interface UpdatePersonCommandHandler {
    PersonWithID handle(UUID id, Person updated);
}
