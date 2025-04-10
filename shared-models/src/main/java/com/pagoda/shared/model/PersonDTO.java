package com.pagoda.shared.model;

import java.time.LocalDate;

public record PersonDTO(
        String lastName,
        String firstName,
        String gender,
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
        String type
) {
}
