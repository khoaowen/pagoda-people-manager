package com.pagoda.shared.model;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record PersonDTO(
        UUID id,
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
