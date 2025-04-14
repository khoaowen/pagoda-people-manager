package com.pagoda.infrastructure.mapper;

import com.pagoda.core.model.Person;
import com.pagoda.infrastructure.entity.PersonEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonEntityMapper {
    PersonEntity toEntity(Person person);

    Person toDomain(PersonEntity entity);
}
