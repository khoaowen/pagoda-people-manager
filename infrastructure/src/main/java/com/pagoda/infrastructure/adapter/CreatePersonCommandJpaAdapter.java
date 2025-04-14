package com.pagoda.infrastructure.adapter;

import com.pagoda.application.CreatePersonCommandHandler;
import com.pagoda.application.PersonWithID;
import com.pagoda.core.model.Person;
import com.pagoda.infrastructure.entity.PersonEntity;
import com.pagoda.infrastructure.mapper.PersonEntityMapper;
import com.pagoda.infrastructure.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatePersonCommandJpaAdapter implements CreatePersonCommandHandler {

    private final PersonRepository personRepository;
    private final PersonEntityMapper personEntityMapper;

    @Override
    public PersonWithID handle(Person person) {
        PersonEntity saved = personRepository.save(personEntityMapper.toEntity(person));
        Person domain = personEntityMapper.toDomain(saved);
        return new PersonWithID(domain, saved.getId());
    }
}
