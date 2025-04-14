package com.pagoda.shared.testfixtures;

import com.pagoda.core.model.Gender;
import com.pagoda.shared.model.PersonDTO;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;

@UtilityClass
public class PersonDTOFixture {

    public static PersonDTO.PersonDTOBuilder personDTOBuilder() {
        return PersonDTO.builder()
                .birthDate(LocalDate.of(1990, 1, 1))
                .birthPlace("HCM")
                .dharmaName("Lac Hanh")
                .email("lac@hanh.com")
                .ethnicity("Kinh")
                .gender(Gender.MALE.name())
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
                .temporaryResidence("Thu Duc");

    }

}
