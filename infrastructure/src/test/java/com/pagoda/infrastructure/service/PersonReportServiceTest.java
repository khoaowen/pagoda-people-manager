package com.pagoda.infrastructure.service;

import com.pagoda.application.PersonWithID;
import com.pagoda.shared.testfixtures.PersonFixture;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PersonReportServiceTest {

    private PersonReportService personReportService = new PersonReportService();

    @Test
    void generateSinglePersonReport() {
        // given
        PersonWithID personWithID = new PersonWithID(PersonFixture.builder().build(), UUID.randomUUID());
        // when
        byte[] bytes = personReportService.generateSinglePersonReport(personWithID);
        // then
        assertThat(bytes).isNotNull();
    }

    @Test
    void generatePersonListReport() {
        // given
        PersonWithID personWithID1 = new PersonWithID(PersonFixture.builder().build(), UUID.randomUUID());
        PersonWithID personWithID2 = new PersonWithID(PersonFixture.builder().build(), UUID.randomUUID());
        // when
        byte[] bytes = personReportService.generatePersonListReport(List.of(personWithID1, personWithID2));
        // then
        assertThat(bytes).isNotNull();
    }
}