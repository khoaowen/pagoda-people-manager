package com.pagoda.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pagoda.application.CreatePersonCommandHandler;
import com.pagoda.application.PersonWithID;
import com.pagoda.core.model.Person;
import com.pagoda.shared.mapper.PersonMapper;
import com.pagoda.shared.model.PersonDTO;
import com.pagoda.shared.testfixtures.PersonDTOFixture;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
@Import(PersonControllerTest.TestConfig.class)
class PersonControllerTest {

    @MockitoBean
    CreatePersonCommandHandler createHandler;

    @Autowired
    PersonMapper personMapper;

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public PersonMapper personMapper() {
            return Mappers.getMapper(PersonMapper.class);
        }
    }

    @SneakyThrows
    @Test
    void shouldCreatePerson() {
        // given
        PersonDTO input = PersonDTOFixture.personDTOBuilder().build();
        Person created = personMapper.toDomain(input);
        UUID id = UUID.randomUUID();
        given(createHandler.handle(created)).willReturn(new PersonWithID(created, id));

        PersonDTO expected = PersonDTOFixture.personDTOBuilder()
                .id(id)
                .firstName(input.firstName())
                .lastName(input.lastName())
                .birthDate(input.birthDate())
                .birthPlace(input.birthPlace())
                .permanentResidence(input.permanentResidence())
                .temporaryResidence(input.temporaryResidence())
                .hometown(input.hometown())
                .nationality(input.nationality())
                .ethnicity(input.ethnicity())
                .dharmaName(input.dharmaName())
                .ordinationDate(input.ordinationDate())
                .idNumber(input.idNumber())
                .idIssueDate(input.idIssueDate())
                .idIssuePlace(input.idIssuePlace())
                .email(input.email())
                .phoneNumber(input.phoneNumber())
                .notes(input.notes())
                .type(input.type())
                .build();

        // when
        String responseJson = mvc.perform(post("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // then
        PersonDTO actual = objectMapper.readValue(responseJson, PersonDTO.class);

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);

    }
}