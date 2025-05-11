package com.pagoda.api.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pagoda.infrastructure.repository.PersonRepository;
import com.pagoda.shared.model.PersonDTO;
import com.pagoda.shared.testfixtures.PersonDTOFixture;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerIT {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PersonRepository repository;


    @BeforeEach
    void cleanDb() {
        repository.deleteAll();
    }


    @SneakyThrows
    @Test
    void shouldCreatePerson() {
        // given
        PersonDTO input = PersonDTOFixture.personDTOBuilder().build();

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
                .ignoringFields("id")
                .isEqualTo(input);

        assertThat(actual.id()).isNotNull();
    }

    @SneakyThrows
    @Test
    void shouldQueryPersonByName() {
        // given
        PersonDTO input = PersonDTOFixture.personDTOBuilder()
                .firstName("ahahfjasdfga").build();

        String createResponse = mvc.perform(post("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        PersonDTO createdPerson = objectMapper.readValue(createResponse, PersonDTO.class);

        // when
        String queryResponse = mvc.perform(get("/api/persons")
                        .param("name", input.firstName())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // then
        List<PersonDTO> foundPersons = objectMapper.readValue(
                queryResponse,
                new TypeReference<>() {
                }
        );

        assertThat(foundPersons)
                .isNotEmpty()
                .hasSize(1)
                .element(0)
                .satisfies(person -> {
                    assertThat(person).usingRecursiveComparison().isEqualTo(createdPerson);
                });
    }

    @SneakyThrows
    @Test
    void shouldReturnEmptyListWhenNoMatchingName() {
        mvc.perform(get("/api/persons")
                        .param("name", "NonExistentName")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @SneakyThrows
    @Test
    void shouldQueryAllPerson() {

        // given
        mvc.perform(post("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PersonDTOFixture.personDTOBuilder().build())))
                .andExpect(status().isOk());
        mvc.perform(post("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(PersonDTOFixture.personDTOBuilder().build())))
                .andExpect(status().isOk());

        // when
        String queryResponse = mvc.perform(get("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        // then
        List<PersonDTO> foundPersons = objectMapper.readValue(
                queryResponse,
                new TypeReference<>() {
                }
        );

        assertThat(foundPersons).hasSize(2);
    }

    @Test
    @DisplayName("Should update person via PUT endpoint")
    void shouldUpdatePerson() throws Exception {
        // given
        var dto = PersonDTOFixture.personDTOBuilder().firstName("Old").build();
        PersonDTO created = createPerson(dto);

        var updatedDTO = PersonDTOFixture.personDTOBuilder()
                .id(created.id())
                .firstName("Updated")
                .build();

        mvc.perform(put("/api/persons/{id}", created.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Updated"));

    }

    @Test
    @DisplayName("Should return 404 if person not found")
    void shouldReturn404() throws Exception {
        // given
        UUID id = UUID.randomUUID();

        var dto = PersonDTOFixture.personDTOBuilder()
                .firstName("NoOne")
                .build();

        // when, then
        mvc.perform(put("/api/persons/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @SneakyThrows
    @Test
    @DisplayName("Should generate PDF report for a single person")
    void shouldGenerateSinglePersonReport() {
        // given
        PersonDTO personDTO = PersonDTOFixture.personDTOBuilder().build();

        // Create a person via API to ensure the person exists
        String response = mvc.perform(post("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personDTO)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        PersonDTO created = objectMapper.readValue(response, PersonDTO.class);

        // when, then
        mvc.perform(get("/api/persons/{id}/report", created.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(created))
                        .accept(MediaType.APPLICATION_PDF))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_PDF))
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=person-" + created.id() + ".pdf"))
                .andExpect(result -> assertThat(result.getResponse().getContentAsByteArray()).isNotEmpty());
    }

    @SneakyThrows
    @Test
    @DisplayName("Should generate PDF report for a list of persons")
    void shouldGeneratePersonListReport() {
        // given
        PersonDTO person1 = PersonDTOFixture.personDTOBuilder().firstName("Linh").build();
        PersonDTO person2 = PersonDTOFixture.personDTOBuilder().firstName("Khoa").build();

        PersonDTO created1 = createPerson(person1);
        PersonDTO created2 = createPerson(person2);

        // when
        mvc.perform(get("/api/persons/report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_PDF))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_PDF))
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=persons-list.pdf"))
                .andExpect(result -> assertThat(result.getResponse().getContentAsByteArray()).isNotEmpty());
    }

    /**
     * Helper to create a person
     */
    private PersonDTO createPerson(PersonDTO dto) throws Exception {
        String response = mvc.perform(post("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        return objectMapper.readValue(response, PersonDTO.class);
    }
}