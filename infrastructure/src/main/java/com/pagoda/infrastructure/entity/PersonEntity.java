package com.pagoda.infrastructure.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "person")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class PersonEntity {
    @Id
    @GeneratedValue
    private UUID id;

    private String lastName;
    private String firstName;
    private String gender;
    private LocalDate birthDate;
    private String birthPlace;
    private String permanentResidence;
    private String temporaryResidence;
    private String hometown;
    private String nationality;
    private String ethnicity;
    private String dharmaName;
    private LocalDate ordinationDate;
    private String idNumber;
    private LocalDate idIssueDate;
    private String idIssuePlace;
    private String email;
    private String phoneNumber;
    private String notes;
    private String type;
}
