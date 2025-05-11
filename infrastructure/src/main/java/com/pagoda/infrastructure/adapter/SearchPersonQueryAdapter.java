package com.pagoda.infrastructure.adapter;

import com.pagoda.application.PersonWithID;
import com.pagoda.application.SearchPersonQuery;
import com.pagoda.application.exception.PersonNotFoundException;
import com.pagoda.infrastructure.entity.PersonEntity;
import com.pagoda.infrastructure.mapper.PersonEntityMapper;
import com.pagoda.infrastructure.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class SearchPersonQueryAdapter implements SearchPersonQuery {
    private final PersonRepository personRepository;
    private final PersonEntityMapper mapper;

    @Override
    public List<PersonWithID> findByName(String name) {
        List<PersonEntity> entities = personRepository.findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCase(name, name);
        return getList(entities.stream());
    }

    @Override
    public PersonWithID findById(UUID id) {
        return personRepository.findById(id)
                .map(this::entityToPersonWithId)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }

    @Override
    public List<PersonWithID> findAll() {
        List<PersonEntity> entities = personRepository.findAll();
        return getList(entities.stream());
    }

    private List<PersonWithID> getList(Stream<PersonEntity> entities) {
        return entities
                .map(this::entityToPersonWithId)
                .toList();
    }

    private PersonWithID entityToPersonWithId(PersonEntity ent) {
        return new PersonWithID(mapper.toDomain(ent), ent.getId());
    }
}
