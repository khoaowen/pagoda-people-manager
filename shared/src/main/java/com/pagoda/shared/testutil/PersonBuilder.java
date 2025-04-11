package com.pagoda.shared.testutil;

import com.pagoda.core.model.Gender;
import com.pagoda.core.model.Person;
import com.pagoda.core.model.PersonType;

import java.time.LocalDate;

public class PersonBuilder {
    private String lastName = "Nguyen";
    private String firstName = "Van A";
    private Gender gender = Gender.MALE;
    private LocalDate birthDate = LocalDate.of(1990, 1, 1);
    private String birthPlace = "Hanoi";
    private String permanentResidence = "123 Street";
    private String temporaryResidence = "456 Street";
    private String hometown = "Hue";
    private String nationality = "Vietnam";
    private String ethnicity = "Kinh";
    private String dharmaName = "Thien Tam";
    private LocalDate ordinationDate = LocalDate.of(2005, 5, 1);
    private String idNumber = "123456789";
    private LocalDate idIssueDate = LocalDate.of(2005, 5, 1);
    private String idIssuePlace = "Hanoi";
    private String email = "vana@example.com";
    private String phoneNumber = "0123456789";
    private String notes = "no notes";
    private PersonType type = PersonType.BUDDHIST;

    public PersonBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public PersonBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public PersonBuilder gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public PersonBuilder birthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public PersonBuilder type(PersonType type) {
        this.type = type;
        return this;
    }


    public Person build() {
        return new Person(
                lastName, firstName, gender, birthDate,
                birthPlace, permanentResidence, temporaryResidence, hometown,
                nationality, ethnicity, dharmaName, ordinationDate,
                idNumber, idIssueDate, idIssuePlace, email, phoneNumber,
                notes, type
        );
    }
}