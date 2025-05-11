package com.pagoda.api.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pagoda.application.*;
import com.pagoda.core.model.Person;
import com.pagoda.shared.mapper.PersonMapper;
import com.pagoda.shared.model.PersonDTO;
import com.pagoda.shared.testfixtures.PersonDTOFixture;
import com.pagoda.shared.testfixtures.PersonFixture;
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

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
@Import(PersonControllerTest.TestConfig.class)
class PersonControllerTest {

    @MockitoBean
    private CreatePersonCommandHandler createHandler;

    @MockitoBean
    private SearchPersonQuery searchPersonQuery;

    @MockitoBean
    private UpdatePersonCommandHandler updatePersonCommandHandler;

    @MockitoBean
    private GeneratePersonReport generatePersonReport;

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

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

    @SneakyThrows
    @Test
    void shouldSearchForPersonName() {
        // given
        String nameToSearch = "John";
        PersonWithID person1 = new PersonWithID(PersonFixture.builder().build(), UUID.randomUUID());
        PersonWithID person2 = new PersonWithID(PersonFixture.builder().build(), UUID.randomUUID());
        given(searchPersonQuery.findByName(nameToSearch)).willReturn(List.of(person1, person2));

        // when, then
        mvc.perform(get("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("name", nameToSearch))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    List<PersonDTO> actual = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });
                    assertThat(actual).hasSize(2);
                });
    }


    @SneakyThrows
    @Test
    void shouldUpdatePerson() {
        // given
        UUID id = UUID.randomUUID();
        PersonDTO input = PersonDTOFixture.personDTOBuilder()
                .id(id)
                .build();
        Person update = personMapper.toDomain(input);
        given(updatePersonCommandHandler.handle(id, update)).willReturn(new PersonWithID(update, id));

        // when
        mvc.perform(put("/api/persons/{id}", input.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    PersonDTO actual = objectMapper.readValue(result.getResponse().getContentAsString(), PersonDTO.class);
                    assertThat(actual).isEqualTo(input);
                });
    }
}