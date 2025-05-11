package com.pagoda.infrastructure.mapper;

import com.pagoda.core.model.Person;
import com.pagoda.infrastructure.entity.PersonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PersonEntityMapper {

    @Mapping(target = "id", ignore = true)
    PersonEntity toEntity(Person person);

    Person toDomain(PersonEntity entity);

    PersonEntity updateEntity(@MappingTarget PersonEntity existing, Person updated);
}
