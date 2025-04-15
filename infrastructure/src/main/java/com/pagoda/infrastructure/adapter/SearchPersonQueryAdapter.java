package com.pagoda.infrastructure.adapter;

import com.pagoda.application.PersonWithID;
import com.pagoda.application.SearchPersonQuery;
import com.pagoda.infrastructure.entity.PersonEntity;
import com.pagoda.infrastructure.mapper.PersonEntityMapper;
import com.pagoda.infrastructure.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchPersonQueryAdapter implements SearchPersonQuery {
    private final PersonRepository personRepository;
    private final PersonEntityMapper mapper;

    @Override
    public List<PersonWithID> findByName(String name) {
        List<PersonEntity> entities = personRepository.findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCase(name, name);
        return entities.stream()
                .map(ent -> new PersonWithID(mapper.toDomain(ent), ent.getId()))
                .toList();
    }
}
