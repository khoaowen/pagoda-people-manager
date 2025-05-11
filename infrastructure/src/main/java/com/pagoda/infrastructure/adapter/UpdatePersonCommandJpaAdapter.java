package com.pagoda.infrastructure.adapter;

import com.pagoda.application.PersonWithID;
import com.pagoda.application.UpdatePersonCommandHandler;
import com.pagoda.application.exception.PersonNotFoundException;
import com.pagoda.core.model.Person;
import com.pagoda.infrastructure.entity.PersonEntity;
import com.pagoda.infrastructure.mapper.PersonEntityMapper;
import com.pagoda.infrastructure.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdatePersonCommandJpaAdapter implements UpdatePersonCommandHandler {
    private final PersonRepository personRepository;
    private final PersonEntityMapper mapper;

    @Override
    public PersonWithID handle(UUID id, Person updated) {
        PersonEntity existing = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
        PersonEntity updatedEntity = mapper.updateEntity(existing, updated);
        PersonEntity saved = personRepository.save(updatedEntity);
        return new PersonWithID(mapper.toDomain(saved), saved.getId());
    }
}
