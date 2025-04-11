package com.pagoda.application;

import com.pagoda.core.model.Person;

public interface CreatePersonCommandHandler {
    Person handle(Person person);
}
