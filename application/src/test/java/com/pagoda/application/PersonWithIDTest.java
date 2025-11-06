package com.pagoda.application;

import com.pagoda.core.model.Gender;
import com.pagoda.core.model.Person;
import com.pagoda.core.model.PersonType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PersonWithIDTest {

    @Test
    void shouldKeepPersonAndId() {
        Person person = new Person(
                "Ho",
                "Van C",
                Gender.MALE,
                LocalDate.of(1992, 2, 2),
                "Hue",
                "Hue",
                "Hue",
                "Hue",
                "Vietnam",
                "Kinh",
                "Thien Hoa",
                LocalDate.of(2015, 5, 5),
                "ID123",
                LocalDate.of(2016, 6, 6),
                "Hue",
                "person@example.com",
                "0909123456",
                null,
                PersonType.BUDDHIST
        );
        UUID id = UUID.randomUUID();

        PersonWithID personWithID = new PersonWithID(person, id);

        assertThat(personWithID.person()).isSameAs(person);
        assertThat(personWithID.id()).isEqualTo(id);
    }
}
