package com.pagoda.infrastructure.mapper;

import com.pagoda.core.model.Person;
import com.pagoda.infrastructure.entity.PersonEntity;
import com.pagoda.infrastructure.testfixtures.PersonEntityFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class PersonEntityMapperTest {
    private final PersonEntityMapper mapper = Mappers.getMapper(PersonEntityMapper.class);

    @Test
    @DisplayName("Should map PersonEntity to Domain and back correctly")
    void testRoundTripMapping() {
        PersonEntity entity = PersonEntityFixture.personEntityBuilder().build();

        Person domain = mapper.toDomain(entity);
        PersonEntity mapped = mapper.toEntity(domain);

        assertThat(mapped)
                .usingRecursiveComparison()
                .ignoringFields("id") // UUID may be regenerated
                .isEqualTo(entity);
    }
}