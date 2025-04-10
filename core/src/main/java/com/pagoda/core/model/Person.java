package com.pagoda.core.model;

import java.time.LocalDate;

public record Person(
        String lastName,
        String firstName,
        Gender gender,
        LocalDate birthDate,
        String birthPlace,
        String permanentResidence,
        String temporaryResidence,
        String hometown,
        String nationality,
        String ethnicity,
        String dharmaName,
        LocalDate ordinationDate,
        String idNumber,
        LocalDate idIssueDate,
        String idIssuePlace,
        String email,
        String phoneNumber,
        String notes,
        PersonType type) {
}

