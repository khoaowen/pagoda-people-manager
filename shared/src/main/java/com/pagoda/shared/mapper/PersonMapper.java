package com.pagoda.shared.mapper;

import com.pagoda.core.model.Person;
import com.pagoda.shared.model.PersonDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    PersonDTO toDto(Person person, UUID id);

    Person toDomain(PersonDTO dto);
}
