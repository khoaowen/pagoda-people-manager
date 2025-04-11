package com.pagoda.shared.mapper;

import com.pagoda.core.model.Gender;
import com.pagoda.core.model.Person;
import com.pagoda.core.model.PersonType;
import com.pagoda.shared.model.PersonDTO;
import com.pagoda.shared.testutil.PersonBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PersonMapperTest {

    private final PersonMapper mapper = PersonMapper.INSTANCE;

    @Test
    @DisplayName("Should map Person to DTO and back correctly")
    void testRoundTripMapping() {
        Person person = new PersonBuilder()
                .firstName("Khoa")
                .gender(Gender.FEMALE)
                .type(PersonType.LAY_BROTHER)
                .build();

        PersonDTO dto = mapper.toDto(person);
        Person mapped = mapper.toEntity(dto);

        assertThat(mapped)
                .usingRecursiveComparison()
                .isEqualTo(person);
    }
}