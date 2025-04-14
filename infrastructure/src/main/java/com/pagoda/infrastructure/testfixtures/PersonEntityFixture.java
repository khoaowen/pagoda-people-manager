package com.pagoda.infrastructure.testfixtures;

import com.pagoda.core.model.Gender;
import com.pagoda.infrastructure.entity.PersonEntity;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.UUID;

@UtilityClass
public class PersonEntityFixture {

    public static PersonEntity.PersonEntityBuilder personEntityBuilder() {
        return PersonEntity.builder()
                .id(UUID.randomUUID())
                .birthDate(LocalDate.of(1990, 1, 1))
                .birthPlace("HCM")
                .dharmaName("Lac Hanh")
                .email("lac@hanh.com")
                .ethnicity("Kinh")
                .gender(Gender.FEMALE.name())
                .firstName("Thomas")
                .lastName("Tuan")
                .hometown("Ho Chi Minh")
                .idIssueDate(LocalDate.of(2010, 1, 2))
                .idIssuePlace("Ho Chi Minh")
                .idNumber("123134145")
                .nationality("Viet Nam")
                .type("BUDDHIST")
                .ordinationDate(LocalDate.of(1991, 1, 1))
                .phoneNumber("0123344588")
                .permanentResidence("Ha Noi")
                .temporaryResidence("Thu Duc")
                .notes("no notes");
    }
}
