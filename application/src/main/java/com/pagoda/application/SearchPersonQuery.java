package com.pagoda.application;

import java.util.List;

public interface SearchPersonQuery {
    List<PersonWithID> findByName(String name);
}
