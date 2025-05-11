package com.pagoda.infrastructure.mapper;

import com.pagoda.core.model.Person;
import com.pagoda.infrastructure.entity.PersonEntity;
import com.pagoda.infrastructure.testfixtures.PersonEntityFixture;
import com.pagoda.shared.testfixtures.PersonFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class PersonEntityMapperTest {
    private final PersonEntityMapper mapper = Mappers.getMapper(PersonEntityMapper.class);

    @Test
    @DisplayName("Should map PersonEntity to Domain and back correctly")
    void testRoundTripMapping() {
        // given
        PersonEntity entity = PersonEntityFixture.personEntityBuilder().build();

        // when
        Person domain = mapper.toDomain(entity);
        PersonEntity mapped = mapper.toEntity(domain);

        // then
        assertThat(mapped)
                .usingRecursiveComparison()
                .ignoringFields("id") // UUID may be regenerated
                .isEqualTo(entity);
    }

    @Test
    @DisplayName("Should update Entity")
    void testUpdateEntity() {
        // given
        PersonEntity existingEntity = PersonEntityFixture.personEntityBuilder().build();
        Person update = PersonFixture.builder().build();

        // when
        PersonEntity updatedEntity = mapper.updateEntity(existingEntity, update);

        // then
        assertThat(updatedEntity)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(mapper.toEntity(update));
        assertThat(updatedEntity.getId()).isEqualTo(existingEntity.getId());

    }
}