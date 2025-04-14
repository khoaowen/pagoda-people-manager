package com.pagoda.shared.mapper;

import com.pagoda.core.model.Gender;
import com.pagoda.core.model.Person;
import com.pagoda.core.model.PersonType;
import com.pagoda.shared.model.PersonDTO;
import com.pagoda.shared.testfixtures.PersonFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PersonMapperTest {

    private final PersonMapper mapper = PersonMapper.INSTANCE;

    @Test
    @DisplayName("Should map Person to DTO and back correctly")
    void testRoundTripMapping() {
        Person person = PersonFixture.builder()
                .firstName("Khoa")
                .gender(Gender.FEMALE)
                .type(PersonType.LAY_BROTHER)
                .build();
        UUID personId = UUID.randomUUID();

        PersonDTO dto = mapper.toDto(person, personId);
        assertThat(dto.id()).isEqualTo(personId);
        Person mapped = mapper.toDomain(dto);
        assertThat(mapped)
                .usingRecursiveComparison()
                .isEqualTo(person);
    }
}