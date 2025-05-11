package com.pagoda.application;

import java.util.List;
import java.util.UUID;

public interface SearchPersonQuery {
    List<PersonWithID> findByName(String name);
    PersonWithID findById(UUID id);
    List<PersonWithID> findAll();
}
