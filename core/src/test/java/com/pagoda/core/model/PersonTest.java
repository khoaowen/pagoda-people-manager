package com.pagoda.core.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class PersonTest {

    @Test
    void shouldExposeProvidedAttributes() {
        LocalDate birthDate = LocalDate.of(1990, 5, 12);
        LocalDate ordinationDate = LocalDate.of(2010, 3, 8);
        LocalDate idIssueDate = LocalDate.of(2015, 7, 21);

        Person person = new Person(
                "Nguyen",
                "Van A",
                Gender.MALE,
                birthDate,
                "Hue",
                "Hanoi",
                "Ho Chi Minh City",
                "Da Nang",
                "Vietnam",
                "Kinh",
                "Thien Tam",
                ordinationDate,
                "123456789",
                idIssueDate,
                "Da Nang",
                "test@example.com",
                "0123456789",
                "Notes",
                PersonType.BUDDHIST
        );

        assertThat(person.lastName()).isEqualTo("Nguyen");
        assertThat(person.firstName()).isEqualTo("Van A");
        assertThat(person.gender()).isEqualTo(Gender.MALE);
        assertThat(person.birthDate()).isEqualTo(birthDate);
        assertThat(person.birthPlace()).isEqualTo("Hue");
        assertThat(person.permanentResidence()).isEqualTo("Hanoi");
        assertThat(person.temporaryResidence()).isEqualTo("Ho Chi Minh City");
        assertThat(person.hometown()).isEqualTo("Da Nang");
        assertThat(person.nationality()).isEqualTo("Vietnam");
        assertThat(person.ethnicity()).isEqualTo("Kinh");
        assertThat(person.dharmaName()).isEqualTo("Thien Tam");
        assertThat(person.ordinationDate()).isEqualTo(ordinationDate);
        assertThat(person.idNumber()).isEqualTo("123456789");
        assertThat(person.idIssueDate()).isEqualTo(idIssueDate);
        assertThat(person.idIssuePlace()).isEqualTo("Da Nang");
        assertThat(person.email()).isEqualTo("test@example.com");
        assertThat(person.phoneNumber()).isEqualTo("0123456789");
        assertThat(person.notes()).isEqualTo("Notes");
        assertThat(person.type()).isEqualTo(PersonType.BUDDHIST);
    }

    @Test
    void shouldImplementValueEquality() {
        Person first = new Person(
                "Tran",
                "Thi B",
                Gender.FEMALE,
                LocalDate.of(1985, 1, 1),
                "Hue",
                "Hue",
                "Hue",
                "Hue",
                "Vietnam",
                "Kinh",
                "Dieu An",
                LocalDate.of(2005, 6, 15),
                "ABC123456",
                LocalDate.of(2010, 8, 20),
                "Hue",
                "another@example.com",
                "0987654321",
                null,
                PersonType.LAY_BROTHER
        );

        Person second = new Person(
                "Tran",
                "Thi B",
                Gender.FEMALE,
                LocalDate.of(1985, 1, 1),
                "Hue",
                "Hue",
                "Hue",
                "Hue",
                "Vietnam",
                "Kinh",
                "Dieu An",
                LocalDate.of(2005, 6, 15),
                "ABC123456",
                LocalDate.of(2010, 8, 20),
                "Hue",
                "another@example.com",
                "0987654321",
                null,
                PersonType.LAY_BROTHER
        );

        assertThat(first).isEqualTo(second);
        assertThat(first).hasSameHashCodeAs(second);
    }
}
