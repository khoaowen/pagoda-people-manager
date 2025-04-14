package com.pagoda.application;

import com.pagoda.core.model.Person;

public interface CreatePersonCommandHandler {
    PersonWithID handle(Person person);
}